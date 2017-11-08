package com.GradleProject.shopManagementSystem.Model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String productName;
    @NotNull(message = "Field must not be empty!")
    @Min(value = 1,message = "Field must not be empty!")
    private Double productPrice;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SellsManDetails> sellsManDetails = new ArrayList<>();

    public Product() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public List<SellsManDetails> getSellsManDetails() {
        return sellsManDetails;
    }
}
