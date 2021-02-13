package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() throws SQLException {
        val idSQL = "SELECT id FROM users";
        val loginSQL = "SELECT login FROM users";
        val runner = new QueryRunner();
        String login, id, password = "";
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            id = (String) (runner.query(conn, idSQL, new ScalarHandler<>()));
            login = (String) (runner.query(conn, loginSQL, new ScalarHandler<>()));

        }
        if (login.equals("vasya")) {
            password = "qwerty123";
        }
        if (login.equals("petya")) {
            password = "123qwerty";
        }
        return new AuthInfo(id, login, password);
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) throws SQLException {
        val codeSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes);";
        val runner = new QueryRunner();
        String verificationCode;
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val code = runner.query(conn, codeSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        }
        return new VerificationCode(verificationCode);
    }

    @Value
    public static class AuthInfo {
        private String id;
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

}
