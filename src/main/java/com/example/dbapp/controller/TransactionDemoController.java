package com.example.dbapp.controller;

import com.example.dbapp.service.TransactionDemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionDemoController {

    private final TransactionDemoService transactionDemoService;

    public TransactionDemoController(TransactionDemoService transactionDemoService) {
        this.transactionDemoService = transactionDemoService;
    }

    @GetMapping("/run-transaction-demo/{fio}")
    public String runTransactionDemo(@PathVariable String fio) {
        transactionDemoService.executeTransactionalOperations(fio);
        return "Transaction demo executed. Check logs for details.";
    }
}
