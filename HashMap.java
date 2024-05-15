import java.util.Objects;

public class HashMap<K, V> {
    private DLList<MyEntry<K,V>>[] hashArray;
    private int size;
    private MyHashSet<K> keySet;
    private int capacity;

    public HashMap() {
        hashArray = new DLList[10];
        capacity = 10;
        size = 0;
        keySet = new MyHashSet<K>();
    }

    public V put(K key, V value) {
        growCapacity();
        int index = Objects.hashCode(key) % hashArray.length;
        if (hashArray[index] == null) {
            hashArray[index] = new DLList<MyEntry<K, V>>();
        }
        for (MyEntry<K, V> entry : hashArray[index]) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        hashArray[index].add(new MyEntry<>(key, value));
        keySet.add(key);
        size++;
        return null;
    }

    private void growCapacity() {
        if (size >= capacity) {
            DLList<MyEntry<K,V>>[] newArray = new DLList<MyEntry<K,V>>[capacity * 2];
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

    @SuppressWarnings("unchecked")
    public V get(Object key) {
        int index = Objects.hashCode(key) % hashArray.length;
        if (hashArray[index] == null) {
            return null;
        }
        DLList<MyEntry<K, V>> list = (DLList<MyEntry<K, V>>) hashArray[index];
        for (MyEntry<K, V> entry : list) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        int index = Objects.hashCode(key) % hashArray.length;
        if (hashArray[index] == null) {
            return null;
        }
        DLList<MyEntry<K, V>> list = hashArray[index];
        for (MyEntry<K, V> entry : list) {
            if (entry.getKey().equals(key)) {
                list.remove(entry);
                keySet.remove((K)key);
                size--;
                return entry.getValue();
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public MyHashSet<K> keySet() {
        return keySet;
    }

    private void resize() {
        DLList[] oldHashArray = hashArray;
        hashArray = new DLList[oldHashArray.length * 2];
        size = 0;
        keySet = new MyHashSet<>();
        for (Object obj : oldHashArray) {
            if (obj != null) {
                DLList<MyEntry<K, V>> list = (DLList<MyEntry<K, V>>) obj;
                for (MyEntry<K, V> entry : list) {
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private static class MyEntry<K, V> {
        private K key;
        private V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
