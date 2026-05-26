package org.example.countryapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Country {
    @Id
    private long id;
    private String name;
    private String region;

    public Country(){}

    public Country(String name, String region){
        this.name = name;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public String getRegion(){
        return region;
    }
}
