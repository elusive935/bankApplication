package com.bankapp.clientServer.multithreaded;

public class BankServerMonitor implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Number of clients on server: " + BankServerThreaded.getThreadsWork());
        }
    }
}
