package jp.co.saison.training.bookmanagement.infrastructure.repositories.jparepositories;

import jp.co.saison.training.bookmanagement.domain.model.useraggregate.Name;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.User;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.UserId;
import jp.co.saison.training.bookmanagement.domain.repositories.UserRepository;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.UserJpaGateway;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.jpamodel.UserJpaModel;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {
    private final UserJpaGateway userJpaGateway;

    public JpaUserRepository(UserJpaGateway userJpaGateway) {
        this.userJpaGateway = userJpaGateway;
    }

    @Override
    public UserId generateId() {
        return UserId.of(UUID.randomUUID());
    }

    @Override
    public boolean existByName(Name name) {
        return userJpaGateway.existsByName(name.toString());
    }

    @Override
    public void save(User user) {
        UserJpaModel userJpaModel = UserJpaModel.builder()
                .id(user.getId().toString())
                .name(user.getName().toString())
                .password(user.getPassword().toString())
                .role(user.getRole().toString())
                .build();
        userJpaGateway.save(userJpaModel);
    }
}
