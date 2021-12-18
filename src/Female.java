import java.io.Serializable;

public class Female extends Person implements Serializable {
    public Female(String firstName, String lastName, int userID,long phoneNumber, int age, String sexualOrientation, String description){
        super(firstName,lastName,userID,phoneNumber,age,sexualOrientation,description);
    }
    public String getGender(){
        return "female";
    }
    public String toString(){
        String a = "person:"+" "+getGender()+" "+getFirstName()+" "+getLastName()+" "+getUserID()+" "+getPhoneNumber()+" "+getAge()+" "+getSexualOrientation()+" "+getDescriptionBIO();
        return a;
    }
}
