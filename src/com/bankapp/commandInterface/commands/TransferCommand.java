package com.bankapp.commandInterface.commands;

import com.bankapp.commandInterface.BankCommander;
import com.bankapp.exceptions.ActiveAccountNotSet;
import com.bankapp.exceptions.ClientNotFoundException;
import com.bankapp.exceptions.NotEnoughFundsException;
import com.bankapp.model.Client;

import java.util.Scanner;

public class TransferCommand extends AbstractCommand {

    public TransferCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Who do you want to transfer money?");
        System.out.println("Enter name of the client");
        Scanner s = new Scanner(System.in);
        String clientName = s.nextLine();
        Client client;
        Float money;
        if (BankCommander.currentClient.getName().equals(clientName)){
            System.out.println("Cannot transfer to the same account");
            return;
        }
        try {
            client = BankCommander.bankService.getClient(BankCommander.currentBank, clientName);
            while (true) {
                try {
                    System.out.println("How much money do you want to transfer?");
                    money = Float.parseFloat(s.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number, try again");
                }
            }
            BankCommander.currentClient.withdraw(money);
            client.deposit(money);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong number to transfer");
        } catch (ClientNotFoundException e) {
            System.out.println("Client wasn't found");
        } catch (NotEnoughFundsException e) {
            System.out.println("Not enough money");
        } catch (ActiveAccountNotSet activeAccountNotSet) {
            System.out.println("Active account not set");
        }
    }
}
