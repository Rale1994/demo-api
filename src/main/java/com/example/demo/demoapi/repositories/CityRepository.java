package com.example.demo.demoapi.repositories;

import com.example.demo.demoapi.entity.City;
import com.example.demo.demoapi.entity.QCity;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Long>, QuerydslPredicateExecutor<City>, QuerydslBinderCustomizer<QCity> {

    @Override
    default void customize(QuerydslBindings bindings, QCity root) {
        bindings.bind(String.class).first(
                (StringPath path, String value) -> path.containsIgnoreCase(value));

    }

    City findByName(String cityName);
}
