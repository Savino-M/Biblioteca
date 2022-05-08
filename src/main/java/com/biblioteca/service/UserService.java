package com.biblioteca.service;

import com.biblioteca.model.Address;
import com.biblioteca.model.Role;
import com.biblioteca.model.User;
import com.biblioteca.repository.IRoleRepository;
import com.biblioteca.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByMailAndPassword(String mail, String password) {

        User user = null;

        try {
            Optional<User> optUser = userRepository.findByEmail(mail); //trovo l'utente con quella mail

            if (optUser.isPresent()) {

                if (optUser.get().getPassword().equals(password)) { //se la password inserita è corretta
                    user = optUser.get();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getUsersByRole(String role) {

        List<User> usersList = null;

        try {

            Optional<Role> optRole = roleRepository.findByRoleName(role); //verifico se esiste già il ruolo
            Role newRole = null;

            if (optRole.isPresent()) { //se esiste
                newRole = optRole.get();
                usersList = userRepository.findByRole(newRole);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersList;
    }

    @Override
    public Role getUserRole(int id) {

        Optional<User> optUser = userRepository.findById(id);
        Role role=null;

        if(optUser.isPresent()) {
            User user=optUser.get();
            role=user.getRole();
        }
        return role;
    }

    @Override
    public boolean insertUser(User user) {

        boolean status = false;

        try {

            Optional<Role> optRole = roleRepository.findByRoleName("user"); //verifico se esiste già il ruolo user
            Role role = null;

            if (optRole.isPresent()) { //se esiste
                role = optRole.get();
            } else {
                role = new Role(1, "user"); //se non esiste creo il ruolo user. che è quello di default quando si inserisce un nuovo utente
            }

            user.setRole(role);
            userRepository.save(user);
            status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean updateUserAddress(int id, Address address) {

        boolean status = false;

        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setAddress(address);
            userRepository.save(user);
            status = true;
        }

        return status;
    }

    @Override
    public boolean updateUserRole(int id, String role) {

        boolean status = false;

        Optional<User> optUser = userRepository.findById(id);
        Optional<Role> optRole = roleRepository.findByRoleName(role); //verifico se esiste già il ruolo che voglio dare all'utente

        if (optUser.isPresent() && optRole.isPresent()) { //se l'utente e il ruolo esistono

            Role newRole = optRole.get();  //prendo il nuovo ruolo
            User user = optUser.get(); // prendo l'utente
            user.setRole(newRole); //aggiorno il ruolo dell'utente

            userRepository.save(user);
            status = true;
        }
        return status;
    }

    @Override
    public boolean updateUserMail(int id, String mail) {

        boolean status = false;

        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()) { //se l'utente esiste

            User user = optUser.get(); // prendo l'utente
            user.setEmail(mail); //aggiorno la mail
            userRepository.save(user);
            status = true;
        }
        return status;
    }

    @Override
    public boolean updateUserPassword(int id, String password) {

        boolean status = false;

        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()) { //se l'utente esiste

            User user = optUser.get(); // prendo l'utente
            user.setPassword(password); //aggiorno la password

            userRepository.save(user);
            status = true;
        }
        return status;
    }

    @Override
    public boolean deleteUser(int id) {

        boolean status = false;

        try {
            userRepository.deleteById(id);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
