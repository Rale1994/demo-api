package com.example.demo.demoapi.repositories;
import com.example.demo.demoapi.entity.News;
import com.example.demo.demoapi.entity.QNews;
import com.example.demo.demoapi.entity.User;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Long>, QuerydslPredicateExecutor<News>, QuerydslBinderCustomizer<QNews> {

    @Override
    default void customize(QuerydslBindings bindings, QNews root){
        bindings.bind(String.class).first(
                (StringPath path, String value) -> path.containsIgnoreCase(value));

    }

    News findByUser(User user);
}
