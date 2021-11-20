package test;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static test.Validator.isValidData;

@Data
public class PointsBean implements Serializable {
    private EntityManager entityManager;
    private DataBase dataBase=new DataBase();
    private ReentrantLock lock = new ReentrantLock();
    private Point newPoint;

    private List<Point> entries;

    public PointsBean() {
        newPoint = new Point();
        entries = new ArrayList<>();
        loadEntries();
    }

    @PostConstruct
    private void loadEntries() {
        lock.lock();
        entries = dataBase.loadEntries();
        lock.unlock();
    }

    public void addEntry() {
        lock.lock();
        if (isValidData(newPoint)) {
            try {
                dataBase.addEntryToDB(newPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            entries.add(newPoint);
        }
        newPoint = new Point();
        lock.unlock();
    }

    public void clearEntry() {
        lock.lock();
        try {
            dataBase.clearDB();
        } catch (SystemException | NotSupportedException | HeuristicRollbackException | HeuristicMixedException | RollbackException e) {
            e.printStackTrace();
        }
        entries.clear();

        lock.unlock();
    }
}