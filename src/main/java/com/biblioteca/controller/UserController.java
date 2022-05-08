package com.biblioteca.controller;

import com.biblioteca.model.Address;
import com.biblioteca.model.Role;
import com.biblioteca.model.User;
import com.biblioteca.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * Questo metodo restituisce tutti gli utenti
     *
     * @return lista di utenti
     */
    @GetMapping(value = "/user/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Questo metodo restituisce l'utente rispetto a mail e password
     *
     * @return utente
     */
    @GetMapping(value = "/user/getUserByMailAndPassword")
    public User getUserByMailAndPassword(@RequestParam String mail,@RequestParam String password){
        return userService.getUserByMailAndPassword(mail,password);
    }

    /**
     * Questo metodo restituisce tutti gli utenti che hanno un dato ruolo
     *
     * @param role nome del ruolo
     * @return lista di utenti
     */
    @GetMapping(value = "/user/getUsersByRole")
    public List<User> getUsersByRole(@RequestParam String role) {
        return userService.getUsersByRole(role);
    }

    /**
     * Questo metodo restituisce il ruolo di un utente
     *
     * @param id dell'utente
     * @return ruolo
     */
    @GetMapping(value = "/user/getUserRole")
    public Role getUserRole(@RequestParam int id) {
        return userService.getUserRole(id);
    }

    /**
     * Questo metodo inserisce un nuovo utente
     *
     * @param user da inserire
     * @return esito
     */
    @PostMapping(value = "/user/saveUser")
    public boolean insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    /**
     * Questo metodo aggiorna l'indirizzo di un utente
     *
     * @param id      dell'utente da modificare
     * @param address nuovo indirizzo
     * @return esito
     */
    @PostMapping(value = "/user/updateUserAddress")
    public boolean updateUserAddress(@RequestParam int id, @RequestBody Address address) {
        return userService.updateUserAddress(id, address);
    }

    /**
     * Questo metodo aggiorna il ruolo di un utente
     *
     * @param id   dell'utente da modificare
     * @param role nome del nuovo ruolo
     * @return esito
     */
    @PostMapping(value = "/user/updateUserRole")
    public boolean updateUserRole(@RequestParam int id, @RequestParam String role) {
        return userService.updateUserRole(id, role);
    }

    /**
     * Questo metodo aggiorna la mail dell'utente
     *
     * @param id   dell'utente da modificare
     * @param mail nuova mail
     * @return esito
     */
    @PostMapping(value = "/user/updateUserMail")
    public boolean updateUserMail(@RequestParam int id, @RequestParam String mail) {
        return userService.updateUserMail(id, mail);
    }

    /**
     * Questo metodo aggiorna la password dell'utente
     *
     * @param id   dell'utente da modificare
     * @param password nuova password
     * @return esito
     */
    @PostMapping(value = "/user/updateUserPassword")
    public boolean updateUserPassword(@RequestParam int id, @RequestParam String password) {
        return userService.updateUserPassword(id, password);
    }

    /**
     * Questo metodo elimina un utente
     *
     * @param id dell'utente da modificare
     * @return esito
     */
    @PostMapping(value = "/user/deleteUser")
    public boolean deleteUser(@RequestParam int id) {
        return userService.deleteUser(id);
    }

}
