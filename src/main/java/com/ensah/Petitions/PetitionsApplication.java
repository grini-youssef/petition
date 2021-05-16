package com.ensah.Petitions;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ensah.Petitions.Entity.Case;
import com.ensah.Petitions.Entity.Signature;
import com.ensah.Petitions.Rest.SignatureController;
import com.ensah.Petitions.Service.CaseService;
import com.ensah.Petitions.Service.SignatureService;
import com.ensah.Petitions.Service.SignatureServiceImp;

@SpringBootApplication
public class PetitionsApplication {
  	
	public static void main(String[] args) {
		SpringApplication.run(PetitionsApplication.class, args);
	}

    @Bean
    public String setup(SignatureService signatureService,CaseService caseService) {
    	
    	//CREATE signature WORKING
    	//Signature s= signatureService.saveSignature(new Signature("1YJHSHS","name","emailf@hh.dd","gg"));
    	//Signature tobedeletedbyidcase= signatureService.saveSignature(new Signature("1YJHSHS","name","email@hh.dd","rr"));
    	//Signature tobedeletedbyid= signatureService.saveSignature(new Signature("1YJHSHS","name","email@hh.dd","hh"));
    	// find signature By Id WORKING
    	//Signature ff = signatureService.getByID("60a04789f323787c89ef9f0d").orElse(null); System.out.println(ff.getEmail()); 
    	//update Signature WORKING
    	//ff.setFullName("full name changed"); Signature s= signatureService.saveSignature(ff); 
    	//delete a signature by case Id WORKING
    	//signatureService.deleteByIdCase("rr"); 
    	//delete a signature by Id WORKING
    	//signatureService.deleteSignatureById("60a0e939c44abf3a23b4bafe"); 
    	//get all signatures by Id Case WORKING
    	//List<Signature> signs = signatureService.getCaseSignatures("gg");  System.out.format("we have %d signatures for gg case id\n",signs.size());
    	
    	
    	//CREATE CASES WORKING
    	//Case s= caseService.saveCase(new Case("name 2","description 2"));   
    	// find Case By Id WORKING
    	//Case ff = caseService.getByID("60a0e54b34244c7c761362ba").orElse(null); System.out.println(ff.getDescription()); 
    	//update Case WORKING
    	//ff.setDescription("name changed"); Case s= caseService.saveCase(ff); 
    	//delete Case WORKING
    	//Case casetobedeleted= caseService.saveCase(new Case("name tobedeleted","description tobedeleted")); 
    	//caseService.deleteCaseById(casetobedeleted.getId()); 
    	//get All Cases WORKING
    	//List<Case> cases = caseService .getAllCases();  System.out.format("we have %d cases in total\n",cases.size());
    	
    	
    	//save unique signatures for same case WORKING
    	//we consider unique signature 
    	//System.out.println("---"+signatureService.isValidSignature(new Signature("sig2","name","email@hh.dd","gg")));
    	//System.out.println("---"+signatureService.isValidSignature(new Signature("sig","name","emailf@hh.dd","gg")));
    	//System.out.println("---"+signatureService.isValidSignature(new Signature("new","name","new@hh.dd","gg")));
    	
    	


     	return "";
    }
}
