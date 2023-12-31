package example.springbootcourse.controller;

import example.springbootcourse.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Comprobar saludo desde controlador Spring Test")
    @Test
    void hello() {
        ResponseEntity<String> response =
                testRestTemplate.getForEntity("/hello", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("What's up bro!", response.getBody());
    }


    @Test
    void findAll() {
        ResponseEntity<Book[]> response =
                testRestTemplate.getForEntity("/api/books", Book[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Book> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    @Test
    void findOneById() {
        ResponseEntity<Book> response =
                testRestTemplate.getForEntity("/api/books/1", Book.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create() {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                                    "title": "Libro creado desde Spring Test",
                                    "author": "Yuval Noah",
                                    "pages": 650,
                                       "price": 19.99,
                                    "releaseDate": "2019-12-01",
                                    "online": false
                                
                }
                """;

        HttpEntity<String> resquest = new HttpEntity<>(json, headers);

        ResponseEntity<Book> response = testRestTemplate.exchange("/api/books", HttpMethod.POST, resquest, Book.class);

        Book result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("Libro creado desde Spring Test", result.getTitle());
    }
}
