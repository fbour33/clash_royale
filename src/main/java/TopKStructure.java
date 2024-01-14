import java.util.Comparator;
import java.util.TreeMap;

public class TopKStructure<K, V> {

    public int k;
    public TreeMap<K, V> topK;

    public TopKStructure(int k, Comparator<K> comparator){
        this.k = k;
        this.topK = new TreeMap<>(comparator);
    }

    public void addDeck(K key, V value){
        topK.put(key, value);
        if(topK.size() > k)
            topK.remove(topK.firstKey());
    }

    public TreeMap<K, V> getTopK() {
        return topK;
    }

    public void clearTreeMap(){
        topK.clear();
    }
}
