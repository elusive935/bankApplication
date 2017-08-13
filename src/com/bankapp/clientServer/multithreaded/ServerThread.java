package com.bankapp.clientServer.multithreaded;

import com.bankapp.model.Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread implements Runnable {
    private Socket socket;
    private final int clientNum;
    private final String prefix;

    public ServerThread(Socket socket, int clientNum) {
        this.socket = socket;
        this.clientNum = clientNum;
        this.prefix =  "Clent " + clientNum + " --- ";
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            Client client;

            System.out.println("SERVER THREAD " + clientNum + " Bank = " + BankServerThreaded.getCurrentBank());

            while (true) {
                System.out.println(prefix + "Wait for name of the client");
                String name = in.readLine();
                client = BankServerThreaded.getCurrentBank().getClient(name);
                if (client != null) {
                    System.out.println(prefix + "Client found");
                    out.write("ok\n");
                    out.flush();
                    break;
                } else {
                    System.out.println(prefix + "Client not found");
                    out.write("client not found\n");
                    out.flush();
                }
            }

            while (true) {
                System.out.println(prefix + "Wait for command");
                String command = in.readLine();
                if (BankServerThreaded.commands.containsKey(command)) {
                    System.out.println(prefix + "Command " + command + " accepted");
                    BankServerThreaded.commands.get(command).execute(client, in, out, prefix);
                } else {
                    System.out.println(prefix + "Command invalid: " + command);
                    out.write("Command invalid\n");
                }
                out.flush();
            }
        } catch (SocketException e){
            System.out.println(prefix + "Disconnected");
            BankServerThreaded.decrementThreadsWork();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


