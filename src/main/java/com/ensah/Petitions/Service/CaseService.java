package com.ensah.Petitions.Service;

import java.util.List;
import java.util.Optional;

import com.ensah.Petitions.Entity.Case;
import com.ensah.Petitions.Entity.Signature;

public interface CaseService {
	
	public void deleteCaseById(String id);
	public Optional<Case> getByID(String id);
	public List<Case> getAllCases();
	public Case saveCase(Case c);
	public int countCaseSignatures(String idCase);
	public void deleteAll();
}
