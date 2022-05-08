package com.biblioteca.controller;

import com.biblioteca.model.Role;
import com.biblioteca.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * Questo metodo restituisce tutti i ruoli
     *
     * @return lista di ruoli
     */
    @GetMapping(value = "/role/getAllRoles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * Questo metodo inserisce un nuovo ruolo
     *
     * @param role da inserire
     * @return esito
     */
    @PostMapping(value = "/role/saveRole")
    public boolean insertRole(@RequestBody Role role) {
        return roleService.insertRole(role);
    }

    /**
     * Questo metodo aggiorna il nome di un ruolo
     *
     * @param id   del ruolo da modificare
     * @param role il nuovo nome del ruolo
     * @return esito
     */
    @PostMapping(value = "/role/updateRole")
    public boolean updateRole(@RequestParam int id, @RequestParam String role) {
        return roleService.updateRole(id, role);
    }

    /**
     * Questo metodo elimina un ruolo
     *
     * @param id del ruolo da eliminare
     * @return esito
     */
    @PostMapping(value = "/role/deleteRole")
    public boolean deleteRole(@RequestParam int id) {
        return roleService.deleteRole(id);
    }

}
