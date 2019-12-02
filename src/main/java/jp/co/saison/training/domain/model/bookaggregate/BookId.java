package jp.co.saison.training.domain.model.bookaggregate;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class BookId {
    private final UUID bookId;

    static public BookId fromString(String name){
        return of(UUID.fromString(name));
    }

    @Override
    public String toString() {
        return bookId.toString();
    }
}
