package jp.co.saison.training.bookmanagement.presentation.form;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateBookForm {
    @Size(min = 13, max = 13)
    @Pattern(regexp = "\\d+")
    private String isbn13;

    @Size(max = 128)
    private String title;
}
