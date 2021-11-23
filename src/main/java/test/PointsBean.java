package test;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.transaction.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static test.Validator.isValidData;

@Data
public class PointsBean implements Serializable {
    private DataBaseManager dataBaseManager;
    private Point newPoint = new Point();

    private List<Point> entries = new ArrayList<>();

    @PostConstruct
    private void loadEntries() {
        entries = dataBaseManager.loadEntries();
    }

    public void addEntry() {
        if (isValidData(newPoint)) {
            try {
                dataBaseManager.addEntryToDB(newPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            entries.add(newPoint);
        }
        newPoint = new Point();
    }

    public void clearEntry() {
        try {
            for (Point p:entries) {
                dataBaseManager.clearDB(p);
            }
            entries.clear();
        } catch (SystemException | NotSupportedException | HeuristicRollbackException | HeuristicMixedException | RollbackException e) {
            e.printStackTrace();
        }
        entries.clear();
    }
}