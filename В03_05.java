import java.util.*;

class Triangle {
    private final double x1, y1, x2, y2, x3, y3;
    private static final double EPS = 1e-9;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        this.x3 = x3; this.y3 = y3;
        if (!isValid()) throw new IllegalArgumentException("Точки не утворюють трикутник (выроджений).");
    }

    public double sideA() { return distance(x2, y2, x3, y3); } // протилежна вершині 1
    public double sideB() { return distance(x1, y1, x3, y3); } // протилежна вершині 2
    public double sideC() { return distance(x1, y1, x2, y2); } // протилежна вершині 3

    private static double distance(double xa, double ya, double xb, double yb) {
        double dx = xa - xb, dy = ya - yb;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double perimeter() {
        return sideA() + sideB() + sideC();
    }
    public double area() {
        double a = sideA(), b = sideB(), c = sideC();
        double s = (a + b + c) / 2.0;
        double val = s * (s - a) * (s - b) * (s - c);
        return val <= 0 ? 0 : Math.sqrt(val);
    }

    public boolean isValid() {
        double area2 = Math.abs(x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2)) / 2.0;
        return area2 > EPS;
    }
    private static boolean eq(double a, double b) {
        return Math.abs(a - b) <= EPS;
    }

    public boolean isEquilateral() {
        double a = sideA(), b = sideB(), c = sideC();
        return eq(a, b) && eq(b, c);
    }

    public boolean isIsosceles() {
        double a = sideA(), b = sideB(), c = sideC();
        return eq(a, b) || eq(b, c) || eq(a, c);
    }

    public boolean isRight() {
        double a = sideA(), b = sideB(), c = sideC();
        double[] s = {a, b, c};
        Arrays.sort(s);
        return eq(s[0]*s[0] + s[1]*s[1], s[2]*s[2]);
    }

    public boolean isScalene() {
        return !isIsosceles();
    }
    public Set<String> getTypes() {
        Set<String> types = new LinkedHashSet<>();
        if (isEquilateral()) types.add("Рівносторонній");
        if (isIsosceles() && !isEquilateral()) types.add("Рівнобедрений");
        if (isRight()) types.add("Прямокутний");
        if (isScalene()) types.add("Довільний");
        return types;
    }

    public String classifyExclusive() {
        if (isEquilateral()) return "Рівносторонній";
        if (isIsosceles()) return "Рівнобедрений";
        if (isRight()) return "Прямокутний";
        return "Довільний";
    }

    @Override
    public String toString() {
        return String.format("Triangle[(%.2f,%.2f),(%.2f,%.2f),(%.2f,%.2f)] area=%.4f perimeter=%.4f",
                x1,y1,x2,y2,x3,y3, area(), perimeter());
    }
}

public class TriangleExample {

    public static Map<String, Integer> countExclusiveTypes(Triangle[] arr) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        counts.put("Рівносторонній", 0);
        counts.put("Рівнобедрений", 0);
        counts.put("Прямокутний", 0);
        counts.put("Довільний", 0);
        for (Triangle t : arr) {
            String cls = t.classifyExclusive();
            counts.put(cls, counts.get(cls) + 1);
        }
        return counts;
    }

    public static Map<String, Integer> countOverlappingTypes(Triangle[] arr) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        counts.put("Рівносторонній", 0);
        counts.put("Рівнобедрений", 0);
        counts.put("Прямокутний", 0);
        counts.put("Довільний", 0);
        for (Triangle t : arr) {
            Set<String> types = t.getTypes();
            for (String type : types) counts.put(type, counts.get(type) + 1);
        }
        return counts;
    }

    public static Triangle largestByArea(Triangle[] arr) {
        if (arr == null || arr.length == 0) return null;
        Triangle best = arr[0];
        double bestArea = best.area();
        for (int i = 1; i < arr.length; ++i) {
            double a = arr[i].area();
            if (a > bestArea) { best = arr[i]; bestArea = a; }
        }
        return best;
    }
    public static void main(String[] args) {
        Triangle[] arr = new Triangle[] {
            new Triangle(0,0, 1,0, 0.5, Math.sqrt(3)/2), // рівносторонній (сторона=1)
            new Triangle(0,0, 2,0, 0,2), // прямокутний та рівнобедрений (прямий кут в (0,0))
            new Triangle(0,0, 3,0, 1,1), // довільний (скажемо скалений)
            new Triangle(0,0, 4,0, 0,3), // прямокутний (3-4-5 аналог)
            new Triangle(0,0, 2,0, 1, Math.sqrt(3)) // рівнобедрений
        };

        System.out.println("Список трикутників:");
        for (Triangle t : arr) System.out.println(t);

        System.out.println("\nПідрахунок ексклюзивних типів:");
        Map<String,Integer> exclusive = countExclusiveTypes(arr);
        exclusive.forEach((k,v) -> System.out.printf("%s : %d\n", k, v));

        System.out.println("\nПідрахунок перетинних типів (один трикутник може належати кільком типам):");
        Map<String,Integer> overlapping = countOverlappingTypes(arr);
        overlapping.forEach((k,v) -> System.out.printf("%s : %d\n", k, v));

        Triangle max = largestByArea(arr);
        System.out.println("\nТрикутник з найбільшою площею: \n" + max);
    }
}
