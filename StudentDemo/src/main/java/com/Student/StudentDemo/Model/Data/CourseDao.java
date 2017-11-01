package com.Student.StudentDemo.Model.Data;

import com.Student.StudentDemo.Model.CourseDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CourseDao extends CrudRepository<CourseDetails, Integer> {

}
