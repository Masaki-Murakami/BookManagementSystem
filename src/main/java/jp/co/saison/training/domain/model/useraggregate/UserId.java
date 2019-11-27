package jp.co.saison.training.domain.model.useraggregate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class UserId {
    private final UUID userId;

    @Override
    public String toString() {
        return userId.toString();
    }

}
