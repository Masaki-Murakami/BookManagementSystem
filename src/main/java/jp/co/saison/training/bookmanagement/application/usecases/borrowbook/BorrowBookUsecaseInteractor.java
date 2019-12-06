package jp.co.saison.training.bookmanagement.application.usecases.borrowbook;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;
import jp.co.saison.training.bookmanagement.domain.model.borroweraggregate.Borrower;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.UserId;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import jp.co.saison.training.bookmanagement.domain.repositories.BorrowerRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowBookUsecaseInteractor implements Usecase<BorrowBookInputData, Void> {
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public BorrowBookUsecaseInteractor(BookRepository bookRepository, BorrowerRepository borrowerRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public Void hundle(BorrowBookInputData inputData) {
        Borrower borrower = borrowerRepository.findById(UserId.fromString(inputData.getBorrowerId()))
                .orElseThrow(IllegalArgumentException::new);
        Book book = bookRepository.findById(BookId.fromString(inputData.getBookId()))
                .orElseThrow(IllegalArgumentException::new);

        if(borrower.getBorrowBooks().size() > 10){
            throw new IllegalStateException();
        }
        book.lend(borrower.getId());
        bookRepository.update(book);
        return null;
    }
}
