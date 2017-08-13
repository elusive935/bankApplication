package com.bankapp.clientServer.commands;

import com.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface ServerCommand {
    void execute(Client client, BufferedReader in, BufferedWriter out, String prefix);

    default void sendAnswer(BufferedWriter out, String message){
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
