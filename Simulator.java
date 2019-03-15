/**
 * Represents the simulator for the network
 * @author Boren Wang
 *   Email: boren.wang@stonybrook.edu
 *   SBU id: 111385010
 */
import java.util.InputMismatchException;
import java.util.Scanner;
public class Simulator {
    private Router dispatcher;
    private Router[] routers;
    private int totalServiceTime;
    private int totalPacketsArrived;
    private int packetsDropped;
    private double arrivalProb;
    private int numIntRouters;
    private int maxBufferSize;
    private int minPacketSize;
    private int maxPacketSize;
    private int bandwidth;
    private int duration;
    private static final int MAX_PACKETS = 3;

    /**
     * Simulates the network and returns the average service time
     * @param arrivalProb the arrival probability of the packets
     * @param numIntRouters number of intermediate router
     * @param maxBufferSize max size of the buffer of the routers
     * @param minPacketSize min size of the packets
     * @param maxPacketSize max size of the packets
     * @param bandwidth bandwidth of the destination
     * @param duration total time unit
     * @return
     *   the average service time
     * @throws Exception
     *   Indicates there is an exception
     */
    public double simulate(double arrivalProb, int numIntRouters, int maxBufferSize, int minPacketSize, int maxPacketSize, int bandwidth, int duration) throws Exception{
        Packet.setPacketCount(0);
        dispatcher = new Router(3);
        routers = new Router[numIntRouters];
        Router queueForPacketsReadyToBeSent = new Router(3*duration);
        for(int i=0; i<numIntRouters; i++){
            Router r = new Router(maxBufferSize);
            routers[i] = r;
        }
        for(int i=1; i<=duration; i++){
            System.out.println("Time: "+i);
            for(int j=0; j<MAX_PACKETS; j++){
                if(Math.random()<arrivalProb){
                    Packet p = new Packet();
                    p.setTimeArrive(i);
                    p.setPacketSize(randInt(minPacketSize,maxPacketSize));
                    p.setTimeToDest(p.getPacketSize()/100);
                    dispatcher.enqueue(p);
                    System.out.println("Packet "+p.getId()+" arrives at dispatcher with size "+p.getPacketSize());
                }
            }
            if(dispatcher.size()==0){
                System.out.println("No packets arrived.");
            }
            for(int j=0; j<numIntRouters; j++){ // decrement all packets counters in the beginning of the queue at each Intermediate router
                Router r = routers[j];
                if(!r.isEmpty()){
                    Packet p = r.peek();
                    if(p.getTimeToDest()>=1){
                        p.setTimeToDest(p.getTimeToDest()-1);
                    }
                }
            }
            if(!dispatcher.isEmpty()){
                int size =dispatcher.size();
                for(int j=0; j<size; j++){
                    Packet p = dispatcher.dequeue();
                    try{
                        p.setRouterNumber(Router.sendPacketTo(routers));
                        System.out.println("Packet "+p.getId()+" sent to Router "+p.getRouterNumber());
                        routers[p.getRouterNumber()-1].enqueue(p);
                    }catch(FullBuffersException e){
                        packetsDropped++;
                        System.out.println("Network is congested. Packet "+p.getId()+" is dropped.");
                    }
                }
            }
            for(int j=0; j<numIntRouters; j++){
                Router r = routers[j];
                if(!r.isEmpty()){
                    Packet p = r.peek();
                    if(p.isInQueueForPacketsToBeSent()==true){
                        continue;
                    }
                    else if(p.getTimeToDest()==0){
                        p.setInQueueForPacketsToBeSent(true);
                        queueForPacketsReadyToBeSent.enqueue(p);
                    }
                }
            }
            for(int j=0; j<bandwidth; j++){
                if(queueForPacketsReadyToBeSent.isEmpty()){
                    break;
                } else{
                    Packet p1 = queueForPacketsReadyToBeSent.dequeue();
                    totalPacketsArrived++;
                    totalServiceTime+=i-p1.getTimeArrive();
                    System.out.println("Packet "+p1.getId()+" has successfully reached its destination: +"+(i-p1.getTimeArrive()));
                    for(int k=0; k<numIntRouters; k++){
                        Router r = routers[k];
                        if(!r.isEmpty()){
                            Packet p2 = r.peek();
                            if(p1==p2){
                                r.dequeue();
                            }
                        }
                    }
                }
            }
            for(int j=0; j<numIntRouters; j++){
                System.out.println("R"+(j+1)+": "+routers[j]);
            }
        }
        if(totalPacketsArrived==0){
            return 0;
        }
        return ((double)totalServiceTime)/(totalPacketsArrived);
    }

    /**
     * Generates a random integer within a given range
     * @param minVal the lower bound of the randInt
     * @param maxVal the upper bound of the ranInt
     * @return
     *    random integer within the given range
     * @throws IllegalArgumentException
     *   Indicates the minPacketSize is greater than the maxPacketSize
     */
    private int randInt(int minVal, int maxVal) throws IllegalArgumentException{
        if(minVal>maxVal){
            throw new IllegalArgumentException("minPacketSize cannot be greater than maxPacketSize.");
        }
        return minVal+(int)(Math.random()*(maxVal-minVal+1));
    }

    /**
     * Main method of the Simulator
     * @param args
     */
    public static void main(String [] args){
        while(true){
            try{
                Simulator s = new Simulator();
                Scanner in = new Scanner(System.in);
                System.out.println("Starting simulator...");
                System.out.println("Enter the number of Intermediate routers:");
                int numIntRouters = in.nextInt();
                if(numIntRouters<1) {
                    System.out.println("numIntRouters should be greater than 1.\n");
                    continue;
                }
                System.out.println("Enter the arrival probability of the packet:");
                double arrivalProb = in.nextDouble();
                if(arrivalProb>1 || arrivalProb<0){
                        System.out.println("arrivalProb cannot be greater than 1 or less than 0.\n");
                        continue;
                }
                System.out.println("Enter the maximum buffer size of a router:");
                int maxBufferSize = in.nextInt();
                if(maxBufferSize<1){
                    System.out.println("maxBufferSize should be greater than 1.\n");
                    continue;
                }
                System.out.println("Enter the minimum size of a packet:");
                int minPacketSize = in.nextInt();
                if(minPacketSize<0){
                    System.out.println("minPacketSize should be greater than 0.\n");
                    continue;
                }
                System.out.println("Enter the maximum size of a packet:");
                int maxPacketSize = in.nextInt();
                if(maxPacketSize<0){
                    System.out.println("maxPacketSize should be greater than 0.\n");
                    continue;
                }
                if(minPacketSize>maxPacketSize){
                    System.out.println("minPacketSize cannot be larger than maxPacketSize.\n");
                    continue;
                }
                System.out.println("Enter the bandwidth:");
                int bandwidth = in.nextInt();
                if(bandwidth<1){
                    System.out.println("bandwidth should be greater than 1.\n");
                    continue;
                }
                System.out.println("Enter the simulation duration:");
                int duration = in.nextInt();
                if(duration<1){
                    System.out.println("duration should be greater than 1.\n");
                    continue;
                }
                double AverageServiceTime = s.simulate(arrivalProb, numIntRouters, maxBufferSize, minPacketSize, maxPacketSize, bandwidth, duration);
                System.out.println("Simulation ending...");
                System.out.println("Total service time: "+s.totalServiceTime);
                System.out.println("Total packets served: "+s.totalPacketsArrived);
                System.out.println("Average Service Time per packet: "+AverageServiceTime);
                System.out.println("Total packets dropped: "+s.packetsDropped);
                in.nextLine();
                System.out.println("Do you want to try another simulation? (y/n):");
                String answer = in.nextLine();
                if(answer.equals("n")) {
                    System.out.println("Program terminating successfully...");
                    break;
                }else if(answer.equals("y")){
                    continue;
                }else{
                    System.out.println("Invalid input.\nPlease try again.\n");
                }

            }catch(InputMismatchException e1){
                System.out.println("Invalid input.\nPlease try again.\n");
            }catch(Exception e2){
                System.out.println(e2.getMessage());
            }
        }

    }
}
