import java.util.PriorityQueue;

public class PointsByDistance {

    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double dist2() {
            return x * x + y * y; // квадрат відстані до (0,0)
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")  dist=" + Math.sqrt(dist2());
        }
    }

    public static void main(String[] args) {

        Point[] points = {
                new Point(3, 4),
                new Point(1, 1),
                new Point(-2, 2),
                new Point(0, 5),
                new Point(10, 0)
        };

        // PriorityQueue сортує за dist2 (зростання)
        PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> Double.compare(a.dist2(), b.dist2()));

        for (Point p : points) {
            pq.add(p);
        }

        System.out.println("Точки у порядку зростання відстані до (0,0):");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
