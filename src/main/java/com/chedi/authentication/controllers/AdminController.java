package com.chedi.authentication.controllers;

import com.chedi.authentication.dtos.CarDto;
import com.chedi.authentication.entities.Car;
import com.chedi.authentication.enums.UserRole;
import com.chedi.authentication.services.admin.AdminServiceImpl;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AdminServiceImpl admin;
    public AdminController(AdminServiceImpl admin) {
        this.admin = admin;
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody CarDto c) {
        try {
                return admin.postCar(c);
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout de la voiture.", e);
            return false;
        }
    }
}
