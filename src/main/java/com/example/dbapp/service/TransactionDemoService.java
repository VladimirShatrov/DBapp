package com.example.dbapp.service;

import com.example.dbapp.model.Author;
import com.example.dbapp.repository.AuthorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class TransactionDemoService {
    private final PlatformTransactionManager transactionManager;

    private final AuthorRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public TransactionDemoService(PlatformTransactionManager platformTransactionManager,
                                  AuthorRepository repository) {
        this.transactionManager = platformTransactionManager;
        this.repository = repository;
    }

    public void executeTransactionalOperations(String fio) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);

        Author author = new Author();
        author.setFio("some fio");
        author.setAge(52);
        repository.save(author);

        entityManager.flush();
        Object savepoint = transaction.createSavepoint();

        author.setFio(fio);

        if (author.getFio().equals("FIO")) {
            transaction.rollbackToSavepoint(savepoint);
            entityManager.refresh(author);
            System.out.println("Rollback to savepoint.");
        }

        if (author.getFio().equals("OIF")) {
            transaction.setRollbackOnly();
        }

        repository.save(author);
        if (transaction.isRollbackOnly()) {
            System.out.println("Transaction is rollback.");
            transactionManager.rollback(transaction);
        }
        else {
            System.out.println("Transaction commit successful");
            transactionManager.commit(transaction);
        }
    }
}
