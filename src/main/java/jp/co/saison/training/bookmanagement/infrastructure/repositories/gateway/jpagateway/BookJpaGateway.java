package jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway;

import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.jpamodel.BookJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BookJpaGateway extends JpaRepository<BookJpaModel, String> {
}
