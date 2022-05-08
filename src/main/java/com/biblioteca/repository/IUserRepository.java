package com.biblioteca.repository;

import com.biblioteca.model.Role;
import com.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    public List<User> findByRole(Role role);

    public Optional<User> findByEmail(String mail);
}
