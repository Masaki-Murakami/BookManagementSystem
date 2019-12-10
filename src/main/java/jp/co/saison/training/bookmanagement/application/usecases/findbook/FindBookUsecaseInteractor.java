package jp.co.saison.training.bookmanagement.application.usecases.findbook;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindBookUsecaseInteractor implements Usecase<FindBookInputData, Optional<Book>> {
    private final BookRepository bookRepository;

    public FindBookUsecaseInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> hundle(FindBookInputData findBookInputData) {
        return bookRepository.findById(BookId.fromString(findBookInputData.getBookId()));
    }
}