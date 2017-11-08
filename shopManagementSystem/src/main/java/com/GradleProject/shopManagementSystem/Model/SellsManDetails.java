package com.GradleProject.shopManagementSystem.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SellsManDetails {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String name;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String password;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String email;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String address;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="sellsManDetails_Product",
            joinColumns=@JoinColumn(name="SellsMan_Id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="Product_Id",referencedColumnName="id"))
    private List<Product> products =  new ArrayList<>();


    public SellsManDetails() {
    }

    public void addProduct(Product product)
    {
        products.add(product);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Product> getProducts() {
        return products;
    }
}
