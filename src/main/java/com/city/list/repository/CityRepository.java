package com.city.list.repository;

import com.city.list.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    String QUERY = """
            SELECT * FROM city WHERE name LIKE CONCAT('%',:name,'%')
            """;

    Page<City> findAll(Pageable pageable);

    @Query(value = QUERY, nativeQuery = true)
    Page<City> searchCityByName(@Param("name") String name, Pageable pageable);
}
