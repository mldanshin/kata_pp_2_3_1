package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.models.User;
import web.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", service.getList());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", service.getById(id));
        return "show";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute("user") User user) {
        service.store(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", service.getById(id));
        return "edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user) {
        service.update(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest request) {
        service.delete(id);
        return "redirect:/";
    }
}
