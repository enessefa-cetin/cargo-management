import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CentralAdmin extends Person{
    /**
     * there keep reference of company
     */
    private Company company;


    /**
     * * Create central admin without Company and without id
     * @param name is admin name
     * @param surname is admin surname
     * @param password is admin password
     */
    public CentralAdmin(String name, String surname,String password) {
        super(name, surname, password);
    }

    /**
     * Create central admin without id
     * @param name is admin name
     * @param surname is admin surname
     * @param password is admin password
     * @param company is referance
     */
    public CentralAdmin(String name, String surname,String password,Company company) {
        super(name, surname, password);
        this.company = company;
    }

    /**
     * Create central admin without id
     * @param name is admin name
     * @param surname is admin surname
     * @param id is admin id
     * @param password is admin password
     * @param company is referance
     */
    public CentralAdmin(String name, String surname,int id ,String password,Company company) {
        super(name, surname, id ,password);
        this.company = company;
    }


    public void seeCargoContent(String branchName, int cargoID) {
        Branch br = company.checkBranch(branchName);

        if(br!=null)
        {
            String info = br.getCargoInfo(cargoID);
            if(info != null)
            {
                System.out.println(info);
                System.out.println(br.findPersonel(cargoID).toString());
                System.out.println(br.getName());
            }
        }
    }

    public boolean addBranch(Branch inp) throws IOException{ 
        if(company.addBranch(inp)){
            FileWriter fw = new FileWriter("Branches.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            int num = company.getBranchNumber()-1;
            System.out.println(num);
            Random rand = new Random();
            for(int i=0;i<rand.nextInt(6)+1;i++){
                System.out.println("Burdayim");
                StringBuilder sb = new StringBuilder();
                sb.append(Integer.toString(num));
                sb.append(" ");
                sb.append(Integer.toString(rand.nextInt(num)));
                sb.append(" ");
                sb.append(Double.toString(rand.nextInt(1000)));
                System.out.println(sb.toString());
                bw.write("\n" + sb.toString());
            }
            bw.close();
            fw.close();
            FileWriter fw2 = new FileWriter("BranchNames.txt",true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write( "\n" + (num+1) + " " + inp.getName());
            bw2.close();
            fw2.close();
            return true;
        }
        return false;
    }



    public boolean removeBranch(Branch inp) throws IOException,FileNotFoundException{ return company.removeBranch(inp); }

    public boolean addEmployee(Person inp, String branchName) throws IOException,FileNotFoundException {
        Branch br = company.checkBranch(branchName);
        if(br!=null)
        {
            if(br.addEmployee(inp)) return company.addPerson(inp);
        }
        return false;
    }

    public boolean removeEmployee( Person inp, String branchName) throws IOException,FileNotFoundException{
        Branch br = company.checkBranch(branchName);
        if(br!=null)
        {
            if( br.removeEmployee(inp)) {
                return company.removePerson(inp);
            }
        }
        return false;
    }
    public boolean removeEmployee( int ID, String branchName) throws IOException,FileNotFoundException {
        Branch br = company.checkBranch(branchName);
        if(br!=null)
        {
            if ( br.removeEmployee(ID)) {
                System.out.println(company.getUser(ID).toString());
                return company.removePerson(company.getUser(ID));
            }
        }
        return false;
    }
    public void systemStatus( boolean value) { }

    public void seeUserInformation(int id){
        Person p=company.getUser(id);
        seeUserInformation(p);
    }
    public void seeUserInformation( Person p){

        if(p instanceof BranchManager)
        {
            System.out.println(((BranchManager) p).toString());
        }
        else if(p instanceof TransportationPersonel)
        {
            System.out.println(((TransportationPersonel) p).toString());
        }
        else if(p instanceof BranchEmployee)
        {
            System.out.println(((BranchEmployee) p).toString());
        }
        else
        {
            System.out.println(((Customer) p).toString());
        }

    }
}