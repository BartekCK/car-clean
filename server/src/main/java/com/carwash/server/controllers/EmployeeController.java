package com.carwash.server.controllers;


import com.carwash.server.dto.GetOrderServiceDto;
import com.carwash.server.services.OrderServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/employees/services")
public class EmployeeController {

    OrderServiceService orderServiceService;

    @GetMapping()
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<GetOrderServiceDto>> getAllServiceByDay(@RequestBody LocalDate localDate) {
        return orderServiceService.getAllServiceByDay(localDate);
    }

    @PutMapping("{idService}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<GetOrderServiceDto> changeServiceStatus(@PathVariable("idService") Long idService, @RequestBody String status) {
        return orderServiceService.changeServiceStatus(idService, status);
    }
}
