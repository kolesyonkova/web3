
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EntryBean {

    private Entry newEntry = new Entry();

    private List<Entry> entries = new ArrayList<>();

    public void clearEntry() {
        entries = new ArrayList<>();
        newEntry = new Entry();
    }

    public void addEntry() {
        System.out.println(newEntry.toString());
        System.out.println(isValidData(newEntry));
        if (isValidData(newEntry)) {
            entries.add(newEntry);
        }
        newEntry = new Entry();
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