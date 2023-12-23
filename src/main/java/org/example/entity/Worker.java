package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Worker {
    private String name;
    private LocalDate birthday;
    private String level;
    private BigDecimal salary;
}
