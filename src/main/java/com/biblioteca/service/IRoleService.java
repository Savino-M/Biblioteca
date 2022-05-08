package com.biblioteca.service;

import com.biblioteca.model.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> getAllRoles();

    public boolean insertRole(Role role);

    public boolean updateRole(int id, String role);

    public boolean deleteRole(int id);
}
