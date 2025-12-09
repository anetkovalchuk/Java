public class B0102 {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Аргументів немає.");
            return;
        }

        System.out.println("Аргументи у зворотному порядку:");

        for (int i = args.length - 1; i >= 0; i--) {
            System.out.println(args[i]);
        }
    }
}
