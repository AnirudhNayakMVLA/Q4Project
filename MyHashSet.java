public class MyHashSet<E> {
    private Object[] hashArray;
    private int capacity;
    private int size;

    public MyHashSet() {
        hashArray = new Object[10];
        capacity = 10;
        size = 0;
    }

    public boolean add(E e) {
        growCapacity();
        int num = e.hashCode();
        if (hashArray[num] == null) {
            hashArray[num] = e;
            size++;
            return true;
        }
        return false;
    }

    public boolean remove(E e) {
        int num = e.hashCode();
        if (hashArray[num] != null) {
            hashArray[num] = null;
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(E e) {
        int num = e.hashCode();
        return hashArray[num] != null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        hashArray = new Object[10];
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @SuppressWarnings("unchecked")
    public DLList<E> toDLList() {
        DLList<E> list = new DLList<E>();
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                list.add((E)hashArray[i]);
                
            }
        }
        return list;
    }

    private void growCapacity() {
        if (size >= capacity) {
            Object[] newArray = new Object[capacity * 2];
            for (int i = 0; i < hashArray.length; i++) {
                if (hashArray[i] != null) {
                    int newnum = hashArray[i].hashCode();
                    newArray[newnum] = hashArray[i];
                }
            }
            hashArray = newArray;
            capacity *= 2;
        }
    }
}