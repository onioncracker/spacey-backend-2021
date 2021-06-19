package com.heroku.spacey.controllers;

import com.heroku.spacey.services.DecreaseAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decrease/auction")
public class DecreaseAuctionController {
    private final DecreaseAuctionService decreaseAuctionService;


}
