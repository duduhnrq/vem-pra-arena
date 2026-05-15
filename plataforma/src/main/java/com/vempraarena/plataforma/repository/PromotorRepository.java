package com.vempraarena.plataforma.repository;

import com.vempraarena.plataforma.model.Promotor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotorRepository extends JpaRepository<Promotor, Long> {
    Optional<Promotor> findByEmailCorporativo(String emailCorporativo);
}
