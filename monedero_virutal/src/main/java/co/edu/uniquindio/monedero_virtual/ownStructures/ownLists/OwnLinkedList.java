package co.edu.uniquindio.monedero_virtual.ownStructures.ownLists;

import java.util.Iterator;
import java.util.NoSuchElementException;


import co.edu.uniquindio.monedero_virtual.ownStructures.Node;

public class OwnLinkedList<T> implements Iterable<T> {
    Node<T> head; // Apunta al primer nodo de la lista
    int size;     // Almacena el tamaño de la lista

    public OwnLinkedList() {
        this.head = null; // La lista empieza vacía
        this.size = 0;    // Inicialmente, la lista está vacía
    }

    public Node<T> getHead() {
        return head;
    }

    /**
     * Agrega un nuevo nodo con el valor especificado al final de la lista enlazada.
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNextNode() != null) {
                current = current.getNextNode();
            }
            current.setNextNode(newNode);
        }
        size++;
    }

    /**
     * Elimina un nodo con el valor especificado de la lista enlazada.
     */
    public void remove(T data) {
        if (head == null) {
            System.err.println("The list is empty");
            return;
        }
        if (head.getData().equals(data)) {
            head = head.getNextNode();
            size--;
            return;
        }

        Node<T> current = head;
        while (current.getNextNode() != null && !current.getNextNode().getData().equals(data)) {
            current = current.getNextNode();
        }

        if (current.getNextNode() != null) {
            current.setNextNode(current.getNextNode().getNextNode());
            size--;
        } else {
            System.err.println("The element is not in the list");
        }
    }

    /**
     * Agrega un nuevo nodo con el valor especificado al inicio de la lista enlazada.
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            newNode.setNextNode(head);
            head = newNode;
        }
        size++;
    }

    /**
     * Elimina el primer nodo de la lista enlazada.
     */
    public void deleteFirst() {
        if (head == null) {
            System.err.println("The list is empty");
            return;
        }
        head = head.getNextNode();
        size--;
    }

    /**
     * Elimina el último nodo de la lista enlazada.
     */
    public void deleteLast() {
        if (head == null) {
            System.err.println("The list is empty");
            return;
        }
        if (head.getNextNode() == null) {
            head = null;
            size--;
            return;
        }

        Node<T> current = head;
        while (current.getNextNode().getNextNode() != null) {
            current = current.getNextNode();
        }
        current.setNextNode(null);
        size--;
    }

    /**
     * Muestra los elementos de la lista enlazada en la consola.
     */
    public void showList() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNextNode();
        }
    }

    /**
     * Obtiene la posición de un nodo en la lista enlazada.
     */
    public int positionElement(T data) {
        Node<T> current = head;
        int position = 0;
        while (current != null) {
            if (current.getData().equals(data)) {
                return position;
            }
            current = current.getNextNode();
            position++;
        }
        return -1;
    }

    /**
     * Modifica el valor de un nodo en la lista.
     */
    public void updateElement(T oldData, T newData) {
        if (isEmpty()) {
            System.err.println("The list is empty");
            return;
        }
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(oldData)) {
                current.setData(newData);
                return;
            }
            current = current.getNextNode();
        }
        System.err.println("The node with the value " + oldData + " was not found in the list.");
    }

    /**
     * Borra toda la lista enlazada.
     */
    public void clearList() {
        head = null;
        size = 0;
    }

    /**
     * Verifica si la lista está vacía.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Devuelve el tamaño actual de la lista enlazada.
     */
    public int getSize() {
        return size;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            private Node<T> actual = head;
             @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                 if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T dato = actual.getData();
                actual = actual.getNextNode();
                return dato;
            }
        };
    }

}
        

