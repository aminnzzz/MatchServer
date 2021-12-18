import java.io.Serializable;
import java.util.HashMap;

public abstract class Person implements Serializable {
    private String firstName;
    private String lastName;
    private int userID;
    private int age;
    private String sexualOrientation;
    private String descriptionBIO;
    private long phoneNumber;
    private HashMap<String ,Integer> myMatches;//userID and score

    public Person(String firstName, String lastName, int userID, long phoneNumber, int age, String sexualOrientation, String descriptionBIO){
        this.firstName = firstName;
        this.lastName= lastName;
        this.userID=userID;
        this.age=age;
        this.sexualOrientation=sexualOrientation;
        this.descriptionBIO=descriptionBIO;
        this.phoneNumber=phoneNumber;
    }
//    public String toString(){
//        return "person:"+" "+getFirstName()+" "+getLastName()+" "+getUserID()+" "+getPhoneNumber()+" "+getAge()+" "+getSexualOrientation()+" "+getDescriptionBIO();
//    }
    abstract public String getGender();
    public String getFirstName(){
        return this.firstName;
    }
    public long getPhoneNumber(){return this.phoneNumber ; }
    public String getLastName(){
        return this.lastName;
    }
    public int getAge(){
        return this.age;
    }
    public String getSexualOrientation(){
        return this.sexualOrientation;
    }
    public int getUserID(){
        return this.userID;
    }
    public String getDescriptionBIO(){
        return this.descriptionBIO;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public void setUserID(int userID){
        this.userID=userID;
    }
    public void setPassword(long phoneNumber){this.phoneNumber=phoneNumber;}
    public void setAge(int age){
        this.age=age;
    }
    public void setSexualOrientation(String sexualOrientation){
        this.sexualOrientation=sexualOrientation;
    }
    public void setDescriptionBIO(String descriptionBIO){
        this.descriptionBIO=descriptionBIO;
    }

}
