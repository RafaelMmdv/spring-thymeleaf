package com.project.mvcthyme;

import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class PageController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/")
    public String home(){
        return "index";

    }

    @GetMapping("/students")

    public  String students(Model model){

        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/create")
    public  String create(Model model){

        Student student = new Student();
        model.addAttribute("student",student);
        return "create";

    }

    @PostMapping("/create")
    public String create(@ModelAttribute Student student){

        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete")
    public String delete(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Student student){

        studentRepository.delete(student);
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit user (ID:" + id + ")");
        ra.addFlashAttribute("message", "The user added successfully.");
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/login")
    public String login (){

        return "login";
    }

    @PostMapping("/login")
    public String login (@RequestParam("firstName") String firstName, String password, Model model ){
        Student student = studentRepository.findByFirstName(firstName);
        if (student != null && student.getPassword().equals(password)) {
            // Successful login
            return "redirect:/students";
        } else {
            // Invalid credentials
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

}
