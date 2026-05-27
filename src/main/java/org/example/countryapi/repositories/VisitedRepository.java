package org.example.countryapi.repositories;

import org.example.countryapi.entities.VisitedCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitedRepository  extends JpaRepository<VisitedCountry, Long> {
    Optional<VisitedCountry> findByUserIdAndCountryId(
            long userId,
            long countryId);
    List<VisitedCountry> findByUserId(
            long userId
    );
}
