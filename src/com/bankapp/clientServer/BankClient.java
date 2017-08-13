package com.bankapp.clientServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5433);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            Scanner s = new Scanner(System.in);

            while (true) {
                System.out.println("Enter name of the client:");
                String name = s.nextLine();
                out.write(name + "\n");
                out.flush();
                String ack = in.readLine();
                if ("ok".equals(ack)) {
                    System.out.println("Client found");
                    System.out.println("-----------");
                    break;
                }
                System.out.println("Client not found. Try again");
            }

            while (true) {
                System.out.println("Choose what do you want to do:");
                System.out.println("1) Balance");
                System.out.println("2) Withdraw");
                System.out.println("3) Exit");
                String command = s.nextLine();
                out.write(command + "\n");
                out.flush();
                if ("2".equals(command)) {
                    System.out.println("How much money would you like to withdraw?");
                    String money = s.nextLine();
                    out.write(money + "\n");
                    out.flush();
                }
                String serverAnswer = in.readLine();
                System.out.println(serverAnswer);
                System.out.println("-----------");
                if ("3".equals(command)) {
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
