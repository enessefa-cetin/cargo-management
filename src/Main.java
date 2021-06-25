import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;
public class Main {

    public static void main(String[] args) throws FileNotFoundException,IOException{
	    Company company = new Company("ARAS");
		String choice="1";
        Scanner input_choice=new Scanner(System.in); 
		String name=" ",surname=" ",id=" ",password=" ";
		int userID=-1;
		System.out.println("Welcome!");
        while(!choice.equals("-1")){
            System.out.println(" ");
            System.out.println("1. Login");
            System.out.println("-1. Exit");
            System.out.println("0.Test");
            System.out.println("Please enter your choice: ");
            choice = input_choice.next();
            if(choice.equals("0")) Test(company);
            if(choice.equals("1")){
				System.out.println("Please enter the information below:");
                name = getInputStr("Name: ", 2);
                surname = getInputStr("Surname: ", 2);
                id = getInputStr("ID: ", 2);
                password = getInputStr("Password: ", 2);
				try {
                    userID = Integer. parseInt(id); 
					Person user = new Person(name,surname,userID,password);
					if(company.getUserClass(user)==CentralAdmin.class){
                        AdminOption(company,new CentralAdmin(name, surname, userID, password, company));
					}
					else if(company.getUserClass(user)==BranchEmployee.class){
                        BranchEmployee employee = (BranchEmployee)company.getUser(userID);
                        BranchEmployeeOption(company,employee);
					}
					else if(company.getUserClass(user)==BranchManager.class){
                        BranchManager manager = (BranchManager)company.getUser(userID);
						BranchManagerOption(company,manager);
					}
					else if(company.getUserClass(user)==TransportationPersonel.class){
                        TransportationPersonel personal = (TransportationPersonel)company.getUser(userID);
						TransportationPersonalOption(company,personal);
					}
					else if(company.getUserClass(user)==Customer.class){
                        Customer customer = (Customer)company.getUser(userID);
						CustomerOption(company,customer);
					}
					else{
						System.out.println("There is no such user");
					}

                } 
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else{
                if(!choice.equals("-1")){
                    System.out.println("Invalid choice.");
                }
                else{
                    System.out.println("Goodbye!");
                }                
            }
            System.out.println(" ");
        }

    }
	public static String getInputStr(String message, int choice){
        Scanner input_choice=new Scanner(System.in);
        String input;
        if(choice==1){
            System.out.println(message);
        }
        else{
            System.out.print(message);
        }
        input=input_choice.next() + input_choice.nextLine();
        return input;
    }
	public static void AdminOption(Company company,CentralAdmin admin) throws FileNotFoundException,IOException{ 
		String choice="1";
        int index,index2;
        Scanner input_choice=new Scanner(System.in);
        String name=" ",surname="",id=" ",password="",employeeName="";
		int cargoID=-1,employeeID=-1,userID=-1;
        System.out.println();


        while(!choice.equals("-1")){
            System.out.println("1. See cargo information");
            System.out.println("2. Add a branch");
            System.out.println("3. Remove a branch");
            System.out.println("4. Add a branch employee to branch");
            System.out.println("5. Remove a branch employee from branch");
			System.out.println("6. Add a branch manager to branch");
            System.out.println("7. Remove a branch manager from branch");
			System.out.println("8. Add a transportation personel to branch");
            System.out.println("9. Remove a transportation personel from branch");
            System.out.println("10. See information of user");
            System.out.println("11. Close System");
            System.out.println("-1. Return Home");
            System.out.println("Please enter your choice: ");
            choice = input_choice.next();
            if(choice.equals("1")){
				System.out.println("Please enter the information below:");
                name = getInputStr("Branch name: ", 2);
                id = getInputStr("Cargo ID: ", 2);
				try {
                    cargoID = Integer. parseInt(id); 
					admin.seeCargoContent(name,cargoID);

                } 
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("2")){ 
				System.out.println("Please enter the information below:");
                name = getInputStr("Branch name: ", 2);
				if(admin.addBranch(new Branch(name,company.getbranchCounter()+1,company))){
					company.setbranchCounter(company.getbranchCounter()+1);
				}
				else{
					System.out.println("This could not be added");
				}

            }
            else if(choice.equals("3")){
				System.out.println("Please enter the information below:");
                name = getInputStr("Branch name: ", 2);
				Branch branch = company.checkBranch(name);
				if(branch!=null){
					admin.removeBranch(branch);
				}
				else{
					System.out.println("There is no such branch");
				}
            }
            else if(choice.equals("4")){ 
				System.out.println("Please enter the information below:");
                employeeName = getInputStr("Name: ", 2);
                surname = getInputStr("Surname: ", 2);
                password = getInputStr("Password: ", 2);
				name = getInputStr("Branch name: ", 2);
                Branch br = company.checkBranch(name);
				BranchEmployee employee = new BranchEmployee(employeeName, surname,password,company,br); 
				if(admin.addEmployee(employee,name)){
					System.out.println("This employee has been added and Employee's ID:"+employee.getId());
				}
				else{
					System.out.println("This employee cannot be added");
				}
            }
            else if(choice.equals("5")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Employee's ID: ", 2);
				name = getInputStr("Branch name: ", 2);
                try {
                    employeeID = Integer. parseInt(id); 
                    if(admin.removeEmployee(employeeID,name)){
                        System.out.println("This employee has been deleted.");
                    }
                    else{
					System.out.println("This employee cannot be deleted");
				}

                } 
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("6")){
                System.out.println("Please enter the information below:");
                employeeName = getInputStr("Name: ", 2);
                surname = getInputStr("Surname: ", 2);
                password = getInputStr("Password: ", 2);
				name = getInputStr("Branch name: ", 2);
                Branch br = company.checkBranch(name);
				BranchManager employee = new BranchManager(employeeName, surname,password,company,br); 
				if(admin.addEmployee(employee,name)){
					System.out.println("This branch manager has been added and Employee's ID:"+employee.getId());
				}
				else{
					System.out.println("This branch manager cannot be added");
				}
            }
            else if(choice.equals("7")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Branch manager's ID: ", 2);
				name = getInputStr("Branch name: ", 2);
                try {
                    employeeID = Integer. parseInt(id); 
                    if(admin.removeEmployee(employeeID,name)){
                        System.out.println("This branch manager has been deleted.");
                    }
                    else{
					System.out.println("This branch manager cannot be deleted");
				}

                } 
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("8")){
                System.out.println("Please enter the information below:");
                employeeName = getInputStr("Name: ", 2);
                surname = getInputStr("Surname: ", 2);
                password = getInputStr("Password: ", 2);
				name = getInputStr("Branch name: ", 2);
                Branch br = company.checkBranch(name);
				TransportationPersonel employee = new TransportationPersonel(employeeName, surname,password,company,br); 
				if(admin.addEmployee(employee,name)){
					System.out.println("This transportation personel has been added and Employee's ID:"+employee.getId());
				}
				else{
					System.out.println("This transportation personel cannot be added");
				}
            }
			else if(choice.equals("9")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Transportation personel's ID: ", 2);
				name = getInputStr("Branch name: ", 2);
                try {
                    employeeID = Integer. parseInt(id); 
                    if(admin.removeEmployee(employeeID,name)){
                        System.out.println("This transportation personel has been deleted.");
                    }
                    else{
					System.out.println("This transportation personel cannot be deleted");
                    }
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
			else if(choice.equals("10")){
				id = getInputStr("Please enter the id of the user whose information you want to see: ", 2);
                try {
                    userID = Integer. parseInt(id); 
                    admin.seeUserInformation(userID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }

            }
			else if(choice.equals("11")){
                System.exit(0);
            }
            else{
                if(!choice.equals("-1")){
                    System.out.println("Invalid choice.");
                }
                else{
                    System.out.println("Returning home...");
                }                
            }
            System.out.println(" ");
        }
	}
    public static void BranchEmployeeOption(Company company,BranchEmployee employee) throws FileNotFoundException,IOException{ 
        String choice="1";
        Scanner input_choice=new Scanner(System.in);
        String name=" ",surname="",id=" ",password="",id2="",adress="",id3="";
        String location1="",location2="",date="",bill="",money_str="";
		int cargoID=-1,userID=-1,userID2=-1;
        double money=0.0;
        boolean isValid=true;
        System.out.println();

        while(!choice.equals("-1")){
            System.out.println("1. Add customer");
            System.out.println("2. Add new cargo");
            System.out.println("3. Search cargo");
            System.out.println("4. Add money");
			System.out.println("5. Remove money");
            System.out.println("6. Add bill");
			System.out.println("7. Remove bill");
            System.out.println("8. Inform branch manager");
            System.out.println("-1. Return Home");
            System.out.println("Please enter your choice: ");
            choice = input_choice.next();
            if(choice.equals("1")){
                System.out.println("Please enter the information below:");
                name = getInputStr("Name: ", 2);
                surname = getInputStr("Surname: ", 2);
                password = getInputStr("Password: ", 2);
                adress = getInputStr("Adress: ", 2);
                Customer customer = new Customer(name,surname,password);
                customer.setCompany(company);
                customer.setAdress(adress);
				if(employee.addCustomer(customer)){
					System.out.println("This customer has been added and customer's ID:"+customer.getId());
				}
				else{
					System.out.println("This customer cannot be added");
				}
            }
            else if(choice.equals("2")){ 
                System.out.println("Please enter the information below:");
                date = getInputStr("Date: ", 2);
                bill = getInputStr("Bill: ", 2);
                id = getInputStr("Sender id: ", 2);
                id2 = getInputStr("Receiver id: ", 2);
                location1 = getInputStr("from: ", 2);
                location2 = getInputStr("to: ", 2);
                try {
                    userID = Integer. parseInt(id); 
                    userID2 = Integer. parseInt(id2); 
                    Customer sender= (Customer)company.getUser(userID);
                    Customer receiver=(Customer)company.getUser(userID2);
                    Cargo cargo = new Cargo(location1, location2 ,date , bill , sender , receiver,company);
                    if(company.checkBranch(location1)==null || company.checkBranch(location2)==null){
                        System.out.println("There is no such branch.");
                        isValid=false;
                    }
                    if(sender==null || receiver==null){
                        System.out.println("There is no such customers.");
                        isValid=false;
                    }
                    if(isValid){
                        employee.addCargo(cargo);
                        System.out.println("Cargo id: "+cargo.getId());
                    }
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
                isValid=true;
            }
            else if(choice.equals("3")){ 
                id = getInputStr("Please enter the corgo's id: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    employee.seeCargoContent(cargoID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("4")){
                money_str = getInputStr("Please enter the money: ", 2);
                try {
                    money = Double.parseDouble(money_str); 
                    employee.updateMoney(money);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Dobule (Dobule Only for money)");
                }
            }
            else if(choice.equals("5")){
                money_str = getInputStr("Please enter the money: ", 2);
                try {
                    money = Double.parseDouble(money_str); 
                    employee.updateMoney(-money);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Dobule (Dobule Only for money)");
                }
            }
            else if(choice.equals("6")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Cargo id: ", 2);
                bill = getInputStr("Bill: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    employee.addBill(cargoID,bill);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("7")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Cargo id: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    employee.removeBill(cargoID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
			else if(choice.equals("8")){
                System.out.println("Please enter the information below:");
                name = getInputStr("Message: ", 2);
                employee.emergencyMessage(name);
            }
            else{
                if(!choice.equals("-1")){
                    System.out.println("Invalid choice.");
                }
                else{
                    System.out.println("Returning home...");
                }                
            }
            System.out.println(" ");
            isValid=true;
        }
    }
    public static void BranchManagerOption(Company company,BranchManager manager) throws FileNotFoundException,IOException{ 
        String choice="1";
        Scanner input_choice=new Scanner(System.in);
        String name=" ",surname="",id=" ",password="";
		int cargoID=-1,userID=-1;
        System.out.println();

        while(!choice.equals("-1")){
            System.out.println("1. Add customer");
            System.out.println("2. Remove customer");
            System.out.println("3. Deny cargo");
            System.out.println("4. See employee information"); 
            System.out.println("5. See cargo information");
            System.out.println("6. See emergency message");
            System.out.println("-1. Return Home");
            System.out.println("Please enter your choice: ");
            choice = input_choice.next();
            if(choice.equals("1")){
                System.out.println("Please enter the information below:");
                name = getInputStr("Name: ", 2);
                surname = getInputStr("Surname: ", 2);
                password = getInputStr("Password: ", 2);
                Customer customer = new Customer(name,surname,password);
				if(manager.addCustomer(customer)){
					System.out.println("This customer has been added and customer's ID:"+customer.getId());
				}
				else{
					System.out.println("This customer cannot be added");
				}
            }
            else if(choice.equals("2")){ 
                System.out.println("Please enter the information below:");
                id = getInputStr("Customer id: ", 2);
                try {
                    userID = Integer. parseInt(id); 
                    if(manager.removeCustomer(userID)){
					    System.out.println("This customer has been deleted.");
				    }
				    else{
					    System.out.println("This customer cannot be deleted");
				    }
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }

            }
            else if(choice.equals("3")){
                id = getInputStr("Cargo id: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    if(manager.denyCargo(cargoID)){
					    System.out.println("This cargo has been denied.");
				    }
				    else{
					    System.out.println("This cargo cannot be denied.");
				    }
				}
                catch (Exception exp){
                    System.out.println(exp.getMessage());
                }
            }
            else if(choice.equals("4")){ 
                System.out.println("Please enter the information below:");
                id = getInputStr("Employee id: ", 2);
                try {
                    userID = Integer. parseInt(id); 
                    manager.viewEmployeeInfo(userID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("5")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Cargo id: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    manager.seeCargoContent(cargoID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("6")){
                manager.seeEmergenyMessage();
            }
            else{
                if(!choice.equals("-1")){
                    System.out.println("Invalid choice.");
                }
                else{
                    System.out.println("Returning home...");
                }                
            }
            System.out.println(" ");
        }
    }
    public static void TransportationPersonalOption(Company company,TransportationPersonel employee) throws FileNotFoundException,IOException{ 
        String choice="1";
        Scanner input_choice=new Scanner(System.in);
        String name=" ",surname="",id=" ",password="";
		int cargoID=-1,userID=-1;
        System.out.println();

        while(!choice.equals("-1")){
            System.out.println("1. See cargo information");
            System.out.println("2. See customer information");
            System.out.println("3. Update cargo status");
            System.out.println("-1. Return Home");
            System.out.println("Please enter your choice: ");
            choice = input_choice.next();
            if(choice.equals("1")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Cargo id: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    employee.seeCargoContent(cargoID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("2")){ 
                System.out.println("Please enter the information below:");
                id = getInputStr("Enter the cargo id to see the customer: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    employee.seeCustomerInfo(cargoID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }

            }
            else if(choice.equals("3")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Cargo id: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    employee.updateCargoStatus(cargoID);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else{
                if(!choice.equals("-1")){
                    System.out.println("Invalid choice.");
                }
                else{
                    System.out.println("Returning home...");
                }                
            }
            System.out.println(" ");
        }

    }
    public static void CustomerOption(Company company,Customer customer) throws FileNotFoundException,IOException{ 
        String choice="1";
        Scanner input_choice=new Scanner(System.in);
        String name=" ",surname="",id=" ",password="",complain="";
		int cargoID=-1,userID=-1;
        System.out.println();

        while(!choice.equals("-1")){
            System.out.println("1. Search cargo");
            System.out.println("2. Upload a complaint");
            System.out.println("3. View bill");
            System.out.println("-1. Return Home");
            System.out.println("Please enter your choice: ");
            choice = input_choice.next();
            if(choice.equals("1")){
                System.out.println("Please enter the information below:");
                id = getInputStr("Please enter the cargo's ID: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    Cargo cargo = customer.searchCargo(cargoID);
                    System.out.println(cargo);
				}
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else if(choice.equals("2")){
                complain = getInputStr("Please enter the complain:",2);
                customer.complain(complain);
                System.out.println("Complaint received.");
            }
            else if(choice.equals("3")){ 
                System.out.println("Please enter the information below:");
                id = getInputStr("Please enter the cargo's ID: ", 2);
                try {
                    cargoID = Integer. parseInt(id); 
                    String str = customer.seeBill(cargoID);
                    if(str!=null){
                        System.out.println(str);
                    }
                    else{
                        System.out.println("There is no such cargo or bill");
                    }
                }
                catch (NumberFormatException exp){
                    System.out.println("Incorrect Integer (Integer Only for id)");
                }
            }
            else{
                if(!choice.equals("-1")){
                    System.out.println("Invalid choice.");
                }
                else{
                    System.out.println("Returning home...");
                }                
            }
            System.out.println(" ");
        }
    }

    public static void Test(Company company) throws FileNotFoundException,IOException{
        CentralAdmin admin = new CentralAdmin("Test", "Testoglu", "123456",company);
        if(company.addPerson(admin)){
            System.out.println("admin can added.");
            System.out.println(admin);
        }
        try {
            FileReader fr = new FileReader("Branches.txt");
            System.out.println("T-1 Successful");
            fr.close();
        } catch (Exception e) {
            System.err.println("T-1 Failed");
        }
        try {
            FileReader fr = new FileReader("Persons.txt");
            System.out.println("T-2 Successful");
            fr.close();
        } catch (Exception e) {
            System.err.println("T-2 Failed");
        }
        try {
            FileReader fr = new FileReader("Cargos.txt");
            System.out.println("T-3 Successful");
            fr.close();
        } catch (Exception e) {
            System.err.println("T-3 Failed");
        }
        System.out.println(" T-4 has already been done in menu");
        Branch br = new Branch("Test_",company.getbranchCounter()+1,company);
        if(admin.addBranch(br)){
            company.setbranchCounter(company.getbranchCounter()+1);
            System.out.println( "T-5 Successful");
        }
        else System.err.println( "T-5 Failed");
        
        company.setbranchCounter(company.getbranchCounter()+1);
        BranchEmployee employee = new BranchEmployee("Test", "TestOglu","123456",company,br); 
        if(admin.addEmployee(employee,br.getName())) System.out.println("T-7 Successful");
        else System.err.println("T-7 Failed");
        
		BranchManager employee2 = new BranchManager("Test", "TestOglu","123456",company,br); 
		if(admin.addEmployee(employee2,br.getName())) System.out.println("T-9 Successful");
        else System.err.println( "T-9 Failed");
        
        TransportationPersonel employee3 = new TransportationPersonel("test", "testgolu","123456",company,br); 
        if(admin.addEmployee(employee3,br.getName()))System.out.println("T-11 Successful");
        else System.err.println("T-11 Failed");
        
        System.out.println("admin tries to add branch employee.");
        if(admin.addEmployee(employee,br.getName())){
            System.out.println("admin can added employee");
        }
        else{
            System.out.println("admin cannot added employee");
        }
        System.out.println("employee tries to add customer.");
        Customer customer = new Customer("Jane","Doe","1234");
        Customer customer2 = new Customer("Jane2","Doe2","12345");
        customer.setCompany(company);
        customer2.setCompany(company);
        customer.setAdress("USA");
        customer2.setAdress("Turkey");
        if( employee.addCustomer(customer)){
            System.out.println("employee can added customer");
        }
        else{
            System.out.println("employee cannot added customer");
        }
        employee.addCustomer(customer2);
        System.out.println("employee tries to add cargo");
        Cargo cargo = new Cargo("Mersin", "Adana" ,"25062021", "from:Mersin to:Adana, date:25062021" , customer , customer2,company);
        if(employee.addCargo(cargo)){
            System.out.println("employee can added cargo");
        }
        else{
            System.out.println("employee cannot added cargo");
        }
        System.out.println("employee tries to see cargo information");
        employee.seeCargoContent(cargo.getId());
        System.out.println("employee tries to add emergency message");
        employee.emergencyMessage("Emergency Message");
        System.out.println("employee can added emergency message");
        System.out.println("employee getBranch method test");
        System.out.println("Employee's branch name:" + employee.getBranch().getName());
        System.out.println("employee tries to remove bill to cargo");
        employee.removeBill(cargo.getId());
        employee.seeCargoContent(cargo.getId());
        System.out.println("employee tries to add money, before:" + employee.getBranch().getMoney());
        employee.updateMoney(100.00);
        System.out.println("employee add money, after:" + employee.getBranch().getMoney());
        System.out.println("manager tries to deny cargo");
        if(employee2.denyCargo(cargo.getId())){
			System.out.println("This cargo has been denied.");
		}
		else{
		    System.out.println("This cargo cannot be denied.");
		}
        System.out.println("manager tries to remove customer");
        Customer customer3 = new Customer("Jane3","Doe3","12345");
        customer3.setCompany(company);
        customer3.setAdress("ABD");
        employee.addCustomer(customer3);
        if(employee2.removeCustomer(customer3.getId())){
				System.out.println("This customer has been deleted.");
		}
		else{
			System.out.println("This customer cannot be deleted");
		}
        System.out.println("manager tries to view employee information");
        employee2.viewEmployeeInfo(employee.getId());
        
		if(admin.removeEmployee(employee.getId(),br.getName())) System.out.println("T-8 Successful");
        else System.err.println("T-8 Failed");		
        if(admin.removeEmployee(employee2.getId(),br.getName())) System.out.println("T-10 Successful");
        else System.out.println("T-10 Failed");
        if(admin.removeEmployee(employee3.getId(),br.getName())) System.out.println("T-12 Successful");
        else System.err.println("T-12 Failed");
        if (admin.removeBranch(br)) System.out.println("T-6 Successful");
        else System.err.println( "T-6 Failed");
        
    }
}
