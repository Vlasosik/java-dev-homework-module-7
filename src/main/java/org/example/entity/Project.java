package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Project {
    private long clientId;
    private LocalDate startDate;
    private LocalDate finishDate;
}
