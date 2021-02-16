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

    @Value
    public static class AuthInfo {
        private String id;
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        val idSQL = "SELECT id FROM users";
        val loginSQL = "SELECT login FROM users";
        val runner = new QueryRunner();
        String login = "", id = "", password = "";
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            id = (String) (runner.query(conn, idSQL, new ScalarHandler<>()));
            login = (String) (runner.query(conn, loginSQL, new ScalarHandler<>()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (login.equals("vasya")) {
            password = "qwerty123";
        }
        if (login.equals("petya")) {
            password = "123qwerty";
        }
        return new AuthInfo(id, login, password);
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        val codeSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes WHERE user_id='" + authInfo.id + "')";
        val runner = new QueryRunner();
        String verificationCode = "";
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            verificationCode = (String) runner.query(conn, codeSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new VerificationCode(verificationCode);
    }

    public static void clearData() {
        val deleteUserSQL = "DELETE FROM users";
        val deleteCardSQL = "DELETE FROM cards";
        val deleteAuthCodesSQL = "DELETE FROM auth_codes";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.update(conn, deleteAuthCodesSQL);
            runner.update(conn, deleteCardSQL);
            runner.update(conn, deleteUserSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
