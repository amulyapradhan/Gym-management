package com.indo.GymManagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indo.GymManagement.entity.FitnessPackage;
import com.indo.GymManagement.repository.PackageRepository;

@Service
public class PackageServiceImpl {

	@Autowired
	private PackageRepository packageRepo;
	public List<FitnessPackage> getAllPackages() {		
		return packageRepo.findAll();
	}

}
