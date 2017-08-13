package com.bankapp.clientServer.multithreaded;

import com.bankapp.clientServer.commands.*;
import com.bankapp.model.Bank;
import com.bankapp.service.BankService;
import com.bankapp.service.BankServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BankServerThreaded {
    public static Map<String, ServerCommand> commands = new HashMap<>();
    private static BankService bankService = new BankServiceImpl();
    private static Bank currentBank = new Bank("MyBank");
    private static AtomicInteger counterWork = new AtomicInteger(0);
    private static AtomicInteger clientNum = new AtomicInteger(0);

    static {
        int i = 1;
        registerCommand("" + i++, new BalanceCommand());
        registerCommand("" + i++, new WithdrawCommand());
        registerCommand("" + i, new ExitCommand());

        bankService.populateBankData(currentBank);
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5433)) {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            Thread monitor = new Thread(new BankServerMonitor());
            monitor.isDaemon();
            monitor.start();

            while (true) {
                Socket socket = serverSocket.accept();
                counterWork.incrementAndGet();
                executorService.execute(new ServerThread(socket, clientNum.incrementAndGet()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void registerCommand(String name, ServerCommand command) {
        commands.put(name, command);
    }

    public static void incrementThreadsWork(){
        counterWork.incrementAndGet();
    }

    public static void decrementThreadsWork(){
        counterWork.decrementAndGet();
    }

    public static int getThreadsWork(){
        return counterWork.get();
    }

    public static Bank getCurrentBank() {
        return currentBank;
    }
}
