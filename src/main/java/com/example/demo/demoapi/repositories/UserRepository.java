package com.example.demo.demoapi.repositories;
import com.example.demo.demoapi.entity.QUser;
import com.example.demo.demoapi.entity.User;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User>,QuerydslBinderCustomizer<QUser> {

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);


    Optional<User> findByUsername(String username);

    @Override
    default  void customize(QuerydslBindings bindings, QUser root){
        bindings.bind(String.class).first(
                (StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(root.email);
    }
}