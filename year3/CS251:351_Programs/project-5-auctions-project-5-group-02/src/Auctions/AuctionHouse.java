package Auctions;

import java.io.*;
import java.nio.Buffer;
import java.util.*;
import java.net.*;

public class AuctionHouse {
    private final List<Item> items;
    private List<Item> itemsForSale = new ArrayList<>();
    private final String name;
    private final int port;
    private final String bankHost;
    private final int bankPort;
    private boolean activelyBidding = false;

    public AuctionHouse (String name, int port, String bankHost, int bankPort,
                         String itemsFilePath) {
        this.name = name;
        this.port = port;
        this.bankHost = bankHost;
        this.bankPort = bankPort;
        this.items = new ArrayList<>();
        initItems(itemsFilePath);
        Collections.shuffle(items);
    }

    // Initialize items for auction house
    private void initItems(String filePath) {
        try (BufferedReader reader = new BufferedReader
                                                   (new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] itemList = line.split("\\|");
                if (itemList.length != 3) {
                    System.err.println("Invalid item format on line: " + line);
                    continue;
                }

                int itemID = Integer.parseInt(itemList[0].trim());
                String itemDescription = itemList[1].trim();
                double minBid = Double.parseDouble(itemList[2].trim());

                items.add(new Item(itemID, itemDescription, minBid));
            }
        } catch (IOException e) {
            System.err.println("Error reading in items file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in items file: "
                                                              + e.getMessage());
        }
    }

    //Start the Server and Handle Agent Connections
    private void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Auction House " + name + " on port " + port);
        registerWithBank();

        while (itemsForSale.size() != 3){
            itemsForSale.add(items.removeFirst());
        }

        //Looking For Clients
        while(true){
            Socket clientSocket = serverSocket.accept();
            new Thread(new auctionHandler(clientSocket, this)).start();
        }
    }

    private void registerWithBank() {
        //Try Statement Inits the Input/Output Streams
        try{
            Socket socket = new Socket(bankHost, bankPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Use NetworkUtils to get the proper host address
            String host = NetworkUtils.getHostAddress(); //Get Host Name for Message
            out.println("REGISTER_AUCTION_HOUSE " + name + " " + host +
                                                                    " " + port);
            System.out.println("REGISTER_AUCTION_HOUSE " + name + " " + host +
                                                                    " " + port);

            // Logging
            System.out.println("[LOG] Sending REGISTER_AUCTION_HOUSE to Bank: "
                                      + name + " " + bankHost + ":" + bankPort);
        } catch (IOException e) {
            System.err.println("Cannot register with bank: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Deregister
    private void deregisterWithBank() {
        try {
            Socket socket = new Socket(bankHost, bankPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("DEREGISTER_AUCTION_HOUSE " + name);
            System.out.println("Auction house " + name + " deregistered with " +
                                                                        "bank.");
            //Optionally uncomment this line if we need to close the socket.
            //socket.close();
        } catch (IOException e) {
            System.err.println("Cannot deregister with bank: " + e.getMessage());
        }
    }

    public synchronized String getItems(){
        StringBuilder sb = new StringBuilder();
        for (Item item: itemsForSale) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString().trim();
    }

    // Notify if someone has been outbid
    private void outbidNotification(String agentName, int itemID) {
        System.out.println("Notifying " + agentName + " of being outbid on item "
                                                                      + itemID);
        // ADD LOGIC FOR COMMUNICATING THE MESSAGE.
    }

    public synchronized String placeBid(int itemID, double bid, String agentName) {
        for (Item item: itemsForSale) {
            System.out.println("[DEBUG] Checking item: " + item.getId());
            if (item.getId() == itemID) {
                System.out.println("[DEBUG] Found item: " + item);

                if (bid < item.getMinimumBid()) {
                    return "ERROR: Bid is below the minimum bid";
                }
                if (bid <= item.getCurrentBid()) {
                    return "ERROR: Bid must be higher than the current bid.";
                }

                try {
                    Socket bankSocket = new Socket(bankHost, bankPort);
                    PrintWriter out = new PrintWriter(bankSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(bankSocket.getInputStream()));

                    System.out.println("[DEBUG] Sending BLOCK_FUNDS command: BLOCK_FUNDS " + agentName + " " + bid);
                    out.println("BLOCK_FUNDS " + agentName + " " + bid);
                    String bankResponse = in.readLine();
                    System.out.println("[DEBUG] Bank response: " + bankResponse);

                    if (bankResponse == null || !bankResponse.startsWith("Funds blocked")) {
                        return "ERROR! UNABLE TO BLOCK FUNDS. " + (bankResponse != null ? bankResponse : "No response from bank");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "ERROR! UNABLE TO CONTACT BANK TO BLOCK FUNDS.";
                }

                String previousBidder = item.getHighestBidder();
                item.setCurrentBid(bid, agentName);

                System.out.println("[LOG] Bid of " + bid + " placed by " + agentName + " on item " + itemID);
                System.out.println("[DEBUG] Bid accepted. Updated item: " + item);

                if (previousBidder != null) {
                    outbidNotification(previousBidder, itemID);
                }

                // Create final references for use in lambda
                final Item finalItem = item;
                final String finalAgentName = agentName;

                new Thread(() -> {
                    try {
                        Thread.sleep(30000); // Wait 30 sec
                        synchronized (this) {
                            if (finalItem.getHighestBidder().equals(finalAgentName)) {
                                // Remove the sold item from itemsForSale list
                                itemsForSale.remove(finalItem);
                                System.out.println("[LOG] Item " + itemID + " sold to " + finalAgentName);

                                // Add a new item from the items list if available
                                if (!items.isEmpty()) {
                                    Item newItem = items.removeFirst();
                                    itemsForSale.add(newItem);
                                    System.out.println("[LOG] Added new item for sale: " + newItem);
                                }

                                System.out.println("[LOG] Current items for sale count: " + itemsForSale.size());
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                return "BID_ACCEPTED";
            }
        }
        return "BID_REJECTED";
    }

    public synchronized void setActivelyBidding(boolean isActive) {
        this.activelyBidding = isActive;
    }

    private void waitForSafeExit() {
        while (activelyBidding) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("No active bids. Safe to exit.");
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("USAGE: java AuctionHouse.java <name> <port> " +
                                        "<bankHost> <bankPort> <itemFile.txt>");
            return;
        }

        String name = args[0];
        int port = Integer.parseInt(args[1]);
        String bankHost = args[2];
        int bankPort = Integer.parseInt(args[3]);
        String itemsFilePath = args[4];

        try {
            AuctionHouse auctionHouse = new AuctionHouse(name, port, bankHost,
                                                       bankPort, itemsFilePath);
            auctionHouse.startServer();
        } catch (IOException e){
            System.err.println("Failed to start Auction House: "
                                                              + e.getMessage());
        }
    }

    /*Inner Class for Handling Agents Connections*/
    private static class auctionHandler implements Runnable {
        private final Socket socket;
        private final AuctionHouse auctionHouse;
        private String agentName; // adding to fix nonblocking funds issue

        public auctionHandler(Socket socket, AuctionHouse auctionHouse) {
            this.socket = socket;
            this.auctionHouse = auctionHouse;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader
                                                     (socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // First message should be the agent's registration
                String registration = in.readLine();
                if (registration != null && registration.startsWith("REGISTER")) {
                    this.agentName = registration.split(" ")[1];
                }

                String request;
                while ((request = in.readLine()) != null) {
                    String[] actions = request.split(" ");
                    String command = actions[0];
                    String response = "";

                    switch (command) {
                        case "GET_ITEMS" -> {
                           response = "GET_ITEMS\n" + auctionHouse.getItems() +
                                                                        "\nEND";
                            System.out.println("[DEBUG] Sending items: \n" +
                                                       auctionHouse.getItems());
                            out.println(response);
                        }
                        case "PLACE_BID" -> {
                            System.out.println("[DEBUG] Received PLACE_BID " +
                                                         "command: " + request);
                            if (actions.length < 3) {
                                response = "ERROR! Invalid command.";
                            } else {
                                try {
                                    int itemID = Integer.parseInt(actions[1]);
                                    double bidAmount = Double.parseDouble
                                                                   (actions[2]);
                                    response = auctionHouse.placeBid(itemID,
                                                     bidAmount, this.agentName);
                                } catch (NumberFormatException e) {
                                    response = "ERROR: Invalid number format";
                                }
                            }
                            out.println(response);  // Just send the bid response
                        }
                        case "QUIT" -> {
                            response = "Disconnecting from the Auction House.";
                            break;
                        }
                        default -> response = "ERROR! Unknown command.";
                    }

                    out.println(response);

                    if (command.equals("QUIT")) {
                        break;
                    }
                }
                //Optionally uncomment this line if we need to close the reader/writers.
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Replaces a sold item with a new item from the main list of items.
     * This method is synchronized to ensure thread safety.
     */
public void replaceSoldItem() {
    synchronized (this) {
        // Only add a new item if we have items left in the main list
        if (!items.isEmpty() && itemsForSale.size() < 3) {
            Item newItem = items.removeFirst();
            itemsForSale.add(newItem);
            System.out.println("[LOG] Added new item for sale: " + newItem);
        }
    }
}



    /*Inner Class for Recording Items*/
    class Item {
        private final int id;
        private final String description;
        private final double minimumBid;
        private double currentBid;
        private String highestBidder;
        public Item (int id, String description, double minimumBid) {
            this.id = id;
            this.description = description;
            this.minimumBid = minimumBid;
            this.currentBid = 0;
            this.highestBidder = null;
        }
        public int getId() {return id;}
        public double getCurrentBid() {return currentBid;}
        public double getMinimumBid() {return minimumBid;}

        public void setCurrentBid(double currentBid, String highestBidder) {
            this.currentBid = currentBid;
            this.highestBidder = highestBidder;
        }

        public String getHighestBidder() {
            return highestBidder == null ? "No Bidder" : highestBidder;
        }

        @Override
        public String toString() {
            return "Item ID: " + id + ", Description: " + description +
                                                ", Minimum Bid: " + minimumBid +
                ", Current Bid: " + currentBid + " by " +
                          (highestBidder == null ? "No Bidder" : highestBidder);
        }
    }
}