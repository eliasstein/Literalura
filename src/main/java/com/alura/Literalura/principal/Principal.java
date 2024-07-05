package com.alura.Literalura.principal;

import com.alura.Literalura.models.Author;
import com.alura.Literalura.models.Book;
import com.alura.Literalura.models.ResultsDTO;
import com.alura.Literalura.repositories.AuthorRepository;
import com.alura.Literalura.repositories.BookRepository;
import com.alura.Literalura.services.ApiRequest;
import com.alura.Literalura.services.DataConverter;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private ApiRequest request = new ApiRequest();
    private final String START_URL = "https://gutendex.com/books/";
    private DataConverter converter = new DataConverter();
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Principal(BookRepository br, AuthorRepository ar){
        this.bookRepository=br;
        this.authorRepository=ar;
    }

    public void showMenu(){
        int option=-1;
        var printMenu = """
                    -----Menu-----
                    1- Buscar libros por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    0- Salir""";
        while(option!=0) {
            System.out.println(printMenu);
            option = input.nextInt();
            input.nextLine();
            System.out.println(option);
            switch(option){
                case 0:break;
                case 1:searchByTitle();break;
                case 2:findBookFromDB();break;
                case 3:findAuthorFromDB();break;
                case 4:findAuthorAlive();break;
                case 5:getBooksFromLanguage();break;
                default:
                    System.out.println("La opcion introducida no es una opcion valida.");
                    input.nextLine();
                    break;
            }//end switch
        }//end while
    }//end show menu
    private void searchByTitle(){
        System.out.print("Ingrese el titulo: ");
        var title=input.nextLine();
        var json = request.GETRequest(START_URL+"?search="+title.replace(" ","+"));
        ResultsDTO results = converter.obtenerDatos(json, ResultsDTO.class);
        if (!results.results().isEmpty()){
            Book book = new Book(results.results().get(0));
            System.out.println("----Se ha encontrado el libro-----");
            System.out.println("Titulo: "+book.getTitle());
            System.out.println("Author: "+book.getAuthor().getName());

            Optional<Author> a=authorRepository.findByNameIgnoreCase(book.getAuthor().getName());
            if (a.isEmpty())
                authorRepository.save(book.getAuthor());
            else{
                System.out.println("El Autor ya existe almacenado en la base de datos.");
                book.setAuthor(authorRepository.findByNameIgnoreCase(book.getAuthor().getName()).get());
            }
            Optional<Book> b=bookRepository.findByTitleIgnoreCase(book.getTitle());
            if(b.isEmpty())
                bookRepository.save(book);
            else
                System.out.println("El libro ya existe almacenado en la base de datos.");
        }//fin if
        else
            System.out.println("Libro no encontrado.");
    }
    private void findBookFromDB(){
        System.out.println("----------");
        for (var b:bookRepository.findAll()){
            System.out.print("Titulo: ");
            System.out.println(b.getTitle());
        }
        System.out.println("----------");
    }
    private void findAuthorFromDB(){
        System.out.println("----------");
        for (var b:authorRepository.findAll()){
            System.out.print("Nombre: ");
            System.out.println(b.getName());
        }
        System.out.println("----------");
    }
    private void findAuthorAlive(){
        System.out.print("Inserte fecha: ");
        int n1=input.nextInt();
        input.nextLine();
        List<Author> authors=authorRepository.aliveAuthorsInTheYear(n1);
        System.out.println("----------");
        for (var b:authors){
            System.out.print("Nombre: ");
            System.out.println(b.getName());
        }
        if (authors.isEmpty()){
            System.out.println("No se encontraron autores vivos en esta fecha");
        }
        System.out.println("----------");
    }
    private void getBooksFromLanguage(){
        System.out.println("Ingrese idioma\n-es\n-en\n-pt\n-etc...");
        var lang=input.nextLine();
        System.out.println("----------");

        List<Book> books=bookRepository.getBooksFromLanguage(lang);
        for (var b:books){
            System.out.print("Titulo: ");
            System.out.println(b.getTitle());
        }
        if (books.isEmpty()){
            System.out.println("Libro no encontrado");
        }
        System.out.println("----------");
    }

}//end class
