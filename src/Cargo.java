//import java.io.*;
import java.util.*;
import java.util.Random;

/**
 * Cargo packet information
 */
public class Cargo implements Comparable<Cargo> {
    private Company company;
    
    /**
     * location database of cargo and use Stack
     */
    private Stack<String> location;
    /**
     * Who sender and who receiver there take and keep in map
     * String : key     Customer : value
     */
    private Map<String, Customer> customer;
    /**
     * id is packet id
     */
    private int id;
    /**
     * is packet date
     */
    private String date;
    /**
     * is packet bill
     */
    private String bill;

    /**
     * recieved = false after if given there be a true
     */
    private boolean recieved;

    /**
     * no parameter constructor
     */

    private long time;

    public Cargo(Company company){
        time = System.nanoTime()/1000000;
        Random random = new Random();
        id = random.nextInt(100000);
        distance = random.nextInt(1000);
        recieved = false;
        this.company = company;
    }

    /**
     *  constructor
     * @param location1 loc. 1
     * @param location2 loc.2
     */
    public Cargo(Company company,String location1, String location2){
        time = System.nanoTime()/1000000;
        Random random = new Random();
        id = random.nextInt(100000);
        distance = random.nextInt(1000);
        CreateLocation(location1,location2);
        recieved = false;
        this.company = company;
    }

    /**
     *  constructor
     * @param location1 loc. 1
     * @param location2 loc.2
     * @param sender is who give
     * @param receiver is who take
     */
    public Cargo(Company company,String location1, String location2 , Customer sender , Customer receiver){
        time = System.nanoTime()/1000000;
        Random random = new Random();
        id = random.nextInt(100000);
        distance = random.nextInt(1000);
        CreateLocation(location1,location2);

        String password_sender = "0"; //sender.getPassword();
        String password_receiver = "1"; //receiver.getPassword();
        customer = new HashMap<String, Customer>();
        customer.put(password_sender,sender);
        customer.put(password_receiver,receiver);

        recieved = false;
        this.company = company;
    }

    /**
     *  constructor
     * @param location1 loc. 1
     * @param location2 loc.2
     * @param date is date
     * @param bill is bill
     * @param sender is who give
     * @param receiver is who take
     */
    public Cargo(int id,Customer sender,Customer receiver,String date,String bill,int distance){
        time = System.nanoTime()/1000000;
        this.id = id;
        this.distance = distance;
        //CreateLocation(location1,location2);

        String password_sender = "0";
        String password_receiver = "1";
        customer = new HashMap<String, Customer>();
        customer.put(password_sender,sender);
        customer.put(password_receiver,receiver);
        location = new Stack<String>();
        this.date = date;
        this.bill = bill;
        recieved = false;
        receiver.addCargo(this);
        sender.addCargo(this);
    }

    public Cargo(String location1, String location2 ,String date , String bill , Customer sender , Customer receiver,Company company){
        this.company = company;
        time = System.nanoTime()/1000000;
        id = 700009;
        distance =  CreateLocation(location1,location2);
        String password_sender = "0";
        String password_receiver = "1";
        customer = new HashMap<String, Customer>();
        customer.put(password_sender,sender);
        customer.put(password_receiver,receiver);

        this.date = date;
        this.bill = bill;
        recieved = false;
        receiver.addCargo(this);
        sender.addCargo(this);
    }

    public void manuelLocation(String inp){
        location.push("At " + inp + " Branch");
    }

    /**
     * this function is helper fonstion for create location
     * @param sender_location which branch take packet
     * @param receiver_location which branch will give packet
     */
    public int CreateLocation(String sender_location, String receiver_location ){
        location = new Stack<String>();
        ArrayList<Integer> blist = new ArrayList<Integer>();
        int distance = company.findShortestDistance(company.findIndexOfBranch(sender_location),company.findIndexOfBranch(receiver_location),blist);
        location.push("Was delivered .");
        location.push("It was released for distribution .");
        for(int i=blist.size()-1;i>-1;i--){
            location.push("At " + company.findNameOfBranch(blist.get(i)) + " Branch");
        }
        return distance;
    }

    public Stack<String> getLocationStack() { return location; }

    /**
     * set methods
     * @param id is new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * set methods
     * @param date is new date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * set methods
     * @param bill is new bill
     */
    public void setBill(String bill) {
        this.bill = bill;
    }

    public long getTime() { return time; }

    /**
     * set methods
     * @param sender is who send corgo
     */
    public void setSender(Customer sender) {
        String password_sender = sender.getPassword();
        customer = new HashMap<String, Customer>();
        customer.put(password_sender,sender);
    }

    /**
     * set methods
     * @param receiver is who take cargo
     */
    public void setReceiver(Customer receiver) {
        String password_receiver = receiver.getPassword();
        customer = new HashMap<String, Customer>();
        customer.put(password_receiver,receiver);
    }

    /**
     * set methods
     * @param recieved is cargo recieve
     */
    public void setRecieved(boolean recieved) {

        this.recieved = recieved;
        if(recieved == true)
        {
            while(location.size()>1)
            {
                location.pop();
            }
        }
    }

    /**
     * set methods
     * @param location is new location
     */
    public void setLocation(Stack<String> location) {
        this.location = location;
    }

    /**
     * get methods
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * get methods
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * get methods
     * @return bill
     */
    public String getBill() {
        return bill;
    }

    /**
     * Get method for take sender
     * @param passWord is sender password
     * @return sender
     */
    public Customer getSender(String passWord) {
        if (customer.get(passWord) != null) {
            return customer.get(passWord);
        }
        else
        {
            System.out.println("This password not about sender");
            return null;
        }
    }

    /**
     * Get method for take receiver
     * @param passWord is receiver password
     * @return receiver
     */
    public Customer getReceiver(String passWord) {
        if (customer.get(passWord) != null) {
            return customer.get(passWord);
        }
        else
        {
            System.out.println("This password not about receiver customer");
            return null;
        }
    }

    /**
     * get methods
     * @return true or false
     */
    public boolean isRecieved() {
        return recieved;
    }

    /**
     * get methods for ask where is the cargo location
     * @return location of cargo
     */
    public String getLocation() {
        return location.peek() ;
    }

    /**
     * Change cargo location
     * @return last station
     */
    public String change() {
        if (getLocation().compareTo("Was delivered .") != 0){
            long current = System.nanoTime()/1000000;
            if ( (current - time) == 15000 ){
                String answer = location.peek();
                location.pop();
                time = System.nanoTime();
                return answer;
            }
            else{
                return null;
            }
        }
        else
        {
            recieved = true;
            return getLocation();
        }


    }

    /**
     * ToString methods for see id and location
     * @return id and location
     */
    @Override
    public String toString() {
        return "ID: " + id + "\nCurrent location is: " + getLocation() + "\n";
    }

    public int getDistance() { return distance; }

    //Comparable implement edildi
    //Yakınlık için distance adlı bir değer tutuldu
    //Bütün constructorlara distance eklendi
    //Equals override edildi

    private int distance;
    
    @Override
    public int compareTo(Cargo o) {
        if( distance < o.distance) return -1;
        else if ( distance > o.distance ) return 1;
        else{
            if( id < o.id) return -1;
            else if( id > o.id) return 1;
            return 0;
        }
    }


    @Override
    public boolean equals(Object o)
    {
        if( o == this) return true;
        if( !(o instanceof Cargo)) return false;
        if(((Cargo) o).getId() == this.id )
        {
            return true;
        }
        else return false;
    }

}
