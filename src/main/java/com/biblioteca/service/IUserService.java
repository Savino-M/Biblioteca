package com.biblioteca.service;

import com.biblioteca.model.Address;
import com.biblioteca.model.Role;
import com.biblioteca.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public List<User> getAllUsers();

    public User getUserByMailAndPassword(String mail, String password);

    public List<User> getUsersByRole(String role);

    public Role getUserRole(int id);

    public boolean insertUser(User user);

    public boolean updateUserAddress(int id, Address address);

    public boolean updateUserRole(int id, String role);

    public boolean updateUserMail(int id, String mail);

    public boolean updateUserPassword(int id, String password);

    public boolean deleteUser(int id);
}
