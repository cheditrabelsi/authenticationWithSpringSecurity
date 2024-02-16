package com.chedi.authentication.services.admin;

import com.chedi.authentication.controllers.AuthController;
import com.chedi.authentication.dtos.CarDto;
import com.chedi.authentication.entities.Car;
import com.chedi.authentication.repositories.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AdminServiceImpl implements AdminService{
private CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
@Autowired
    public AdminServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public boolean postCar(CarDto car) throws IOException {
    try {
        logger.info(String.valueOf(car));
        Car c = new Car();
        c.setModele(car.getModele());
        c.setMatricule(car.getMatricule());
        c.setCouleur(car.getCouleur());
        c.setPrix(car.getPrix());
        c.setAnnee(car.getAnnee());
       c.setImage(car.getImage());
        this.carRepository.save(c);
        return true;
    }catch(Exception e){
logger.info(String.valueOf(e));
        return false;
    }
}
}
