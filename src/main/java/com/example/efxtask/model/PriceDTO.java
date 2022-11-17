package com.example.efxtask.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {
    @CsvBindByPosition(position = 0)
    private long id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private double bid;
    @CsvBindByPosition(position = 3)
    private double ask;
    @CsvBindByPosition(position = 4)
    @CsvDate(value = "dd-MM-yyyy HH:mm:ss:SSS")
    private Timestamp timestamp;
}
