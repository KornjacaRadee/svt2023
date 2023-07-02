package com.example.svt2023sr50.services;

import com.example.svt2023sr50.Constructor.UserConstruct;
import com.example.svt2023sr50.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.svt2023sr50.repository.UserRepository;
import com.example.svt2023sr50.model.Role;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User geOne(Long userId) { return userRepository.getOne(userId);};
    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isEmpty()) {
            return user.get();
        }
        return null;
    }
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(UserConstruct userr) {

        Optional<User> user = userRepository.findByUsername(userr.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userr.getUsername());
        newUser.setPassword(passwordEncoder.encode(userr.getPassword()));
        newUser.setRole(Role.USER);
        newUser.setFirstName(userr.getFirstName());
        newUser.setLastName(userr.getLastName());
        newUser.setEmail(userr.getEmail());
        newUser = userRepository.save(newUser);

        return newUser;
    }
}