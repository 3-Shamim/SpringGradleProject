package com.Student.StudentDemo.Controller;

import com.Student.StudentDemo.Model.Data.DepertmentDao;
import com.Student.StudentDemo.Model.DepertmentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "Depertment")
public class DepertmentController {
    @Autowired
    private DepertmentDao depertmentDao;

    @RequestMapping(value = "")
    public String index(Model model)
    {
        model.addAttribute("deptList",depertmentDao.findAll());
        model.addAttribute("title","Department List");
        return "Depertment/Depertment";
    }
    @RequestMapping(value = "addDepertment", method = RequestMethod.GET)
    public String displayAddDepertment(Model model)
    {
        model.addAttribute("title","Add Department");
        model.addAttribute("depertmentDetails", new DepertmentDetails());
        return "Depertment/addDepertment";
    }
    @RequestMapping(value = "addDepertment",method = RequestMethod.POST)
    public String processAddStudent(@ModelAttribute @Valid DepertmentDetails deptDetails, Errors errors, Model model)
    {
        if(errors.hasErrors())
        {
            model.addAttribute("title","Add Department");
            model.addAttribute("deptDetails", new DepertmentDetails());
            return "Depertment/addDepertment";
        }
        depertmentDao.save(deptDetails);
        return "redirect:";
    }


}
