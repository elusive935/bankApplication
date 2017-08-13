package com.bankapp.clientServer.multithreaded;

import com.bankapp.exceptions.ActiveAccountNotSet;
import com.bankapp.model.Client;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankServerThreadedTest {
    private static final int THREADS_COUNT = 1000;

    @Test
    public void BankServerTest() {
        try {
            new Thread(() -> {
                BankServerThreaded.main(null);
            }).start();

            Client client = BankServerThreaded.getCurrentBank().getClient("John Smith");
            Float balanceBefore = client.getBalance();
            System.out.println("Before = " + balanceBefore);

            Thread[] mocks = new Thread[THREADS_COUNT];
            for (int i = 0; i < THREADS_COUNT; i++) {
                mocks[i] = new Thread(new BankClientMock(client));
                mocks[i].start();
            }
            for (int i = 0; i < THREADS_COUNT; i++) {
                mocks[i].join();
            }

            Float balanceAfter = client.getBalance();
            System.out.println("After = " + balanceAfter);
            assertEquals(THREADS_COUNT, balanceBefore - balanceAfter, 0);

        } catch (ActiveAccountNotSet | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
