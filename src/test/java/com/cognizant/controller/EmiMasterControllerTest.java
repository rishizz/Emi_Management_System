package com.cognizant.controller;

import com.cognizant.dto.EmiMasterDTO;
import com.cognizant.exceptions.EmiPlanLimitExceededException;
import com.cognizant.exceptions.LoanPlanIdNotDistinctException;
import com.cognizant.service.EmiMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmiMasterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmiMasterService emiMasterService;

    @InjectMocks
    private EmiMasterController emiMasterController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(emiMasterController).build();
    }

    @Test
    void testCreateEMIPlanPositive() throws Exception {
        EmiMasterDTO emiMasterDTO = EmiMasterDTO.builder()
                .id(5)
                .customerId(101)
                .loanPlanId(203)
                .emiAmount(1000.0)
                .emiStart("2024-05-09")
                .numberOfEmi(12)
                .customerName("Test Customer")
                .customerPhone("1234567890")
                .customerAddress("Test Address")
                .customerPan("ABCDE1234F")
                .emiStatus("OnGoing")
                .build();

        when(emiMasterService.createEmiPlan(any(EmiMasterDTO.class))).thenReturn(emiMasterDTO);

        mockMvc.perform(post("/api/emiplans")
                        .content(new ObjectMapper().writeValueAsString(emiMasterDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void testCreateEMIPlanThrowsEmiPlanLimitExceededException() throws Exception {
        EmiMasterDTO emiMasterDTO = EmiMasterDTO.builder()
                .id(1)
                .customerId(1)
                .loanPlanId(1)
                .emiAmount(1000.0)
                .emiStart("2024-04-21")
                .numberOfEmi(12)
                .customerName("Test Customer")
                .customerPhone("1234567890")
                .customerAddress("Test Address")
                .customerPan("ABCDE1234F")
                .emiStatus("OnGoing")
                .build();

        when(emiMasterService.createEmiPlan(any(EmiMasterDTO.class))).thenThrow(new EmiPlanLimitExceededException("Cannot create more than two EmiPlans for a customer"));

        mockMvc.perform(post("/api/emiplans"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateEMIPlanThrowsLoanPlanIdNotDistinctException() throws Exception {
        EmiMasterDTO emiMasterDTO = EmiMasterDTO.builder()
                .id(1)
                .customerId(1)
                .loanPlanId(1)
                .emiAmount(1000.0)
                .emiStart("2024-04-21")
                .numberOfEmi(12)
                .customerName("Test Customer")
                .customerPhone("1234567890")
                .customerAddress("Test Address")
                .customerPan("ABCDE1234F")
                .emiStatus("OnGoing")
                .build();

        when(emiMasterService.createEmiPlan(any(EmiMasterDTO.class))).thenThrow(new LoanPlanIdNotDistinctException("loanPlanId must be distinct for a customer"));

        mockMvc.perform(post("/api/emiplans"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEMIPlan() throws Exception {
        int customerId = 101;
        int loanPlanId = 201;

        EmiMasterDTO emiMasterDTO = EmiMasterDTO.builder()
                .id(1)
                .customerId(101)
                .loanPlanId(201)
                .emiAmount(5000.00)
                .emiStart("2024-04-01")
                .numberOfEmi(12)
                .customerName("John Doe")
                .customerPhone("1234567890")
                .customerAddress("123 Street, City, Country")
                .customerPan("ABCDE1234F")
                .emiStatus("OnGoing")
                .build();

        when(emiMasterService.viewEMIPlan(customerId, loanPlanId)).thenReturn(emiMasterDTO);

        mockMvc.perform(get("/api/emiplans/" + customerId + "/" + loanPlanId))
                .andExpect(status().isOk());
    }

}
