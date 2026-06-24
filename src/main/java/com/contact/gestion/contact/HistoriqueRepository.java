package com.contact.gestion.contact.historique.repository;

import com.contact.gestion.contact.historique.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueRepository
        extends JpaRepository<Historique, Long> {
}