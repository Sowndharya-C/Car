package com.controller;

import com.model.Car;
import com.model.CarOwner;
import com.repository.CarOwnerRepository;
import com.repository.CarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car-owner")
class CarOwnerController {
    private final CarRepository carRepo;
    private final CarOwnerRepository ownerRepo;
    CarOwnerController(CarRepository carRepo, CarOwnerRepository ownerRepo) {
        this.carRepo = carRepo;
        this.ownerRepo = ownerRepo;
    }
    @PostMapping("/{ownerId}/cars")
    public Car registerCar(@PathVariable Long ownerId, @RequestBody Car car) {
        CarOwner owner = ownerRepo.findOne(ownerId);
        car.setOwner(owner);
        car.setStatus("Available");
        return carRepo.save(car);
    }
    @GetMapping("/{ownerId}/cars")
    public List<Car> getOwnerCars(@PathVariable Long ownerId) {
        return carRepo.findAll();
    }
}
