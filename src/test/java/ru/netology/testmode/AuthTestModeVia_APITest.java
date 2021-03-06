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

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        RegistrationDto registeredUser = genValidActiveUser();
        String wrongLogin = faker.name().username();

        given() // "дано"
                .spec(requestSpec)
                .body(new RegistrationDto(
                        wrongLogin,
                        registeredUser.getPassword(),
                        "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        RegistrationDto registeredUser = genValidActiveUser();
        String wrongPassword = faker.internet().password();

        given() // "дано"
                .spec(requestSpec)
                .body(new RegistrationDto(
                        registeredUser.getLogin(),
                        wrongPassword,
                        "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}



