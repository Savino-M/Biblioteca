package com.biblioteca.repository;

import com.biblioteca.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {

    public Optional<Role> findByRoleName(String roleName);

}
