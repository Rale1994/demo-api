package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.request.NewsRequestDTO;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequestDTO;
import com.example.demo.demoapi.dtos.response.NewsResponseDTO;
import com.example.demo.demoapi.dtos.response.UserNewsResponseDTO;
import com.example.demo.demoapi.entity.News;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.NewsRepository;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.services.NewsService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NewsServiceImplementation implements NewsService {

    private NewsRepository newsRepository;
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Autowired
    public NewsServiceImplementation(NewsRepository newsRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<NewsResponseDTO> findAll(Predicate predicate, Pageable pageable) {

        Page<News> news = newsRepository.findAll(predicate, pageable);
        List<NewsResponseDTO> streamList = news.stream().map(element -> modelMapper.map(element, NewsResponseDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(streamList, pageable, news.getTotalElements());
//        return newses.stream().map(news -> modelMapper.map(news, NewsResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public NewsResponseDTO create(NewsRequestDTO newsRequestDTO) {
        Optional<User> userOptional = userRepository.findById(newsRequestDTO.getUsers_id());
        if (!userOptional.isPresent()) {
            throw new ApiRequestException("User with id: " + newsRequestDTO.getUsers_id() + " does not exist!");
        }
        User user = userOptional.get();

        UserNewsResponseDTO userNewsResponseDTO = new UserNewsResponseDTO();
        userNewsResponseDTO.setFirstName(user.getFirstName());
        userNewsResponseDTO.setLastName(user.getLastName());

        News news = modelMapper.map(newsRequestDTO, News.class);

        if (newsRequestDTO.getDate() == null) {
            news.setDate(LocalDateTime.now());
        } else {
//            LocalDateTime dateTime = Utils.parseStringToDate(newsRequestDTO.getDate());
            news.setDate(newsRequestDTO.getDate());
        }
        news.setUser(user);

        News savedNews = newsRepository.save(news);

        NewsResponseDTO returnValue = modelMapper.map(savedNews, NewsResponseDTO.class);
        return returnValue;
    }


    @Override
    public NewsResponseDTO update(long id, NewsUpdateRequestDTO newsUpdateRequestDTO) {
        Optional<News> newsOptional = newsRepository.findById(id);
        News news = newsOptional.get();

        if (news == null) {
            throw new ApiRequestException("News with this id: " + id + " does not exist!");
        }
        if (newsUpdateRequestDTO.getUpdatedDate() == null) {
            news.setUpdatedDate(LocalDateTime.now());
        } else {
            news.setUpdatedDate(newsUpdateRequestDTO.getUpdatedDate());
            //news.setUpdatedDate(Utils.parseStringToDate(newsUpdateRequestDTO.getUpdatedDate()));
        }

        news.setTitle(newsUpdateRequestDTO.getTitle());

        News updatedNews = newsRepository.save(news);

        NewsResponseDTO returnValue = modelMapper.map(updatedNews, NewsResponseDTO.class);

        return returnValue;
    }
}
