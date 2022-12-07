package com.example.demo.demoapi.controller;

import com.example.demo.demoapi.dtos.request.NewsRequestDTO;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequestDTO;
import com.example.demo.demoapi.dtos.response.NewsResponseDTO;
import com.example.demo.demoapi.entity.News;
import com.example.demo.demoapi.services.NewsService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("news")
@Api
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public NewsResponseDTO create(@Valid @RequestBody NewsRequestDTO newsRequestDTO) {
        return newsService.create(newsRequestDTO);
       }

    @GetMapping
    public Page<NewsResponseDTO> findAll(@QuerydslPredicate(root = News.class) Predicate predicate, Pageable pageable) {
        return  newsService.findAll(predicate,pageable);

    }

    @PutMapping(path = "/{id}")
    public NewsResponseDTO update(@PathVariable long id, @RequestBody NewsUpdateRequestDTO newsUpdateRequestDTO) {
        return newsService.update(id, newsUpdateRequestDTO);

    }
}
