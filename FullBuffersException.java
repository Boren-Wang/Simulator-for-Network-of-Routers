/**
 * Indicates all the router buffers are full
 * @author Boren Wang
 *   Email: boren.wang@stonybrook.edu
 *   SBU id: 111385010
 */
public class FullBuffersException extends Exception{
    /**
     * Constructs an instance of the FullBuffersException
     * @param s
     *   the message of the FullBuffersException
     */
    public FullBuffersException(String s){
        super(s);
    }
}
