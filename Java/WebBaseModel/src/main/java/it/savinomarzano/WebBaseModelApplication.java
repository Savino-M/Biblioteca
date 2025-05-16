package it.savinomarzano;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "WebBaseModel", version = "1.0", description = "Template BE e FE per una web app", license = @License(name = "License of API"), contact = @Contact(name = "Marzano Savino")))
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class WebBaseModelApplication {

	@Bean
	ModelMapper modelMapper() {

		return new ModelMapper();

	}

	public static void main(String[] args) {

		SpringApplication.run(WebBaseModelApplication.class, args);

	}

}
