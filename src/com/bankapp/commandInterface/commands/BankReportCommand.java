package com.bankapp.commandInterface.commands;

import com.bankapp.commandInterface.BankCommander;
import com.bankapp.service.BankReportService;
import com.bankapp.model.Bank;
import com.bankapp.model.Client;
import com.bankapp.service.BankReportServiceImpl;

import java.util.List;
import java.util.Map;

public class BankReportCommand extends AbstractCommand {
    private static BankReportService bankReportService = new BankReportServiceImpl();

    public BankReportCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        Bank bank = BankCommander.currentBank;
        System.out.println("========== Bank " + bank.getName() + " report ====================");
        System.out.println("Number of clients: " + bankReportService.getNumberOfBankClients(bank));
        System.out.println("Number of accounts: " + bankReportService.getAccountsNumber(bank));
        System.out.println("Credit sum: " + bankReportService.getBankCreditSum(bank));
        System.out.println("\n---------- Clients (by balance) --------------");
        bankReportService.getClientsSorted(bank).forEach(client -> {
            System.out.println(client.getSimpleNameInfo() + "\n");
        });
        System.out.println("\n---------- Clients (by city) -----------------");
        Map<String, List<Client>> clientsByCity = bankReportService.getClientsByCity(bank);
        for (String city : clientsByCity.keySet()) {
            System.out.println("\t= = = City " + city + " = = =");
            clientsByCity.get(city).forEach(c -> System.out.println(c.getSimpleNameInfo() + "\n"));
        }
        System.out.println("============================================= end of report");
    }
}
