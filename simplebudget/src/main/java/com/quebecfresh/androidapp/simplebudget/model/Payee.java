package com.quebecfresh.androidapp.simplebudget.model;

/**
 * Created by Tong Huang on 2015-02-13, 9:27 AM.
 */
public class Payee extends BaseData {

    private String address;
    private String telephone;
    private String contactPerson;

    public Payee(){

    }

    public Payee(String name){
        super(name);
    }

    public Payee(String name, String note){
        super(name, note);
    }

    public Payee(Long id, String name, String note){
        super(id,name,note);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
