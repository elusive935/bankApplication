package com.bankapp.clientServer.commands;

import com.bankapp.exceptions.ActiveAccountNotSet;
import com.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class BalanceCommand implements ServerCommand {
    @Override
    public void execute(Client client, BufferedReader in, BufferedWriter out, String prefix) {
        String answer;
        float balance;
        try {
            balance = client.getBalance();
            answer = "Balance = " + balance + "\n";
        } catch (ActiveAccountNotSet activeAccountNotSet) {
            answer = "activeAccountNotSet\n";
        }
        sendAnswer(out, answer);
    }
}
