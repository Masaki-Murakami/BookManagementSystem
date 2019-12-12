package jp.co.saison.training.bookmanagement.application.usecases.createbook;

import jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookUsecaseInteractor;
import jp.co.saison.training.bookmanagement.domain.repositories.BookRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateBookUsecaseInteractorTest {
    @InjectMocks
    CreateBookUsecaseInteractor sut;

    @Mock
    BookRepository bookRepository;
}