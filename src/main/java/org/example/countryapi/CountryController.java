package org.example.countryapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService){
        this.countryService = countryService;
    }

   @GetMapping("")
    public Page<Country> getAllCountries(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25")int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        return countryService.getAllCountries(pageRequest);
    }

    @GetMapping("/name")
    public Page<Country> getCountriesNameSorted(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25")int size){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("name"));
        return countryService.getCountriesNameSorted(pageRequest);
    }

    @GetMapping("/name/{region}")
    public Page<Country> getRegionNamesSorted(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25")int size,@PathVariable String region){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("name"));
        return countryService.getRegionNameSorted(region,pageRequest);
    }

    @GetMapping("/population")
    public Page<Country> getCountriesPopulationSorted(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25")int size){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("population"));
        return countryService.getCountriesPopulationSorted(pageRequest);
    }

    @GetMapping("/population/{region}")
    public Page<Country> getRegionPopulationSorted(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25")int size,@PathVariable String region){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("population"));
        return countryService.getRegionPopulationSorted(region,pageRequest);
    }


}
