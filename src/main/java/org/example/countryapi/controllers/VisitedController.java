package org.example.countryapi.controllers;

import org.example.countryapi.entities.Country;
import org.example.countryapi.services.VisitedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visited")
public class VisitedController {

    private final VisitedService visitedService;

    public VisitedController(VisitedService visitedService){
        this.visitedService =visitedService;
    }

    @PostMapping("")
    public void addVisit(@RequestParam long userId, @RequestParam long countryId){
        visitedService.addVisit(userId, countryId);
    }

    @DeleteMapping("")
    public void removeVisit(@RequestParam long userId, @RequestParam long countryId){
        visitedService.removeVisit(userId, countryId);
    }

    @GetMapping("/{user}")
    public List<Long> getVisitedCountryIds(@PathVariable long user){
        return visitedService.getVisitedCountryIds(user);
    }



}
