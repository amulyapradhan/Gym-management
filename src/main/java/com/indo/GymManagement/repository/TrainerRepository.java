package com.indo.GymManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indo.GymManagement.entity.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}
