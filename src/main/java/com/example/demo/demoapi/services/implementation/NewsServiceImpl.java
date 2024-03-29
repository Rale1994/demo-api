package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.request.NewsRequest;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequest;
import com.example.demo.demoapi.dtos.response.NewsResponseDTO;
import com.example.demo.demoapi.dtos.response.UserNewsResponseDTO;
import com.example.demo.demoapi.entity.News;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.NewsRepository;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.services.NewsService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<NewsResponseDTO> findAll(Predicate predicate, Pageable pageable) {

        Page<News> news = newsRepository.findAll(predicate, pageable);
        List<NewsResponseDTO> streamList = news.stream().map(element -> modelMapper.map(element, NewsResponseDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(streamList, pageable, news.getTotalElements());
//        return newses.stream().map(news -> modelMapper.map(news, NewsResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public NewsResponseDTO create(NewsRequest newsRequest) {
        Optional<User> userOptional = userRepository.findById(newsRequest.getUsersId());
        if (!userOptional.isPresent()) {
            throw new ApiRequestException("User with id: " + newsRequest.getUsersId() + " does not exist!");
        }
        User user = userOptional.get();

        UserNewsResponseDTO userNewsResponseDTO = new UserNewsResponseDTO();
        userNewsResponseDTO.setFirstName(user.getFirstName());
        userNewsResponseDTO.setLastName(user.getLastName());

        News news = modelMapper.map(newsRequest, News.class);

        if (newsRequest.getCreatedDate() == null) {
            news.setCreatedDate(LocalDateTime.now());
        } else {
            news.setCreatedDate(newsRequest.getCreatedDate());
        }
        news.setUser(user);

        News savedNews = newsRepository.save(news);

        return modelMapper.map(savedNews, NewsResponseDTO.class);
    }


    @Override
    public NewsResponseDTO update(long id, NewsUpdateRequest newsUpdateRequest) {
        Optional<News> newsOptional = newsRepository.findById(id);

        if (!newsOptional.isPresent()) {
            throw new ApiRequestException("News with this id: " + id + " does not exist!");
        }

        News news = newsOptional.get();

        if (newsUpdateRequest.getUpdatedDate() == null) {
            news.setUpdatedDate(LocalDateTime.now());
        } else {
            news.setUpdatedDate(newsUpdateRequest.getUpdatedDate());
        }

        news.setTitle(newsUpdateRequest.getTitle());

        News updatedNews = newsRepository.save(news);

        return modelMapper.map(updatedNews, NewsResponseDTO.class);

    }
}
