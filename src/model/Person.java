package model;

import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Encapsulation - private fields
    private String id;
    private String name;
    private int age;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;

    public Person() {}

    public Person(String id, String name, int age, String gender, 
                  String phoneNumber, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    // Getters and Setters (Encapsulation)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // Polymorphism - abstract method to be overridden
    public abstract String getRole();

    @Override
    public String toString() {
        return String.format("ID: %-8s | Name: %-15s | Age: %-3d | Gender: %-6s | Role: %-8s",
                id, name, age, gender, getRole());
    }
}