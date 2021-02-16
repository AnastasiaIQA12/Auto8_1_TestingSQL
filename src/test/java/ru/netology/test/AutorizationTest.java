package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

import lombok.val;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AutorizationTest {
    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999/");
    }

    @AfterAll
    static void tearDown() {
        DataHelper.clearData();
    }

    @Test
    void shouldAuthorizationInPersonalArea() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAuthorizationInPersonalAreaWrongCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.wrongVerify("12345");
        $(withText("Система заблокирована")).shouldBe(Condition.visible);
    }
}
