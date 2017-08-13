package com.bankapp.model;

import com.bankapp.exceptions.ClientExistsException;
import com.bankapp.service.feed.Feed;

import java.util.*;

public class Bank implements Report {
	private String name = "Default Bank";

	@Feed
	private Set<Client> clients = new HashSet<>();
    private Map<String, Client> clientsIndex = new HashMap<>();

	public Set<Client> getClients() {
		return Collections.unmodifiableSet(clients);
	}

	public void addClient(Client c) throws ClientExistsException {
		if (!checkIfClientExists(c)) {
			clients.add(c);
            clientsIndex.put(c.getName(), c);
		} else {
			throw new ClientExistsException();
		}
	}

	public void removeClient(Client c) {
		clients.remove(c);
        clientsIndex.remove(c.getName());
	}

	@Override
	public void printReport() {
		for (Client c : clients) {
			c.printReport();
		}

	}

	public boolean checkIfClientExists(Client client) {
		for (Client c : clients) {
			if (c.getName().equals(client.getName())) {
				return true;
			}
		}
		return false;
	}

    public Map<String, Client> getClientsIndex() {
        return Collections.unmodifiableMap(clientsIndex);
    }

    public String getName() {
        return name;
    }

    public Client getClient(String name){
		Client client = clientsIndex.get(name);
		return client;
	}

    public void parseFeed(Map<String, String> map){
		String name = map.get("name");
		Client client = getClientsIndex().get(name);
		Client.Gender gender = Client.parseGender(map.get("gender"));
		if (client == null) {
            try {
                addClient(new Client(name, gender));
				client = getClientsIndex().get(name);
            } catch (ClientExistsException ignore) {
				System.err.println("Persistence in bank index corrupted!");

			}
        }
        client.parseFeed(map);
    }

    public Bank() {
	}

	public Bank(String name) {
		this.name = name;
	}
}
