package jp.co.saison.training.bookmanagement.application.usecases.givebackbook;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookInputData;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.UserId;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GiveBackBookUsecaseInteractor implements Usecase<GiveBackBookInputData, Void> {
    private final BookRepository bookRepository;

    public GiveBackBookUsecaseInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Void hundle(GiveBackBookInputData giveBackBookInputData) {
        Book book = bookRepository.findById(BookId.fromString(giveBackBookInputData.getBookId()))
                .orElseThrow(() -> new IllegalArgumentException("book not found"));

        book.lend(UserId.fromString(giveBackBookInputData.getBookId()));

        bookRepository.update(book);
        return null;
    }
}
