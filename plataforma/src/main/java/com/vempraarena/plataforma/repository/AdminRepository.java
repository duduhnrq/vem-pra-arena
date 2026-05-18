package com.vempraarena.plataforma.repository;

import com.vempraarena.plataforma.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByLoginAdmin(String loginAdmin);
}