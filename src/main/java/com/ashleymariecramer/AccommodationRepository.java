package com.ashleymariecramer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

@RepositoryRestResource
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Accommodation findByCity(@Param("city") String city); //This works to pull up entire Hostel data based on city
    Accommodation findByName(@Param("name") String name); //This works to pull up entire Hostel data based on name
    Accommodation findById(@Param("id") Long id);
    List<Accommodation> findAll();
}
