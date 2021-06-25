import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Customer extends Person{
    private String adress;
    private Company company;
    private Queue<Cargo> Cargos;
    private ArrayList<String> complains;
    
    public Customer(){
        super();
        adress = new String("");
        Cargos = new LinkedList<Cargo>();
        complains = new ArrayList<String>();
    }
    
    public Customer(String name, String surname,String password){
        super(name, surname, password);
        adress = new String("");
        Cargos = new LinkedList<Cargo>();
        complains = new ArrayList<String>();
    }
    
    public Customer(String name, String surname,int id ,String password){
        super(name, surname, id, password);
        adress = new String("");
        Cargos = new LinkedList<Cargo>();
        complains = new ArrayList<String>();
    }

    public Customer(String name, String surname,int id ,String password,String adress){
        super(name, surname, id, password);
        this.adress = adress;
        Cargos = new LinkedList<Cargo>();
        complains = new ArrayList<String>();
    }

    public Customer(String name, String surname,int id ,String password,Company company,String adress){
        super(name, surname, id, password);
        this.adress = adress;
        Cargos = new LinkedList<Cargo>();
        this.company = company;
        complains = new ArrayList<String>();
    }

    public boolean addCargo(Cargo c){
        return Cargos.add(c);
    }

    public void setCompany(Company company) { this.company = company; }

    public void deleteCargo(int id){
        Iterator<Cargo> iter = Cargos.iterator();
        while( iter.hasNext() ){
            Cargo c = iter.next();
            if (  c.getId() == id ){
                iter.remove();
                break;
            }
        }
    }
    
    public Cargo searchCargo(int id){
        Iterator<Cargo> iter = Cargos.iterator();
        while(iter.hasNext()){
            Cargo rtr = iter.next();
            if( rtr.getId() == id ) return rtr;
        }
        return null;
    }

    public String getAdress() { return this.adress; }

    public boolean setAdress(String adress){
        if ( adress.length() == 0) return false;
        this.adress = new String(adress);
        return true;
    }

    public void setRecieved(int ID){
        Cargo c = searchCargo(ID);
        if(c==null){
            System.out.println("There is no such cargo");
        }
        else{
            c.setRecieved(true);
        }
    }
    public void setLocation(int ID,int location){
        Cargo cr = searchCargo(ID);
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

    public String seeBill(int id){
        Iterator<Cargo> iter = Cargos.iterator();
        while(iter.hasNext()){
            Cargo check = iter.next();
            if( check.getId() == id ) return check.getBill();
        }
        return null;
    }

    public void complain(String message){
        complains.add(message);
    }

    
    @Override
    public String toString()
    {
        StringBuilder SB = new StringBuilder();
        SB.append("Name: " + this.getName() + "\n");
        SB.append("Surname: " + this.getSurname() + "\n");
        SB.append("ID: " + this.getId() + "\n");
        SB.append("Address: " + this.adress + "\n");

        return SB.toString();
    }

}
