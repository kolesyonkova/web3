package test;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
            transaction.begin();
            Query query = entityManager.createQuery("SELECT e FROM test.Entry e");
            entries = query.getResultList();
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
        transaction = entityManager.getTransaction();
    }

    public void clearEntry() {
        try {
            transaction.begin();
            Query query = entityManager.createQuery("DELETE from test.Entry");
            query.executeUpdate();
            entries.clear();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public boolean isValidData(Entry entry) {
        try {
            if (entry.getX() != null && entry.getY() != null && entry.getR() != null) {
                double x = Double.parseDouble(entry.getX().replace(',', '.'));
                double y = Double.parseDouble(entry.getY().replace(',', '.'));
                double r = Double.parseDouble(entry.getR().replace(',', '.'));
                if (isValid(x, y, r)) {
                    entry.setHitResult(checkArea(x, y, r) ? "Да" : "Нет");
                    return true;
                } else return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkArea(double x, double y, double r) {
        return (checkRectangle(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r));
    }

    public boolean checkRectangle(double x, double y, double r) {
        return x <= 0 && y <= 0 && x >= -r && y >= -r / 2;
    }

    public boolean checkTriangle(double x, double y, double r) {
        return x >= 0 && y >= 0 && y <= -x + r / 2;
    }

    public boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y >= 0 && x * x + y * y <= (r / 2) * (r / 2);
    }

    public boolean isValid(double x, double y, double r) {
        return (x >= -3 && x <= 5) && (y >= -5 && y <= 5) && (r >= 1 && r <= 5);
    }
}