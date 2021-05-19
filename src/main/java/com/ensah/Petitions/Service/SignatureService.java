package com.ensah.Petitions.Service;

import java.util.List;
import java.util.Optional;

import com.ensah.Petitions.Entity.Case;
import com.ensah.Petitions.Entity.Signature;


public interface SignatureService {
	
	public void deleteSignatureById(String id);
	public Optional<Signature> getByID(String id);
	public Signature saveSignature(Signature signature);
	public List<Signature> getCaseSignatures(String idCase);
	public void deleteAll();
	public void deleteByIdCase(String idCase);
	public boolean isValidSignature(Signature signature);
	
}
