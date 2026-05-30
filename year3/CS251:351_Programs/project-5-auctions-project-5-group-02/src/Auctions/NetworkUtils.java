package Auctions;

public class NetworkUtils {
    /**
     * Gets the most appropriate host address for network communication.
     * Prioritizes non-localhost IPv4 addresses.
     * @return String representing the host address
     */
    public static String getHostAddress() {
        try {
            // Try to get the primary non-loopback address. If not found, fallback to localhost
            // loopback address = 127.0.0.1 (IPv4) or ::1 (IPv6)
            java.net.InetAddress localHost = java.net.InetAddress.getLocalHost();
            java.net.NetworkInterface ni = java.net.NetworkInterface.getByInetAddress(localHost);

            if (ni != null) {
                for (java.net.InterfaceAddress address : ni.getInterfaceAddresses()) {
                    java.net.InetAddress addr = address.getAddress();
                    // Skip IPv6 and loopback addresses
                    if (!addr.isLoopbackAddress() && addr instanceof java.net.Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }

            // Fallback to basic getHostAddress if no suitable address found
            return localHost.getHostAddress();
        } catch (java.net.UnknownHostException | java.net.SocketException e) {
            // Fallback to localhost if network interface detection fails
            return "127.0.0.1";
        }
    }

    /**
     * Validates and attempts to connect to a host (IP or hostname) and port
     * @param host The host address or hostname (e.g. "192.168.1.230" or "B146.cslab.unm.edu")
     * @param port The port number
     * @return ConnectionResult containing success status and resolved address
     */
    public static ConnectionResult validateConnection(String host, int port) {
        try {
            // Try to resolve the host (works for both IP addresses and hostnames)
            java.net.InetAddress address = java.net.InetAddress.getByName(host);

            // Try to connect with a reasonable timeout
            java.net.Socket socket = new java.net.Socket();
            socket.connect(new java.net.InetSocketAddress(address, port), 3000);
            socket.close();

            return new ConnectionResult(true,
                    address.getHostAddress(),
                    "Successfully connected to " + host + " (" + address.getHostAddress() + ")");
        } catch (java.net.UnknownHostException e) {
            return new ConnectionResult(false, null,
                    "Could not resolve host '" + host + "'. Please check if the hostname/IP is correct.");
        } catch (java.net.SocketTimeoutException e) {
            return new ConnectionResult(false, null,
                    "Connection to " + host + ":" + port + " timed out. Please verify the host is running and reachable.");
        } catch (java.net.ConnectException e) {
            return new ConnectionResult(false, null,
                    "Connection refused to " + host + ":" + port + ". Please verify the service is running.");
        } catch (Exception e) {
            return new ConnectionResult(false, null,
                    "Failed to connect to " + host + ":" + port + " - " + e.getMessage());
        }
    }

    public static class ConnectionResult {
        public final boolean success;
        public final String resolvedAddress;
        public final String message;

        public ConnectionResult(boolean success, String resolvedAddress, String message) {
            this.success = success;
            this.resolvedAddress = resolvedAddress;
            this.message = message;
        }
    }
}