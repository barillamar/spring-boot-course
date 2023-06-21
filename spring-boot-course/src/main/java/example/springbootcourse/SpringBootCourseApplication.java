package example.springbootcourse;

import example.springbootcourse.entities.Book;
import example.springbootcourse.exercises.sesiones_7_8_9.entities.Laptop;
import example.springbootcourse.exercises.sesiones_7_8_9.repositories.LaptopRepository;
import example.springbootcourse.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class SpringBootCourseApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootCourseApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		// Con control + d, se puede duplicar una línea

		// CRUD
		// crear libro
		Book book1 = new Book(null, "El túnel", "Ernesto Sabato", 50, 15.99, LocalDate.of(1959, 6, 12),false);
		Book book2 = new Book(null, "Ciudades de Papel", "John Green", 276, 21.99, LocalDate.of(2014, 11, 5),true);

		// almacenar libro
		//System.out.println("Número de libros en base de datos: " + repository.findAll().size());

		repository.save(book1);
		repository.save(book2);

		// recuperar todos los libros
		//System.out.println("Número de libros en base de datos: " + repository.findAll().size());

		// borrar un libro
		// repository.deleteById(1L);

		//System.out.println("Número de libros en base de datos: " + repository.findAll().size());

		//////////////////////////////////////////////////////////////////////////////////////////

		LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null, "Dell", "Inspiron", 1199.99, 15, 200);
		Laptop laptop2 = new Laptop(null, "Macintosh", "MacBookPro", 1599.35, 20, 300);

		laptopRepository.save(laptop1);
		laptopRepository.save(laptop2);
	}

}
