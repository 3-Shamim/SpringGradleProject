package com.Student.StudentDemo.Controller;

import com.Student.StudentDemo.Model.CourseDetails;
import com.Student.StudentDemo.Model.Data.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "Course")
public class CourseController {

    @Autowired
    private CourseDao courseDao;

    @RequestMapping(value = "")
    public String courseList(Model model){
        model.addAttribute("title","Course List");
        model.addAttribute("List",courseDao.findAll());
        return "Course/Course";
    }
    @RequestMapping(value = "addCourse", method = RequestMethod.GET)
    public String displayAddCourse(Model model){
        model.addAttribute("courseDetails",new CourseDetails());
        model.addAttribute("title","Course List");
        return "Course/addCourse";
    }
    @RequestMapping(value = "addCourse", method = RequestMethod.POST)
    public String processAddCourse(@ModelAttribute @Valid CourseDetails course, Errors error,Model model){
        if(error.hasErrors())
        {
            model.addAttribute("title","Course List");
            return "Course/addCourse";
        }
        courseDao.save(course);
        return "redirect:";
    }
}
