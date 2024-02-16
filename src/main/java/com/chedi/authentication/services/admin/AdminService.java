package com.chedi.authentication.services.admin;

import com.chedi.authentication.dtos.CarDto;
import com.chedi.authentication.entities.Car;

import java.io.IOException;

public interface AdminService {
    boolean postCar(CarDto car) throws IOException;
}
