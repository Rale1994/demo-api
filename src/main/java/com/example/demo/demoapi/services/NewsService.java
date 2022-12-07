package com.example.demo.demoapi.services;

import com.example.demo.demoapi.dtos.request.NewsRequestDTO;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequestDTO;
import com.example.demo.demoapi.dtos.response.NewsResponseDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {

    Page<NewsResponseDTO> findAll(Predicate predicate, Pageable pageable);

    NewsResponseDTO create(NewsRequestDTO newsRequestDTO);

    NewsResponseDTO update(long id, NewsUpdateRequestDTO newsUpdateRequestDTO);
}
