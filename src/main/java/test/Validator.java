package test;

public abstract class Validator {
    public static boolean isValidData(Point point) {
        try {
            if (point.getX() != null && point.getY() != null && point.getR() != null) {
                double x = Double.parseDouble(point.getX().replace(',', '.'));
                double y = Double.parseDouble(point.getY().replace(',', '.'));
                double r = Double.parseDouble(point.getR().replace(',', '.'));
                if (isValid(x, y, r)) {
                    point.setHitResult(checkArea(x, y, r) ? "Да" : "Нет");
                    return true;
                } else return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkArea(double x, double y, double r) {
        return (checkRectangle(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r));
    }

    public static boolean checkRectangle(double x, double y, double r) {
        return x <= 0 && y <= 0 && x >= -r && y >= -r / 2;
    }

    public static boolean checkTriangle(double x, double y, double r) {
        return x >= 0 && y >= 0 && y <= -x + r / 2;
    }

    public static boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y >= 0 && x * x + y * y <= (r / 2) * (r / 2);
    }

    public static boolean isValid(double x, double y, double r) {
        return (x >= -3 && x <= 5) && (y >= -5 && y <= 5) && (r >= 1 && r <= 5);
    }
}
