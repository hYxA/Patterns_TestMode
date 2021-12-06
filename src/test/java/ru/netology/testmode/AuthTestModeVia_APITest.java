package ru.netology.testmode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationDto;

import static io.restassured.RestAssured.given;
import static ru.netology.data.DataGenerator.*;

/**
 * Для активации этого тестового режима при запуске SUT
 * нужно указать флаг -P:profile=test,
 * т.е.: java -jar .\artifacts\app-ibank.jar -P:profile=test.
 **/

public class AuthTestModeVia_APITest {

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        RegistrationDto registeredUser = genValidActiveUser();

        given() // "дано"
                .spec(requestSpec)
                .body(new RegistrationDto(
                        registeredUser.getLogin(),
                        registeredUser.getPassword(),
                        "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        RegistrationDto notRegisteredUser = genInvalidLogin();

        given() // "дано"
                .spec(requestSpec)
                .body(new RegistrationDto(
                        notRegisteredUser.getLogin(),
                        notRegisteredUser.getPassword(),
                        "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        RegistrationDto blockedUser = genValidBlockedUser();
        given() // "дано"
                .spec(requestSpec)
                .body(new RegistrationDto(
                        blockedUser.getLogin(),
                        blockedUser.getPassword(),
                        "blocked"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

//    @Test
//    @DisplayName("Should get error message if login with wrong login")
//    void shouldGetErrorIfWrongLogin() {
//        var registeredUser = getRegisteredUser("active");
//        var wrongLogin = getRandomLogin();
//        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
//        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
//        //  "Пароль" - пользователя registeredUser
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with wrong password")
//    void shouldGetErrorIfWrongPassword() {
//        var registeredUser = getRegisteredUser("active");
//        var wrongPassword = getRandomPassword();
//        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
//        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
//        //  "Пароль" - переменную wrongPassword
//    }
//


