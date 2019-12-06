package jp.co.saison.training.bookmanagement.presentation.controller;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.application.usecases.borrowbook.BorrowBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.createbook.CreateBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookInputData;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleLoginUser;
import jp.co.saison.training.bookmanagement.presentation.dto.BookDto;
import jp.co.saison.training.bookmanagement.presentation.form.CreateBookForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/books")
@RestController
public class BookController {
    private final Usecase<CreateBookInputData, Book> createBookUsecase;
    private final Usecase<FindBookInputData, Optional<Book>> findBookUsecase;
    private final Usecase<BorrowBookInputData, Void> borrowBookUsecase;

    public BookController(
            Usecase<CreateBookInputData, Book> createBookUsecase,
            Usecase<FindBookInputData, Optional<Book>> findBookUsecase,
            Usecase<BorrowBookInputData, Void> borrowBookUsecase) {
        this.createBookUsecase = createBookUsecase;
        this.findBookUsecase = findBookUsecase;
        this.borrowBookUsecase = borrowBookUsecase;
    }

    @GetMapping("/{bookId}")
    public BookDto findBook(
            @AuthenticationPrincipal SimpleLoginUser simpleLoginUser,
            @PathVariable("bookId") String bookId) {
        FindBookInputData findBookInputData = FindBookInputData.builder()
                .bookId(bookId)
                .build();
        Book book = findBookUsecase.hundle(findBookInputData).orElseThrow();
        return BookDto.fromModel(book);
    }

    @PostMapping("")
    public BookDto createBook(
            @AuthenticationPrincipal SimpleLoginUser simpleLoginUser,
            @RequestBody CreateBookForm createBookForm) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
//        var user = UserDetails.class.cast(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
            @PathVariable("bookId") String bookId) {
        BorrowBookInputData borrowBookInputData = BorrowBookInputData.builder()
                .borrowerId(simpleLoginUser.getUserId())
                .bookId(bookId)
                .build();
        borrowBookUsecase.hundle(borrowBookInputData);
    }
}
