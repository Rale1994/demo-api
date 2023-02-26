package com.example.demo.demoapi.resource;

import com.example.demo.demoapi.dtos.request.NewsRequest;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequest;
import com.example.demo.demoapi.dtos.response.NewsResponseDTO;
import com.example.demo.demoapi.entity.News;
import com.example.demo.demoapi.services.NewsService;
import com.example.demo.demoapi.shared.Constants;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.BASE_URL+"news")
@Api
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public NewsResponseDTO create(@Valid @RequestBody NewsRequest newsRequest) {
        return newsService.create(newsRequest);
       }

    @GetMapping
    public Page<NewsResponseDTO> findAll(@QuerydslPredicate(root = News.class) Predicate predicate, Pageable pageable) {
        return  newsService.findAll(predicate,pageable);

    }

    @PutMapping(path = "/{id}")
    public NewsResponseDTO update(@PathVariable long id, @RequestBody NewsUpdateRequest newsUpdateRequest) {
        return newsService.update(id, newsUpdateRequest);
    }


}
