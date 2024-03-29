package com.example.demo.demoapi.repositories;

import com.example.demo.demoapi.entity.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    List<Subscription> findByUserId(long userId);
}
