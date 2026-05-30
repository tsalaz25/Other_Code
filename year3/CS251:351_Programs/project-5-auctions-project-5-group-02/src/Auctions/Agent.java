package Auctions;

import java.awt.image.BufferedImageFilter;
import java.io.*;
import java.util.*;
import java.net.*;

public class Agent {
    private static String bankHost;
    private static int bankPort;
    private static String agentName;
    private static double initialBalance;
    private static Map<String, String> auctionHouses = new HashMap<>();

    public static void main(String[] args) throws IOException {
        if(args.length != 4){
            System.out.println("USAGE: java Agent.java " +
                               "<name> <initialBalance> <bankHost> <bankPort>");
        } else {
            agentName = args[0];
            initialBalance = Double.parseDouble(args[1]);
            bankHost = args[2];
            bankPort = Integer.parseInt(args[3]);
        }

        //Bank Registry
        registerWithBank();

        //Connect to Auction Houses
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the Auction House host:");
            String auctionHost = scanner.nextLine();
            System.out.println("Enter the Auction House port:");
            int auctionPort = scanner.nextInt();

            // interact w the selected auction house
            interactWithAuctionHouse(auctionHost, auctionPort);
        } finally {
            // cleanup
            deregisterWithBank();
        }
    }

    private static void interactWithAuctionHouse(String auctionHost,
                                                 int auctionPort) {

        //validate connection first. Should validate for hostnames & ip address.
        NetworkUtils.ConnectionResult result = NetworkUtils.validateConnection
                                                    (auctionHost, auctionPort);
        if (!result.success) {
            System.err.println("Connection Error: " + result.message);
            return;
        }
        System.out.println("Successfully validated connection to auction house " +
                    "at: " + auctionHost + " (" + result.resolvedAddress + ")");

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Scanner scanner = null;


        try {
            socket = new Socket(auctionHost, auctionPort);
            in = new BufferedReader(new InputStreamReader
                                                     (socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);

            // Send agent name upon connection
            out.println("REGISTER " + agentName);

            System.out.println("Connected to Auction House at " + auctionHost +
                                                             ":" + auctionPort);
            System.out.println("Available Commands:");
            System.out.println("  GET_ITEMS - Fetch the list of items for " +
                                                                    "auction.");
            System.out.println("  PLACE_BID <itemId> <bidAmount> - Place a " +
                                                             "bid on an item.");
            System.out.println("  GET_BALANCE - Check your current account " +
                                                                    "balance.");
            System.out.println("  QUIT - Exit the auction house connection.");

            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine().trim();

                // Handle commands
                if (command.equalsIgnoreCase("GET_ITEMS") ||
                        command.equalsIgnoreCase("QUIT")) {
                    out.println(command);
                } else if (command.startsWith("PLACE_BID")) {
                    String[] parts = command.split(" ");
                    if (parts.length != 3) {
                        System.out.println("ERROR: Invalid PLACE_BID command. " +
                                        "Usage: PLACE_BID <itemId> <bidAmount>");
                        continue;
                    }
                    try {
                        Integer.parseInt(parts[1]);
                        Double.parseDouble(parts[2]);
                        out.println(command);
                        out.flush();
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Item ID and bid amount must " +
                                                                "be numbers.");
                        continue;
                    }
                } else if (command.equalsIgnoreCase("GET_BALANCE")) {
                    String balance = getBalanceFromBank();
                    System.out.println(balance);
                    continue;
                } else {
                    System.out.println("ERROR: Unknown command. Please try again.");
                    continue;
                }

                // Handle responses from the auction house RESPONSE HANDLING BLOCK IS HERE
                // Handle responses from the auction house
                // Handle responses from the auction house
                String response;
                response = in.readLine();
                if (response != null) {
                    if (response.equals("GET_ITEMS")) {
                        // Handle GET_ITEMS response
                        while ((response = in.readLine()) != null) {
                            if (response.equals("END")) break;
                            System.out.println(response);
                        }
                    }
                    else if (response.equals("BID_ACCEPTED")) {
                        System.out.println("Bid accepted!");
                        // Get updated item list
                        out.println("GET_ITEMS");
                        // Handle the GET_ITEMS response
                        response = in.readLine();  // Should be "GET_ITEMS"
                        while ((response = in.readLine()) != null) {
                            if (response.equals("END")) break;
                            System.out.println(response);
                        }
                    }
                    else if (response.startsWith("WINNER")) {
                        handleWinnerNotification(response, auctionHost);
                    }
                    else {
                        System.out.println(response);
                    }
                }

                if (command.equalsIgnoreCase("QUIT")) {
                    System.out.println("Disconnected from the Auction House.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: Could not connect to the Auction House. "
                                                              + e.getMessage());
        } finally {
            try {
                if (scanner != null) {
                    scanner.close();
                }

                if (in != null) {
                    in.close();
                }

                if (out != null) {
                    out.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("ERROR! Failed to close resources in " +
                                "interactWithAuctionHouses: " + e.getMessage());
            }
        }
    }

    private static void handleWinnerNotification(String response,
                                                 String auctionHost) {
        System.out.println("You won the auction!");
        String[] parts = response.split(" ");
        if (parts.length == 2) {
            try {
                double amount = Double.parseDouble(parts[1]);
                transferFunds(auctionHost, amount);
            } catch (NumberFormatException e) {
                System.err.println("ERROR: Invalid amount in WINNER response: "
                                                                    + response);
            }
        } else {
            System.err.println("ERROR: Malformed WINNER response: " + response);
        }
    }

    private static void registerWithBank() throws IOException {
        Socket socket = null;

        try {
            socket = new Socket(bankHost, bankPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader
                                                     (socket.getInputStream()));

            out.println("REGISTER_AGENT " + agentName + ' ' + initialBalance);

            //Fetch AH list from bank
            out.println("GET_AUCTION_HOUSES");
            StringBuilder responseBuild  = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                if ("END".equals(line.trim())) { break; }
                responseBuild.append(line).append('\n');
            }

            // Display the auction houses
            String auctionHousesList = responseBuild.toString().trim();
            System.out.println("Available Auction Houses:");
            System.out.println(auctionHousesList);

            // Parse auction houses into a map
            parseAuctionHouses(auctionHousesList);

        } catch (IOException e) {
            System.err.println("Unsuccessful registration with the bank: "
                                                              + e.getMessage());
            throw e;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Failed to close the bank socket: "
                                                              + e.getMessage());
                }
            }
        }
    }

    private static void parseAuctionHouses(String response) {
        auctionHouses.clear();
        String[] lines = response.split("\n");
        for (String line : lines) {
            String[] parts = line.split(" -> ");
            if (parts.length == 2) {
                auctionHouses.put(parts[0].trim(), parts[1].trim());
            }
        }
    }

    private static String getBalanceFromBank() {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(bankHost, bankPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader
                                                     (socket.getInputStream()));

            out.println("GET_BALANCE " + agentName);
            return in.readLine();
        } catch (IOException e) {
            return "ERROR: Unable to retrieve balance. " + e.getMessage();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

                if (out != null) {
                    out.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("ERROR! Failed to close resources in " +
                                       "getBalanceFromBank: " + e.getMessage());
            }
        }
    }

    private static void transferFunds(String auctionHost, double amount) {
        PrintWriter out = null;
        Socket socket = null;

        try {
            socket = new Socket(bankHost, bankPort);
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("TRANSFER_FUNDS " + agentName + " " + auctionHost + " "
                                                                      + amount);
            System.out.println("Transferred " + amount + " to " + auctionHost);
        } catch (IOException e) {
            System.err.println("Failed to transfer funds: " + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("ERROR! Filed to close resourced within " +
                                            "transferFunds: " + e.getMessage());
            }
        }
    }

    private static void deregisterWithBank() {
        Socket socket = null;

        try {
            socket = new Socket(bankHost, bankPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("DEREGISTER_AGENT " + agentName);
            System.out.println("Agent " + agentName + " deregistered with " +
                                                                    "the bank.");
        } catch (IOException e) {
            System.err.println("Unsuccessful deregistering with bank: "
                                                              + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Failed to close the bank socket: "
                                                              + e.getMessage());
                }
            }
        }
    }
}
