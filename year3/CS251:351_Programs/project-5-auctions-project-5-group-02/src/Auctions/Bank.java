package Auctions;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {

    //Maps for tracking balances, String holds the account number i.e. 'Client 1'
    //                           Double holds the dollar amount
    private ConcurrentHashMap<String, Double> accounts =
                                                      new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Double> blockedFunds =
                                                      new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> auctionHouses =
                                                      new ConcurrentHashMap<>();
    private String bankHost;


    public static void main (String[] args) throws IOException {
        int portNumber = 5678; //HardCoded
        Bank bank = new Bank();
        bank.startServer(portNumber);
    }

    private void startServer(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        bankHost = serverSocket.getInetAddress().getHostName();
        System.out.println("Bank is running on port " + portNumber);
        System.out.println("Bank has host name " +
                                   serverSocket.getInetAddress().getHostName());
        System.out.println("Waiting for connections...");


        while (true){
            Socket clientSocket = serverSocket.accept();
            new Thread(new BankHandler(clientSocket,this)).start();
        }
    }

    public synchronized String createAccount(String accountName, double balance){
        if (accounts.containsKey(accountName)) return "ERROR: Account " +
                                                              "already exists!";

        accounts.put(accountName, balance);
        blockedFunds.put(accountName, 0.0);

        // Logging in case errors happen before return statement
        System.out.println("[LOG] Account created for " + accountName +
                                                    " with balance " + balance);
        return "Account created for " + accountName + " with balance " + balance;
    }

    public synchronized String blockFunds(String accountName, double balance){
        System.out.println("[DEBUG] Attempting to block funds: Account=" +
                                           accountName + ", Amount=" + balance);

        if (!accounts.containsKey(accountName)) {
            System.out.println("Account not found: " + accountName);
            return "ERROR: Account DNE";
        }
        if (accounts.get(accountName) < balance) {
            return "ERROR: Insufficient Funds";
        }

        accounts.put(accountName, accounts.get(accountName) - balance);
        blockedFunds.put(accountName, blockedFunds.getOrDefault
                                       (accountName, 0.0) + balance);
        // Use getOrDefault to avoid null pointer exception

        // Logging in case errors happen before return statement
        System.out.println("[LOG] Funds blocked for " + accountName +
                                                   ", Curr Balance " + balance);
        return "Funds blocked for " + accountName + ", Curr Balance " + balance;
    }

    // Unblock funds from account.
    public synchronized String unblockFunds(String accountName, double balance) {
        if (!blockedFunds.containsKey(accountName) ||
                blockedFunds.get(accountName) < balance) {
            return "Error. No blocked funds.";
        }

        blockedFunds.put(accountName, blockedFunds.get(accountName) + balance);
        accounts.put(accountName, accounts.get(accountName) + balance);

        // Logging in case errors happen before return statement
        System.out.println("[LOG] Funds unblocked for account: " + accountName +
                                                                ": " + balance);
        return "Funds unblocked for account: " + accountName;
    }

    /*
     When purchase is successful (sufficient funds blocked and auction is won),
     call this method to transfer funds from buyer to seller.
     */
    public synchronized String transferFunds(String fromAccount,
                                             String toAccount, double amount) {
        if (!blockedFunds.containsKey(fromAccount) ||
                blockedFunds.get(fromAccount) < amount) {
            return "Error. Insufficient blocked funds.";
        }

        if (!accounts.containsKey(toAccount)) {
            return "Error. Recipient account not found.";
        }

        blockedFunds.put(fromAccount, blockedFunds.get(fromAccount) - amount);
        accounts.put(toAccount, accounts.get(toAccount) + amount);

        // Logging in case errors happen before return statement
        System.out.println("[LOG] Successfully transferred " + amount + " from "
                                            + fromAccount + " to " + toAccount);
        return "Successfully transferred " + amount + " from " + fromAccount +
                                                             " to " + toAccount;
    }

    public synchronized String getBalance(String accountName) {
        if (!accounts.containsKey(accountName)) {
            return "ERROR: Account does not exist.";
        }
        double balance = accounts.get(accountName);
        double blocked = blockedFunds.getOrDefault(accountName, 0.0);
        return "Total: " + (balance + blocked) + ", Blocked: " + blocked +
                                                      ", Available: " + balance;
    }

    // Store auction house's host and port info
    public synchronized String auctionHouseRegister(String name,
                                                    String host, int port) {
        auctionHouses.put(name, host + ":" + port);

        // Logging in case errors happen before return statement
        System.out.println("[LOG] Auction house " + name + " registered at " +
                                                             host + ":" + port);
        return "Auction house " + name + " registered at " + host + ":" + port;
    }

    // Getter
    public synchronized String getAuctionHouses() {
        StringBuilder s = new StringBuilder();
        auctionHouses.forEach((name, address) ->
                s.append(name + " ->  Host:" + bankHost).append(" -> ")
                                                 .append(address).append("\n"));
        s.append("END");
        return s.toString();
    }

    /*Inner Class for handling the Bank Initialization as a thread*/
    private static class BankHandler implements Runnable {
        private Socket clientSocket;
        private Bank bank;

        //Constructor for assigning the Client to the Socket
        public BankHandler(Socket clientSocket, Bank bank) {
            this.clientSocket = clientSocket;
            this.bank = bank;
        }

        @Override
        public void run() {
            BufferedReader in  = null;
            PrintWriter out = null;
            //Try Statement Inits the Input/Output Streams
            try {
                in = new BufferedReader(new InputStreamReader
                                               (clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("[LOG] Received command: " + request);
                    String[] actions = request.split(" ");
                    String command = actions[0];
                    String response = "";

                    try {
                        switch (command) {
                            case "CREATE_ACCOUNT" ->
                                    response = bank.createAccount(actions[1],
                                            Double.parseDouble(actions[2]));
                            case "BLOCK_FUNDS" ->
                                    response = bank.blockFunds(actions[1],
                                            Double.parseDouble(actions[2]));
                            case "UNBLOCK_FUNDS" ->
                                    response = bank.unblockFunds(actions[1],
                                            Double.parseDouble(actions[2]));
                            case "TRANSFER_FUNDS" ->
                                    response = bank.transferFunds(actions[1],
                                            actions[2],
                                            Double.parseDouble(actions[3]));
                            case "REGISTER_AUCTION_HOUSE" -> {
                                if (actions.length != 4){
                                    System.out.println("ERROR: " +
                                            "REGISTER_AUCTION_HOUSE requires " +
                                            "4 arguments: <REGISTER> <name> " +
                                            "<host> <port>");
                                } else {
                                    response = bank.auctionHouseRegister
                                            (actions[1], actions[2],
                                                    Integer.parseInt(actions[3]));
                                }
                            }
                            case "GET_AUCTION_HOUSES" ->
                                    response = bank.getAuctionHouses();
                            case "GET_BALANCE" ->
                                    response = bank.getBalance(actions[1]);
                            case "REGISTER_AGENT" -> {
                                if (actions.length != 3){
                                    System.out.println("[ERROR] REGISTER_AGENT " +
                                            "requires 3 arguments: <REGISTER> " +
                                            "<name> <initBalance>");
                                } else {
                                    response = bank.createAccount(actions[1],
                                            Double.parseDouble(actions[2]));
                                }
                            }
                            default ->
                                    response = "ERROR! UNKNOWN COMMAND";
                        }
                    } catch (NumberFormatException e) {
                        response = "ERROR! Invalid number format.";
                        System.err.println("[LOG] NumberFormatException: " +
                                                                e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        response = "ERROR! Missing args.";
                        System.err.println("[LOG] ArrayIndexOutOfBounds" +
                                                "Exception: " + e.getMessage());
                    }

                    out.println(response);
                    System.out.println("[LOG] Processed command: " + command +
                                                    " | Response: " + response);
                }
            } catch (IOException e) {
                System.err.println("[LOG] IOException in BankHandler: " +
                                                                e.getMessage());
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }

                    if (out != null) {
                        out.close();
                    }

                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("[LOG] Failed to close all resources in " +
                                              "BankHandler: " + e.getMessage());
                }
            }
        }
    }
}