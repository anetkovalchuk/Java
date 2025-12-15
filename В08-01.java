// Клас стек як рекурсивна структура даних для об’єктів довільного типу
public class RecursiveStack<T> {

    // Рекурсивна структура: елемент + посилання на "хвіст" стеку
    private static class Node<T> {
        T value;
        Node<T> next;   // решта стеку

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> top;   // верхній елемент стеку
    private int size;      // кількість елементів

    public RecursiveStack() {
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    // покласти елемент на вершину стеку
    public void push(T value) {
        top = new Node<>(value, top);
        size++;
    }

    // зняти елемент з вершини стеку
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек порожній");
        }
        T result = top.value;
        top = top.next;
        size--;
        return result;
    }

    // подивитися верхній елемент, не видаляючи
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек порожній");
        }
        return top.value;
    }

    // рекурсивний вивід стеку (зверху донизу)
    public void printRecursive() {
        printFromNode(top);
        System.out.println();
    }

    private void printFromNode(Node<T> node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        printFromNode(node.next);
    }

    // приклад використання
    public static void main(String[] args) {
        RecursiveStack<String> stack = new RecursiveStack<>();
        stack.push("one");
        stack.push("two");
        stack.push("three");

        System.out.print("Стек: ");
        stack.printRecursive();          // three two one

        System.out.println("Верх: " + stack.peek()); // three

        System.out.println("pop -> " + stack.pop()); // three
        System.out.print("Після pop: ");
        stack.printRecursive();          // two one
    }
}
