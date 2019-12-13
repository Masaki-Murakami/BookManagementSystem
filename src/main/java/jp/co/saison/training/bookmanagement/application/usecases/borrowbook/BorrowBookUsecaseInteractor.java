package jp.co.saison.training.bookmanagement.application.usecases.borrowbook;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;
import jp.co.saison.training.bookmanagement.domain.model.borroweraggregate.Borrower;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.UserId;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import jp.co.saison.training.bookmanagement.domain.repositories.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowBookUsecaseInteractor implements Usecase<BorrowBookInputData, Void> {
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public BorrowBookUsecaseInteractor(BookRepository bookRepository, BorrowerRepository borrowerRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Void hundle(BorrowBookInputData inputData) {
        Book book = bookRepository.findById(BookId.fromString(inputData.getBookId()))
                .orElseThrow(() -> new IllegalArgumentException("book not found"));
        Borrower borrower = borrowerRepository.findById(UserId.fromString(inputData.getBorrowerId()))
                .orElseThrow(() -> new IllegalArgumentException("borrower not found"));

        if(borrower.getBorrowBooks().size() >= 5){
            throw new IllegalStateException("borrowed books must be up to 5");
        }
        book.lend(borrower.getId());
        bookRepository.update(book);
        return null;
    }
}
