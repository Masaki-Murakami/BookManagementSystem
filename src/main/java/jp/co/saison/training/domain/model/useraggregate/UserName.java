package jp.co.saison.training.domain.model.useraggregate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class UserName {
    private final String userName;

    @Override
    public String toString() {
        return userName;
    }
}
