package test;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.transaction.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static test.Validator.isValidData;

@Data
public class PointsBean implements Serializable {
    private DataBaseManager dataBaseManager = new DataBaseManager();
    private ReentrantLock lock = new ReentrantLock();
    private Point newPoint = new Point();

    private List<Point> entries = new ArrayList<>();

    @PostConstruct
    private void loadEntries() {
        lock.lock();
        entries = dataBaseManager.loadEntries();
        lock.unlock();
    }

    public void addEntry() {
        lock.lock();
        if (isValidData(newPoint)) {
            try {
                dataBaseManager.addEntryToDB(newPoint);
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
            dataBaseManager.clearDB();
        } catch (SystemException | NotSupportedException | HeuristicRollbackException | HeuristicMixedException | RollbackException e) {
            e.printStackTrace();
        }
        entries.clear();

        lock.unlock();
    }
}