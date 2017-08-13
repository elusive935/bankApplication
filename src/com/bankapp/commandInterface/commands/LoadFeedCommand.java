package com.bankapp.commandInterface.commands;

import com.bankapp.commandInterface.BankCommander;

import java.util.Scanner;

public class LoadFeedCommand extends AbstractCommand {

    public LoadFeedCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Enter folder");
        Scanner s = new Scanner(System.in);
        String folder = s.nextLine();
        BankCommander.bankFeedService.loadFeed(folder);
    }
}
