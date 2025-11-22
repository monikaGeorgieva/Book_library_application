package book_library_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "book_library_app.feign")
@SpringBootApplication
public class BookLibraryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookLibraryAppApplication.class, args);
	}

}
