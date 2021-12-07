package ru.netology.testmode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationDto;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

/**
 * Для активации этого тестового режима при запуске SUT
 * нужно указать флаг -P:profile=test,
 * т.е.: java -jar .\artifacts\app-ibank.jar -P:profile=test.
 **/

public class AuthTestModeVia_UITest {

    @BeforeEach
    @Test
    void setTest() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser_via_UI() {
        RegistrationDto registeredUser = genValidActiveUser();

        $("[data-test-id=login] input").sendKeys(registeredUser.getLogin());
        $("[data-test-id=password] input").sendKeys(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(5));

    }


    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser_via_UI() {
        RegistrationDto notRegisteredUser = genInvalidLogin();

        $("[data-test-id=login] input").sendKeys(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").sendKeys(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(5));
    }


    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser_via_UI() {
        RegistrationDto blockedUser = genValidBlockedUser();

        $("[data-test-id=login] input").sendKeys(blockedUser.getLogin());
        $("[data-test-id=password] input").sendKeys(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin_via_UI() {
        RegistrationDto notRegisteredUser = genInvalidLogin();

        $("[data-test-id=login] input").sendKeys(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").sendKeys(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword_via_UI() {
        RegistrationDto registeredUser = genBadPassword();

        $("[data-test-id=login] input").sendKeys(registeredUser.getLogin());
        $("[data-test-id=password] input").sendKeys(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login is empty")
    void shouldGetErrorIfLoginIsEmpty_via_UI() {
        RegistrationDto registeredUser = genValidActiveUser();

        $("[data-test-id=password] input").sendKeys(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if password is empty")
    void shouldGetErrorIfPasswordIsEmpty_via_UI() {
        RegistrationDto registeredUser = genValidActiveUser();

        $("[data-test-id=login] input").sendKeys(registeredUser.getLogin());
        $("[data-test-id=action-login]").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(5));
    }
}




