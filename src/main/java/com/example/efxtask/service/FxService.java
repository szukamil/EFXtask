package com.example.efxtask.service;

import com.example.efxtask.model.PriceDTO;
import com.example.efxtask.model.command.FeedCommand;
import com.example.efxtask.model.entity.Price;
import com.example.efxtask.repository.PriceEntityRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FxService {
    private final PriceEntityRepository priceEntityRepository;
    private final ModelMapper modelMapper;

    public void onMessage(FeedCommand feed) {
        List<Price> prices = getPrices(feed);
        priceEntityRepository.saveAll(prices);
    }

    public List<Price> getPrices(FeedCommand feed) {
        String modifiedFeed = String.join("\n", feed.getFeed().split("…\n*"));
        return new CsvToBeanBuilder<PriceDTO>(new CSVReader(new StringReader(modifiedFeed)))
                .withType(PriceDTO.class).build().parse()
                .stream()
                .map(price -> modelMapper.map(price, Price.class))
                .peek(this::adCommission)
                .toList();
    }

    public void adCommission(Price price) {
        double commission = 0.001;
        price.setBid(price.getBid() - (price.getBid() * commission));
        price.setAsk(price.getAsk() + (price.getAsk() * commission));
    }

    public Page<PriceDTO> getAll(Pageable pageable) {
        return priceEntityRepository.findAll(pageable).map(price -> modelMapper.map(price, PriceDTO.class));
    }
}
