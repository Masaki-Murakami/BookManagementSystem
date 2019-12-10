package jp.co.saison.training.bookmanagement.presentation.controller;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.application.usecases.borrowbook.BorrowBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.createbook.CreateBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.givebackbook.GiveBackBookInputData;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleLoginUser;
import jp.co.saison.training.bookmanagement.presentation.dto.BookDto;
import jp.co.saison.training.bookmanagement.presentation.form.CreateBookForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Optional;

@RequestMapping("/api/books")
@RestController
public class BookController {
    private final Usecase<CreateBookInputData, Book> createBookUsecase;
    private final Usecase<FindBookInputData, Optional<Book>> findBookUsecase;
    private final Usecase<BorrowBookInputData, Void> borrowBookUsecase;
    private final Usecase<GiveBackBookInputData, Book> giveBackBookUsecase;

    public BookController(
            Usecase<CreateBookInputData, Book> createBookUsecase,
            Usecase<FindBookInputData, Optional<Book>> findBookUsecase,
            Usecase<BorrowBookInputData, Void> borrowBookUsecase, Usecase<GiveBackBookInputData, Book> giveBackBookUsecase) {
        this.createBookUsecase = createBookUsecase;
        this.findBookUsecase = findBookUsecase;
        this.borrowBookUsecase = borrowBookUsecase;
        this.giveBackBookUsecase = giveBackBookUsecase;
    }

    @GetMapping("/{bookId}")
    public BookDto findBook(
            @AuthenticationPrincipal SimpleLoginUser simpleLoginUser,
            @PathVariable("bookId") @Valid @Size(min = 36, max = 36) String bookId) {
        FindBookInputData findBookInputData = FindBookInputData.builder()
                .bookId(bookId)
                .build();
        Book book = findBookUsecase.hundle(findBookInputData).orElseThrow();
        return BookDto.fromModel(book);
    }

    @PostMapping("")
    public BookDto createBook(
            @AuthenticationPrincipal SimpleLoginUser simpleLoginUser,
            @RequestBody @Valid CreateBookForm createBookForm) {
        CreateBookInputData createBookInputData = CreateBookInputData.builder()
                .isbn13(createBookForm.getIsbn13())
                .title(createBookForm.getTitle())
                .build();
        Book book = createBookUsecase.hundle(createBookInputData);
        return BookDto.fromModel(book);
    }

    @PutMapping("/{bookId}/borrow")
    public void borrowBook(
            @AuthenticationPrincipal SimpleLoginUser simpleLoginUser,
            @PathVariable("bookId") @Valid @Size(min = 36, max = 36) String bookId) {
        BorrowBookInputData borrowBookInputData = BorrowBookInputData.builder()
                .borrowerId(simpleLoginUser.getUserId())
                .bookId(bookId)
                .build();
        borrowBookUsecase.hundle(borrowBookInputData);
    }

    @PutMapping("/{bookId}/giveback")
    public void giveBackBook(
            @AuthenticationPrincipal SimpleLoginUser simpleLoginUser,
            @PathVariable("bookId") @Valid @Size(min = 36, max = 36) String bookId) {
        GiveBackBookInputData giveBackBookInputData = GiveBackBookInputData.builder()
                .borrowerId(simpleLoginUser.getUserId())
                .bookId(bookId)
                .build();
        giveBackBookUsecase.hundle(giveBackBookInputData);
    }
}
