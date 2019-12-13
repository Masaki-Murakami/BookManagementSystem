package jp.co.saison.training.bookmanagement.application.usecases.borrowbook;

import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.borroweraggregate.Borrower;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import jp.co.saison.training.bookmanagement.domain.repositories.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowBookUsecaseInteractorTest {
    @InjectMocks
    BorrowBookUsecaseInteractor sut;

    @Mock
    BookRepository bookRepository;

    @Mock
    BorrowerRepository borrowerRepository;

    @Test
    void 書籍を貸出できる() {
        var borrowBookInputData = BorrowBookInputData;
        var book = Book.create();

        doReturn(Optional.of()).when(bookRepository).findById();

        sut.hundle(borrowBookInputData);
    }

    @Test
    void 書籍が存在しない場合例外が発生する() {
        var borrowBookInputData = BorrowBookInputData;

        doReturn(Optional.empty()).when(bookRepository).findById();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> sut.hundle(borrowBookInputData)
        );

        assertAll(
                () -> assertEquals("book not found", exception.getMessage()),
                () -> verify(bookRepository, never()).update(any())
        );
    }

    @Test
    void 利用者が存在しない場合例外が発生する() {
        var borrowBookInputData = BorrowBookInputData;
        var book = Book.create();

        doReturn(Optional.of(book)).when(bookRepository).findById(book.getId());
        doReturn(Optional.empty()).when(borrowerRepository).findById();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> sut.hundle(borrowBookInputData)
        );

        assertAll(
                () -> assertEquals("book not found", exception.getMessage()),
                () -> verify(bookRepository, never()).update(any())
        );
    }

    @Test
    void 利用者が書籍を10冊以上借りていた場合例外が発生する() {
        var borrowBookInputData = BorrowBookInputData;
        var book = Book.create();
        var borrower = Borrower.builder()
                .id()
                .name()
                .role()
                .borrowBooks()
                .build();

        doReturn(Optional.of(book)).when(bookRepository).findById(book.getId());
        doReturn(Optional.of(borrower)).when(borrowerRepository).findById(borrower.getId);

        var exception = assertThrows(
                IllegalStateException.class,
                () -> sut.hundle(borrowBookInputData)
        );

        assertAll(
                () -> assertEquals("book not found", exception.getMessage()),
                () -> verify(bookRepository, never()).update(any())
        );
    }
}