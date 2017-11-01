package com.Student.StudentDemo.Model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CourseDetails {

    @Id
    @GeneratedValue
    private int Id;
    private Integer courseCredit;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String courseCode;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String courseTitle;

    @ManyToMany(mappedBy = "courseDetails")
    private List<StudentDetails> studentDetails = new ArrayList<>();

    public CourseDetails() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Integer getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(Integer courseCredit) {
        this.courseCredit = courseCredit;
    }

    public List<StudentDetails> getStudentDetails() {
        return studentDetails;
    }
}
