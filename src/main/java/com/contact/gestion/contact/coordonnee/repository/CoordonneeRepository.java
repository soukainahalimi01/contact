package com.contact.gestion.contact.coordonnee.repository;

import com.contact.gestion.contact.coordonnee.model.Coordonnee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoordonneeRepository extends JpaRepository<Coordonnee, Long> {
    List<Coordonnee> findByContact_Id(Long contactId);
}