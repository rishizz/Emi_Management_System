package com.cognizant.controller;

import com.cognizant.dto.EmiMasterDTO;
import com.cognizant.service.EmiMasterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/emiplans")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmiMasterController {

    private EmiMasterService emiMasterService;

    @PostMapping
    public ResponseEntity<String> createEMIPlan(@Valid @RequestBody EmiMasterDTO emiMasterDTO) {
        emiMasterService.createEmiPlan(emiMasterDTO);
        return new ResponseEntity<String>("EMI plan created successfully!",HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/{loanPlanId}")
    public ResponseEntity<EmiMasterDTO> getEMIPlan(@PathVariable int customerId, @PathVariable int loanPlanId) {
        EmiMasterDTO emiMasterDTO = emiMasterService.viewEMIPlan(customerId, loanPlanId);
        if (emiMasterDTO != null) {
            return ResponseEntity.ok(emiMasterDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
