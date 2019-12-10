package jp.co.saison.training.bookmanagement.domain.model.bookaggregate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class Isbn13 {
    private final String isbn13;

    @Override
    public String toString() {
        return isbn13;
    }
}
