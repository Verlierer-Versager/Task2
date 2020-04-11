package LinkedList;

public class LinkedList {

    protected class Node {
        protected int value;
        protected Node next;

        protected Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }

    public void addElement(int value) {
        if (size == 0) {
            head = tail = new Node(value, null);
        } else {
            tail.next = new Node(value, null);
            tail = tail.next;
        }
        size++;
    }

    private boolean isValuePrime(int value) {
        if (value < 1) {
            return false;
        }
        if (value == 1) {
            return true;
        }
        int maxNumber = (int) Math.sqrt(value);
        for (int i = 2; i <= maxNumber; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }

    public void sort() {
        if (size <= 1) {
            return;
        }
        if (!isValuePrime(head.value)) {
            if (isValuePrime(head.next.value)) {
                head = head.next;
                size--;
            } else if (isValuePrime(head.next.next.value)) {
                head.next = head.next.next;
                size--;
            }
        }
        Node current = head;
        while (current.next != null && current.next.next != null) {
            if (isValuePrime(current.value)) {
                if (!isValuePrime(current.next.value)) {
                    Node viaOne = current.next.next;
                    current.next = viaOne;
                    size--;
                    if (isValuePrime(viaOne.value)) {
                        current = viaOne;
                    } else if (current.next.next != null && isValuePrime(current.next.next.value)) {
                        current.next = current.next.next;
                        size--;
                    }
                }
            } else if (isValuePrime(current.next.next.value)) {
                current.next = current.next.next;
                size--;
            }
            current = current.next;
        }
        if (size > 1 && isValuePrime(current.value) && !isValuePrime(tail.value)) {
            tail = current;
            size--;
        }
    }

    public void deleteFirstElement(int value) {
        if (size == 0) {
            return;
        }
        if (size == 1 && head.value == value) {
            clearAll();
            return;
        }
        if (head.value == value) {
            head = head.next;
            size--;
            return;
        }
        Node current = head;
        while (current.next.next != tail) {
            if (current.next.value == value) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
        current.next = null;
        tail = current;
        size--;
    }

    public int[] toArray() {
        int[] array = new int[size];
        Node current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.value;
            current = current.next;
        }
        return array;
    }

    public void fromArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            addElement(array[i]);
        }
    }

    public void clearAll() {
        head = null;
        tail = null;
        size = 0;
    }
}