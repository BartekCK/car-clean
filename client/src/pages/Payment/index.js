import React, { useEffect, useState } from 'react';
import { Alert, Badge, Button, Col, Container, Row } from 'react-bootstrap';
import { MyInput } from '../SignUp';
import {
  MdAccountCircle,
  MdHome,
  MdLocationCity,
  MdPhone,
} from 'react-icons/md';
import { FaRoute } from 'react-icons/fa';
import { useHistory, useParams } from 'react-router-dom';
import { ORDER_PRODUCT, ORDER_SERVICE } from '../../helpers/orderActions';
import {
  addShipping,
  getUserOrderProductsById,
  getUserServicesById,
  payForOrder,
} from '../../helpers/apiCommands';

export const Payment = () => {
  const [shippingDto, setShippingDto] = useState({
    name: '',
    surname: '',
    street: '',
    additional: '',
    city: '',
    postalCode: '',
    phone: '',
    countryCode: 'PL',
  });

  const [orderServiceDto, setOrderServiceDto] = useState(null);
  const [orderProductsDto, setOrderProductsDto] = useState(null);
  const [error, setError] = useState(null);

  const [settings, setSettings] = useState({
    shipping: false,
    paypal: false,
    shippingPriceDelivery: 15,
    shippingPricePersonal: 20,
  });
  const [payments, setPayments] = useState({
    totalPrice: 0,
    subtotalPrice: 0,
    shippingPrice: 0,
  });

  const updateData = (e) => {
    setShippingDto({ ...shippingDto, [e.target.name]: e.target.value });
  };

  const { type, id } = useParams();
  const history = useHistory();

  useEffect(() => {
    if (type === ORDER_SERVICE) {
      getUserServicesById(id)
        .then((res) => {
          setOrderServiceDto(res.data);
          setPayments({
            totalPrice: res.data.servicesDto.price,
            subtotalPrice: res.data.servicesDto.price,
            shippingPrice: 0,
          });
        })
        .catch((err) => {
          console.log(err);
          setError(err);
        });
    } else if (type === ORDER_PRODUCT) {
      getUserOrderProductsById(id)
        .then((res) => {
          setOrderProductsDto(res.data);
          setPayments({
            totalPrice: res.data.bill,
            subtotalPrice: res.data.bill,
            shippingPrice: 0,
          });
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
          setError(err);
        });
    } else throw new Error('BŁAD');
  }, [id, type]);

  useEffect(() => {
    const temp = payments.subtotalPrice;
    if (settings.shipping && settings.paypal)
      setPayments({ ...payments, shippingPrice: 15, totalPrice: temp + 15 });
    else if (settings.shipping === true && settings.paypal === false)
      setPayments({ ...payments, shippingPrice: 20, totalPrice: temp + 20 });
    else if (settings.shipping === false)
      setPayments({ ...payments, shippingPrice: 0, totalPrice: temp });
  }, [settings]);

  // const setShippingPrice = () => {
  //   console.log(settings.shipping + ' ' + settings.paypal);
  //   if (settings.shipping && settings.paypal)
  //     setPayments({ ...payments, shippingPrice: 15 });
  //   else if (settings.shipping === true && settings.paypal === false)
  //     setPayments({ ...payments, shippingPrice: 20 });
  //   else if (settings.shipping === false)
  //     setPayments({ ...payments, shippingPrice: 0 });
  // };

  const redirectToPayPal = async () => {
    if (type === ORDER_PRODUCT) {
      if (settings.paypal === false && orderProductsDto) {
        history.push('/');
      } else {
        const tempTotalPrice = payments.subtotalPrice + payments.shippingPrice;
        setPayments({ ...payments, totalPrice: tempTotalPrice });

        try {
          if (settings.shipping) {
            await addShipping(orderProductsDto.id, shippingDto);
            const result = await payForOrder({
              ...payments,
              totalPrice: tempTotalPrice,
              orderProductsDto,
              shippingDto,
            });
            window.location.assign(result.data);
          } else {
            const result = await payForOrder({
              ...payments,
              orderProductsDto,
            });
            window.location.assign(result.data);
          }
        } catch (e) {
          console.log(e);
        }
      }
    } else if (type === ORDER_SERVICE) {
      if (settings.paypal === false && orderServiceDto) {
        history.push('/');
      } else {
        try {
          const result = await payForOrder({ ...payments, orderServiceDto });
          window.location.assign(result.data);
        } catch (e) {
          console.log(e);
        }
      }
    }
  };

  if (error) history.push('/');

  if (orderServiceDto || orderProductsDto) {
    return (
      <Container>
        {orderProductsDto && (
          <Row className='shadow p-2 my-2'>
            <Col className='d-flex flex-column align-items-center justify-content-center'>
              <p>Wybierz metodę dostawy:</p>
              <Button
                className='my-2'
                variant='outline-primary'
                onClick={() => {
                  setSettings({ ...settings, shipping: true });
                }}
              >
                Kurier GLS{' '}
                <Badge>
                  {settings.paypal === false
                    ? settings.shippingPricePersonal
                    : settings.shippingPriceDelivery}{' '}
                  zł
                </Badge>
              </Button>
              <Button
                className='my-2'
                variant='outline-success'
                onClick={() => {
                  setSettings({ ...settings, shipping: false });
                }}
              >
                Odbiór w sklepie <Badge>0 zł</Badge>
              </Button>
            </Col>
            {settings.shipping && (
              <ShippingInputs
                shippingDto={shippingDto}
                updateData={updateData}
              />
            )}
          </Row>
        )}
        <Row className='shadow p-2 my-3'>
          <Col className='d-flex flex-column align-items-center justify-content-center'>
            <p>Wybierz formę płatności:</p>
            <Button
              className='my-2'
              variant='outline-primary'
              onClick={() => setSettings({ ...settings, paypal: true })}
            >
              PayPal
            </Button>
            <Button
              className='my-2'
              variant='outline-success'
              onClick={() => setSettings({ ...settings, paypal: false })}
            >
              Platność przy odbiorze
            </Button>
          </Col>
          <Details
            orderServiceDto={orderServiceDto}
            orderProductsDto={orderProductsDto}
            settings={settings}
          />
        </Row>
        <Row className='shadow p-2 my-3'>
          <Col>
            <Alert variant='primary' className='text-center'>
              Razem: {payments.subtotalPrice + payments.shippingPrice} zł
            </Alert>
          </Col>
          <Col className='float-right'>
            <Button
              className='my-2 float-right'
              variant='outline-success'
              onClick={redirectToPayPal}
            >
              Potwierdź
            </Button>
          </Col>
        </Row>
      </Container>
    );
  } else return <h1>Oczekiwanie</h1>;
};

const Details = ({ settings, orderProductsDto, orderServiceDto }) => (
  <Col>
    <p>Aktywne zamówienie:</p>
    <p>
      <Badge>
        Sposób transportu: {settings.shipping ? 'kurier' : 'odbiór osobisty'}
      </Badge>
    </p>
    {settings.shipping && (
      <p>
        <Badge>
          Wartość przesyłki:{' '}
          {settings.paypal === false
            ? settings.shippingPricePersonal
            : settings.shippingPriceDelivery}{' '}
          zł
        </Badge>
      </p>
    )}
    <p>
      <Badge>Płatność: {settings.paypal ? 'paypal' : 'przy odbiorze'}</Badge>
    </p>
    <p>
      {' '}
      <Badge>
        Wartość zamówienia:{' '}
        {orderProductsDto
          ? orderProductsDto.bill
          : orderServiceDto.servicesDto.price}{' '}
        zł
      </Badge>
    </p>

    <p>Szczegóły zamówienia:</p>
    {orderServiceDto ? (
      <OrderServiceDetails orderServiceDto={orderServiceDto} />
    ) : (
      <OrderProductsDetails orderProductsDto={orderProductsDto} />
    )}
  </Col>
);

const OrderServiceDetails = ({ orderServiceDto }) => (
  <p>
    <Badge>
      {orderServiceDto.id +
        '. ' +
        orderServiceDto.servicesDto.name +
        ' ' +
        orderServiceDto.servicesDto.price +
        ' zł'}
    </Badge>
  </p>
);

const OrderProductsDetails = ({ orderProductsDto }) => (
  <Badge>
    <ul>
      {orderProductsDto.prods.map((prod, id) => (
        <li key={id}>
          {prod.name}
          <br /> Cena: {prod.price + ' zł'} <br />
        </li>
      ))}
    </ul>
  </Badge>
);

const ShippingInputs = ({ shippingDto, updateData }) => (
  <Col>
    <MyInput
      value={shippingDto.name}
      name='name'
      SpecialIcon={MdAccountCircle}
      updateCredentials={updateData}
      placeholder={'Imię'}
    />
    <MyInput
      value={shippingDto.surname}
      name='surname'
      SpecialIcon={MdAccountCircle}
      updateCredentials={updateData}
      placeholder={'Nazwisko'}
    />
    <MyInput
      value={shippingDto.street}
      name='street'
      SpecialIcon={FaRoute}
      updateCredentials={updateData}
      placeholder={'Ulica'}
    />
    <MyInput
      value={shippingDto.additional}
      name='additional'
      SpecialIcon={MdHome}
      updateCredentials={updateData}
      placeholder={'Numer domu/lokalu'}
    />
    <MyInput
      value={shippingDto.city}
      name='city'
      SpecialIcon={MdLocationCity}
      updateCredentials={updateData}
      placeholder={'Miasto'}
    />
    <MyInput
      value={shippingDto.postalCode}
      name='postalCode'
      SpecialIcon={MdLocationCity}
      updateCredentials={updateData}
      placeholder={'Kod pocztowy'}
    />
    <MyInput
      value={shippingDto.phone}
      name='phone'
      SpecialIcon={MdPhone}
      updateCredentials={updateData}
      placeholder={'Numer telefonu'}
    />
  </Col>
);
