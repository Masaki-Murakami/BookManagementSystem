package jp.co.saison.training.domain.model.useraggregate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@EqualsAndHashCode
public class User {
    @NonNull
    private final UserId id;
    @NonNull
    private final UserName userName;
    @NonNull
    private final Role role;
}
