package com.cognizant.service;

import com.cognizant.dto.EmiPaymentDTO;
import com.cognizant.entities.EmiMaster;
import com.cognizant.entities.EmiPayment;
import com.cognizant.entities.PaymentMethod;
import com.cognizant.exceptions.LoanPaymentCompletedException;
import com.cognizant.repositories.EmiMasterRepository;
import com.cognizant.repositories.EmiPaymentRepository;
import com.cognizant.repositories.PaymentMethodRepository;
import com.cognizant.service.impl.EmiPaymentServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmiPaymentServiceImplTest {
    @InjectMocks
    private EmiPaymentServiceImpl emiPaymentService;

    @Mock
    private EmiPaymentRepository emiPaymentRepository;
    @Mock
    private EmiMasterRepository emiMasterRepository;
    @Mock
    private PaymentMethodRepository paymentMethodRepository;
    @Mock
    private EntityManager entityManager;

    @Test
    public void testPayEMIThrowsLoanPaymentCompletedException() throws Exception {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(101);
        emiPaymentDTO.setAmount(2000.25);
        emiPaymentDTO.setPaymentDate(LocalDate.now().toString());
        emiPaymentDTO.setLateFee(0);
        emiPaymentDTO.setEmiId(new EmiMaster());
        emiPaymentDTO.setPaymentMethodId(new PaymentMethod());

        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);
        emiMaster.setCustomerId(101);
        emiMaster.setLoanPlanId(201);
        emiMaster.setEmiAmount(2000.25);
        emiMaster.setNumberOfEmi(24);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1);
        paymentMethod.setPaymentMethod("Credit Card");

        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(anyInt(), anyInt())).thenReturn(Optional.of(emiMaster));
        when(paymentMethodRepository.findById(anyInt())).thenReturn(Optional.of(paymentMethod));

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(25);

        assertThrows(LoanPaymentCompletedException.class, () -> emiPaymentService.payEMI(101, 201, emiPaymentDTO, 1));
    }

    @Test
    public void testPayEMI() throws Exception {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(101);
        emiPaymentDTO.setAmount(2000.25);
        emiPaymentDTO.setPaymentDate(LocalDate.now().toString());
        emiPaymentDTO.setLateFee(0);
        emiPaymentDTO.setEmiId(new EmiMaster());
        emiPaymentDTO.setPaymentMethodId(new PaymentMethod());

        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);
        emiMaster.setCustomerId(101);
        emiMaster.setLoanPlanId(201);
        emiMaster.setEmiAmount(2000.25);
        emiMaster.setNumberOfEmi(24);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1);
        paymentMethod.setPaymentMethod("Credit Card");

        when(paymentMethodRepository.findById(anyInt())).thenReturn(Optional.of(paymentMethod));
        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(anyInt(), anyInt())).thenReturn(Optional.of(emiMaster));

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(23);

        when(emiPaymentRepository.save(any(EmiPayment.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        EmiPaymentDTO result = emiPaymentService.payEMI(101, 201, emiPaymentDTO, 1);

        assertNotNull(result);
        assertEquals(emiPaymentDTO.getId(), result.getId());
        assertEquals(emiPaymentDTO.getAmount(), result.getAmount());
        assertEquals(emiPaymentDTO.getPaymentDate(), result.getPaymentDate());
        assertEquals(emiPaymentDTO.getLateFee(), result.getLateFee());
    }
    @Test
    public void testPayEMIThrowsEntityNotFoundExceptionForEmiMaster() throws Exception {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(101);
        emiPaymentDTO.setAmount(2000.25);
        emiPaymentDTO.setPaymentDate(LocalDate.now().toString());
        emiPaymentDTO.setLateFee(0);
        emiPaymentDTO.setEmiId(new EmiMaster());
        emiPaymentDTO.setPaymentMethodId(new PaymentMethod());

        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> emiPaymentService.payEMI(101, 201, emiPaymentDTO, 1));
    }

    @Test
    public void testPayEMIThrowsEntityNotFoundExceptionForPaymentMethod() throws Exception {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(101);
        emiPaymentDTO.setAmount(2000.25);
        emiPaymentDTO.setPaymentDate(LocalDate.now().toString());
        emiPaymentDTO.setLateFee(0);
        emiPaymentDTO.setEmiId(new EmiMaster());
        emiPaymentDTO.setPaymentMethodId(new PaymentMethod());

        when(paymentMethodRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> emiPaymentService.payEMI(101, 201, emiPaymentDTO, 1));
    }



    @Test
    public void testViewEMIPaymentsThrowsRuntimeException() {
        when(emiPaymentRepository.findByCustomerIdAndLoanPlanId(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> emiPaymentService.viewEMIPayments(101, 201));
    }
    @Test
    public void testGetTotalNumberOfPaidEmi() {
        int customerId = 101;
        int loanPlanId = 201;

        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);

        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(customerId, loanPlanId)).thenReturn(Optional.of(emiMaster));

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(10);

        int result = emiPaymentService.getTotalNumberOfPaidEmi(customerId, loanPlanId);

        assertEquals(10, result);
    }

    @Test
    public void testPayEMIWhenPaymentDateIsLessThanOrEqualToFive() throws Exception {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(101);
        emiPaymentDTO.setAmount(2000.25);
        emiPaymentDTO.setPaymentDate(LocalDate.now().withDayOfMonth(5).toString());
        emiPaymentDTO.setLateFee(0);
        emiPaymentDTO.setEmiId(new EmiMaster());
        emiPaymentDTO.setPaymentMethodId(new PaymentMethod());


        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);
        emiMaster.setCustomerId(101);
        emiMaster.setLoanPlanId(201);
        emiMaster.setEmiAmount(2000.25);
        emiMaster.setNumberOfEmi(24);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1);
        paymentMethod.setPaymentMethod("Credit Card");

        when(paymentMethodRepository.findById(anyInt())).thenReturn(Optional.of(paymentMethod));
        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(anyInt(), anyInt())).thenReturn(Optional.of(emiMaster));

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(23);

        when(emiPaymentRepository.save(any(EmiPayment.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        EmiPaymentDTO result = emiPaymentService.payEMI(101, 201, emiPaymentDTO, 1);

        assertNotNull(result);
        assertEquals(emiPaymentDTO.getId(), result.getId());
        assertEquals(emiPaymentDTO.getAmount(), result.getAmount());
        assertEquals(emiPaymentDTO.getPaymentDate(), result.getPaymentDate());
        assertEquals(emiPaymentDTO.getLateFee(), result.getLateFee());
    }

    @Test
    public void testViewEMIPayments() {
        int customerId = 101;
        int loanPlanId = 201;

        List<EmiPayment> emiPayments = new ArrayList<>();
        EmiPayment emiPayment = new EmiPayment();
        emiPayment.setId(101);
        emiPayment.setAmount(2000.25);
        emiPayment.setPaymentDate("2024-04-01");
        emiPayment.setLateFee(0);
        emiPayment.setEmiId(new EmiMaster());
        emiPayment.setPaymentMethodId(new PaymentMethod());
        emiPayments.add(emiPayment);

        when(emiPaymentRepository.findByCustomerIdAndLoanPlanId(customerId, loanPlanId)).thenReturn(emiPayments);

        List<EmiPaymentDTO> result = emiPaymentService.viewEMIPayments(customerId, loanPlanId);

        assertNotNull(result);
        assertEquals(1, result.size());

        EmiPaymentDTO resultDTO = result.get(0);
        assertEquals(emiPayment.getId(), resultDTO.getId());
        assertEquals(emiPayment.getAmount(), resultDTO.getAmount());
        assertEquals(emiPayment.getPaymentDate(), resultDTO.getPaymentDate());
        assertEquals(emiPayment.getLateFee(), resultDTO.getLateFee());
        // Add more assertions as needed to check the properties of the returned EmiPaymentDTO
    }


    @Test
    public void testPayEMIThrowsExceptionWhenFindByCustomerIdAndLoanPlanIdThrowsException() throws Exception {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(101);
        emiPaymentDTO.setAmount(2000.25);
        emiPaymentDTO.setPaymentDate(LocalDate.now().withDayOfMonth(5).toString());
        emiPaymentDTO.setLateFee(0);
        emiPaymentDTO.setEmiId(new EmiMaster());
        emiPaymentDTO.setPaymentMethodId(new PaymentMethod());


        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(anyInt(), anyInt())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> emiPaymentService.payEMI(101, 201, emiPaymentDTO, 1));
    }

    @Test
    public void testPayEMIThrowsExceptionWhenFindByIdThrowsException() throws Exception {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(101);
        emiPaymentDTO.setAmount(2000.25);
        emiPaymentDTO.setPaymentDate(LocalDate.now().withDayOfMonth(5).toString());
        emiPaymentDTO.setLateFee(0);
        emiPaymentDTO.setEmiId(new EmiMaster());
        emiPaymentDTO.setPaymentMethodId(new PaymentMethod());


        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);
        emiMaster.setCustomerId(101);
        emiMaster.setLoanPlanId(201);
        emiMaster.setEmiAmount(2000.25);
        emiMaster.setNumberOfEmi(24);

        when(emiMasterRepository.findByCustomerIdAndLoanPlanId(anyInt(), anyInt())).thenReturn(Optional.of(emiMaster));
        when(paymentMethodRepository.findById(anyInt())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> emiPaymentService.payEMI(101, 201, emiPaymentDTO, 1));
    }


}
