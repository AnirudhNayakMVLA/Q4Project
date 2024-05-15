import java.util.Arrays;
import java.util.Iterator;
import java.io.Serializable;
import java.lang.Iterable;

public class ArrayList<E> implements Iterable<E>, Serializable{
    private Object[] list;
    private int size;
    private int capacity;

    public ArrayList() {
        size = 0;
        capacity = 1;
        list = new Object[capacity];
    }

    public ArrayList(int size) {
        this.size = size;
        capacity = size + 1;
        list = new Object[capacity];
    }

    public boolean add(E e) {
        size++;
        if (list.length <= size) {
            capacity *= 2;
            list = Arrays.copyOf(list, capacity);
        }

        try {
            list[size - 1] = e;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void add(int num, E e) {
        size++;
        if (list.length <= size) {
            capacity *= 2;
            list = Arrays.copyOf(list, capacity);
        }
        for (int i = num; i < size; i++) {
            Object temp = list[i + 1];
            list[i + 1] = list[num];
            list[num] = temp;
        }
        list[num] = e;
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
        return (E) list[i];
    }

    public E remove(int i) {
        E e = get(i);
        if (i >= size - 1)
            list[i] = null;
        for (int j = i + 1; j < list.length; j++) {
            list[i] = list[j];
            i++;
        }
        list[size - 1] = null;
        size--;
        return e;
    }

    public E remove(E e) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(e)) {
                remove(i);
            }
        }
        return e;
    }

    public void set(int i, E e) {
        list[i] = e;
    }

    public String toString() {
        String endString = "[";
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                endString += ", ";
            }
            endString += list[i].toString();
        }
        endString += "]";
        return endString;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new myIterator();
    }

    private class myIterator implements Iterator<E>, Serializable{
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            return get(index++);
        }
    };
}
