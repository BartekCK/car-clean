package com.carwash.server.controllers;

import com.carwash.server.services.ServicesService;
import com.carwash.server.dto.ServicesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/services")
public class ServicesController {

    ServicesService servicesService;

    @Autowired
    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping
    public List<ServicesDto> getAllServices() {
        return servicesService.getAllServices();
    }

    @GetMapping("{id}")
    public ServicesDto getService(@PathVariable("id") int id) {
        return servicesService.getService(id);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity noHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
