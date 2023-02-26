package com.example.demo.demoapi.services;

import com.example.demo.demoapi.dtos.request.NewsRequest;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequest;
import com.example.demo.demoapi.dtos.response.NewsResponseDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {

    Page<NewsResponseDTO> findAll(Predicate predicate, Pageable pageable);

    NewsResponseDTO create(NewsRequest newsRequest);

    NewsResponseDTO update(long id, NewsUpdateRequest newsUpdateRequest);
}
