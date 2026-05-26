package org.example.countryapi;

import org.aspectj.internal.lang.annotation.ajcDeclareParents;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CountryService {

    private final RestClient restClient;

    public CountryService(){
        restClient = RestClient.builder()
                .baseUrl("https://restcountries.com/v3.1")
                .build();
    }

    public List<Country> findAll(){
        return restClient.get()
                .uri("/all?fields=name,region")
                .retrieve()
                .body();
    }
}
