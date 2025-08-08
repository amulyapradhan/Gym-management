package com.indo.GymManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indo.GymManagement.entity.FitnessPackage;


@Repository
public interface PackageRepository extends JpaRepository<FitnessPackage, Long> {

}
