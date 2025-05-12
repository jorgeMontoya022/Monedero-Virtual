package co.edu.uniquindio.monedero_virtual.ownStructures.ownGraphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ownDirectedGraph<T> {

       private Map<T, List<T>> adjacencyList;

    // Constructor que inicializa el grafo vacío
    public ownDirectedGraph() {
        this.adjacencyList = new HashMap<>();
    }

    // Agregar un vértice al grafo
    public void addVertex(T vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    // Eliminar un vértice del grafo
    public void removeVertex(T vertex) {
        // Elimina el vértice de las listas de adyacencia de todos los vértices
        adjacencyList.values().forEach(e -> e.remove(vertex));
        adjacencyList.remove(vertex);
    }

    // Agregar un arco dirigido entre dos vértices
    public void addEdge(T from, T to) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(to, new ArrayList<>());
        adjacencyList.get(from).add(to);
    }

    // Eliminar un arco dirigido entre dos vértices
    public void removeEdge(T from, T to) {
        List<T> edges = adjacencyList.get(from);
        if (edges != null) {
            edges.remove(to);
        }
    }

    // Obtener la lista de adyacencia de un vértice dado (vértices a los que apunta)
    public List<T> getAdjacencyList(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    // Verificar si existe un arco dirigido entre dos vértices
    public boolean hasEdge(T from, T to) {
        return adjacencyList.containsKey(from) && adjacencyList.get(from).contains(to);
    }

    // Obtener todos los vértices del grafo
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    // Obtener todos los arcos del grafo como un conjunto de cadenas
    public Set<String> getEdges() {
        Set<String> edges = new HashSet<>();
        for (T vertex : adjacencyList.keySet()) {
            for (T adjacent : adjacencyList.get(vertex)) {
                edges.add(vertex + " -> " + adjacent);
            }
        }
        return edges;
    }

    // Obtener el grado de entrada de un vértice (número de arcos entrantes)
    public int getInDegree(T vertex) {
        int inDegree = 0;
        for (List<T> edges : adjacencyList.values()) {
            if (edges.contains(vertex)) {
                inDegree++;
            }
        }
        return inDegree;
    }

    // Obtener el grado de salida de un vértice (número de arcos salientes)
    public int getOutDegree(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>()).size();
    }

    // Mostrar el grafo imprimiendo las listas de adyacencia de cada vértice
    public void displayGraph() {
        for (T vertex : adjacencyList.keySet()) {
            System.out.print(vertex + " -> ");
            List<T> edges = adjacencyList.get(vertex);
            for (T adjacent : edges) {
                System.out.print(adjacent + " ");
            }
            System.out.println();
        }
    }
    
}
