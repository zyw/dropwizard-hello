package cn.c5cn.dropwizard.core;

/**
 * Created by ZYW on 2014/6/17.
 */
public class Contact {
    private final int id;
    private final String fistName;
    private final String lastName;
    private final String phone;

    public Contact(){
        this.id = 0;
        this.fistName = "";
        this.lastName = "";
        this.phone = "";
    }
    public Contact(int id,String fistName,String lastName,String phone){
        this.id = id;
        this.fistName = fistName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFistName() {
        return fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
}
