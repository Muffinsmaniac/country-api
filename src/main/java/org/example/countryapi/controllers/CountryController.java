package org.example.countryapi.controllers;

import org.example.countryapi.services.CountryService;
import org.example.countryapi.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService){
        this.countryService = countryService;
    }

   @GetMapping("")
    public Page<Country> getAllCountries(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25")int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        return countryService.getAllCountries(pageRequest);
    }

    @GetMapping("/name")
    public Page<Country> getCountriesNameSorted(
            @RequestParam(defaultValue="0")  int page,
            @RequestParam(defaultValue="25") int size,
            @RequestParam(defaultValue="false") boolean descending){

        PageRequest pageRequest = PageRequest.of(page,size, setSorting("name",descending));
        return countryService.getCountriesNameSorted(pageRequest);
    }

    @GetMapping("/name/{region}")
    public Page<Country> getRegionNamesSorted(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="25")int size,
            @RequestParam(defaultValue="false") boolean descending,
            @PathVariable String region)
    {
        PageRequest pageRequest = PageRequest.of(page,size, setSorting("name",descending));
        return countryService.getRegionNameSorted(region,pageRequest);
    }

    @GetMapping("/population")
    public Page<Country> getCountriesPopulationSorted(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="25")int size,
            @RequestParam(defaultValue="false") boolean descending
            ){
        PageRequest pageRequest = PageRequest.of(page,size, setSorting("population",descending));
        return countryService.getCountriesPopulationSorted(pageRequest);
    }

    @GetMapping("/population/{region}")
    public Page<Country> getRegionPopulationSorted(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="25")int size,
            @RequestParam(defaultValue="false") boolean descending,
            @PathVariable String region){
        PageRequest pageRequest = PageRequest.of(page,size, setSorting("population",descending));
        return countryService.getRegionPopulationSorted(region,pageRequest);
    }

    private Sort setSorting(String sortType,boolean descending){
        if(descending){
            return Sort.by(sortType).descending();
        }
        else{
            return Sort.by(sortType);
        }
    }


}
