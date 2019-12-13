package jp.co.saison.training.bookmanagement.application.usecases.givebackbook;

import jp.co.saison.training.bookmanagement.application.usecases.borrowbook.BorrowBookUsecaseInteractor;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiveBackBookUsecaseInteractorTest {
    @InjectMocks
    GiveBackBookUsecaseInteractor sut;

    @Mock
    BookRepository bookRepository;
    private IllegalArgumentException exception;

    @Test
    void 書籍を返却できる() {
        var book = Book.create();
        var expectBook = Book.create();

        var giveBackBookInputData = GiveBackBookInputData;

        doReturn(Optional.of(book)).when(bookRepository).findById(book.getBookId());
        doNothing().when(bookRepository).update(expectBook);

        sut.hundle(giveBackBookInputData);

        verify(bookRepository, times(1)).update(argThat(book -> book.getStatus()));
    }

    @Test
    void 書籍が存在しない場合例外が発生する() {
        var giveBackBookInputData = GiveBackBookInputData;

        doReturn(Optional.empty()).when(bookRepository).findById(book.getBookId());
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> sut.hundle(giveBackBookInputData)
        );

        assertAll(
                () -> assertEquals("book not found", exception.getMessage()),
                () -> verify(bookRepository, never()).update(any())
        );
    }

    @Test
    void 利用者が書籍のBollowerIdと一致しない場合例外が発生する() {

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> sut.hundle(giveBackBookInputData)
        );

        assertAll(
                () -> assertEquals("book not found", exception.getMessage()),
                () -> verify(bookRepository, never()).update(any())
        );
    }
}