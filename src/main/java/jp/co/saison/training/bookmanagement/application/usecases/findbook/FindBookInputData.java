package jp.co.saison.training.bookmanagement.application.usecases.findbook;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class FindBookInputData {
    private String bookId;
}
