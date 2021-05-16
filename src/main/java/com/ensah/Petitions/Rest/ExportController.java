package com.ensah.Petitions.Rest;

import com.ensah.Petitions.Entity.Signature;
import com.ensah.Petitions.Service.Export.SignatureExcelExport;
import com.ensah.Petitions.Service.SignatureServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/case/export")
public class ExportController {

    @Autowired
    private SignatureServiceImp signatureService  ;

    @GetMapping("/{id}")
    public void exportToExcel(HttpServletResponse response, @PathVariable String id) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=signatures_"+id+"_"  + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Signature>  signatures = signatureService.getCaseSignatures(id) ;
        SignatureExcelExport signatureExcelExport = new SignatureExcelExport(signatures) ;
        signatureExcelExport.export(response,id);
    }
}
