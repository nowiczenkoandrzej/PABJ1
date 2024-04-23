package com.an;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BankTest  extends TestCase {

    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new BankImpl();
    }

    public void testCreateAccount()  {
        long accountId = bank.createAccount("Andrzej Nowiczenko", "Szkolna 17, Białystok");

        long balance = bank.findAccount("Andrzej Nowiczenko", "Szkolna 17, Białystok");

        assertEquals(0L, balance);
    }

}
