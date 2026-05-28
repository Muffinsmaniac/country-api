package org.example.countryapi.repositories;

import org.example.countryapi.entities.Country;
import org.example.countryapi.entities.VisitedCountry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitedRepository  extends JpaRepository<VisitedCountry, Long> {
    Optional<VisitedCountry> findByUserIdAndCountryId(
            long userId,
            long countryId);
    Page<VisitedCountry> findByUserId(long userId, Pageable pageable);


}
