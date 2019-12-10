package jp.co.saison.training.bookmanagement.application.usecases.createbook;

import lombok.*;

import java.util.Optional;

@Value
@Builder
public class CreateBookInputData {
    private String isbn13;
    @NonNull
    private String title;

    public Optional<String> getIsbn13() {
        return Optional.ofNullable(isbn13);
    }
}
