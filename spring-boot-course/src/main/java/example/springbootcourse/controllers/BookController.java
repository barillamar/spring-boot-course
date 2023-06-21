package example.springbootcourse.controllers;

import example.springbootcourse.entities.Book;
import example.springbootcourse.repositories.BookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    // atributos
    private BookRepository bookRepository;

    // constructores

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    /**
     * Buscar todos los libros que hay en base de datos (ArrayList de libros)
     * http://localhost:8081/api/books
     * @return
     */
    @GetMapping("/api/books")
    public List<Book> findAll() {
        // recuperar y devolver los libros de base de datos
        return bookRepository.findAll();
    }

    /**
     * Buscar un solo libro en base de datos según su ID
     *
     * http://localhost:8081/api/books/1
     * http://localhost:8081/api/books/2
     *
     * @param id
     * @return
     */
    @GetMapping("/api/books/{id}")
    @ApiOperation("Buscar un libro por clave primaria id Long")
    public ResponseEntity<Book> findOneById(@ApiParam("Clave primaria tipo Long") @PathVariable Long id) {

        Optional<Book> bookOpt = bookRepository.findById(id);

        // opción 1
        if(bookOpt.isPresent()) {
            return ResponseEntity.ok(bookOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }

        // opción 2
        // return bookOpt.orElse(null);
        // return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound.build())
    }

    /**
     * Crear un nuevo libro en base de datos
     * @param book
     * @param headers
     * @return
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));
        // guardar el libro recibido por parámetro en la base de datos
        if(book.getId() != null) { // quiere decir que existe el "id", por lo tanto, no es una creación
            log.warn("trying to create a book with id");
            System.out.println("trying to create a book with id");
            return ResponseEntity.badRequest().build();
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primaria

    }

    /**
     * Actualizar un libro existente en base de datos
     *
     */
    @PutMapping("api/books")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if(book.getId() == null) { // si no tiene "id", quiere decir, que sí es una creación
            log.warn("Trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }

        // El proceso de actualización
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primaria

    }

    // Borrar un libro en base de datos
    @ApiIgnore
    @DeleteMapping("api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {

        if(!bookRepository.existsById(id)) {
            log.warn("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // Borrar todos los libros
    @ApiIgnore
    @DeleteMapping("api/books")
    public ResponseEntity<Book> deleteAll() {
        log.info("REST Request for delete all books");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}

