package org.example.countryapi.services;

import org.example.countryapi.entities.User;
import org.example.countryapi.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    //Created this way only for test purposes.
    @EventListener
    public void createUsers(ApplicationStartedEvent event){
        User user = new User("Gunnar Globetrotter");
        repository.save(user);
        User alsoUser = new User("Resenär Rikard");
        repository.save(alsoUser);
    }
}

