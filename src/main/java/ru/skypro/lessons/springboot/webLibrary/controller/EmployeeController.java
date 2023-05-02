package ru.skypro.lessons.springboot.webLibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/high-salary")
    public ModelAndView getAboveAveragePaidEmployees() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employees", employeeService.getAboveAveragePaidEmployees());
        modelAndView.setViewName("high-salary");
        return modelAndView;
    }
    @GetMapping("/salary/max")
    public ModelAndView getMaxSalary() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employees", employeeService.getMaxSalaryEmployee());
        modelAndView.setViewName("max");
        return modelAndView;
    }
    @GetMapping("/salary/min")
    public ModelAndView getMinSalary() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employees", employeeService.getMinSalaryEmployee());
        modelAndView.setViewName("min");
        return modelAndView;
    }
    @GetMapping("/salary/sum")
    public ModelAndView getSumSalary() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sum", employeeService.getSalarySum());
        modelAndView.setViewName("sum");
        return modelAndView;
    }
    @GetMapping("/salary")
    public ModelAndView getIndexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        return modelAndView;
    }
}
