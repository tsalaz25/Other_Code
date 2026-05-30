package Auctions;

import java.io.*;
import java.net.*;
import java.util.*;

public class AutoBidderAgent {
    private static String bankHost = "localhost";
    private static int bankPort = 5678;
    private static String agentName = "AutoBidder";
    private static double initialBalance = 500.0;
    private static boolean activelyBidding = true;

    public static void main(String[] args) {
        if (args.length == 4) {
            agentName = args[0];
            initialBalance = Double.parseDouble(args[1]);
            bankHost = args[2];
            bankPort = Integer.parseInt(args[3]);
        }

        try {
            // Register with bank and get auction house list
            registerWithBank();
            Map<String, String> auctionHouseMap = getAuctionHouses();

            // Continue bidding until we run out of funds or there's nothing to bid on
            while (activelyBidding) {
                for (Map.Entry<String, String> entry :
                                                   auctionHouseMap.entrySet()) {
                    String[] hostPort = entry.getValue().split(":");
                    String host = hostPort[0];
                    int port = Integer.parseInt(hostPort[1]);

                    try (Socket socket = new Socket(host, port)) {
                        BufferedReader in = new BufferedReader
                                (new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter
                                      (socket.getOutputStream(), true);

                        // Register with auction house
                        out.println("REGISTER " + agentName);
                        System.out.println("Connected to auction house at " +
                                                             host + ":" + port);

                        // Get available items
                        out.println("GET_ITEMS");
                        List<String> items = new ArrayList<>();
                        String line;
                        // Skip the GET_ITEMS header
                        line = in.readLine();
                        while ((line = in.readLine()) != null) {
                            if (line.equals("END")) break;
                            items.add(line);
                        }

                        // Process each item
                        for (String item : items) {
                            String[] parts = item.split(",");
                            if (parts.length < 3) continue;

                            // Parse item details
                            String idPart = parts[0].trim();
                            int itemId = Integer.parseInt
                                            (idPart.split(":")[1].trim());

                            // Find minimum bid and current bid
                            double minBid = 0;
                            double currentBid = 0;
                            String currentBidder = "No Bidder";
                            for (String part : parts) {
                                part = part.trim();
                                if (part.contains("Minimum Bid:")) {
                                    minBid = Double.parseDouble
                                            (part.split(":")[1].trim());
                                }
                                if (part.contains("Current Bid:")) {
                                    String[] bidInfo = part.split("by");
                                    currentBid = Double.parseDouble(bidInfo[0]
                                                   .split(":")[1].trim());
                                    currentBidder = bidInfo[1].trim();
                                }
                            }

                            // Skip if we're already the highest bidder
                            if (currentBidder.equals(agentName)) {
                                System.out.println("Already highest bidder " +
                                                            "on item " + itemId);
                                continue;
                            }

                            // Calculate required bid
                            double requiredBid = Math.max(minBid,
                                                            currentBid + 0.01);

                            // Check available funds
                            double availableFunds = getAvailableFunds();
                            if (availableFunds < requiredBid) {
                                System.out.println("Insufficient funds for " +
                                                              "item " + itemId +
                                        " (Required: " + requiredBid +
                                        ", Available: " + availableFunds + ")");
                                continue;
                            }

                            // Place bid slightly above required
                            double bidAmount = requiredBid + (Math.random() * 10);
                            System.out.println("Placing bid of " + bidAmount +
                                                          " on item " + itemId);
                            out.println("PLACE_BID " + itemId + " " + bidAmount);

                            // Wait for bid response
                            String response = in.readLine();
                            System.out.println("Bid response for item " + itemId
                                                             + ": " + response);

                            // Wait a bit before next bid
                            Thread.sleep(2000);
                        }

                        // Close this auction house connection before moving to next
                        out.println("QUIT");
                    } catch (IOException e) {
                        System.err.println("Error connecting to auction house: "
                                                              + e.getMessage());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                // Wait before checking auctions again
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static double getAvailableFunds() throws IOException {
        try (Socket socket = new Socket(bankHost, bankPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader
                                                   (socket.getInputStream()))) {

            out.println("GET_BALANCE " + agentName);
            String response = in.readLine();
            if (response != null && response.contains("Available:")) {
                String[] parts = response.split("Available: ");
                return Double.parseDouble(parts[1]);
            }
            return 0.0;
        }
    }

    private static void registerWithBank() throws IOException {
        try (Socket socket = new Socket(bankHost, bankPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader
                                                   (socket.getInputStream()))) {

            out.println("REGISTER_AGENT " + agentName + " " + initialBalance);
            String response = in.readLine();
            System.out.println("Bank registration response: " + response);
        }
    }

    private static Map<String, String> getAuctionHouses() throws IOException {
        Map<String, String> auctionHouseMap = new HashMap<>();

        try (Socket socket = new Socket(bankHost, bankPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader
                                                   (socket.getInputStream()))) {

            out.println("GET_AUCTION_HOUSES");
            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("END")) break;
                String[] parts = line.split(" -> ");
                if (parts.length >= 2) {
                    String[] addressParts = parts[parts.length - 1].trim()
                                                              .split(":");
                    if (addressParts.length == 2) {
                        auctionHouseMap.put(parts[0], addressParts[0] + ":" +
                                                                addressParts[1]);
                    }
                }
            }
        }
        return auctionHouseMap;
    }
}