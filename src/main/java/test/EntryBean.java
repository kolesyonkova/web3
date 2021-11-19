package test;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static test.Validator.isValidData;

@Data
public class EntryBean implements Serializable {
    private static final String PERSISTENCE_UNIT_NAME = "data";
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private Entry newEntry;

    private List<Entry> entries;

    public EntryBean() {
        newEntry = new Entry();
        entries = new ArrayList<>();

        connection();
        loadEntries();
    }

    private void loadEntries() {
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entries = entityManager.createQuery("SELECT e FROM test.Entry e").getResultList();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public void addEntry() {
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (isValidData(newEntry)) {
                entityManager.persist(newEntry);
                entries.add(newEntry);
            }
            newEntry = new Entry();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    private void connection() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void clearEntry() {
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createQuery("DELETE from test.Entry").executeUpdate();
            entries.clear();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }
}