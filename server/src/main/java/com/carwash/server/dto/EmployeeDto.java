package com.carwash.server.dto;

import com.carwash.server.models.Employee;
import lombok.Value;


@Value
public class EmployeeDto {

    private int id;

    private UserDto userDto;

    private String name;

    private String position;

    public static EmployeeDto build(Employee employee) {
        if (employee != null)
            return new EmployeeDto(employee.getId(), UserDto.build(employee.getUser_id()), employee.getName(), employee.getPosition());
        else
            return null;
    }

}
