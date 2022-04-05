package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashBoardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TransferMoneyTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
    }

    @Test
    void shouldTransferFirstCard() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        int firstBalance = dashBoardPage.getFirstCardBalance();
        int secondBalance = dashBoardPage.getSecondCardBalance();
        var transferMoney = dashBoardPage.firstCardButton();
        String sum = "5000";
        var card = DataHelper.getSecondCard();
        transferMoney.validTransfer(sum, card);
        assertEquals(firstBalance + Integer.parseInt(sum), dashBoardPage.getFirstCardBalance());
        assertEquals(secondBalance - Integer.parseInt(sum), dashBoardPage.getSecondCardBalance());
    }
    @Test
    void shouldTransferSecondCard(){
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        int firstBalance = dashBoardPage.getFirstCardBalance();
        int secondBalance = dashBoardPage.getSecondCardBalance();
        var transferMoney = dashBoardPage.secondCardButton();
        String sum = "5000";
        var card = DataHelper.getFirstCard();
        transferMoney.validTransfer(sum, card);
        assertEquals(secondBalance + Integer.parseInt(sum), dashBoardPage.getSecondCardBalance());
        assertEquals(firstBalance - Integer.parseInt(sum), dashBoardPage.getFirstCardBalance());
    }
    @Test
    void shouldGetError(){
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        var transferMoney = dashBoardPage.secondCardButton();
        String sum = "50000";
        var card = DataHelper.getFirstCard();
        transferMoney.validTransfer(sum, card);
        transferMoney.errorMessage();
    }
}