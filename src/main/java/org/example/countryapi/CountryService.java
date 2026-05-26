package org.example.countryapi;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;

@Service
public class CountryService {

    private final RestClient restClient;
    private final CountryRepository repository;

    public CountryService(CountryRepository repository){
        this.repository = repository;
        restClient = RestClient.builder()
                .baseUrl("https://restcountries.com/v3.1")
                .build();
    }

    @EventListener
    public void importCountries(ApplicationStartedEvent event){
        JsonNode countries = restClient.get()
                .uri("/all?fields=name,region,population")
                .retrieve()
                .body(JsonNode.class);

        for(JsonNode country : countries){
            String countryName = country.get("name").get("common").asText();
            String countryRegion = country.get("region").asText();
            long countryPopulation = country.get("population").asLong();

            Country newCountry = new Country(countryName,countryRegion,countryPopulation);
            repository.save(newCountry);
        }
        System.out.println("Jobs done!");
    }

    public List<Country> getAllCountries(){
        return repository.findAll();
    }

    public List<Country> getCountriesNameSorted(){
        return repository.findAll(Sort.by("name"));
    }


}
