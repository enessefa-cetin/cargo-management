import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ConcurrentSkipListSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Branch {
    private PriorityQueue<Cargo> orderQueue;
    private ArrayList<Person> employeelist;
    private ConcurrentSkipListSet<String> messages;
    private double money=0.0;
    private int index;
    private String name;
    private Company company;

    public Branch(String branchName,int index,Company company){
        orderQueue = new PriorityQueue<Cargo>();
        employeelist = new ArrayList<Person>();
        name = branchName;
        messages = new ConcurrentSkipListSet<String>();
        this.index = index;
        this.company = company;
    }

    public int getIndex() {
        return index;
    }

    public boolean addMessage(String mes) { return messages.add(mes); }

    public String getMessage(){
        return messages.pollLast();
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public Branch(int newMoney, String branchName){
        orderQueue = new PriorityQueue<Cargo>();
        employeelist = new ArrayList<Person>();
        money = newMoney;
        name = branchName;
    }

    
    
    public boolean addCargo(Cargo c,boolean bool) throws IOException,FileNotFoundException{
        if( orderQueue.contains(c) ) return false;
        ArrayList<TransportationPersonel> personel = new ArrayList<TransportationPersonel>();
        for(int i=0;i<employeelist.size();i++){
            if( employeelist.get(i) instanceof TransportationPersonel ) personel.add( (TransportationPersonel)employeelist.get(i));
        }
        Random rand = new Random();
        if( personel.size() == 0 ){
            System.err.println("There is no available Personel to deliver Cargo at the moment ...");
            return false;
        }
        orderQueue.add(c);
        if( bool == false) return true;
        int num = rand.nextInt(personel.size());
        personel.get(num).addCargo(c);
        c.setId(700000 + company.getidCount());
        company.incrementidCount();
        FileWriter fw = new FileWriter("Cargos.txt",true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(c.getId() + " " + c.getDistance());
        pw.println(c.getSender("0").getId());
        pw.println(c.getReceiver("1").getId());
        pw.println(c.getDate());
        pw.println(c.getBill());
        pw.println(name);
        pw.println(personel.get(num).getId());
        Stack<String> temp = new Stack<String>();
        Iterator<String> iter = c.getLocationStack().iterator();
        String str;
        while( iter.hasNext()) temp.add(iter.next());
        if( c.isRecieved() ) pw.print("NULL");
        else{
            while( true ){
                str = temp.pop();
                if( str.equals("It was released for distribution .")) break;
                pw.println(str);
            }
        }
        pw.println("***");
        pw.close();
        fw.close();
        return true;
    }

    public boolean removeCargo(int id) throws IOException,FileNotFoundException{ 
        if( getCargo(id) == null) return false;
        orderQueue.remove(getCargo(id));
        FileReader fr = new FileReader("Cargos.txt");
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        String[] items;
        while( (line = br.readLine()) != null){
            items = line.split(" ");
            if( Integer.parseInt(items[0]) != id){
                lines.add(line);
                while( true ){
                    line = br.readLine();
                    lines.add(line);
                    if(line.equals("***")) break;
                }
            }
            else{
                while( true ){
                    line = br.readLine();
                    if(line.equals("***")) break;
                }
            }
        }
        fr.close();
        br.close();
        File file = new File("Cargos.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        for(int i=0;i<lines.size();i++) pw.println(lines.get(i));
        pw.close();
        fw.close();
        return true;
    }
    
    public Cargo getCargo(int id){ 
        Iterator<Cargo> iter = orderQueue.iterator();
        while( iter.hasNext()){
            Cargo temp = iter.next();
            if( temp.getId() == id) return temp;
        }
        return null;
    }

    public TransportationPersonel findPersonel(int id){
        for(int i=0;i<employeelist.size();i++){
            if ( employeelist.get(i) instanceof TransportationPersonel){
                TransportationPersonel temp = ( TransportationPersonel )employeelist.get(i);
                if ( temp.findCargo(id) != null ) return temp;
            }
        }
        return null;
    }

    public TransportationPersonel findPersonelwithID(int id){
        for(int i=0;i<employeelist.size();i++){
            if ( employeelist.get(i) instanceof TransportationPersonel){
                TransportationPersonel temp = ( TransportationPersonel )employeelist.get(i);
                if ( temp.getId() ==id ) return temp;
            }
        }
        return null;
    }

    public String getCargoInfo(int id)
    {
        Cargo shipment = getCargo(id);
        if(shipment== null) return null;
        StringBuilder SB = new StringBuilder();
        SB.append(shipment.toString());
        SB.append("Date: " + shipment.getDate() + "\n");
        SB.append("Bill: " + shipment.getBill() + "\n");
        SB.append("Sender: " + shipment.getSender("0").toString() + "\n");
        SB.append("Receiver: " + shipment.getReceiver("1").toString() + "\n");

        return SB.toString();
    }

    public Cargo deliverCargo() { return orderQueue.poll(); };

    public boolean addEmployee(Person p){
        return employeelist.add(p);
    }

    public boolean removeEmployee(Person p) { 
        System.out.println(employeelist.contains(p));
        return employeelist.remove(p);
    }

    public boolean removeEmployee(int ID) { 
        Person employee = findEmployee(ID);
        if(employee==null){
            return false;
        }
        return employeelist.remove(employee);
    }

    public Person findEmployee(int ID) { 
        for(int i=0; employeelist.size()>i;i++){
            if(employeelist.get(i).getId()==ID){
                return employeelist.get(i);
            }
        }
        return null;
    }
    
    public Person getEmployee(Integer ID)
    {
        for(int i=0; i<employeelist.size(); ++i)
        {
            System.out.println(employeelist.get(i).getId());
            if(employeelist.get(i).getId() == ID)
            {
                return employeelist.get(i);
            }
        }
        return null;
    }

    public Person getEmployee(int id){
        Iterator<Person> iter = employeelist.iterator();
        while(iter.hasNext()){
            Person temp = iter.next();
            if ( temp.getId() == id) return temp;
        }
        return null;
    }

    public double getMoney(){
        return money;
    }

    public void updateMoney(double amount){
        money +=amount;
    }

    public void receivedStatus(int ID){
        Cargo shipment = getCargo(ID);
        if(shipment!=null){
            shipment.setRecieved(true);
        }
    }
    public void setLocation(int ID,int location){
        Cargo cr = getCargo(ID);
        if(cr==null){
            System.out.println("There is no such cargo");
        }
        else{
            if(location>0){
                String str = cr.change();
                location--;
                while(str!=null && location>0){
                    str=cr.change();
                    location--;
                }
            }
        }
    }

    public String getName(){return name;}
    public void setName(String newName) {name=newName;}

    @Override
    public boolean equals(Object o)
    {
        if( o == this) return true;
        if( !(o instanceof Branch)) return false;
        if(((Branch) o).getName().equals(this.name))
        {
            return true;
        }
        else return false;
    }

    public void print()
    {
        System.out.println("Branch Name: " + name);
        System.out.println("Employees---");
        for (int i=0; i<employeelist.size(); ++i)
        {
            System.out.println("Employee["+i+"]: " + employeelist.get(i));
        }

        System.out.println("Cargos---");
        int k=0;
        Iterator<Cargo> itr = orderQueue.iterator();
        while(itr.hasNext())
        {
            System.out.println("Cargo "+ (k++) +": " + itr.next().toString());
        }

    }
}