package jp.co.saison.training.bookmanagement.domain.repositories;

import jp.co.saison.training.bookmanagement.domain.model.borroweraggregate.Borrower;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.UserId;

import java.util.Optional;

public interface BorrowerRepository {
    Optional<Borrower> findById(UserId userId);

    void update(Borrower borrower);
}
