package com.an;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class BankImpl implements Bank {

    private List<Account> accounts = new ArrayList<>();


    @Override
    public Long createAccount(String name, String address) {
        Account newAccount = new Account(name, address);
        accounts.add(newAccount);

        return newAccount.getId();
    }

    @Override
    public Long findAccount(String name, String address) {

        Account searchedAccount = accounts
                .stream()
                .filter(a -> a.getAddress().equals(address) && a.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(searchedAccount != null)
            return searchedAccount.getId();
        else  {
            return null;
        }
    }

    @Override
    public void deposit(Long id, BigDecimal amount) {
        Account account = accounts
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (account == null) throw new AccountIdException();

        double oldBalance = account.getBalance();

        account.setBalance(oldBalance + amount.doubleValue());
    }

    @Override
    public BigDecimal getBalance(Long id) {

        Account account = accounts
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (account == null) throw new AccountIdException();

        return BigDecimal.valueOf(account.getBalance());
    }

    @Override
    public void withdraw(Long id, BigDecimal amount) {

        Account account = accounts
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (account == null) throw new AccountIdException();

        double currentBalance = account.getBalance();

        if (currentBalance < amount.doubleValue()) throw new InsufficientFundsException();

        account.setBalance(currentBalance - amount.doubleValue());

    }

    @Override
    public void transfer(Long idSource, Long idDestination, BigDecimal amount) {

        Account sourceAccount = accounts
                .stream()
                .filter(a -> a.getId() == idSource)
                .findFirst()
                .orElse(null);

        Account destinationAccount = accounts
                .stream()
                .filter(a -> a.getId() == idDestination)
                .findFirst()
                .orElse(null);

        if(sourceAccount == null || destinationAccount == null) throw new AccountIdException();

        double sourceBalance = sourceAccount.getBalance();

        if(sourceBalance < amount.doubleValue()) throw new InsufficientFundsException();

        double destinationBalance = destinationAccount.getBalance();

        sourceAccount.setBalance(sourceBalance - amount.doubleValue());
        destinationAccount.setBalance(destinationBalance + amount.doubleValue());

    }
}
