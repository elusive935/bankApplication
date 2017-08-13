package com.bankapp.commandInterface.commands;

import com.bankapp.commandInterface.BankCommander;

public class GetAccountsCommand extends AbstractCommand {

    public GetAccountsCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        if (BankCommander.checkIfCurrentClientSet()) {
            System.out.println(BankCommander.currentClient.toString());
            System.out.println();
        } else {
            System.out.println("Current client not set");
        }
    }

}
