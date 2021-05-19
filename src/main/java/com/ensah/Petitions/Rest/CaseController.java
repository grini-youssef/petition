package com.ensah.Petitions.Rest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping; 

import com.ensah.Petitions.Entity.Case; 
import com.ensah.Petitions.Service.CaseServiceImpl;
import com.ensah.Petitions.Service.SignatureServiceImp;

@Controller
@RequestMapping("/case")
public class CaseController {
	
	
	@Autowired
	private SignatureServiceImp signatureService;
	
	@Autowired
	private CaseServiceImpl caseService;
	
    @GetMapping("/caseSignatures-ui/{idCase}")
    public String CaseSignatures(@PathVariable("idCase") String idCase, Model model) {
        model.addAttribute("case-signatures", signatureService.getCaseSignatures(idCase));
        return "manage-cases";
    }
    
    @GetMapping("/cases")
    public String homeCases(Model model){
        model.addAttribute("cases", caseService.getAllCases());
        return "home-cases";
    }
    
    @GetMapping("/admin/manage-cases-ui")
    public String getAllCases(Model model){
        model.addAttribute("cases", caseService.getAllCases());
        return "manage-cases";
    }
    
    //FIXME: add success/error response
    @PostMapping("/admin/addCase")
    public String addCase(@ModelAttribute("case") Case c, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:manage-cases-ui";//TODO error page
        }
        caseService.saveCase(c);
        return "redirect:manage-cases-ui";//TODO success page
    }

    @DeleteMapping("/admin/deleteCase/{idCase}")
    public String deleteCase(@PathVariable("idCase") String idCase) {
    	caseService.deleteCaseById(idCase);
    	signatureService.deleteByIdCase(idCase);
        return "redirect:manage-cases-ui"; 
    }
    
    @PutMapping("/admin/updateCase")
    public String updateCase(@ModelAttribute("case") Case updatedCase, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:manage-cases-ui";//TODO error page
        }
        caseService.saveCase(updatedCase);
        return "redirect:manage-cases-ui";//TODO success page
    }   

}
