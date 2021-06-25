public class TransportationPersonel extends Person implements Employee{
    private AVLTree<Cargo> cargos;
    private Branch branch;
    private Company company;

    public TransportationPersonel(String name, String surname, String password, Branch newBranch,Company company)
    {
        super(name, surname, password);
        cargos = new AVLTree<Cargo>();
        branch = newBranch;
        this.company = company;
    }

    public TransportationPersonel(String name, String surname,int ID, String password, Company company,Branch newBranch)
    {
        super(name, surname,ID, password);
        cargos = new AVLTree<Cargo>();
        branch = newBranch;
        this.company = company;
    }

    public TransportationPersonel(String name, String surname, String password, Company company,Branch newBranch)
    {
        super(name, surname, password);
        cargos = new AVLTree<Cargo>();
        branch = newBranch;
        this.company = company;
    }
    
    public TransportationPersonel(String name, String surname, String password,Company company)
    {
        super(name, surname, password);
        cargos = new AVLTree<Cargo>();
        this.company = company;
    }

    public Branch getBranch(){return branch;}
    public void setBranch(Branch newBranch) {branch = newBranch;}

    public boolean addCargo(Cargo c)  { 
        //System.out.println(cargos.toString());
        return cargos.add(c); 
    }
    
    public Cargo findCargo(int id){
        Cargo c = new Cargo(company);
        c.setId(id);
        Cargo shipment = cargos.finditer(c);
        return shipment;
    }

    public Cargo deleteCargo(int id){
        Cargo c = new Cargo(company);
        c.setId(id);
        Cargo shipment = (Cargo) cargos.finditer(c);
        if(shipment==null){
            System.out.println("There is no such cargo.");
            return null;
        }
        return cargos.delete(shipment);
    }
    
    @Override
    public void seeCargoContent(int cargoID) {
        Cargo c = new Cargo(company);
        c.setId(cargoID);
        Cargo shipment = (Cargo) cargos.finditer(c);
        if(shipment!=null)
        {
            System.out.println(shipment.toString());
        }
        else System.out.println("Cargo is not found.");
    }

    public void updateCargoStatus(Cargo c) {
        Cargo shipment = (Cargo) cargos.finditer(c);
        if(shipment!=null)
        {
            shipment.setRecieved(true);
            company.updateCargoStatus(c.getId(),branch.getName(),true,0);
        }
        System.out.println("Cargo is not found.");
    }
    public void setLocation(int ID,int location){
        Cargo c = new Cargo(company);
        c.setId(ID);
        Cargo cr = (Cargo) cargos.finditer(c);
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
    public void updateCargoStatus(int cargoID) {
        Cargo c = new Cargo(company);
        c.setId(cargoID);
        Cargo shipment = (Cargo) cargos.finditer(c);
        if(shipment!=null)
        {
            shipment.setRecieved(true);
        }
        else{
            System.out.println("Cargo is not found.");
        }
    }

    public void seeCustomerInfo(Cargo c) {
        Cargo shipment = (Cargo) cargos.finditer(c);
        if(shipment!=null)
        {
            System.out.println("Receiver: " + shipment.getReceiver("1") + "\n");
        }
        System.out.println("Cargo is not found.");
    }

    public void seeCustomerInfo(int cargoID) {
        Cargo c = new Cargo(company);
        c.setId(cargoID);
        Cargo shipment = (Cargo) cargos.finditer(c);
        if(shipment!=null)
        {
            System.out.println("Receiver: " + shipment.getReceiver("1") + "\n");
        }
        System.out.println("Cargo is not found.");
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
