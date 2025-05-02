package co.edu.uniquindio.monedero_virtual.ownStructures.ownQueues;

import co.edu.uniquindio.monedero_virtual.ownStructures.Node;
import java.util.NoSuchElementException;

public class ownPriorityQueue<T extends Comparable<T>> {

    private Node<T> front;  // Apunta al primer nodo de la cola (frente)
    private int size;       // Tamaño de la cola

    public ownPriorityQueue() {
        this.front = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento a la cola de prioridad de acuerdo con su valor.
     * Los elementos con mayor valor se agregan al frente.
     * @param data El dato a agregar en la cola.
     * @throws IllegalArgumentException Si el dato es nulo.
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        
        Node<T> newNode = new Node<>(data);
        
        if (front == null || front.getData().compareTo(data) < 0) {
            // Si la cola está vacía o el nuevo dato tiene mayor prioridad
            newNode.setNextNode(front);
            front = newNode;
        } else {
            Node<T> current = front;
            while (current.getNextNode() != null && current.getNextNode().getData().compareTo(data) >= 0) {
                current = current.getNextNode();
            }
            newNode.setNextNode(current.getNextNode());
            current.setNextNode(newNode);
        }
        size++;
    }

    /**
     * Elimina y devuelve el primer elemento de la cola de prioridad.
     * @return El elemento eliminado.
     * @throws NoSuchElementException Si la cola está vacía.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty");
        }
        T data = front.getData();  // Obtiene el dato del primer nodo
        front = front.getNextNode();  // El siguiente nodo pasa a ser el nuevo frente
        size--;  // Decrementa el tamaño
        return data;
    }

    /**
     * Devuelve el primer elemento de la cola de prioridad sin eliminarlo.
     * @return El primer elemento de la cola.
     * @throws NoSuchElementException Si la cola está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty");
        }
        return front.getData();  // Devuelve el dato del frente sin eliminarlo
    }

    /**
     * Verifica si la cola de prioridad está vacía.
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * Devuelve el tamaño actual de la cola de prioridad.
     * @return El tamaño de la cola.
     */
    public int getSize() {
        return size;
    }

    /**
     * Muestra los elementos de la cola de prioridad.
     */
    public void showQueue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty");
        }
        Node<T> current = front;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNextNode();
        }
    }

    /**
     * Elimina todos los elementos de la cola de prioridad.
     */
    public void clear() {
        front = null;
        size = 0;
    }
}
