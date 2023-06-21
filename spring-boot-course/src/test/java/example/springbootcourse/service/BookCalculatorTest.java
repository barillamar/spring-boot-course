package example.springbootcourse.service;

import example.springbootcourse.entities.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookCalculatorTest {

    @Test
    void calculatePriceTest() {

        // Configuración de pruebas
        Book book = new Book(1L, "El señor de los anillos", "Author", 1000, 24.99, LocalDate.now(), true);
        BookPriceCalculator calculator = new BookPriceCalculator();

        // Se ejecuta el comportamiento a testear
        double price = calculator.calculatePrice(book);
        System.out.println(price);

        // Comprobaciones/Aserciones
        assertTrue(price > 0);
        assertEquals(32.98, price);
    }
}
