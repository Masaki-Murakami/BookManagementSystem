package jp.co.saison.training.domain.model.bookaggregate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class Isbn13 {
    private final String isbn13;

    @Override
    public String toString() {
        return isbn13;
    }
}
