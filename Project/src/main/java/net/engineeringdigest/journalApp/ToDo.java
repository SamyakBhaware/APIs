package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class ToDo {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ToDo.class, args);
		System.out.println(run.getEnvironment());
	}

	@Bean
	public TransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return new RestTemplate();
	}
}
