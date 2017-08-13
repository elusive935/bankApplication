package com.bankapp.commandInterface.commands;

import com.bankapp.commandInterface.BankCommander;
import com.bankapp.exceptions.AccountNumberLimitException;
import com.bankapp.exceptions.ClientExistsException;
import com.bankapp.model.Client;

import java.util.Scanner;

public class AddClientCommand extends AbstractCommand {

    public AddClientCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Overdraft:");
        String overdraft = scanner.nextLine();
        float overdraftValue = Float.parseFloat(overdraft);
        System.out.println("Gender (m/f):");
        Client.Gender g = Client.parseGender(scanner.nextLine());
        Client client = new Client(name, overdraftValue, g);

        while (true) {
            System.out.println("Create account? (y/n)");
            String answer = scanner.nextLine();
            if (!answer.equals("y")) {
                break;
            }
            System.out.println("Please, note that only one Checking and one Saving accounts are available");
            System.out.println("Type of account (Checking/Saving):");
            String type = scanner.nextLine();
            try {
                client.addAccount(client.createAccount(type));
            } catch (AccountNumberLimitException e) {
                System.out.println("Limit of accounts for one client (2) is reached");
                break;
            }
        }

        client.setActiveAccountIfNotSet();
        try {
            BankCommander.currentBank.addClient(client);
            System.out.println("Client has been added to the system");
        } catch (ClientExistsException e) {
            System.out.println("Client already exists. Not created");
        }
    }

}
