package com.example.efxtask.service;

import com.example.efxtask.model.command.FeedCommand;
import com.example.efxtask.model.entity.Price;
import com.example.efxtask.repository.PriceEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;


import java.util.List;


class FxServiceTest {

    private FxService underTest;

    @Mock
    private PriceEntityRepository priceEntityRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        underTest = new FxService(priceEntityRepository, modelMapper);
    }

    @Test
    void testGetPrices() {
        //given
        FeedCommand feedCommand = new FeedCommand("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001" +
                "…" +
                "107, EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002" +
                "…" +
                "108, GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:002" +
                "…" +
                "109, GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:100" +
                "…" +
                "110, EUR/JPY,119.61,119.91,01-06-2020 12:01:02:110");
        //when
        int size = underTest.getPrices(feedCommand).size();
        long idSum = underTest.getPrices(feedCommand).stream().map(Price::getId).mapToLong(Long::shortValue).sum();

        //then
        Assertions.assertEquals(5, size);
        Assertions.assertEquals(540, idSum);
    }

    @Test
    void testAdCommission() {
        //given
        FeedCommand feedCommand = new FeedCommand("106, EUR/USD, 1.1000,1.2,01-06-2020 12:01:01:001…" +
                "107, EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002");
        List<Price> testedPrices = underTest.getPrices(feedCommand).stream().peek(price -> underTest.adCommission(price)).toList();
        //when
        double commissionedAskSum = testedPrices.stream().map(Price::getAsk).mapToDouble(Double::doubleValue).sum();
        //then
        Assertions.assertEquals(121.3423211, commissionedAskSum);
    }

}