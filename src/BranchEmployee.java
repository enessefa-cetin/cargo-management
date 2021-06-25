import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

public class BranchEmployee extends Person implements Employee{

    private Branch branch;
    private Company company;

    public BranchEmployee(String name, String surname,String password){
        super(name,surname,password);
    }
    public BranchEmployee(String name, String surname,String password,Company company, Branch branch){
        super(name,surname,password);
        this.company=company;
        this.branch=branch;
    }

    public BranchEmployee(String name, String surname,int ID,String password,Company company, Branch branch){
        super(name,surname,ID,password);
        this.company=company;
        this.branch=branch;
    }
    public void setBranch(Branch receivedBranch){
        this.branch=receivedBranch;
    }
    public Branch getBranch(){
        return this.branch;
    }
    public void setCompany(Company receivedCompany){
        this.company=receivedCompany;
    }
    public Company getCompany(){
        return this.company;
    }
    public boolean addCustomer(Customer inp)throws IOException,FileNotFoundException{
        return company.addPerson(inp);
    }

    @Override
    public void seeCargoContent(int cargoID) {

        String info = branch.getCargoInfo(cargoID);
        if(info != null)
        {
            System.out.println(info);
        }
        else{
            System.out.println("There is no such cargo in this branch");
        }
    }
    public void  updateMoney(double amount){
        branch.updateMoney(amount);
    }
    public boolean  addCargo(Cargo inp) throws IOException,FileNotFoundException{
        return branch.addCargo(inp,true);
    }
    public void  addBill(int ID, String inp) throws IOException,FileNotFoundException {
        Cargo cr = branch.getCargo(ID);
        if(cr != null)
        {
            branch.removeCargo(ID);
            cr.setBill(inp);
            branch.addCargo(cr, true);
        }
    }
    public void  removeBill(int ID) throws IOException,FileNotFoundException{
        Cargo cr = branch.getCargo(ID);
        if(cr != null)
        {
            branch.removeCargo(ID);
            cr.setBill("");
            branch.addCargo(cr, true);
        }
    }
    public void  emergencyMessage(String message) {
        branch.addMessage(message);
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
}