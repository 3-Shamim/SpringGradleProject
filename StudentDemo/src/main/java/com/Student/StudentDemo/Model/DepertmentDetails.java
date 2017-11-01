package com.Student.StudentDemo.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class DepertmentDetails {
    @Id
    @GeneratedValue
    private int Id;

    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String DeptName;
    @OneToMany()
    @JoinColumn(name = "depertment_details_id")
    private List<StudentDetails> studentList = new ArrayList<>();

    public DepertmentDetails() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public List<StudentDetails> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDetails> studentList) {
        this.studentList = studentList;
    }
}
