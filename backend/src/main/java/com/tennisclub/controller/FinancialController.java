package com.tennisclub.controller;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial")
public class FinancialController {

    @Autowired
    private FinancialService financialService;
    
    @PostMapping("/transaction")
    public FinancialTransaction processTransaction(@RequestBody FinancialTransaction transaction) {
        return financialService.processTransaction(transaction);
    }
    
    @PostMapping("/refund")
    public FinancialTransaction processRefund(@RequestBody FinancialTransaction transaction) {
        return financialService.processRefund(transaction);
    }
    
    @GetMapping("/report")
    public String generateReport() {
        return financialService.generateReport();
    }
    
    @PostMapping("/reconcile")
    public String reconcileAccounts() {
        return financialService.reconcileAccounts() ? "Reconciliation successful" : "Reconciliation failed";
    }
}
