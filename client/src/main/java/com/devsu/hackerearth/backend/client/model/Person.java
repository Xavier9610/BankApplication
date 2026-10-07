package com.devsu.hackerearth.backend.client.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends Base {
    
    private String name;
    private String dni;
    private String gender;
    private int age;
    private String address;
    private String phone;

    // --- Getters y Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}