package jp.co.saison.training.bookmanagement.infrastructure.repositories.inmemoryrepositories;

import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryBookRepository implements BookRepository {
    Map<BookId, Book> bookMap = new HashMap<>();

    @Override
    public BookId generateId() {
        return BookId.of(UUID.randomUUID());
    }

    @Override
    public Optional<Book> findById(BookId bookId) {
        Book book = bookMap.get(bookId);
        return Optional.ofNullable(book);
    }

    @Override
    public void create(Book book) {
        if (bookMap.containsKey(book.getId())) {
            throw new IllegalArgumentException();
        }
        bookMap.put(book.getId(), book);
    }

    @Override
    public void update(Book book) {
        if (!bookMap.containsKey(book.getId())) {
            throw new IllegalArgumentException();
        }
        bookMap.put(book.getId(), book);
    }
}
