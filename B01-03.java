public class B0103 {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Аргументів немає.");
            return;
        }

        long product = 1;

        for (String a : args) {
            try {
                int value = Integer.parseInt(a);  // пробуємо перетворити аргумент на int
                product *= value;
            } catch (NumberFormatException e) {
                System.out.println("Аргумент \"" + a + "\" не є цілим числом!");
                return;
            }
        }

        System.out.println("Добуток аргументів = " + product);
    }
}
