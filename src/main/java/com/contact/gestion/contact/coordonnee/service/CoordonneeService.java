package com.contact.gestion.contact.coordonnee.service;

import com.contact.gestion.contact.coordonnee.model.Coordonnee;
import com.contact.gestion.contact.coordonnee.repository.CoordonneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordonneeService {
    @Autowired
    private CoordonneeRepository coordonneeRepository;

    public Coordonnee save(Coordonnee coordonnee) {
        return coordonneeRepository.save(coordonnee);
    }

    public List<Coordonnee> findAll() {
        return coordonneeRepository.findAll();
    }
}