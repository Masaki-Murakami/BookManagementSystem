package jp.co.saison.training.bookmanagement.presentation.controller;

import jp.co.saison.training.bookmanagement.application.usecases.createuser.CreateUserInputData;
import jp.co.saison.training.bookmanagement.application.usecases.createuser.CreateUserUsecaseInteractor;
import jp.co.saison.training.bookmanagement.domain.model.useraggregate.*;
import jp.co.saison.training.bookmanagement.presentation.authentication.LoginUserDetailsService;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleAuthenticationSuccessHandler;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleLoginUser;
import jp.co.saison.training.bookmanagement.presentation.authentication.SimpleTokenFilter;
import jp.co.saison.training.bookmanagement.presentation.config.SecurityConfig;
import jp.co.saison.training.bookmanagement.presentation.config.SpringSecurityConfig;
import org.junit.jupiter.api.*;
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

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringSecurityConfig.class, SecurityConfig.class})
@Import({UserController.class, SimpleTokenFilter.class, SimpleAuthenticationSuccessHandler.class})
@WebMvcTest(value = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserUsecaseInteractor createUserUsecaseInteractor;

    @MockBean
    private LoginUserDetailsService loginUserDetailsService;

    @Test
    void 管理ユーザはユーザー作成を呼び出しできる() throws Exception {
        String authorization = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        SimpleLoginUser loginUser = new SimpleLoginUser("00000000-0000-0000-0000-000000000001", "loginUser", "hashedpassword", "Administrator");
        String inputJson = "{ \"name\": \"user\", \"password\": \"password\", \"role\": \"GeneralUser\" }";

        CreateUserInputData createUserInputData = CreateUserInputData.builder()
                .name("user")
                .password("password")
                .role("GeneralUser")
                .build();

        User user = User.builder()
                .id(UserId.fromString("00000000-0000-0000-0000-000000000002"))
                .name(Name.of("user"))
                .password(Password.of("encodedpassword"))
                .role(Role.GeneralUser)
                .build();

        String expectJson = "{ \"id\": \"00000000-0000-0000-0000-000000000002\", \"name\": \"user\", \"role\": \"GeneralUser\"}";

        doReturn(loginUser).when(loginUserDetailsService).loadUserByUsername(loginUser.getUsername());
        doReturn(Optional.of(loginUser)).when(loginUserDetailsService).loadUserAndAuthenticationByToken(authorization);
        doReturn(user).when(createUserUsecaseInteractor).hundle(createUserInputData);

        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        mockMvc.perform(
                post("/api/users")
                        .header("Authorization", "Bearer " + authorization)
                        .contentType(MEDIA_TYPE_JSON_UTF8)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson, true));
    }

    @Test
    void 一般ユーザがユーザー作成実行するとステータスコード403が返却される() throws Exception {
        String authorization = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        SimpleLoginUser loginUser = new SimpleLoginUser("00000000-0000-0000-0000-000000000001", "loginUser", "hashedpassword", "GeneralUser");
        String inputJson = "{ \"name\": \"user\", \"password\": \"password\", \"role\": \"GeneralUser\" }";

        doReturn(loginUser).when(loginUserDetailsService).loadUserByUsername(loginUser.getUsername());
        doReturn(Optional.of(loginUser)).when(loginUserDetailsService).loadUserAndAuthenticationByToken(authorization);

        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        mockMvc.perform(
                post("/api/users")
                        .header("Authorization", "Bearer " + authorization)
                        .contentType(MEDIA_TYPE_JSON_UTF8)
                        .content(inputJson))
                .andExpect(status().isForbidden());
    }

    @Nested
    class バリデーションテスト {
        String authorization = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        SimpleLoginUser loginUser = new SimpleLoginUser("00000000-0000-0000-0000-000000000001", "loginUser", "hashedpassword", "Administrator");

        @Tag("異常系")
        @ParameterizedTest(name = "{0}")
        @CsvFileSource(resources = "/presentation/controller/CreateUserValidation.csv", numLinesToSkip = 1)
        void バリデーションに失敗するとステータスコード400が返却される(String comment, String inputJson) throws Exception {
            doReturn(loginUser).when(loginUserDetailsService).loadUserByUsername(loginUser.getUsername());
            doReturn(Optional.of(loginUser)).when(loginUserDetailsService).loadUserAndAuthenticationByToken(authorization);

            MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

            mockMvc.perform(
                    post("/api/users")
                            .header("Authorization", "Bearer " + authorization)
                            .contentType(MEDIA_TYPE_JSON_UTF8)
                            .content(inputJson))
                    .andExpect(status().isBadRequest());
        }
    }
}