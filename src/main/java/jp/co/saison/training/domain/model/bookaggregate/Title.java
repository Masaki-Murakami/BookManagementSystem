package jp.co.saison.training.domain.model.bookaggregate;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class Title {
    private final String title;

    @Override
    public String toString() {
        return title;
    }
}
