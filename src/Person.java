
import java.util.Random;
/**
 * Person information class
 * @author Caner AKIN
 */
public class Person implements Comparable<Person>{
    /**
     * Person name
     */
    private String name;
    /**
     * Person surname
     */
    private String surname;
    /**
     * person id
     */
    private int id;
    /**
     * person password
     */
    private String password;

    /**
     * No parameter constructor
     */
    public Person(){
        name = new String("");
        surname = new String("");
        Random random = new Random();
        id = random.nextInt(100000);
        password = new String("");
    }

    /**
     * Constructor is create for give a id with random
     * @param name is person name
     * @param surname is person surname
     * @param password is person password
     */
    public Person(String name, String surname,String password){
        this.name = name;
        this.surname = surname;
        this.password = password;
        Random random = new Random();
        id = random.nextInt(100000);
    }

    /**
     * Constructor is work for take all information about person
     * @param name is person name
     * @param surname is person surname
     * @param id is person id
     * @param password is person password
     */
    public Person(String name, String surname,int id ,String password){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.id = id;
    }

    /**
     * get method about person name
     * @return  is person name
     */
    public String getName() {
        return name;
    }

    /**
     * get method about surname
     * @return is person surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * get method about id
     * @return is person id
     */
    public int getId() {
        return id;
    }

    /**
     * get method about password
     * @return is person password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set method about name
     * @param name is person name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set method about surname
     * @param surname is person surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * set method about id
     * @param id is person id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * set method about password
     * @param password is person password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * ToString methods
     * @return the information of person
     */
    public String toString(){
        return name + " " + surname + " " + id + " " + password;
    }

    @Override
    public boolean equals(Object o)
    {
        if( o == this) return true;
        if( !(o instanceof Person)) return false;
        if(((Person) o).getName().equals(this.name) && ((Person) o).getSurname().equals(this.surname) &&
                ((Person) o).getId()==this.id && ((Person) o).getPassword().equals(this.password))
        {
            return true;
        }
        else return false;
    }

    @Override
    public int compareTo(Person p) {
        if( p == this) return 0;
        if( this.id < p.getId()) return -1;
        if( this.id > p.getId()) return  1;
        return 0;
    }
}
