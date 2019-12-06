package jp.co.saison.training.bookmanagement.domain.repositories;

import jp.co.saison.training.bookmanagement.domain.model.borroweraggregate.Borrower;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.User;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.UserId;

public interface UserRepository {
    UserId generateId();

    void create(User user);

    void update(User user);
}
