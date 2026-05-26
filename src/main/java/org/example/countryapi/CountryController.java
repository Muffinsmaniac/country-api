package org.example.countryapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService){
        this.countryService = countryService;
    }

    @GetMapping("")
    public List<Country> getAllCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/name")
    public List<Country> getCountriesNameSorted(){
        return countryService.getCountriesNameSorted();
    }

    @GetMapping("/name/{region}")
    public List<Country> getRegionNamesSorted(@PathVariable String region){
        return countryService.getRegionNameSorted(region);
    }

    @GetMapping("/population")
    public List<Country> getCountriesPopulationSorted(){
        return countryService.getCountriesPopulationSorted();
    }

    @GetMapping("/population/{region}")
    public List<Country> getRegionPopulationSorted(@PathVariable String region){
        return countryService.getRegionPopulationSorted(region);
    }


}
