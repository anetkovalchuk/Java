public class SinTaylorSum {
    public static double sinSum(double x, double epsilon) {
        double term;      
        double sum = 0.0; 
        int k = 0;

        do {
            term = Math.pow(-1, k) * Math.pow(x, 2 * k + 1) / factorial(2 * k + 1);

            if (Math.abs(term) <= epsilon) {
                sum += term;
            }

            k++;
        } while (Math.abs(term) > 1e-15 && k < 100);

        return sum;
    }

    public static long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        double x = 0.5;
        double epsilon = 0.001;

        double result = sinSum(x, epsilon);
        System.out.println("Сума доданків з модулем ≤ ε: " + result);
    }
}

