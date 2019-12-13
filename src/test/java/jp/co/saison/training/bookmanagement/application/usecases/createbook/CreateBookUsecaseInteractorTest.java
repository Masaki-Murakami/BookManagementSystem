package jp.co.saison.training.bookmanagement.application.usecases.createbook;

import jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookUsecaseInteractor;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Isbn13;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Title;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBookUsecaseInteractorTest {
    @InjectMocks
    CreateBookUsecaseInteractor sut;

    @Mock
    BookRepository bookRepository;

    @Test
    void 書籍を登録できる(){
        var createBookInputData = CreateBookInputData.builder()
                .isbn13(Isbn13.of(""))
                .title(Title.of(""))
                .build():
        var expectBook = Book.create()

        doNothing().when(bookRepository).create(expectBook);

        sut.hundle(createBookInputData);

        verify(bookRepository, times(1)).create(expectBook);
    }
}