package com.controller;

import com.model.Car;
import com.model.EndCustomer;
import com.model.Lease;
import com.repository.CarRepository;
import com.repository.EndCustomerRepository;
import com.repository.LeaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/end-customer")
class EndCustomerController {
    private final CarRepository carRepo;
    private final LeaseRepository leaseRepo;
    private final EndCustomerRepository customerRepo;
    EndCustomerController(CarRepository carRepo, LeaseRepository leaseRepo, EndCustomerRepository customerRepo) {
        this.carRepo = carRepo;
        this.leaseRepo = leaseRepo;
        this.customerRepo = customerRepo;
    }
    @PostMapping("/register")
    public EndCustomer register(@RequestBody EndCustomer customer) {
        return customerRepo.save(customer);
    }
    @GetMapping("/cars")
    public List<Car> getAvailableCars() {
        return carRepo.findAll();
    }
    @PostMapping("/{customerId}/lease/{carId}")
    public Lease startLease(@PathVariable Long customerId, @PathVariable Long carId) {
        Car car = carRepo.findOne(carId);
        EndCustomer customer = customerRepo.findOne(customerId);
        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setActive(true);
        car.setStatus("On Lease");
        carRepo.save(car);
        return leaseRepo.save(lease);
    }
    @PostMapping("/{customerId}/lease/{carId}/end")
    public String endLease(@PathVariable Long customerId, @PathVariable Long carId) {
        Lease lease = leaseRepo.findAll().stream()
                .filter(l -> l.getCustomer().getId().equals(customerId) && l.getCar().getId().equals(carId) && l.isActive())
                .findFirst()
                .orElseThrow(()->new RuntimeException("error"));
        lease.setActive(false);
        lease.getCar().setStatus("Available");
        leaseRepo.save(lease);
        return "Lease ended successfully";
    }
}