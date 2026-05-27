package org.example.countryapi;

import org.example.countryapi.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Page<Country> findByRegion(
            String region,
            Pageable pageable
    );
}
