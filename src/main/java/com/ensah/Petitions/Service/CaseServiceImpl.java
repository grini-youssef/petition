package com.ensah.Petitions.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensah.Petitions.Entity.Case;
import com.ensah.Petitions.Entity.Signature;
import com.ensah.Petitions.Repository.CaseRepository;
import com.ensah.Petitions.Repository.SignatureRepository;

 
@Service
public class CaseServiceImpl  implements CaseService {
	
	@Autowired
	CaseRepository caseRepository;
	@Autowired
	SignatureRepository signatureRepository;
    

	
	@Override
	public Optional<Case> getByID(String id) {
		return caseRepository.findById(id);
	}
	
	@Override
	public void deleteCaseById(String id) {
		caseRepository.deleteById(id);
	}

	@Override 
	public void deleteAll() {
		caseRepository.deleteAll();
	} 
	
	@Override
	public int countCaseSignatures(String idCase) {
		return signatureRepository.countByIdCase(idCase);
	}

	@Override
	public Case saveCase(Case c) {
		return caseRepository.save(c);
	}

	@Override
	public List<Case> getAllCases() {
		return caseRepository.findAll();
	}
}
