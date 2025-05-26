package co.edu.uniquindio.monedero_virtual.ownStructures.ownLists;

import java.util.NoSuchElementException;

import co.edu.uniquindio.monedero_virtual.ownStructures.Node;
import java.util.Iterator;

public class OwnCircularList<T> implements Iterable<T> {

    private Node<T> head;  // Apunta al primer nodo de la lista circular
    private Node<T> tail;  // Apunta al último nodo de la lista circular
    private int size;  
    private boolean firstIteration = true;
        // Almacena el tamaño de la lista circular

    public OwnCircularList() {
        this.head = null;   // La lista empieza vacía
        this.tail = null;   // No hay nodo final
        this.size = 0;      // Inicialmente, la lista está vacía
    }

    /**
     * Agrega un nuevo nodo con el valor especificado al final de la lista circular.
     * @throws IllegalStateException Si la operación no puede realizarse (aunque no es probable).
     */
    public void add(T data) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null");

        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            newNode.setNextNode(head);  // El siguiente de tail es head (circular)
        } else {
            tail.setNextNode(newNode);  // El siguiente de tail apunta al nuevo nodo
            tail = newNode;             // Actualizamos tail al nuevo nodo
            tail.setNextNode(head);     // El siguiente de tail es head (circular)
        }
        size++;
    }

    /**
     * Agrega un nuevo nodo con el valor especificado al inicio de la lista circular.
     * @throws IllegalStateException Si la operación no puede realizarse.
     */
    public void addFirst(T data) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null");

        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            newNode.setNextNode(head); // Circularidad
        } else {
            newNode.setNextNode(head);
            head = newNode;
            tail.setNextNode(head); // El siguiente de tail es head (circular)
        }
        size++;
    }

    /**
     * Elimina un nodo con el valor especificado de la lista circular.
     * @param data El valor a eliminar.
     * @throws NoSuchElementException Si el elemento no se encuentra en la lista.
     */
    public void remove(T data) {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        if (data == null) throw new IllegalArgumentException("Data cannot be null");

        Node<T> current = head;
        Node<T> previous = null;

        // Caso cuando el nodo a eliminar es el head
        if (head.getData().equals(data)) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.getNextNode();
                tail.setNextNode(head); // Actualizamos tail para que apunte a head
            }
            size--;
            return;
        }

        // Caso cuando el nodo a eliminar está en el medio o final
        do {
            previous = current;
            current = current.getNextNode();
            if (current.getData().equals(data)) {
                previous.setNextNode(current.getNextNode());
                if (current == tail) {
                    tail = previous; // Actualizamos tail
                }
                size--;
                return;
            }
        } while (current != head); // Si llegamos de nuevo al head, significa que no encontramos el nodo

        throw new NoSuchElementException("The element was not found in the list");
    }

    /**
     * Elimina el primer nodo de la lista circular.
     * @throws IllegalStateException Si la lista está vacía.
     */
    public void deleteFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.getNextNode();
            tail.setNextNode(head); // Actualizamos tail para que apunte a head
        }
        size--;
    }

    /**
     * Elimina el último nodo de la lista circular.
     * @throws IllegalStateException Si la lista está vacía.
     */
    public void deleteLast() {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node<T> current = head;
            while (current.getNextNode() != tail) {
                current = current.getNextNode();
            }
            current.setNextNode(head); // El penúltimo nodo apunta a head
            tail = current;
        }
        size--;
    }

    /**
     * Muestra los elementos de la lista circular en la consola.
     * @throws IllegalStateException Si la lista está vacía.
     */
    public void showList() {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        Node<T> current = head;
        do {
            System.out.println(current.getData());
            current = current.getNextNode();
        } while (current != head);
    }

    /**
     * Verifica si la lista circular está vacía.
     * @return true si la lista está vacía.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Devuelve el tamaño actual de la lista circular.
     * @return El tamaño de la lista.
     */
    public int getSize() {
        return size;
    }

    /**
     * Borra toda la lista circular.
     */
    public void clearList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Obtiene el primer nodo de la lista circular.
     * @throws IllegalStateException Si la lista está vacía.
     * @return El primer nodo de la lista.
     */
    public Node<T> getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        return head;
    }

    /**
     * Obtiene el último nodo de la lista circular.
     * @throws IllegalStateException Si la lista está vacía.
     * @return El último nodo de la lista.
     */
    public Node<T> getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        return tail;
    }

    /**
     * Obtiene la posición de un nodo en la lista circular.
     * @param data El valor a buscar.
     * @throws IllegalStateException Si la lista está vacía.
     * @return La posición del nodo, o -1 si no se encuentra.
     */
    public int positionElement(T data) {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        if (data == null) throw new IllegalArgumentException("Data cannot be null");

        Node<T> current = head;
        int position = 0;
        do {
            if (current.getData().equals(data)) {
                return position;
            }
            current = current.getNextNode();
            position++;
        } while (current != head);
        
        return -1; // Si no se encuentra el nodo
    }

    /**
     * Modifica el valor de un nodo en la lista circular.
     * @param oldData El valor antiguo del nodo.
     * @param newData El valor nuevo del nodo.
     * @throws IllegalStateException Si la lista está vacía.
     * @throws NoSuchElementException Si el nodo no se encuentra.
     */
    public void updateElement(T oldData, T newData) {
        if (isEmpty()) {
            throw new IllegalStateException("The list is empty");
        }
        if (oldData == null || newData == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        Node<T> current = head;
        do {
            if (current.getData().equals(oldData)) {
                current.setData(newData);
                return;
            }
            current = current.getNextNode();
        } while (current != head);
        
        throw new NoSuchElementException("The node with the value " + oldData + " was not found in the list.");
    }

    /**
     * Obtiene el nodo en una posición específica (indexada desde 0).
     * @param index El índice del nodo a obtener.
     * @throws IndexOutOfBoundsException Si el índice está fuera de rango.
     * @return El nodo en la posición dada.
     */
    public Node<T> getNodeAtPosition(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNextNode();
        }
        return current;
    }

    /**
     * Verifica si un nodo con el valor especificado existe en la lista circular.
     * @param data El valor a buscar.
     * @return true si el nodo existe, false si no.
     */
    public boolean contains(T data) {
        if (isEmpty()) {
            return false;
        }
        if (data == null) throw new IllegalArgumentException("Data cannot be null");

        Node<T> current = head;
        do {
            if (current.getData().equals(data)) {
                return true;
            }
            current = current.getNextNode();
        } while (current != head);

        return false;
    }
    
    public void addAll(OwnCircularList<T> otherList) {
        if (otherList == null || otherList.isEmpty()) {
            return;
        }

        for (T data : otherList) {
            this.add(data);
        }
    }

     @Override
    public Iterator<T> iterator() {
        return new CircularListIterator();
    }

    private class CircularListIterator implements Iterator<T> {
        private Node<T> current = head;
        private boolean firstIteration = true;

        @Override
        public boolean hasNext() {
            return current != null && (firstIteration || current != head);
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T data = current.getData();
            current = current.getNextNode();
            firstIteration = false;
            return data;
        }
    }
}
        

  

