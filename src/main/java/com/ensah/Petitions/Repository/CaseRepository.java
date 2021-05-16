package com.ensah.Petitions.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ensah.Petitions.Entity.Case;

@Repository
public interface CaseRepository extends MongoRepository<Case, String>{
	
}

 