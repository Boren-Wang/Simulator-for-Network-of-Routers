/**
 * Represents the Routers in the network
 * @author Boren Wang
 *   Email: boren.wang@stonybrook.edu
 *   SBU id: 111385010
 */
public class Router {
    private int size;
    private int front;
    private int rear;
    private int capacity;
    private Packet[] buffer;

    /**
     * Constructor for the Router
     * @param capacity
     *   the capacity of the buffer of the router
     */
    public Router(int capacity){
        size=0;
        front=-1;
        rear=-1;
        this.capacity=capacity;
        buffer = new Packet[capacity];
    }

    /**
     * Inserts the packet into the rear of the buffer
     * @param p
     *   the packet to be enqueued
     * @throws Exception
     *   indicates the buffer is full
     */
    public void enqueue(Packet p) throws Exception{
        if((rear+1)%capacity==front){
            throw new Exception("The buffer is full.");
        }
        if(isEmpty()){
            front=0;
            rear=0;
        }else{
            rear = (rear+1)%capacity;
        }
        buffer[rear] = p;
        size++;
    }

    /**
     * Returns and removes the front of the buffer
     * @return
     *   the front of the buffer
     * @throws Exception
     *   indicates the buffer is empty
     */
    public Packet dequeue() throws Exception{
        Packet answer;
        if(isEmpty()){
            throw new Exception("The buffer is empty.");
        }
        answer = buffer[front];
        if(front==rear){
            front=-1;
            rear=-1;
        }else{
            front = (front+1)%capacity;
        }
        size--;
        return answer;
    }

    /**
     * Returns but not removes the front of the buffer
     * @return
     *   the front of the buffer
     * @throws Exception
     *   indicates the buffer is empty
     */
    public Packet peek()throws Exception{
        if(front==-1){
            throw new Exception("The buffer is empty.");
        }
        return buffer[front];
    }

    /**
     * Returns the number of the packets in the buffer
     * @return
     *   the number of the packets in the buffer
     */
    public int size(){
        return size;
    }

    /**
     * Checks whether the router is empty
     * @return
     *   true if it is empty, false otherwise
     */
    public boolean isEmpty(){
        return(front==-1);
    }

    /**
     * Returns a String representation of the Router
     * @return
     *   a String representation of the Router
     */
    @Override
    public String toString(){
        if(size==0){
            return "{}";
        }else{
            if(front<rear||front==rear){
                String answer = "{";
                for(int i=front;i<rear;i++){
                    answer = answer+buffer[i].toString()+", ";
                }
                answer = answer+buffer[rear].toString()+"}";
                return answer;
            }
            else{
                String answer = "{";
                for(int i=front;i<=capacity-1;i++){
                    answer = answer+buffer[i].toString()+", ";
                }
                for(int i=0; i<rear; i++){
                    answer = answer+buffer[i].toString()+", ";
                }
                answer = answer+buffer[rear].toString()+"}";
                return answer;
            }
        }
    }

    /**
     * Returns the number of router where the packets should be sent to
     * @param routers
     *   the Collection of Router where the packet will be sent to
     * @return
     *   the number of the router where the packet should be sent to
     * @throws FullBuffersException
     *   indicates all the router buffers are full
     */
    public static int sendPacketTo(Router[] routers) throws FullBuffersException{
        int index=1;
        int minSize = Integer.MAX_VALUE;
        int bufferCapacity = routers[0].capacity;
        for(int i=0; i<routers.length; i++){
            Router router = routers[i];
            if(router.size<minSize){
                minSize=router.size;
                index = i+1;
            }
        }
        if(minSize == bufferCapacity){
            throw new FullBuffersException("All router buffers are full.");
        }
        return index;
    }
}
