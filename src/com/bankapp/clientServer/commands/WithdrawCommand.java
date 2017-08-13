package com.bankapp.clientServer.commands;

import com.bankapp.exceptions.ActiveAccountNotSet;
import com.bankapp.exceptions.NotEnoughFundsException;
import com.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class WithdrawCommand implements ServerCommand {
    public void execute(Client client, BufferedReader in, BufferedWriter out, String prefix) {
        String answer = "";
        try {
            System.out.println(prefix + "Wait for money amount");
            String money = in.readLine();
            System.out.println(prefix + "Money accepted");

            float m = 0;
            try {
                m = Float.parseFloat(money);
            } catch (NumberFormatException e){
                answer = "Withdrawal failed: invalid number\n";
            }
            if (m <= 0){
                answer = "Withdrawal failed: invalid number\n";
                sendAnswer(out, answer);
                return;
            }
            client.withdraw(m);
            answer = "Withdrawal success, " + m + "\n";
        } catch (NotEnoughFundsException e) {
            answer = "Withdrawal failed: not enough funds\n";
        } catch (ActiveAccountNotSet activeAccountNotSet) {
            answer = "Withdrawal failed: internal error\n";
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendAnswer(out, answer);
    }
}
