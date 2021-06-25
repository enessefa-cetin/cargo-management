import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

public class Company{
    private ArrayList<Branch> Branches;
    private ListGraph BranchDistances;
    private ArrayList<Person> Persons; 
    private String name;
    private int branchCounter;
    private int idCount;

    public Company(String name) throws FileNotFoundException,IOException
    {
        idCount = 0;
        Branches = new ArrayList<Branch>(1054);
        FileReader fr = new FileReader("BranchNames.txt");
        BufferedReader br = new BufferedReader(fr);
        String read;
        branchCounter = 0;
        while( (read = br.readLine()) != null){
            String [] arr = read.split(" ");
            Branches.add(new Branch(arr[1],branchCounter++,this));
        }
        BranchDistances = new ListGraph(Branches.size(), false);
        fr.close();
        br.close();
        FileReader fr2 = new FileReader("Branches.txt");
        BufferedReader br2 = new BufferedReader(fr2);
        BranchDistances.loadEdgesFromFile(br2);
        Persons = new ArrayList<Person>();
        this.name = name;
        fr2.close();
        br2.close();
        FileReader fr3 = new FileReader("Persons.txt");
        BufferedReader br3 = new BufferedReader(fr3);
        String line;
        String[] items;
        while( (line = br3.readLine()) != null ){
            items = line.split(" ");
            int type = Integer.parseInt(Character.toString(items[0].charAt(0)));
            if( type == 1) Persons.add(new CentralAdmin(items[1], items[2], Integer.parseInt(items[0]), items[3], this));
            else if( type == 2){
            Persons.add(new BranchManager(items[1], items[2], Integer.parseInt(items[0]), items[3], this,Branches.get(findIndexOfBranch(items[4]))));
            Branches.get(findIndexOfBranch(items[4])).addEmployee(new BranchManager(items[1], items[2], Integer.parseInt(items[0]), items[3], this,Branches.get(findIndexOfBranch(items[4]))));
            }
            else if( type == 3){
            Persons.add(new BranchEmployee(items[1], items[2], Integer.parseInt(items[0]), items[3], this,Branches.get(findIndexOfBranch(items[4]))));
            Branches.get(findIndexOfBranch(items[4])).addEmployee(new BranchEmployee(items[1], items[2], Integer.parseInt(items[0]), items[3], this,Branches.get(findIndexOfBranch(items[4]))));
            }
            else if( type == 4){
            Persons.add(new TransportationPersonel(items[1], items[2], Integer.parseInt(items[0]), items[3], this,Branches.get(findIndexOfBranch(items[4]))));
            Branches.get(findIndexOfBranch(items[4])).addEmployee(new TransportationPersonel(items[1], items[2], Integer.parseInt(items[0]), items[3], this,Branches.get(findIndexOfBranch(items[4]))));
            }
            else if( type == 5) Persons.add(new Customer(items[1], items[2], Integer.parseInt(items[0]), items[3], this,items[4]));
            idCount++;
        }
        fr3.close();
        br3.close();
        FileReader fr4 = new FileReader("Cargos.txt");
        BufferedReader br4 = new BufferedReader(fr4);
        ArrayList<String> lines = new ArrayList<String>();
        while( (line = br4.readLine()) != null ){
            items = line.split(" ");
            lines.add(line);
            while( true ){
                line = br4.readLine();
                lines.add(line);
                if( line.equals("***")) break;
            }
            Branch branch = Branches.get(findIndexOfBranch(lines.get(5)));
            Customer sender = (Customer) getUser(Integer.parseInt(lines.get(1)));
            Customer receiver =  (Customer) getUser(Integer.parseInt(lines.get(2)));
            Cargo cargo = new Cargo(Integer.parseInt(items[0]),sender , receiver, lines.get(3), lines.get(4),Integer.parseInt(items[1]));
            cargo.getLocationStack().push("Was delivered .");
            if( lines.get(7).equals("NULL")) cargo.setRecieved(true);
            else{
                cargo.setRecieved(false);
                cargo.getLocationStack().push("It was released for distribution .");
                for(int i=7;i<lines.size()-1;i++){
                    cargo.manuelLocation(lines.get(i));
                }
            }
            branch.addCargo(cargo,false);
            idCount++;
            TransportationPersonel personel = (TransportationPersonel) getUser(Integer.parseInt(lines.get(6)));
            TransportationPersonel personel2 =  branch.findPersonelwithID(Integer.parseInt(lines.get(6)));
            personel.addCargo(cargo);
            personel2.addCargo(cargo);
            sender.addCargo(cargo);
            receiver.addCargo(cargo);
            lines = new ArrayList<String>();
        }
    }

    public int getidCount() { return idCount; }
    public void incrementidCount() { idCount++; }
    
    public int getbranchCounter() { return branchCounter; }
    public void setbranchCounter(int num) { this.branchCounter = num; }

    public int findIndexOfBranch(String name){
        for(int i=0;i<Branches.size();i++){
            if((Branches.get(i).getName()).equals(name)){
                return Branches.get(i).getIndex();
            }
        }
        return -1;
    }

    public String findNameOfBranch(int index){
        for(int i=0;i<Branches.size();i++){
            if(Branches.get(i).getIndex() == index) return Branches.get(i).getName();
        }
        return null;
    }

    public int findShortestDistance(int from,int to,ArrayList<Integer> temp){
        if( from == -1 || to == -1 ) return -1;
        int [] pred = new int[BranchDistances.getNumV()];
        double[] dist = new  double[BranchDistances.getNumV()];
        DijkstrasAlgorithm.dijkstrasAlgorithm(BranchDistances,from, pred, dist);
        temp.add(to);
        int rtr = 0;
        while( pred[to] != from){
            temp.add(pred[to]);
            rtr += dist[to];
            to = pred[to];
        }
        temp.add(from); 
        return rtr;
    }

    public int getBranchNumber(){ return Branches.size(); }
    
    public boolean addBranch(Branch newBranch)
    {
        if(!Branches.contains(newBranch))
        {
            Branches.add(newBranch);
            return true;
        }
        return false;
    }

    public boolean removeBranch(Branch oldBranch) throws FileNotFoundException,IOException
    {
        if(Branches.contains(oldBranch))
        {
            int index = oldBranch.getIndex();
            Branches.remove(oldBranch);
            branchCounter--;
            ArrayList<String> lines = new ArrayList<String>();
            FileReader fr = new FileReader("Branches.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            String [] items;
            while((line = br.readLine()) != null){
                items = line.split(" ");
                if(Integer.parseInt(items[0])== index){
                    
                }
                else if (Integer.parseInt(items[1])== index){
                    StringBuilder sb = new StringBuilder();
                    Random rand = new Random();
                    sb.append(items[0]);
                    sb.append(" ");
                    int number;
                    while( true ){
                        number = rand.nextInt(branchCounter);
                        if( number != Integer.parseInt(items[0])) break;
                    }
                    sb.append(number);
                    sb.append(" ");
                    sb.append( (double) rand.nextInt(1000));
                    lines.add(sb.toString());
                    }
                else{
                    lines.add(line);
                    }
            }
            fr.close();
            br.close();
            File file = new File("Branches.txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            for(int i=0;i<lines.size()-1;i++) pw.println(lines.get(i));
            pw.print(lines.get(lines.size()-1));
            fw.close();
            pw.close();
            fr = new FileReader("BranchNames.txt");
            br = new BufferedReader(fr);
            lines = new ArrayList<String>();
            while((line = br.readLine()) != null){
                items = line.split(" ");
                if ( Integer.parseInt(items[0]) != index+1) lines.add(line); 
            }
            fr.close();
            br.close();
            file = new File("BranchNames.txt");
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            for(int i=0;i<lines.size()-1;i++) pw.println(lines.get(i));
            pw.print(lines.get(lines.size()-1));
            fw.close();
            pw.close();
            return true;
        }
        return false;
    }

    public boolean addPerson(Person person) throws IOException,FileNotFoundException
    {
        if(!Persons.contains(person))
        {
            Persons.add(person);
            FileWriter fw = new FileWriter("Persons.txt",true);
            PrintWriter pw = new PrintWriter(fw);
            sortPersons();
            if ( person instanceof CentralAdmin) person.setId(100000 + idCount++);
            else if ( person instanceof BranchManager) person.setId(200000 + idCount++);
            else if ( person instanceof BranchEmployee) person.setId(300000 + idCount++);
            else if ( person instanceof TransportationPersonel) person.setId(400000 + idCount++);
            else if ( person instanceof Customer) person.setId(500000 + idCount++);
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append(person.getId());
            sb.append(" ");
            sb.append(person.getName());
            sb.append(" ");
            sb.append(person.getSurname());
            sb.append(" ");
            sb.append(person.getPassword());
            if ( person instanceof BranchManager){
                sb.append(" ");
                BranchManager p = (BranchManager) person;
                sb.append(p.getBranch().getName());
            }
            else if ( person instanceof BranchEmployee){
                sb.append(" ");
                BranchEmployee p = (BranchEmployee) person;
                sb.append(p.getBranch().getName());
            }
            else if ( person instanceof TransportationPersonel){
                sb.append(" ");
                TransportationPersonel p = (TransportationPersonel) person;
                sb.append(p.getBranch().getName());
            }
            else if ( person instanceof Customer ){
                sb.append(" ");
                Customer p = (Customer) person;
                sb.append(p.getAdress());
            }
            pw.print(sb.toString());
            fw.close();
            pw.close();
            return true;
        }
        return false;
    }

    public boolean removePerson(Person person) throws IOException,FileNotFoundException
    {
        if(Persons.contains(person))
        {
            int id = person.getId();
            Persons.remove(person);
            sortPersons();
            ArrayList<String> lines = new ArrayList<String>();
            String line;
            String[] items;
            FileReader fr = new FileReader("Persons.txt");
            BufferedReader br = new BufferedReader(fr);
            while ( (line = br.readLine()) != null){
                items = line.split(" ");
                if( !(Integer.parseInt(items[0]) == id) ) lines.add(line);
            }
            fr.close();
            br.close();
            File file = new File("Persons.txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(file);
            for(int i=0;i<lines.size()-1;i++) pw.println(lines.get(i));
            pw.print(lines.get(lines.size()-1));
            fw.close();
            pw.close();
            return true;
        }
        return false;
    }

    public Branch checkBranch(String branchName)
    {
        for(int i=0; i<Branches.size(); ++i)
        {
            if(branchName.equals(Branches.get(i).getName()))
            {
                return Branches.get(i);
            }
        }
        return null;
    }

    public boolean isUserExists(Person check){return Persons.contains(check);}

    public Class getUserClass(Person person){
        if(isUserExists(person)){
            int index= Persons.indexOf(person);
            return Persons.get(index).getClass();
        }
        return null;
    }
    public Person getUser(int id){
        sortPersons();
        return binarySearch(id);
    }

    private  Person binarySearch(int target,int first, int last) {
    if (first > last) return null; // Base case for unsuccessful search.
    else {
        int middle = (first + last) / 2; // Next probe index.
        if (Persons.get(middle).getId() == target)
        return Persons.get(middle); // Base case for successful search.
        else if ( target < Persons.get(middle).getId())
        return binarySearch(target, first, middle - 1);
        else
        return binarySearch(target, middle + 1, last);
        }
    }
    
    public  Person binarySearch(int target) {
    return binarySearch(target, 0, Persons.size() - 1);
    }

    @SuppressWarnings("unchecked")
    public void sortPersons(){
        Object [] temp = Persons.toArray();
        Person [] arr = new Person[temp.length];
        for(int i=0;i<temp.length;i++) arr[i] = (Person) temp[i];
        MergeSort.sort(arr);
        Persons = new ArrayList<Person>(Arrays.asList(arr));
    }

    public void removeCargo(int ID, String name){
        Branch b=checkBranch(name);
        if(b==null){
            System.out.println("There is no such branch.");
        }
        else{
            Cargo c = b.getCargo(ID);
            if(c==null){
                System.out.println("There is no such cargo.");
            }
            else{
                TransportationPersonel personel = b.findPersonel(ID);
                TransportationPersonel personel2 = (TransportationPersonel )getUser( personel.getId());
                personel2.deleteCargo(c.getId());
                personel.deleteCargo(c.getId());
                Customer s = c.getSender("0");
                Customer r = c.getReceiver("1");
                s.deleteCargo(c.getId());
                r.deleteCargo(c.getId());
            }
        }
    }
    
    public void updateCargoStatus(int ID, String name,boolean isRecieved, int step){
        Branch b=checkBranch(name);
        if(b==null){
            System.out.println("There is no such branch.");
        }
        else{
            Cargo c = b.getCargo(ID);
            if(c==null){
                System.out.println("There is no such cargo.");
            }
            else{
                TransportationPersonel personel = b.findPersonel(ID);
                if(isRecieved){
                    personel.updateCargoStatus(c);
                    b.receivedStatus(ID);
                    Customer s = c.getSender("0");
                    Customer r = c.getReceiver("1");
                    s.setRecieved(ID);
                    r.setRecieved(ID);
                }
                else{
                    if(step>0){
                        personel.setLocation(ID,step);
                        Customer s = c.getSender("0");
                        Customer r = c.getReceiver("1");
                        s.setLocation(ID,step);
                        r.setLocation(ID,step);
                        b.setLocation(ID,step);
                    }
                }
            }
        }

    }


}
