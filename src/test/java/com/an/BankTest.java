package com.an;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest  extends TestCase {

    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new BankImpl();
    }

    @Test
    public void testCreateAccount()  {
        long accountId = bank.createAccount("Andrzej Nowiczenko", "Szkolna 17, Białystok");

        long searchedAccountId = bank.findAccount("Andrzej Nowiczenko", "Szkolna 17, Białystok");

        assertEquals(accountId, searchedAccountId);
    }


    @Test
    void findNonExistentAccount() {
        Long foundAccountId = bank.findAccount("Non Existent", "123 Main St");
        assertNull(foundAccountId);
    }

    @Test
    void deposit() {
        Long accountId = bank.createAccount("John Doe", "123 Main St");
        BigDecimal initialBalance = bank.getBalance(accountId);
        BigDecimal depositAmount = new BigDecimal("100.0");
        bank.deposit(accountId, depositAmount);
        BigDecimal newBalance = bank.getBalance(accountId);
        assertEquals(initialBalance.add(depositAmount), newBalance);
    }

    @Test
    void getBalance() {
        Long accountId = bank.createAccount("John Doe", "123 Main St");
        BigDecimal balance = bank.getBalance(accountId);
        assertEquals(new BigDecimal("0.0"), balance);
    }

    @Test
    void withdraw() {
        Long accountId = bank.createAccount("John Doe", "123 Main St");
        BigDecimal depositAmount = new BigDecimal("100.0");
        bank.deposit(accountId, depositAmount);
        BigDecimal initialBalance = bank.getBalance(accountId);
        BigDecimal withdrawAmount = new BigDecimal("50.0");
        bank.withdraw(accountId, withdrawAmount);
        BigDecimal newBalance = bank.getBalance(accountId);
        assertEquals(initialBalance.subtract(withdrawAmount), newBalance);
    }

    @Test
    void withdrawWithInsufficientFunds() {
        Long accountId = bank.createAccount("John Doe", "123 Main St");
        BigDecimal depositAmount = new BigDecimal("100.0");
        bank.deposit(accountId, depositAmount);
        BigDecimal withdrawAmount = new BigDecimal("150.0");
        assertThrows(Bank.InsufficientFundsException.class, () -> bank.withdraw(accountId, withdrawAmount));
    }

    @Test
    void transfer() {
        Long sourceAccountId = bank.createAccount("John Doe", "123 Main St");
        Long destinationAccountId = bank.createAccount("Jane Doe", "456 Oak St");
        BigDecimal depositAmount = new BigDecimal("100.0");
        bank.deposit(sourceAccountId, depositAmount);
        BigDecimal sourceInitialBalance = bank.getBalance(sourceAccountId);
        BigDecimal destinationInitialBalance = bank.getBalance(destinationAccountId);
        BigDecimal transferAmount = new BigDecimal("50.0");
        bank.transfer(sourceAccountId, destinationAccountId, transferAmount);
        BigDecimal sourceNewBalance = bank.getBalance(sourceAccountId);
        BigDecimal destinationNewBalance = bank.getBalance(destinationAccountId);
        assertEquals(sourceInitialBalance.subtract(transferAmount), sourceNewBalance);
        assertEquals(destinationInitialBalance.add(transferAmount), destinationNewBalance);
    }

    @Test
    void transferWithInsufficientFunds() {
        Long sourceAccountId = bank.createAccount("John Doe", "123 Main St");
        Long destinationAccountId = bank.createAccount("Jane Doe", "456 Oak St");
        BigDecimal depositAmount = new BigDecimal("100.0");
        bank.deposit(sourceAccountId, depositAmount);
        BigDecimal transferAmount = new BigDecimal("150.0");
        assertThrows(Bank.InsufficientFundsException.class, () -> bank.transfer(sourceAccountId, destinationAccountId, transferAmount));
    }

    @Test
    void transferWithNonExistentSourceAccount() {
        Long destinationAccountId = bank.createAccount("Jane Doe", "456 Oak St");
        BigDecimal transferAmount = new BigDecimal("50.0");
        assertThrows(Bank.AccountIdException.class, () -> bank.transfer(999L, destinationAccountId, transferAmount));
    }

    @Test
    void transferWithNonExistentDestinationAccount() {
        Long sourceAccountId = bank.createAccount("John Doe", "123 Main St");
        BigDecimal depositAmount = new BigDecimal("100.0");
        bank.deposit(sourceAccountId, depositAmount);
        BigDecimal transferAmount = new BigDecimal("50.0");
        assertThrows(Bank.AccountIdException.class, () -> bank.transfer(sourceAccountId, 999L, transferAmount));
    }
}
