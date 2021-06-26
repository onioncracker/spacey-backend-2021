package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.services.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/all")
    public List<AuctionDto> getAllAuctions() {
        return auctionService.getAll();
    }

    @GetMapping("/all_type")
    public List<AuctionDto> getAllAuctionsByType(@RequestParam Boolean type) {
        return auctionService.getAllByType(type);
    }

    @GetMapping("/{id}")
    public AuctionDto getAuctionById(@PathVariable Long id) {
        return auctionService.getById(id);
    }

    @PostMapping("/add")
    public HttpStatus addAuction(@RequestBody AuctionDto auctionDto) {
        auctionService.add(auctionDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("/edit/{id}")
    public HttpStatus editAuction(@RequestBody AuctionDto auctionDto,
                                   @PathVariable Long id) {
        AuctionDto auction = auctionService.getById(id);
        auctionService.update(auctionDto, auction.getAuctionId());
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteAuction(@PathVariable Long id) {
        AuctionDto auction = auctionService.getById(id);
        auctionService.remove(auction.getAuctionId());
        return HttpStatus.ACCEPTED;
    }
}
