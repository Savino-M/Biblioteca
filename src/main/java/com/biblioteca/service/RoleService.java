package com.biblioteca.service;

import com.biblioteca.model.Role;
import com.biblioteca.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public boolean insertRole(Role role) {

        boolean operationStatus = false;

        try {
            Optional<Role> optRole = roleRepository.findById(role.getId());

            if (optRole.isPresent()) { // se il ruolo è già presente

            } else {
                roleRepository.save(role);
                operationStatus = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return operationStatus;

    }

    @Override
    public boolean updateRole(int id, String role) {

        boolean status = false;

        Optional<Role> optRole = roleRepository.findById(id); //trovo il ruolo da aggiornare

        if (optRole.isPresent()) {
            Role newRole = optRole.get();
            newRole.setRoleName(role);
            roleRepository.save(newRole);
            status = true;
        }

        return status;
    }

    @Override
    public boolean deleteRole(int id) {

        boolean status = false;

        try {
            roleRepository.deleteById(id);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}