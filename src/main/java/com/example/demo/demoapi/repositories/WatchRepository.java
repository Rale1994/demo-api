package com.example.demo.demoapi.repositories;

import com.example.demo.demoapi.entity.Watch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepository extends CrudRepository<Watch, Long> {
}
