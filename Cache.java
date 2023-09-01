import java.util.ArrayList;
import java.util.HashMap;

public class Cache<K, V extends KeyInterface<K>> {
    private ArrayList<V> cacheList;
    private HashMap<K, Integer> accessFrequency;
    private int maxSize;
    private int hits;
    private int misses;

    public Cache(int size) {
        this.maxSize = size;
        this.cacheList = new ArrayList<V>();
        this.hits = 0;
        this.misses = 0;
        this.accessFrequency = new HashMap<K, Integer>();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Cache with " + maxSize + " entries has been created");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public V get(K key) {
        for (V value : cacheList){
            if (value.getKey().equals(key)) {
                hits++;
                accessFrequency.put(key, accessFrequency.getOrDefault(key, 0) + 1);
                return value;
            }
        }
        misses++;
        return null;
    }

    public void add(V value) {
        if (cacheList.size() >= maxSize) {
            cacheList.remove(0);
        }
        cacheList.add(value);
        accessFrequency.put(value.getKey(), accessFrequency.getOrDefault(value.getKey(), 0) + 1);
    }
    public HashMap<K, Integer> getAccessFrequency() {
        return accessFrequency;
    }
    public int getHits() {
        return hits;
    }
    public int getMisses() {
        return misses;
    }

    public void clear() {
        cacheList.clear();
        accessFrequency.clear();
        hits = 0;
        misses = 0;
        
    }
 }