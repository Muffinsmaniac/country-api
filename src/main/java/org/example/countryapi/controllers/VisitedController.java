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

    @GetMapping("/{userId}")
    public Page<Country> getVisitedCountries(@PathVariable long userId,
                                             @RequestParam(defaultValue="0")  int page,
                                             @RequestParam(defaultValue="25") int size,
                                             @RequestParam(defaultValue="false") boolean descending){
        PageRequest pageRequest = PageRequest.of(page,size);
        return visitedService.getVisitedCountries(userId, pageRequest );
    }

    @GetMapping("/{user}/{country}")
    public String getVisitedCountryNote(@PathVariable long user,@PathVariable long country){
        return visitedService.getVisitedCountryNote(user,country);
    }

    @PatchMapping("")
    public void addVisitNote(@RequestParam long userId,
                             @RequestParam long countryId,
                             @RequestParam String note){
        visitedService.addNote(userId, countryId, note);
    }


}
