package com.cognizant.controller;

import com.cognizant.dto.EmiPaymentDTO;
import com.cognizant.exceptions.LoanPaymentCompletedException;
import com.cognizant.service.EmiPaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/emiplans")
@CrossOrigin(origins="http://localhost:4200")
public class EmiPaymentController {
    @Autowired
    private EmiPaymentService emiPaymentService;
    @PostMapping("/{customerId}/{loanPlanId}")
    public ResponseEntity<?> payEMI(@Valid @PathVariable int customerId, @PathVariable int loanPlanId, @RequestBody EmiPaymentDTO emiPaymentDTO,@RequestParam int paymentMethodId) {
        try {
            EmiPaymentDTO emiPaymentDTOCreated = emiPaymentService.payEMI(customerId, loanPlanId,emiPaymentDTO,paymentMethodId);
            return ResponseEntity.status(HttpStatus.CREATED).body(emiPaymentDTOCreated);
        } catch (LoanPaymentCompletedException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
    @GetMapping("/{customerId}/{loanPlanId}/paymenthistory")
    public ResponseEntity<List<EmiPaymentDTO>> viewEMIPayments(@PathVariable int customerId, @PathVariable int loanPlanId) {
        List<EmiPaymentDTO> emiPaymentDTO = emiPaymentService.viewEMIPayments(customerId,loanPlanId);
        if(emiPaymentDTO!=null){
            return ResponseEntity.ok(emiPaymentDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
