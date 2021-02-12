package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

import lombok.val;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

import java.sql.SQLException;

public class AutorizationTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldAutorizationInPersonalArea() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAutorizationInPersonalAreaWrongCode() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        $("[data-test-id=code] input").setValue("12345");
        $("[data-test-id=action-verify]").click();
        $("[data-test-id=code] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        $("[data-test-id=code] input").setValue("54321");
        $("[data-test-id=action-verify]").click();
        $("[data-test-id=code] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        $("[data-test-id=code] input").setValue("12345");
        $("[data-test-id=action-verify]").click();
        $(withText("Система заблокирована")).shouldBe(Condition.visible);
    }
}
