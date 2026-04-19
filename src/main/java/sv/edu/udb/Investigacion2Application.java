package sv.edu.udb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Investigacion2Application {
	public static void main(String[] args) {
		SpringApplication.run(Investigacion2Application.class, args);
		System.out.println(" Consola H2: http://localhost:8080/h2-console");
	}
}