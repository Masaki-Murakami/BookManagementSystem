package jp.co.saison.training.bookmanagement.presentation.controller;

import jp.co.saison.training.bookmanagement.application.usecases.borrowbook.BorrowBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.borrowbook.BorrowBookUsecaseInteractor;
import jp.co.saison.training.bookmanagement.application.usecases.createbook.CreateBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.createbook.CreateBookUsecaseInteractor;
import jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookUsecaseInteractor;
import jp.co.saison.training.bookmanagement.application.usecases.givebackbook.GiveBackBookInputData;
import jp.co.saison.training.bookmanagement.application.usecases.givebackbook.GiveBackBookUsecaseInteractor;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.BookId;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Isbn13;
import jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Title;
import jp.co.saison.training.bookmanagement.presentation.authentication.LoginUserDetailsService;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleAuthenticationSuccessHandler;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleLoginUser;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleTokenFilter;
import jp.co.saison.training.bookmanagement.presentation.config.SecurityConfig;
import jp.co.saison.training.bookmanagement.presentation.config.SpringSecurityConfig;
import jp.co.saison.training.bookmanagement.presentation.handler.ConstraintViolationExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringSecurityConfig.class, SecurityConfig.class})
@Import({BookController.class, SimpleTokenFilter.class, SimpleAuthenticationSuccessHandler.class, ConstraintViolationExceptionHandler.class})
@WebMvcTest(value = BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindBookUsecaseInteractor findBookUsecaseInteractor;

    @MockBean
    private CreateBookUsecaseInteractor createBookUsecaseInteractor;

    @MockBean
    private BorrowBookUsecaseInteractor borrowBookUsecaseInteractor;

    @MockBean
    private GiveBackBookUsecaseInteractor giveBackBookUsecaseInteractor;

    @MockBean
    private LoginUserDetailsService loginUserDetailsService;

    @ParameterizedTest(name = "{0}")
    @CsvFileSource(resources = "/presentation/controller/FindBook.csv", numLinesToSkip = 1)
    void 書籍情報を取得できる(String comment, String userid, String name, String password, String role) throws Exception {
        var authorization = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        var loginUser = new SimpleLoginUser(userid, name, password, role);
        var bookId = "00000000-0000-0000-0001-000000000001";
        var expectJson = "{\"id\":\"00000000-0000-0000-0001-000000000001\",\"isbn13\":\"9784774153773\",\"title\":\"JUnit実践入門\"}";

        var findBookInputData = FindBookInputData.builder()
                .bookId(bookId)
                .build();

        var book = Book.create(
                BookId.fromString(bookId),
                Isbn13.of("9784774153773"),
                Title.of("JUnit実践入門"));

        doReturn(loginUser).when(loginUserDetailsService).loadUserByUsername(loginUser.getUsername());
        doReturn(Optional.of(loginUser)).when(loginUserDetailsService).loadUserAndAuthenticationByToken(authorization);
        doReturn(Optional.of(book)).when(findBookUsecaseInteractor).hundle(findBookInputData);

        mockMvc.perform(
                get("/api/books/{0}", bookId)
                        .header("Authorization", "Bearer " + authorization))
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson, true));
        verify(findBookUsecaseInteractor, times(1)).hundle(findBookInputData);
    }

    @ParameterizedTest(name = "{0}")
    @CsvFileSource(resources = "/presentation/controller/FindBookValidationFail.csv", numLinesToSkip = 1)
    void 書籍情報取得時にバリデーションに失敗するとステータスコード400が返却される(String comment, String bookId) throws Exception {
        var authorization = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        var loginUser = new SimpleLoginUser("00000000-0000-0000-0000-000000000001", "loginUser", "hashedpassword", "Administrator");

        doReturn(loginUser).when(loginUserDetailsService).loadUserByUsername(loginUser.getUsername());
        doReturn(Optional.of(loginUser)).when(loginUserDetailsService).loadUserAndAuthenticationByToken(authorization);

        mockMvc.perform(
                get("/api/books/{0}", bookId)
                        .header("Authorization", "Bearer " + authorization))
                .andExpect(status().isBadRequest());
        verify(findBookUsecaseInteractor, never()).hundle(any(FindBookInputData.class));
    }
}
