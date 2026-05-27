package com.contact.gestion.contact.repository;

import com.contact.gestion.contact.model.Coordonnee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordonneeRepository extends JpaRepository<Coordonnee, Long> {
}