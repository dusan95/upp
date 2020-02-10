package root.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import root.demo.model.User;
import root.demo.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/{username}/{password}", produces = "application/json")
    public @ResponseBody
    ResponseEntity getLogin(@PathVariable String username, @PathVariable String password) {
        List<User> allUsers = userRepository.findAll();
        System.out.println(allUsers);
        User user = new User();
        for(User userDB:allUsers){
            if(userDB.getUsername().equals(username) && userDB.getPassword().equals(password)){
                user = userDB;
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(user, HttpStatus.NOT_ACCEPTABLE);
    }
}
