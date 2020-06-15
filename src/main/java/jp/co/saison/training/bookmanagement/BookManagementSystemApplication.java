package jp.co.saison.training.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.jpamodel")
@EnableJpaRepositories("jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway")
@EnableTransactionManagement
//@ComponentScan("jp.co.saison.training.bookmanagement")
public class BookManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookManagementSystemApplication.class, args);
    }
}
