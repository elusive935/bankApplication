package com.bankapp.model;

import com.bankapp.exceptions.NotEnoughFundsException;

import java.util.Map;

public class SavingAccount extends AbstractAccount {

	public SavingAccount(float initialBalance) throws IllegalArgumentException {
		if (initialBalance >= 0) {
			balance = initialBalance;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void withdraw(float x) throws IllegalArgumentException, NotEnoughFundsException {
		if (x < 0){
			throw new IllegalArgumentException("Cannot withdraw negative");
		}
		if (balance >= x) {
			balance -= x;
			System.out.println("Successful withdrawal from Saving account, thread " + Thread.currentThread().getName());
		}
		else {
			throw new NotEnoughFundsException(x);
		}
	}

	public String getAccountName() {
		return "Saving Account";
	}

	@Override
	public void parseFeed(Map<String, String> map) {
		super.parseFeed(map);
	}

	@Override
	public String getAccountType() {
		return "s";
	}
}
