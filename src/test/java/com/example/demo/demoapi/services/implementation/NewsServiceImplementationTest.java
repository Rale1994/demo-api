package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.request.NewsRequest;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequest;
import com.example.demo.demoapi.dtos.response.NewsResponseDTO;
import com.example.demo.demoapi.dtos.response.UserNewsResponseDTO;
import com.example.demo.demoapi.entity.News;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.NewsRepository;
import com.example.demo.demoapi.repositories.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.demoapi.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class NewsServiceImplementationTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private NewsServiceImpl newsService;


    @Test
    void tryToGetAllNews() {
        // GIVEN
        List<News> news = getNewsList();
        Page<News> newsPage = new PageImpl<>(news);
        NewsResponseDTO newsResponse = new NewsResponseDTO();

        // WHEN
        when(newsRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(newsPage);
        when(modelMapper.map(any(News.class), ArgumentMatchers.eq(NewsResponseDTO.class))).thenReturn(newsResponse);

        // ACTION
        Page<NewsResponseDTO> response = newsService.findAll(new BooleanBuilder(), PageRequest.of(0, 10));

        // THEN
        List<News> newNews = new ArrayList<>();
        newNews.addAll(newsPage.getContent());
        assertNotNull(response);
        assertEquals(122333L, news.get(2).getId());
        assertEquals(2221167, news.get(0).getUser().getId());

    }

    @Test
    void testCreateNews() {
        // GIVEN
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle("TITLE1");
        newsRequest.setUsersId(2221167);

        User user = new User();
        user.setFirstName("RADOS");
        user.setLastName("GOLUBOVIC");

        UserNewsResponseDTO userNewsResponseDTO = new UserNewsResponseDTO(user);

        News news = new News(newsRequest);

        NewsResponseDTO newsResponseDTO = new NewsResponseDTO(news);

        // WHEN
        when(userRepository.findById(newsRequest.getUsersId())).thenReturn(Optional.of(user));
        when(modelMapper.map(newsRequest, News.class)).thenReturn(news);
        when(newsRepository.save(news)).thenReturn(news);
        when(modelMapper.map(news, NewsResponseDTO.class)).thenReturn(newsResponseDTO);

        // ACTION
        NewsResponseDTO response = newsService.create(newsRequest);

        // THEN
        assertNotNull(response);
    }

    @Test
    void testCreateNewsWithUserWhoDoesNotExist() {
        // GIVEN
        NewsRequest newsRequest = createNewsRequestDTO();

        User user = new User();
        user.setFirstName("RADOS");
        user.setLastName("GOLUBOVIC");

        // WHEN
        when(userRepository.findById(newsRequest.getUsersId())).thenReturn(Optional.empty());

        //THEN
        assertThrows(ApiRequestException.class, () -> newsService.create(newsRequest));
    }

    @Test
    void testCreateUserWithInsertedDate() {
        // GIVEN
        NewsRequest newsRequest = createNewsRequestDTO();
        LocalDateTime dateTime = dateTimeFormatterFromString("2022-12-08 09:42:51");
        newsRequest.setCreatedDate(dateTime);

        User user = new User();
        user.setFirstName("RADOS");
        user.setLastName("GOLUBOVIC");

        UserNewsResponseDTO userNewsResponseDTO = new UserNewsResponseDTO(user);

        News news = new News(newsRequest);

        NewsResponseDTO newsResponseDTO = new NewsResponseDTO(news);

        // WHEN
        when(userRepository.findById(newsRequest.getUsersId())).thenReturn(Optional.of(user));
        when(modelMapper.map(newsRequest, News.class)).thenReturn(news);
        when(newsRepository.save(news)).thenReturn(news);
        when(modelMapper.map(news, NewsResponseDTO.class)).thenReturn(newsResponseDTO);

        // ACTION
        NewsResponseDTO response = newsService.create(newsRequest);

        // THEN
        assertNotNull(response);
    }

    @Test
    void testUpdateNews() {
        // GIVE
        NewsUpdateRequest updateRequestDTO = new NewsUpdateRequest();
        updateRequestDTO.setTitle("TITLE UPDATED");
        News news = new News(updateRequestDTO);
        news.setId(123123124412L);

        NewsResponseDTO newsResponseDTO = new NewsResponseDTO(news);

        // WHEN
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));
        when(newsRepository.save(news)).thenReturn(news);
        when(modelMapper.map(news, NewsResponseDTO.class)).thenReturn(newsResponseDTO);

        // ACTION
        NewsResponseDTO response = newsService.update(news.getId(), updateRequestDTO);

        // THEN
        assertNotNull(response);
    }

    @Test
    void testUpdateUserSetUpdatedDate() {
        // GIVE
        NewsUpdateRequest updateRequestDTO = new NewsUpdateRequest();
        updateRequestDTO.setTitle("TITLE UPDATED");
        LocalDateTime dateTime = dateTimeFormatterFromString("2022-12-08 09:42:51");
        updateRequestDTO.setUpdatedDate(dateTime);
        News news = new News(updateRequestDTO);
        news.setId(123123124412L);

        NewsResponseDTO newsResponseDTO = new NewsResponseDTO(news);

        // WHEN
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));
        when(newsRepository.save(news)).thenReturn(news);
        when(modelMapper.map(news, NewsResponseDTO.class)).thenReturn(newsResponseDTO);

        // ACTION
        NewsResponseDTO response = newsService.update(news.getId(), updateRequestDTO);

        // THEN
        assertNotNull(response);

    }

    @Test
    void testUpdateNewsWhichDoesNotExist() {
        // GIVE
        NewsUpdateRequest updateRequestDTO = new NewsUpdateRequest();
        updateRequestDTO.setTitle("TITLE NOT UPDATED");
        updateRequestDTO.setUpdatedDate(LocalDateTime.now());
        News news = new News(updateRequestDTO);
        news.setId(123123124412L);

        // WHEN
        when(newsRepository.findById(news.getId())).thenReturn(Optional.empty());

        // ACTION
        assertThrows(ApiRequestException.class, () -> newsService.update(news.getId(), updateRequestDTO));

    }


    private List<News> getNewsList() {
        User user = new User();
        user.setId(2221167);
        List<News> news = new ArrayList<>();
        news.add(new News(1233L, "Title1", LocalDateTime.now(), LocalDateTime.now(), user));
        news.add(new News(12233L, "Title2", LocalDateTime.now(), LocalDateTime.now(), user));
        news.add(new News(122333L, "Title3", LocalDateTime.now(), LocalDateTime.now(), user));
        return news;
    }

    private NewsRequest createNewsRequestDTO() {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle("TITLE1");
        newsRequest.setCreatedDate(LocalDateTime.now());
        newsRequest.setUsersId(2221167);
        return newsRequest;
    }

    private LocalDateTime dateTimeFormatterFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }

}