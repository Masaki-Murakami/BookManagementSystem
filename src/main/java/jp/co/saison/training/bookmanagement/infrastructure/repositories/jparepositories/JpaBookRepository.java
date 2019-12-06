package jp.co.saison.training.bookmanagement.infrastructure.repositories.jparepositories;

import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Isbn13;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Title;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.BookJpaGateway;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.UserJpaGateway;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.jpamodel.BookJpaModel;
import jp.co.saison.training.bookmanagement.infrastructure.repositories.gateway.jpagateway.jpamodel.UserJpaModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JpaBookRepository implements BookRepository {
    private final BookJpaGateway bookJpaGateway;
    private final UserJpaGateway userJpaGateway;

    public JpaBookRepository(BookJpaGateway bookJpaGateway, UserJpaGateway userJpaGateway) {
        this.bookJpaGateway = bookJpaGateway;
        this.userJpaGateway = userJpaGateway;
    }

    //この部分はfactoryで実装するべき
    public static Book convertBookJpaModelToBook(BookJpaModel bookJpaModel) {
        return Book.create(
                BookId.fromString(bookJpaModel.getId()),
                bookJpaModel.getIsbn13() == null ? null : Isbn13.of(bookJpaModel.getIsbn13()),
                Title.of(bookJpaModel.getTitle())
        );
    }

    @Override
    public BookId generateId() {
        return BookId.of(UUID.randomUUID());
    }

    @Override
    public Optional<Book> findById(BookId bookId) {
        return bookJpaGateway.findById(bookId.toString())
                .map(bookJpaModel -> convertBookJpaModelToBook(bookJpaModel));
    }

    @Override
    public void create(Book book) {
        if(bookJpaGateway.existsById(book.getId().toString())){
            throw new IllegalArgumentException();
        }
        if(book.getBorrowerId().isPresent()){
            throw new IllegalArgumentException();
        }

        BookJpaModel bookJpaModel = BookJpaModel.builder()
                .id(book.getId().toString())
                .title(book.getTitle().toString())
                .isbn13(book.getIsbn13().map(Isbn13::toString).orElseThrow())
                .status(book.getStatus().name())
                .build();

        bookJpaGateway.save(bookJpaModel);
    }

    @Override
    public void update(Book book) {
        BookJpaModel bookJpaModel = bookJpaGateway.findById(book.getId().toString())
                .orElseThrow(() -> new IllegalStateException("BookId not found"));
        bookJpaModel.setId(book.getId().toString());
        bookJpaModel.setIsbn13(book.getIsbn13().toString());
        bookJpaModel.setStatus(book.getStatus().name());
        UserJpaModel borrower = userJpaGateway.findById(book.getBorrowerId().toString())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        bookJpaModel.setBorrower(borrower);

        bookJpaGateway.save(bookJpaModel);
    }
}
