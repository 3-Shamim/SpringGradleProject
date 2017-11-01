package com.Student.StudentDemo.Model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StudentDetails {
    @Id
    @GeneratedValue
    private int Id;
    @NotNull
    @Size(min = 13,max = 13, message = "Must be 13 Digits!")
    private String studentId;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String Name;
    @NotNull
    @Size(min = 1, message = "Field must not be empty!")
    private String Email;

    @ManyToOne
    private DepertmentDetails depertmentDetails;

    @ManyToMany
    @JoinTable(
            name="StudentDetails_CourseDetails",
            joinColumns=@JoinColumn(name="StudentDetails_Id", referencedColumnName="Id"),
            inverseJoinColumns=@JoinColumn(name="CourseDetails_Id", referencedColumnName="Id"))
    private List<CourseDetails> courseDetails = new ArrayList<>();

    public StudentDetails() {
    }

    public void addCourse(CourseDetails course)
    {
        courseDetails.add(course);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public DepertmentDetails getDepertmentDetails() {
        return depertmentDetails;
    }

    public void setDepertmentDetails(DepertmentDetails depertmentDetails) {
        this.depertmentDetails = depertmentDetails;
    }

    public List<CourseDetails> getCourseDetails() {
        return courseDetails;
    }

}
