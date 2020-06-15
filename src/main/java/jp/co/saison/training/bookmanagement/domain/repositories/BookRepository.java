package jp.co.saison.training.bookmanagement.domain.repositories;

import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;

import java.util.Optional;

public interface BookRepository {
    BookId generateId();

    Optional<Book> findById(BookId bookId);

    void save(Book book);
}
