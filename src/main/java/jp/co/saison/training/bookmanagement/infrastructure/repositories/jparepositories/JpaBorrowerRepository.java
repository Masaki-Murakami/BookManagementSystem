package jp.co.saison.training.bookmanagement.infrastructure.repositories.jparepositories;

import jp.co.saison.training.bookmanagement.domain.model.borroweraggregate.Borrower;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.Name;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.Role;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.UserId;
import jp.co.saison.training.bookmanagement.domain.repositories.BorrowerRepository;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.UserJpaGateway;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.jpamodel.UserJpaModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaBorrowerRepository implements BorrowerRepository {
    private final UserJpaGateway userJpaGateway;

    private static Borrower convertUserJpaModelToBorrower(UserJpaModel userJpaModel) {
        return Borrower.builder()
                .id(UserId.fromString(userJpaModel.getId()))
                .name(Name.of(userJpaModel.getName()))
                .role(Role.valueOf(userJpaModel.getRole()))
                .build();
    }

    public JpaBorrowerRepository(UserJpaGateway userJpaGateway) {
        this.userJpaGateway = userJpaGateway;
    }

    @Override
    public Optional<Borrower> findById(UserId userId) {
        return userJpaGateway.findById(userId.toString())
                .map(JpaBorrowerRepository::convertUserJpaModelToBorrower);
    }
}
