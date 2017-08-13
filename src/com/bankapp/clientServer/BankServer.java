package com.bankapp.clientServer;

import com.bankapp.clientServer.commands.*;
import com.bankapp.model.Bank;
import com.bankapp.model.Client;
import com.bankapp.service.BankService;
import com.bankapp.service.BankServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class BankServer {
    public static Map<String, ServerCommand> commands = new HashMap<>();
    private static BankService bankService = new BankServiceImpl();
    private static Bank currentBank = new Bank("MyBank");

    static {
        int i = 1;
        registerCommand("" + i++, new BalanceCommand());
        registerCommand("" + i++, new WithdrawCommand());
        registerCommand("" + i, new ExitCommand());

        bankService.populateBankData(currentBank);
    }

    public static void main(String[] args) {
        ServerSocket s = null;
        try {
            s = new ServerSocket(5433);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try (Socket socket = s.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                Client client;
                PrintWriter outPrint = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                while (true) {
                    System.out.println("Wait for name of the client");
                    String name = in.readLine();
                    client = currentBank.getClientsIndex().get(name);
                    if (client != null) {
                        System.out.println("Client found");
//                        out.write("ok\n");
//                        out.flush();
                        outPrint.println("ok");
                        outPrint.flush();
                        break;
                    } else {
                        System.out.println("Client not found");
                        out.write("client not found\n");
                        out.flush();
                    }
                }

                while (true) {
                    System.out.println("Wait for command");
                    String command = in.readLine();
                    if (commands.containsKey(command)) {
                        System.out.println("Command " + command + " accepted");
                        commands.get(command).execute(client, in, out, "");
                    } else {
                        System.out.println("Command invalid");
                        out.write("Command invalid\n");
                    }
                    out.flush();
                }
            } catch (SocketException e) {
                System.out.println("Disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerCommand(String name, ServerCommand command) {
        commands.put(name, command);
    }

}
