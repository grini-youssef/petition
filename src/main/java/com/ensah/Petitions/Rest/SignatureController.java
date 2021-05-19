package com.ensah.Petitions.Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ensah.Petitions.Entity.Case;
import com.ensah.Petitions.Entity.Signature;
import com.ensah.Petitions.Service.CaseServiceImpl;
import com.ensah.Petitions.Service.SignatureServiceImp;

@Controller
@RequestMapping("/signature")
public class SignatureController {
	
	@Autowired
	private SignatureServiceImp signatureService;
	
	@Autowired
	private CaseServiceImpl caseService;

    @GetMapping("/addSignature-ui/{idCase}")
    public String addSignature(@PathVariable("idCase") String idCase,Model model) {
        model.addAttribute("case", caseService.getByID(idCase));
        return "addSignature";
        //return  new ResponseEntity<Case>(caseService.getByID(idCase).orElse(null),HttpStatus.OK); //tested working
    }

	@DeleteMapping("/admin/deleteSignature/{idSignature}") //TODO
    public String deleteSignature(@PathVariable("idSignature") String idSignature, Model model) {
    	signatureService.deleteSignatureById(idSignature);
        return "redirect:caseSignatures-ui";
    }
    
    @PostMapping("/addSignature")
    public String addSignature(@ModelAttribute("signature") Signature signature, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !signatureService.isValidSignature(signature) ) {
            return "addSignature";//TODO error page
        }
        signatureService.saveSignature(signature);
        return "redirect:addSignature-ui";//TODO success page
    }
    
}
