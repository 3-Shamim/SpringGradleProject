package com.GradleProject.shopManagementSystem.Model.DAO;

import com.GradleProject.shopManagementSystem.Model.SellsManDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellsManDao extends JpaRepository<SellsManDetails, Integer> {
}
