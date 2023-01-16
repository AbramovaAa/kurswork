package kurswork.hospital.controller;

import jakarta.validation.Valid;
import kurswork.hospital.repository.RoleRepository;
import kurswork.hospital.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import kurswork.hospital.dto.UserDto;
import kurswork.hospital.entity.User;
import kurswork.hospital.service.UserService;

import java.util.Arrays;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public AuthController(UserService userService,RoleRepository roleRepository,UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("home")
    public String home1(){
        return "home";
    }

    @GetMapping("")
    public String start(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }
    @GetMapping("admin")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        List<String> roles= Arrays.asList("ADMIN","DOCTOR","CHIEF");
        model.addAttribute("roles",roles);
        return "admin";
    }

    @PostMapping("/admin/{user}")
    public String userEdit(@PathVariable User user,@RequestParam String role_name){
        user.getRoles().clear();
        user.getRoles().add(roleRepository.findByName("ROLE_"+role_name));
        userRepository.save(user);
        return "redirect:/admin";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

}
