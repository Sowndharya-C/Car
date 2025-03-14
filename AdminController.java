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
@RequestMapping("/admin")
class AdminController {
    private final CarRepository carRepo;
    private final LeaseRepository leaseRepo;
    private final EndCustomerRepository customerRepo;
    AdminController(CarRepository carRepo, LeaseRepository leaseRepo, EndCustomerRepository customerRepo) {
        this.carRepo = carRepo;
        this.leaseRepo = leaseRepo;
        this.customerRepo = customerRepo;
    }
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }
    @GetMapping("/customers")
    public List<EndCustomer> getAllCustomers() {
        return customerRepo.findAll();
    }
    @GetMapping("/lease-history")
    public List<Lease> getLeaseHistory() {
        return leaseRepo.findAll();
    }
    @PostMapping("/lease/{leaseId}/end")
    public String endAnyLease(@PathVariable Long leaseId) {
        Lease lease = leaseRepo.findOne(leaseId);
        lease.setActive(false);
        lease.getCar().setStatus("Available");
        leaseRepo.save(lease);
        return "Lease ended by Admin";
    }
}

