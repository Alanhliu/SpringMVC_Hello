package com.siasun.entities;

/**
 * Created by hliu on 16/9/16.
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private int age;
    private String email;
    private Address address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if (this.password == null) {
            this.password = "null";
        }
        return "id:"+this.id+",username:"+this.username+",password:"+this.password+",age:"+this.age
                +",email:"+this.email;
    }

    public User() {

    }

    public User(Integer id, String username, String password, int age, String email, Address address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
    }
}
