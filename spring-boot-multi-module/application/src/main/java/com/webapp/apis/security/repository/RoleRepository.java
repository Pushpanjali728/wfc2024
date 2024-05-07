package com.webapp.apis.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.apis.security.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(String name);
}
