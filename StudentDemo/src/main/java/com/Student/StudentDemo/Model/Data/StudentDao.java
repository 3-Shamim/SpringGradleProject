package com.Student.StudentDemo.Model.Data;

import com.Student.StudentDemo.Model.StudentDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StudentDao extends CrudRepository<StudentDetails, Integer> {
}
