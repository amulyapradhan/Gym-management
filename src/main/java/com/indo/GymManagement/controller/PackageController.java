package com.indo.GymManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indo.GymManagement.entity.FitnessPackage;
import com.indo.GymManagement.serviceimpl.PackageServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageServiceImpl packageService;

    @GetMapping
    public ResponseEntity<List<FitnessPackage>> getAllPackages() {
        List<FitnessPackage> packages = packageService.getAllPackages();
        return ResponseEntity.ok(packages);
    }
}
