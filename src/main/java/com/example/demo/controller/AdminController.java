package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users")
    public String getAllUsers( Model model) {
        List<User> list = userService.getAllUsers();
        model.addAttribute("listOfUsers", list);
        return "ADMIN/users";
    }

    @GetMapping(value = "/user/{id}")
    public String getUser(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return "User/user";
    }

    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user1", new User());
        model.addAttribute("listOfRoles", roleService.getAllRoles());
        return "ADMIN/new";
    }

    @PostMapping(value = "/users")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(value = "listRoleID", required = false, defaultValue = "2")
                             List<String> listRoleID) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/new";
        }
        Set<Role> set = new HashSet<>();
        for (String roleID : listRoleID) {
            Optional<Role> optionalRole = roleService.findById((Long.parseLong(roleID)));
            Role role = new Role();
            if (optionalRole.isPresent()) {
                role = optionalRole.get();
            }
            set.add(role);
        }
        user.setRoles(set);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/{id}/update")
    public String update(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("list", roleService.getAllRoles());
        return "ADMIN/update";
    }

    @PatchMapping(value = "/{id}")
    public String update1(@ModelAttribute("user") User user) {
        System.out.println(user.getPass());
        userService.update(user);
        return "redirect:users";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
