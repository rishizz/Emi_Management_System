package com.cognizant.service;

import com.cognizant.dto.EmiMasterDTO;
import com.cognizant.entities.EmiMaster;
import com.cognizant.exceptions.EmiPlanLimitExceededException;
import com.cognizant.exceptions.LoanPlanIdNotDistinctException;
import com.cognizant.repositories.EmiMasterRepository;
import com.cognizant.service.impl.EmiMasterServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmiMasterServiceImplTest {
    @InjectMocks
    private EmiMasterServiceImpl emiMasterService;

    @Mock
    private EmiMasterRepository emiMasterRepository;

    @Test
    public void testCreateEmiPlan() throws Exception {
        EmiMasterDTO emiMasterDTO = EmiMasterDTO.builder()
                .id(101)
                .customerId(101)
                .loanPlanId(201)
                .emiAmount(2000.25)
                .numberOfEmi(24)
                .customerName("Rishikesh")
                .customerPhone("9876543210")
                .customerAddress("21 street avenue, london")
                .customerPan("12334567890")
                .emiStatus("OnGoing")
                .build();

        when(emiMasterRepository.save(any(EmiMaster.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        EmiMasterDTO emiMasterDTOResult = emiMasterService.createEmiPlan(emiMasterDTO);

        assertEquals(emiMasterDTO.getId(),emiMasterDTOResult.getId());
        assertEquals(emiMasterDTO.getCustomerId(),emiMasterDTOResult.getCustomerId());
        assertEquals(emiMasterDTO.getLoanPlanId(), emiMasterDTOResult.getLoanPlanId());
        assertEquals(emiMasterDTO.getEmiAmount(), emiMasterDTOResult.getEmiAmount());
        assertEquals(emiMasterDTO.getEmiStart(), emiMasterDTOResult.getEmiStart());
        assertEquals(emiMasterDTO.getNumberOfEmi(), emiMasterDTOResult.getNumberOfEmi());
        assertEquals(emiMasterDTO.getCustomerName(), emiMasterDTOResult.getCustomerName());
        assertEquals(emiMasterDTO.getCustomerPhone(), emiMasterDTOResult.getCustomerPhone());
        assertEquals(emiMasterDTO.getCustomerAddress(), emiMasterDTOResult.getCustomerAddress());
        assertEquals(emiMasterDTO.getCustomerPan(), emiMasterDTOResult.getCustomerPan());
        assertEquals(emiMasterDTO.getEmiStatus(), emiMasterDTOResult.getEmiStatus());

    }
    @Test
    public void testViewEMIPlan() {
        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(1);
        emiMaster.setCustomerId(1);
        emiMaster.setLoanPlanId(1);
        emiMaster.setEmiAmount(10000.0);
        emiMaster.setEmiStart("2024-03-15");
        emiMaster.setNumberOfEmi(12);
        emiMaster.setCustomerName("John Doe");
        emiMaster.setCustomerPhone("1234567890");
        emiMaster.setCustomerAddress("123 Street, City, Country");
        emiMaster.setCustomerPan("ABCDE1234F");
        emiMaster.setEmiStatus("OnGoing");

        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(1, 1)).thenReturn(Optional.of(emiMaster));

        EmiMasterDTO result = emiMasterService.viewEMIPlan(1, 1);

        assertNotNull(result);
        assertEquals(emiMaster.getId(), result.getId());
        assertEquals(emiMaster.getCustomerId(), result.getCustomerId());
        assertEquals(emiMaster.getLoanPlanId(), result.getLoanPlanId());
        assertEquals(emiMaster.getEmiAmount(), result.getEmiAmount());
        assertEquals(emiMaster.getEmiStart(), result.getEmiStart());
        assertEquals(emiMaster.getNumberOfEmi(), result.getNumberOfEmi());
        assertEquals(emiMaster.getCustomerName(), result.getCustomerName());
        assertEquals(emiMaster.getCustomerPhone(), result.getCustomerPhone());
        assertEquals(emiMaster.getCustomerAddress(), result.getCustomerAddress());
        assertEquals(emiMaster.getCustomerPan(), result.getCustomerPan());
        assertEquals(emiMaster.getEmiStatus(), result.getEmiStatus());
    }
    @Test
    public void testViewEMIPlanNotFound() {
        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(1, 1)).thenReturn(Optional.empty());

        EmiMasterDTO result = emiMasterService.viewEMIPlan(1, 1);
        assertNull(result);
    }
    @Test
    public void testCreateEmiPlanThrowsEmiPlanLimitExceededException() throws Exception {
        EmiMasterDTO emiMasterDTO = EmiMasterDTO.builder()
                .id(101)
                .customerId(101)
                .loanPlanId(201)
                .emiAmount(2000.25)
                .numberOfEmi(24)
                .customerName("Rishikesh")
                .customerPhone("9876543210")
                .customerAddress("21 street avenue, london")
                .customerPan("12334567890")
                .emiStatus("OnGoing")
                .build();

        List<EmiMaster> existingPlans = new ArrayList<>();
        existingPlans.add(new EmiMaster());
        existingPlans.add(new EmiMaster());

        when(emiMasterRepository.findByCustomerId(101)).thenReturn(existingPlans);

        assertThrows(EmiPlanLimitExceededException.class, () -> emiMasterService.createEmiPlan(emiMasterDTO));
    }

    @Test
    public void testCreateEmiPlanThrowsLoanPlanIdNotDistinctException() throws Exception {
        EmiMasterDTO emiMasterDTO = EmiMasterDTO.builder()
                .id(101)
                .customerId(101)
                .loanPlanId(201)
                .emiAmount(2000.25)
                .numberOfEmi(24)
                .customerName("Rishikesh")
                .customerPhone("9876543210")
                .customerAddress("21 street avenue, london")
                .customerPan("12334567890")
                .emiStatus("OnGoing")
                .build();

        EmiMaster existingPlan = new EmiMaster();
        existingPlan.setLoanPlanId(201);

        List<EmiMaster> existingPlans = new ArrayList<>();
        existingPlans.add(existingPlan);

        when(emiMasterRepository.findByCustomerId(101)).thenReturn(existingPlans);

        assertThrows(LoanPlanIdNotDistinctException.class, () -> emiMasterService.createEmiPlan(emiMasterDTO));
    }


}
