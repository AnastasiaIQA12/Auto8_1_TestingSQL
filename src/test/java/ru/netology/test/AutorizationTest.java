package ru.netology.test;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

import lombok.val;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;


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
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.PersonalArea();
    }

    @Test
    void shouldAuthorizationInPersonalAreaWrongCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.wrongCode(DataHelper.getWrongVerificationCode().getCode());
    }

    @Test
    void shouldAuthorizationInPersonalAreaWrongPassword() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoWrongPassword();
        loginPage.wrongPassword(authInfo);
    }
}
