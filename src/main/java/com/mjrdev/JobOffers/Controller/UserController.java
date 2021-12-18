package com.mjrdev.JobOffers.Controller;

import com.mjrdev.JobOffers.Model.User;
import com.mjrdev.JobOffers.Service.UserService.UserService;
import com.mjrdev.JobOffers.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    // PASSWORD ENCODER
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    // LOG IN

    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody HashMap<String, String> auth, HttpSession httpSession) {

        HashMap<String, Object> response = new HashMap<>();

        String email = auth.get("email").toLowerCase(Locale.ROOT);
        String password = auth.get("password");

        if(userService.userExist(email)) {
            // EMAIL EXIST

            User user = userService.getUser(email);
            boolean password_match = passwordEncoder.matches(password, user.getPassword());

            if(password_match) {
                // CORRECT PASSWORD
                httpSession.setAttribute("user_id", user.getId());

                response.put("status", 1);
                response.put("action", "LOGGED IN");
                response.put("role", user.getRole());
                response.put("user_id", user.getId());
                response.put("http_session", httpSession);
                response.put("token", null);

            } else {
                // INCORRECT PASSWORD

                response.put("status", 0);
                response.put("error", "INCORRECT PASSWORD");
                response.put("token", null);
            }
        } else {
            // EMAIL DOESN'T EXIST

            response.put("status", 0);
            response.put("error", "EMAIL DOESN'T EXIST");
            response.put("token", null);
        }



        return response;
    }

    // LOG OUT

    @PostMapping("/logout")
    public HashMap<String, Object> logout(HttpSession httpSession) {

        HashMap<String, Object> response = new HashMap<>();
        httpSession.removeAttribute("user_id");
        httpSession.invalidate();
        response.put("status", 1);
        response.put("action", "LOGGED OUT SUCCESS");

        return response;
    }

    // LOG STATUS

    @GetMapping("/log-status")
    public HashMap<String, Object> logStatus(HttpSession httpSession) {

        HashMap<String, Object> response = new HashMap<>();

        Object session_user_id = httpSession.getAttribute("user_id");
        int user_id = session_user_id != null ? Integer.parseInt(session_user_id.toString()) : 0;
        String user_role = userService.userExist(user_id) ? userService.getUser(user_id).getRole() : "";

        response.put("status", session_user_id != null ? 1 : 0ù);
        response.put("message", session_user_id != null ? "LOGGED IN" : "LOGGED OUT");
        response.put("role", user_role);
        response.put("http_session", httpSession);
        response.put("session_user_id", String.valueOf(session_user_id));

        return response;
    }

    // SIGN UP NEW USER

    @PostMapping("/signup")
    public HashMap<String, Object> createUser(@RequestBody User user) {

        HashMap<String, Object> response = new HashMap<>();

        if(!userService.userExist(user.getEmail())) {
            // EMAIL AVAILABLE
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail(user.getEmail().toLowerCase(Locale.ROOT));
            user.setRole(Utility.USER_ROLES.get(user.getRole().toUpperCase(Locale.ROOT)));
            response.put("status", 1);
            response.put("action", "SIGNED UP SUCCESSFULLY");
            response.put("user", userService.saveUser(user));
        } else {
            // EMAIL ALREADY TAKEN
            response.put("status", 0);
            response.put("user", new User());
            response.put("error", "EMAIL TAKEN");
        }

        return response;
    }

    // GET LIST OF ALL USERS

    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    // GET ONE USER BY ID

    @GetMapping("/id/")
    public HashMap<String, Object> getUser(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(id)) {
            // USER EXISTS
            response.put("status", 1);
            response.put("user", userService.getUser(id));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("user", new User());
            response.put("error", "USER DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

    // GET ONE USER BY EMAIL

    @GetMapping("/email/")
    public HashMap<String, Object> getUser(@RequestParam(name = "email") String email) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(email)) {
            // USER EXISTS
            response.put("status", 1);
            response.put("user", userService.getUser(email));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("user", new User());
            response.put("error", "USER DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

    // UPDATE USER

    @PutMapping("/update")
    public HashMap<String, Object> updateUser(@RequestBody User new_user) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(new_user.getId())) {
            // USER EXISTS
            User current_user = userService.getUser(new_user.getId());

            // CHANGEABLE COLUMNS : FIRST NAME, LAST NAME, PASSWORD, BIRTH DATE
            current_user.setFirstName(new_user.getFirstName());
            current_user.setLastName(new_user.getLastName());
            current_user.setPassword(passwordEncoder.encode(new_user.getPassword()));
            current_user.setBirthDate(new_user.getBirthDate());

            if(userService.updateUser(current_user)) {
                // USER UPDATED
                response.put("status", 1);
                response.put("user", current_user);
            } else {
                // USER NOTE UPDATED
                response.put("status", 0);
                response.put("user", new User());
                response.put("error", "ERROR IN THE USER UPDATE OPERATION");
            }

        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("user", new User());
            response.put("error", "USER DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

    // DELETE USER

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteUser(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(id)) {
            // USER EXIST
            if(userService.deleteUser(id)) {
                // USER DELETED
                response.put("status", 1);
            } else {
                // USER NOT DELETED
                response.put("status", 0);
                response.put("error", "ERROR IN THE USER DELETE OPERATION");
            }
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

}
