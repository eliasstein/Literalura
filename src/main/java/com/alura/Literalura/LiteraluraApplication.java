package com.alura.Literalura;

import com.alura.Literalura.principal.Principal;
import com.alura.Literalura.repositories.AuthorRepository;
import com.alura.Literalura.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private BookRepository br;
	@Autowired
	private AuthorRepository ar;

	public static void main(String[] args){
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(br,ar);
		principal.showMenu();
	}
}
