package jp.co.saison.training.bookmanagement.application.usecases.createuser;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class CreateUserInputData {
    private String name;
    private String role;
    private String password;
}
