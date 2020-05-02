package com.carwash.server.services;

import com.carwash.server.dto.payments.PaymentDto;
import com.carwash.server.dto.payments.ShippingDto;
import com.carwash.server.dto.payments.TypeOrderPay;
import com.carwash.server.models.Order;
import com.carwash.server.models.OrderService;
import com.carwash.server.models.enums.OrderType;
import com.carwash.server.models.enums.PaidStatus;
import com.carwash.server.models.enums.PaymentMethod;
import com.carwash.server.repositories.OrderProductsRepo;
import com.carwash.server.repositories.OrderServiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {

    private final APIContext apiContext;
    private final OrderProductsRepo orderProductsRepo;
    private final OrderServiceRepository orderServiceRepository;

    @Value("${domain.full.name}")
    private String domain;

    @Autowired
    public PaymentService(APIContext apiContext, OrderProductsRepo orderProductsRepo, OrderServiceRepository orderServiceRepository) {
        this.apiContext = apiContext;
        this.orderProductsRepo = orderProductsRepo;
        this.orderServiceRepository = orderServiceRepository;
    }

    public ResponseEntity<String> createPayment(String username, PaymentDto paymentDto) throws PayPalRESTException, JsonProcessingException {


        Transaction transaction = createTransaction(username, paymentDto);

        Payer payer = new Payer();
        payer.setPaymentMethod(PaymentMethod.paypal.name());

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(domain + "/api/v1/pay/false");
        redirectUrls.setReturnUrl(domain + "/api/v1/pay/true");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(Arrays.asList(transaction));
        payment.setRedirectUrls(redirectUrls);

        Payment readyPayment = payment.create(apiContext);
        System.out.println(readyPayment);

        for (Links link : readyPayment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return ResponseEntity.ok(link.getHref());
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<String> executePayment(String paymentId, String payerId) throws PayPalRESTException, JsonProcessingException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        Payment result = payment.execute(apiContext, paymentExecute);

        if (result.getState().equals("approved")) {
            String descriptionResultString = result.getTransactions().get(0).getDescription();
            ObjectMapper objectMapper = new ObjectMapper();

            TypeOrderPay typeOrderPay = objectMapper.readValue(descriptionResultString, TypeOrderPay.class);
            //PLACE TO CHANGE ORDER STATUS
            if (typeOrderPay.getOrderType() == OrderType.ORDER_SERVICE) {
                OrderService orderService = orderServiceRepository.findById(typeOrderPay.getId()).orElseThrow(() -> new NullPointerException("Wystąpił nieoczekiwany błąd skontaktuj się z administratorem podająć kod " + paymentId));
                orderService.setPaidStatus(PaidStatus.PAID);
                orderServiceRepository.save(orderService);
            } else if (typeOrderPay.getOrderType() == OrderType.ORDER_PRODUCT) {
                Order orderProduct = orderProductsRepo.findById(Math.toIntExact(typeOrderPay.getId())).orElseThrow(() -> new NullPointerException("Wystąpił nieoczekiwany błąd skontaktuj się z administratorem podająć kod " + paymentId));
                orderProduct.setPaid_status(PaidStatus.PAID);
                orderProductsRepo.save(orderProduct);
            } else
                throw new NullPointerException("Wystąpił nieoczekiwany błąd skontaktuj się z administratorem podająć kod " + paymentId);

            return ResponseEntity.ok("Płatność została zrealizowana");
        }

        return ResponseEntity.badRequest().body("Wystąpił bład, spróbuj ponownie później");
    }

    private Transaction createTransaction(String username, PaymentDto paymentDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Transaction transaction = new Transaction();
        TypeOrderPay typeOrderPay;
        if (paymentDto.getOrderServiceDto() != null) {
            typeOrderPay = new TypeOrderPay(OrderType.ORDER_SERVICE, paymentDto.getOrderServiceDto().getId());
        } else if (paymentDto.getOrderProductsDto() != null) {
            typeOrderPay = new TypeOrderPay(OrderType.ORDER_PRODUCT, (long) paymentDto.getOrderProductsDto().getId());
        } else
            throw new NullPointerException("Zamówienie nie zostało wybrane");

        transaction.setDescription(objectMapper.writeValueAsString(typeOrderPay));


        transaction.setAmount(createAmount(paymentDto));
        transaction.setItemList(
                createItemList(username, paymentDto)
        );
        return transaction;
    }

    private ItemList createItemList(String username, PaymentDto paymentDto) {

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        if (paymentDto.getOrderProductsDto() != null) {
            Order order = orderProductsRepo.findByUserUsernameAndId(username, paymentDto.getOrderProductsDto().getId());
            order.getOrderProducts()
                    .forEach(
                            product ->
                                    items.add(
                                            new Item(
                                                    product.getName(),
                                                    "1",
                                                    String.valueOf(product.getPrice()),
                                                    "PLN"))
                    );
        } else if (paymentDto.getOrderServiceDto() != null) {
            OrderService orderService = orderServiceRepository.findByIdAndAndUserUsername(
                    paymentDto.getOrderServiceDto().getId(), username).orElseThrow(
                    () -> new RuntimeException("Użytkownik " + username + " nie ma dostępu do danego zamówienia "));
            items.add(
                    new Item(
                            orderService.getServiceid().getName(),
                            "1",
                            String.valueOf(orderService.getServiceid().getPrice()),
                            "PLN")
            );
        } else {
            throw new NullPointerException("Zamówienie nie zostało wybrane");
        }
        itemList.setItems(items);

        if (paymentDto.getShippingDto() != null) {
            itemList.setShippingAddress(
                    createShippingAddress(paymentDto)
            );
        }

        return itemList;
    }

    private ShippingAddress createShippingAddress(PaymentDto paymentDto) {
        ShippingDto shippingDto = paymentDto.getShippingDto();
        ShippingAddress shippingAddress = new ShippingAddress(shippingDto.getName() + " " + shippingDto.getSurname());
        shippingAddress.setLine1(shippingDto.getStreet());
        shippingAddress.setLine2(shippingDto.getAdditional());
        shippingAddress.setCity(shippingDto.getCity());
        shippingAddress.setPostalCode(shippingDto.getPostalCode());
        shippingAddress.setPhone(shippingDto.getPhone());
        shippingAddress.setCountryCode(shippingDto.getCountryCode());
        return shippingAddress;
    }

    private Amount createAmount(PaymentDto paymentDto) {
        Amount amount = new Amount("PLN", paymentDto.getTotalPrice());//TOTAL WITH SHIPMENT

        Details details = new Details();
        details.setSubtotal(paymentDto.getSubtotalPrice());
        details.setShipping(paymentDto.getShippingPrice());

        amount.setDetails(details);

        return amount;
    }


}
