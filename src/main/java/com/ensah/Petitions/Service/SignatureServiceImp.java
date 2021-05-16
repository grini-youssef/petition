package com.ensah.Petitions.Service;

import com.ensah.Petitions.Entity.Case;
import com.ensah.Petitions.Entity.Signature;
import com.ensah.Petitions.Repository.SignatureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
/**
 * 
 * @author idamerce
 *
 */
@Service
public class SignatureServiceImp  implements SignatureService {
	
	@Autowired
	SignatureRepository signatureRepository;
    
	@Override
	public Signature saveSignature(Signature signature){
		return signatureRepository.save(signature);
	}
	
	@Override
	public Optional<Signature> getByID(String id) {
		return signatureRepository.findById(id);
	}
	
	@Override
	public void deleteSignatureById(String id) {
		signatureRepository.deleteById(id);
	}

	@Override 
	public void deleteAll() {
		signatureRepository.deleteAll();
	}
	
	@Override 
	public List<Signature> getCaseSignatures(String idCase) { 
		return signatureRepository.getByIdCase(idCase);
	}
	
	@Override 
	public void deleteByIdCase(String idCase) {
		signatureRepository.deleteByIdCase(idCase);
	}
	
	/**
	 * consider a unique signature as combination of unique EMAIL&Id_Case and unique CIN&Id_Case
	 * FIXME in a new feature: for mor credibility add checking emails/cin from school database
	 */
	@Override
    public boolean isValidSignature(Signature signature) {
    	return signatureRepository.findByCinAndIdCase(signature.getCin(),signature.getIdCase()).size()==0 && 
    			signatureRepository.findByEmailAndIdCase(signature.getEmail(),signature.getIdCase()).size()==0;
    }
	
}
