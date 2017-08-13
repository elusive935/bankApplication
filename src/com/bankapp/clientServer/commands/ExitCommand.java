package com.bankapp.clientServer.commands;

import com.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ExitCommand implements ServerCommand {
    @Override
    public void execute(Client client, BufferedReader in, BufferedWriter out, String prefix) {
        sendAnswer(out, "Thank you and good buy.\n");
    }
}
