package jp.co.saison.training.bookmanagement.application.usecases.createuser;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.Name;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.Password;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.Role;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.User;
import jp.co.saison.training.bookmanagement.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecaseInteractor implements Usecase<CreateUserInputData, User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUsecaseInteractor(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User hundle(CreateUserInputData inputData) {
        User user = User.builder()
                .id(userRepository.generateId())
                .name(Name.of(inputData.getName()))
                .role(Role.valueOf(inputData.getRole()))
                .password(Password.of(passwordEncoder.encode(inputData.getPassword())))
                .build();
        userRepository.create(user);
        return user;
    }
}
