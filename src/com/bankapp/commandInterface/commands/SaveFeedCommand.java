package com.bankapp.commandInterface.commands;

import com.bankapp.commandInterface.BankCommander;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class SaveFeedCommand extends AbstractCommand {

    public SaveFeedCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Enter file name to save");
        Scanner s = new Scanner(System.in);
        String fileName = s.nextLine();
        try {
            BankCommander.bankFeedService.saveFeed(fileName);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
