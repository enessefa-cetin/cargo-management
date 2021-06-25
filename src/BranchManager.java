import java.io.FileNotFoundException;
import java.io.IOException;

public class BranchManager extends Person implements Employee{
    private Branch branch;
    private Company company;


    public BranchManager(String name, String surname,String password, Branch newBranch, Company comp)
    {
        super(name, surname, password);
        branch = newBranch;
        company = comp;
    }

    public BranchManager(String name, String surname,int ID, String password, Company comp,Branch newBranch)
    {
        super(name, surname,ID, password);
        branch = newBranch;
        company = comp;
    }

    public BranchManager(String name, String surname, String password, Company comp,Branch newBranch)
    {
        super(name, surname, password);
        branch = newBranch;
        company = comp;
    }
    public BranchManager(String name, String surname,String password,Company comp)
    {
        super(name, surname, password);
        company = comp;
    }

    public boolean addCustomer(Customer newCustomer) throws IOException,FileNotFoundException{
        return company.addPerson(newCustomer);
    }

    public boolean removeCustomer(Customer oldCustomer) throws IOException,FileNotFoundException
    {
        return company.removePerson(oldCustomer);
    }

    public boolean removeCustomer(int ID) throws IOException,FileNotFoundException
    {
        Customer c = (Customer)company.getUser(ID);
        if(c==null) return false;
        return company.removePerson(c);
    }

    public boolean denyCargo(Integer id) throws IOException,FileNotFoundException
    {
        return branch.removeCargo(id);
    }

    @Override
    public void seeCargoContent(int cargoID) {

        String info = branch.getCargoInfo(cargoID);
        if(info != null)
        {
            System.out.println(info);
            System.out.println(branch.findPersonel(cargoID).toString());
        }
    }

    public void viewEmployeeInfo(Integer ID){
        if(branch.getEmployee(ID) == null ) System.err.println("There is no such Employee");
        else System.out.println(branch.getEmployee(ID).toString());
    }

    public Branch getBranch(){
        return branch;
    }

    @Override
    public String toString()
    {
        StringBuilder SB = new StringBuilder();
        SB.append("Name: " + this.getName() + "\n");
        SB.append("Surname: " + this.getSurname() + "\n");
        SB.append("ID: " + this.getId() + "\n");
        SB.append("Branch: " + branch.getName() + "\n");

        return SB.toString();
    }

    public void seeEmergenyMessage(){
        String mes = branch.getMessage();
        if(  mes == null) System.out.println("There is no emergency message at this branch");
        else System.out.println(mes);
    }

}
