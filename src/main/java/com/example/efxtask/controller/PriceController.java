package com.example.efxtask.controller;

import com.example.efxtask.model.PriceDTO;
import com.example.efxtask.model.command.FeedCommand;
import com.example.efxtask.service.FxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriceController {
    private final FxService fxService;

    @PostMapping("/process")
    public ResponseEntity put(@Valid @RequestBody FeedCommand feed) {
        fxService.onMessage(feed);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<PriceDTO>> getAll(Pageable pageable) {
        return new ResponseEntity<>(fxService.getAll(pageable), HttpStatus.OK);
    }
}
