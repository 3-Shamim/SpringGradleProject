package com.GradleProject.shopManagementSystem.Model.DAO;

import com.GradleProject.shopManagementSystem.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Integer> {

}
