package com.bankapp.service;

import com.bankapp.exceptions.AccountNumberLimitException;
import com.bankapp.exceptions.ClientExistsException;
import com.bankapp.exceptions.ClientNotFoundException;
import com.bankapp.model.Account;
import com.bankapp.model.Bank;
import com.bankapp.model.Client;

public interface BankService {
	void addClient(Bank bank, Client client)
			throws ClientExistsException;

	void removeClient(Bank bank, Client client);

	void addAccount(Client client, Account account) throws AccountNumberLimitException;

	void setActiveAccount(Client client, Account account);

	Client getClient(Bank bank, String clientName) throws ClientNotFoundException;

	void saveClient(Client client);

	Client loadClient(String path);

	void populateBankData(Bank bank);

}
