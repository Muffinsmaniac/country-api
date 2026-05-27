package org.example.countryapi.services;

import org.example.countryapi.entities.Country;
import org.example.countryapi.entities.User;
import org.example.countryapi.entities.VisitedCountry;
import org.example.countryapi.repositories.CountryRepository;
import org.example.countryapi.repositories.UserRepository;
import org.example.countryapi.repositories.VisitedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitedService {

    private final VisitedRepository visitedRepository;
    private final UserRepository userRepository;

    private final CountryRepository countryRepository;

    public VisitedService(VisitedRepository visitedRepository,
                          UserRepository userRepository,
                          CountryRepository countryRepository) {
        this.visitedRepository = visitedRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }

    public void addVisit(long userId, long countryId) {
        User user = userRepository.findById(userId).orElseThrow();
        Country country = countryRepository.findById(countryId).orElseThrow();
        VisitedCountry visit = new VisitedCountry();
        visit.setUser(user);
        visit.setCountry(country);
        visitedRepository.save(visit);
    }

    public void removeVisit(long userId, long countryId) {
        VisitedCountry visit = visitedRepository.findByUserIdAndCountryId(userId,countryId).orElseThrow();
        visitedRepository.delete(visit);
    }

    public List<Long> getVisitedCountryIds(long userId){
        List<Long> countryIds = new ArrayList<>();
        List<VisitedCountry> entries = visitedRepository.findByUserId(userId);
        for(VisitedCountry entry : entries){
            countryIds.add(entry.getCountry().getId());
        }
        return countryIds;
    }
}
