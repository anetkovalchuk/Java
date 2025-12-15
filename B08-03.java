import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<T> {

    private Map<T, Set<T>> adj = new HashMap<>();

    // додати вершину
    public void addVertex(T v) {
        adj.putIfAbsent(v, new HashSet<>());
    }

    // видалити вершину
    public void removeVertex(T v) {
        if (!adj.containsKey(v)) return;

        for (T u : adj.get(v)) {
            adj.get(u).remove(v);
        }
        adj.remove(v);
    }

    // додати ребро (неорієнтований граф)
    public void addEdge(T v1, T v2) {
        addVertex(v1);
        addVertex(v2);
        adj.get(v1).add(v2);
        adj.get(v2).add(v1);
    }

    // видалити ребро
    public void removeEdge(T v1, T v2) {
        if (adj.containsKey(v1)) adj.get(v1).remove(v2);
        if (adj.containsKey(v2)) adj.get(v2).remove(v1);
    }

    // вивести граф
    public void printGraph() {
        for (T v : adj.keySet()) {
            System.out.println(v + " -> " + adj.get(v));
        }
    }

    public static void main(String[] args) {
        Graph<String> g = new Graph<>();

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");
        g.addEdge("C", "D");

        g.printGraph();

        g.removeEdge("A", "B");
        g.removeVertex("D");

        System.out.println("After changes:");
        g.printGraph();
    }
}
