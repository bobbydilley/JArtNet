package JArtNet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * ArtNet class for broadcasting to ArtNet Devices
 * Created by bobbydilley on 26/12/2016.
 */
public class JArtNet {
    private static final int ART_NET_PORT = 6454;
    private static final byte ART_POLL_1 = 0x00;
    private static final byte ART_POLL_2 = 0x20;
    private static final byte ART_DMX_1 = 0x00;
    private static final byte ART_DMX_2 = 0x50;
    private static final int MAX_BUFFER_ARTNET = 530;
    private static final String ART_NET_ID = "Art-Net\0";
    private static final int ART_DMX_START = 18;


    private int port;
    private InetAddress address;
    private DatagramSocket socket = null;
    private DatagramPacket packet;
    private byte[] dmx;
    private byte sequence;

    public JArtNet(String address) {
        this(address, 6454);
    }

    public JArtNet(String address, int port) {
        this.port = port;
        try {
            this.address = InetAddress.getByName(address);
            socket = new DatagramSocket();
        } catch (Exception e) {
            System.err.println("Could not create DatagramSocket()");
        }

        // Set the DMX Buffer
        dmx = new byte[512];
        for (int i = 0; i < dmx.length; i++) {
            dmx[i] = 0;
        }

        // Set a static sequence number
        sequence = (byte)128;

    }


    /**
     * This will generate and send an ArtNet Packet over UDP
     */
    public void send() {
        byte[] artNetPacket = generatePacket();
        DatagramPacket packet = new DatagramPacket(artNetPacket, artNetPacket.length, address, port);

        try {
            socket.send(packet);
        } catch (Exception e) {
            System.err.println("Failed to send packet");
        }
    }

    /**
     * This generates the ArtNet Packet to send
     *
     * @return The ArtNet Packet
     */
    public byte[] generatePacket() {
        byte[] packet = new byte[MAX_BUFFER_ARTNET];

        // Convert the ArtNET ID String to a char array and add to the front (0 - 7)
        char[] artNetID = ART_NET_ID.toCharArray();
        for (int i = 0; i < artNetID.length; i++) {
            packet[i] = (byte) artNetID[i];
        }

        // Add two bytes to the packet showing we want to set some DMX data (8 - 9)
        packet[8] = ART_DMX_1;
        packet[9] = ART_DMX_2;

        // Not really sure what 10 and 11 are for so we will set them to 0 (10 - 11)
        packet[10] = 0;
        packet[11] = 0;

        // Not really sure what sequence is but, we set that for packet 12 (12)
        packet[12] = sequence;

        // Again not really sure what packet 13 does so we will set to 0 (13)
        packet[13] = 0;

        // We set which universe we are talking to (14 - 15)
        packet[14] = 1;
        packet[15] = 0;

        // We set the length of data we are sending to 512 (16 - 17)
        packet[16] = 1;
        packet[17] = 0;

        // Now we simply add on the DMX data to the end
        for (int i = 0; i < dmx.length; i++) {
            packet[18 + i] = dmx[i];
        }

        return packet;
    }

    public void set(int address, int value) {
        if(value > 255) {
            System.err.println("Value set over 255");
        } else {
            dmx[address] = (byte)value;
        }
    }
}
