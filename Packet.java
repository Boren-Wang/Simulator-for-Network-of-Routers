/**
 * Represents the Packets in the network
 * @author Boren Wang
 *   Email: boren.wang@stonybrook.edu
 *   SBU id: 111385010
 */
public class Packet {
    private static int packetCount=0;
    private int id;
    private int packetSize;
    private int timeArrive;
    private int timeToDest;
    private int routerNumber;
    private boolean inQueueForPacketsToBeSent; // whether the packet is in the Queue for packets to be sent

    /**
     * Constructs an instance of the Packet
     */
    public Packet(){
        packetCount++;
        id = packetCount;
    }

    /**
     * Getter for PacketCount
     * @return
     *   the number of Packets that have been created
     */
    public static int getPacketCount() {
        return packetCount;
    }

    /**
     * Getter for id
     * @return
     *   the id of the Packet
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for packetSize
     * @return
     *   the size of the packet
     */
    public int getPacketSize() {
        return packetSize;
    }

    /**
     * Getter for timeArrive
     * @return
     *   the time when the packet arrives at the dispatcher
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    /**
     * Getter for timeToDest
     * @return
     *   the time needed for the packet to be processed by the Intermediate Router
     */
    public int getTimeToDest(){
        return timeToDest;
    }

    /**
     * Getter for routerNumber
     * @return
     *   the number of Intermediate Router where the packet should be sent to
     */
    public int getRouterNumber() {
        return routerNumber;
    }

    /**
     * Checks whether the packet is in the queue for the packet ready to be sent
     * @return
     *   true if it is, false otherwise.
     */

    public boolean isInQueueForPacketsToBeSent() {
        return inQueueForPacketsToBeSent;
    }

    /**
     * Setter for packetCount
     * @param packetCount
     *   packetCount to be set
     */
    public static void setPacketCount(int packetCount) {
        Packet.packetCount = packetCount;
    }

    /**
     * Setter for id
     * @param id
     *   id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for packetSize
     * @param packetCount
     *   packetSize to be set
     */
    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    /**
     * Setter for timeArrive
     * @param timeArrive
     *   timeArrive to be set
     */
    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    /**
     * Setter for timeToDest
     * @param timeToDest
     *   timeToDest to be set
     */
    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /**
     * Setter for routerNumber
     * @param routerNumber
     *   routerNumber to be set
     */
    public void setRouterNumber(int routerNumber) {
        this.routerNumber = routerNumber;
    }

    /**
     * Setter for inQueueForPacketsToBeSent
     * @param inQueueForPacketsToBeSent
     *   inQueueForPacketsToBeSent to be set
     */

    public void setInQueueForPacketsToBeSent(boolean inQueueForPacketsToBeSent) {
        this.inQueueForPacketsToBeSent = inQueueForPacketsToBeSent;
    }

    /**
     * Returns the String representation of the packet
     * @return
     *   the String representation of the packet
     */
    @Override
    public String toString(){
        return "["+id+", "+timeArrive+", "+timeToDest+"]";
    }

}
