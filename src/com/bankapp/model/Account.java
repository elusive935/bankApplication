package com.bankapp.model;

import com.bankapp.exceptions.NotEnoughFundsException;

import java.util.Map;

public interface Account extends Report {
	String getAccountName();

	float getBalance();

	void deposit(float x) throws IllegalArgumentException;

	void withdraw(float x) throws IllegalArgumentException, NotEnoughFundsException;

	void parseFeed(Map<String, String> map);

	String getAccountType();
	
}
