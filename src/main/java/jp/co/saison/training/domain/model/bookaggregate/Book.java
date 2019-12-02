package jp.co.saison.training.domain.model.bookaggregate;

import jp.co.saison.training.domain.model.useraggregate.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

@Getter
@EqualsAndHashCode
public class Book {
    private final BookId id;
    private final Isbn13 isbn13;
    private final Title title;
    private BookStatus status;
    private UserId borrowerId;

    private Book(BookId id, Isbn13 isbn13, Title title) {
        this.id = id;
        this.isbn13 = isbn13;
        this.title = title;
        status = BookStatus.Lendable;
        borrowerId = null;
    }

    public static Book create(@NonNull BookId bookId, @NonNull Isbn13 isbn13, @NonNull Title title) {
        return new Book(bookId, isbn13, title);
    }

    public static Book create(@NonNull BookId bookId, @NonNull Title title) {
        return new Book(bookId, null, title);
    }


    public Optional<Isbn13> getIsbn13() {
        return Optional.ofNullable(isbn13);
    }

    public Optional<UserId> getBorrowerId() {
        return Optional.ofNullable(borrowerId);
    }

    public void lend(@NonNull UserId borrowerId) {
        if (status != BookStatus.Lendable) {
            throw new IllegalStateException("bookStatus must be " + BookStatus.Lendable.name());
        }

        this.borrowerId = borrowerId;
        status = BookStatus.InLending;
    }

    public void giveBack(@NonNull UserId borrowerId) {
        if (status != BookStatus.InLending) {
            throw new IllegalStateException("bookStatus must be " + BookStatus.InLending.name());
        }
        if (!(this.getBorrowerId().orElseThrow(IllegalStateException::new).equals(borrowerId))) {
            throw new IllegalArgumentException("borrowerId does not match");
        }

        this.borrowerId = null;
        status = BookStatus.Lendable;
    }
}
