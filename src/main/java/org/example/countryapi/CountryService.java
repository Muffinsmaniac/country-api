package org.example.countryapi;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.countryapi.entities.Country;
import org.example.countryapi.entities.User;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
    public void setUp(ApplicationStartedEvent event){
        importCountries();
        createUsers();
        System.out.println("Jobs done!");
    }

    public void importCountries(){
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
    }

    public void createUsers(){
        User user = new User("Hasse");
        User alsoUser = new User("Nisse");
    }

    public Page<Country> getAllCountries(PageRequest pr){
        return repository.findAll(pr);
    }

    public Page<Country> getCountriesNameSorted(PageRequest pr){
        return repository.findAll(pr);
    }

    public Page<Country> getRegionNameSorted(String region, PageRequest pr){
        return repository.findByRegion(region, pr);
    }

    public Page<Country> getCountriesPopulationSorted(PageRequest pr){
        return repository.findAll(pr);
    }

    public Page<Country> getRegionPopulationSorted(String region, PageRequest pr){
        return repository.findByRegion(region, pr);
    }

}
