package com.bankapp.clientServer.multithreaded;

import com.bankapp.exceptions.ActiveAccountNotSet;
import com.bankapp.exceptions.NotEnoughFundsException;
import com.bankapp.model.Client;

public class BankClientMock implements Runnable{
    private final Client client;

    public BankClientMock(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            client.withdraw(1);
        } catch (NotEnoughFundsException | ActiveAccountNotSet e) {
            e.printStackTrace();
        }
    }
}
