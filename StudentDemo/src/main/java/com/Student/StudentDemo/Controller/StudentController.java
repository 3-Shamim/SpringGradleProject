package com.Student.StudentDemo.Controller;

import com.Student.StudentDemo.Model.CourseDetails;
import com.Student.StudentDemo.Model.Data.CourseDao;
import com.Student.StudentDemo.Model.Data.DepertmentDao;
import com.Student.StudentDemo.Model.Data.StudentDao;
import com.Student.StudentDemo.Model.DepertmentDetails;
import com.Student.StudentDemo.Model.StudentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "Student")
public class StudentController {

    private List<StudentDetails> sortedStudentList = new ArrayList<>();
    private String DeptName;
    private int DeptId;

    private List<CourseDetails> sortedCouserList = new ArrayList<>();
    private String sutdentId;
    private String sutdentName;
    private int sutdentkeyId;
    private int totalCredit = 0;

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private DepertmentDao depertmentDao;
    @Autowired
    private CourseDao courseDao;

    @RequestMapping(value = "")
    public String index(Model model)
    {
        model.addAttribute("studentList", studentDao.findAll());
        model.addAttribute("title", "Student table");
        return "Student/Student";
    }
    @RequestMapping(value = "addStudent",method = RequestMethod.GET)
    public String displayAddStudent(Model model)
    {
        model.addAttribute("studentDetails", new StudentDetails());
        model.addAttribute("deptList",depertmentDao.findAll());
        model.addAttribute("title", "Add Student");
        return "Student/addStudent";
    }
    @RequestMapping(value = "addStudent",method = RequestMethod.POST)
    public String processAddStudent(@ModelAttribute @Valid StudentDetails student, Errors error,Model model)
    {
        if(error.hasErrors())
        {
            model.addAttribute("deptList",depertmentDao.findAll());
            model.addAttribute("title", "Add Student");
            return "Student/addStudent";
        }
        studentDao.save(student);
        return "redirect:";
    }
    @RequestMapping(value = "removeStudent",method = RequestMethod.GET)
    public String displayRemoveStudent(Model model)
    {
        model.addAttribute("studentList", studentDao.findAll());
        model.addAttribute("title", "Remove Student");
        return "Student/removeStudent";
    }
    @RequestMapping(value = "removeStudent",method = RequestMethod.POST)
    public String processRemoveStudent(@RequestParam(required = false) int[] studentCheckedId,Model model)
    {
        if(!(studentCheckedId == null))
        {
            for (int id: studentCheckedId) {
                studentDao.delete(id);
            }
        }
        else
        {
            model.addAttribute("studentList", studentDao.findAll());
            model.addAttribute("title", "Remove Student");
            return "Student/removeStudent";
        }

        return "redirect:";
    }
    @RequestMapping(value = "removeSortedStudent",method = RequestMethod.GET)
    public String displayRemoveSortedStudent(Model model)
    {
        if(DeptName == null)
        {
            DeptName = "There no selected Department";
        }
        model.addAttribute("studentList", sortedStudentList);
        model.addAttribute("title", "Remove Student : " + DeptName);

        return "Student/removeSortedStudent";
    }
    @RequestMapping(value = "removeSortedStudent",method = RequestMethod.POST)
    public String processRemoveSortedStudent(@RequestParam(required = false) int[] studentCheckedId,Model model)
    {
        if(!(studentCheckedId == null))
        {
            for (int id: studentCheckedId) {
                studentDao.delete(id);
            }
        }
        else
        {
            model.addAttribute("studentList", sortedStudentList);
            model.addAttribute("title", "Remove Student : " + DeptName);

            return "Student/removeSortedStudent";
        }
        DepertmentDetails d = depertmentDao.findOne(DeptId);
        sortedStudentList = d.getStudentList();
        DeptName = d.getDeptName();
        model.addAttribute("studentList", sortedStudentList);

        return "Student/sortedStudent";
    }
    @RequestMapping(value="asDepertment", method=RequestMethod.GET)
    public String getEditPerson(@RequestParam("deptId") int deptId, Model model) {
        DeptId = deptId;
        DepertmentDetails d = depertmentDao.findOne(DeptId);
        sortedStudentList = d.getStudentList();
        DeptName = d.getDeptName();
        model.addAttribute("studentList", sortedStudentList);
        model.addAttribute("title", "Student table : "+ DeptName);

        return "Student/sortedStudent";
    }

    @RequestMapping(value="addCourseForStudent", method=RequestMethod.GET)
    public String addSelectedStudentCourse(Model model) {
        model.addAttribute("courseList", courseDao.findAll());
        model.addAttribute("studentKeyId", sutdentkeyId);
        model.addAttribute("title", sutdentName + " : " + sutdentId);

        return "Student/addCourseForStudent";
    }
    @RequestMapping(value="addCourseForStudent", method=RequestMethod.POST)
    public String processaddSelectedStudentCourse(@RequestParam("studentKeyId") int studentKeyId,
                                                  @RequestParam("courseKeyId") int courseKeyId, Model model) {
        CourseDetails courseDetails = courseDao.findOne(courseKeyId);
        StudentDetails studentDetails = studentDao.findOne(studentKeyId);

        getCredit();
        totalCredit += courseDetails.getCourseCredit();

        for (CourseDetails c: sortedCouserList) {

            if(totalCredit > 15)
            {
                model.addAttribute("courseList", courseDao.findAll());
                model.addAttribute("studentKeyId", sutdentkeyId);
                model.addAttribute("title", sutdentName + " : " + sutdentId);
                model.addAttribute("errors", "You have already taken 15 credit");
                totalCredit = 0;

                return "Student/addCourseForStudent";
            }
            else if(courseKeyId == c.getId())
            {
                model.addAttribute("courseList", courseDao.findAll());
                model.addAttribute("studentKeyId", sutdentkeyId);
                model.addAttribute("title", sutdentName + " : " + sutdentId);
                model.addAttribute("errors", "Course is already selected!");
                totalCredit = 0;

                return "Student/addCourseForStudent";
            }
        }
        studentDetails.addCourse(courseDetails);
        studentDao.save(studentDetails);
        totalCredit = 0;

        return "redirect:/Student/viewCourseForStudent?studentKeyId="+studentKeyId+"";
    }
    @RequestMapping(value="viewCourseForStudent", method=RequestMethod.GET)
    public String getSelectedStudentCourse(@RequestParam("studentKeyId") int studentKeyId, Model model) {
        sutdentkeyId = studentKeyId;
        StudentDetails student = studentDao.findOne(studentKeyId);
        sortedCouserList = student.getCourseDetails();
        sutdentId = student.getStudentId();
        sutdentName = student.getName();
        model.addAttribute("List", sortedCouserList);
        model.addAttribute("title", sutdentName + " : " + sutdentId);
        model.addAttribute("totalCredit", "Total Credit : " + getCredit());
        totalCredit = 0;

        return "Student/viewCourseForStudent";
    }

    public int getCredit()
    {
        for (CourseDetails c: sortedCouserList) {
            totalCredit += c.getCourseCredit();
        }
        return  totalCredit;
    }
}
