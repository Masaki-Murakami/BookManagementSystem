package jp.co.saison.training.bookmanagement.presentation.controller;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.application.usecases.createuser.CreateUserInputData;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.Role;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.User;
import jp.co.saison.training.bookmanagement.presentation.dto.UserDto;
import jp.co.saison.training.bookmanagement.presentation.form.CreateUserForm;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final Usecase<CreateUserInputData, User> createUserUsecase;

    public UserController(Usecase<CreateUserInputData, User> createUserUsecase) {
        this.createUserUsecase = createUserUsecase;
    }

    @PostMapping("")
    @Secured("ROLE_Administrator")
    public UserDto createUser(@RequestBody @Valid CreateUserForm createUserForm) {
        CreateUserInputData createUserInputData = CreateUserInputData.builder()
                .name(createUserForm.getName())
                .password(createUserForm.getPassword())
                .role(createUserForm.getRole())
                .build();
        User user = createUserUsecase.hundle(createUserInputData);
        return UserDto.fromModel(user);
    }
}
