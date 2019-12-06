package jp.co.saison.training.bookmanagement.application.usecases.createbook;

import jp.co.saison.training.bookmanagement.application.usecases.Usecase;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Isbn13;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Title;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBookUsecaseInteractor implements Usecase<CreateBookInputData, Book> {
    private final BookRepository bookRepository;

    public CreateBookUsecaseInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book hundle(CreateBookInputData createBookInputData) {
        Book book = Book.create(
                bookRepository.generateId(),
                Isbn13.of(createBookInputData.getIsbn13().orElse(null)),
                Title.of(createBookInputData.getTitle())
        );
        bookRepository.create(book);
        return book;
    }
}
