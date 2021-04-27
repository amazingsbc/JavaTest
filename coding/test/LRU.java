package coding.test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LRU<k,v> implements Iterable {

    int Max = 3;
    LinkedHashMap<k,v> cache = new LinkedHashMap<k, v>();

    public void cache(k key,v value){
        if(cache.containsKey(key)){
            cache.remove(key);
        } else if(cache.size() >=Max){
                Iterator<k> it = cache.keySet().iterator();
                k first = it.next();
                cache.remove(first);
            }
        cache.put(key,value);
    }
    @Override
    public Iterator<k> iterator() {
        Iterator<Map.Entry<k,v>> it = cache.entrySet().iterator();
        return new Iterator() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Object next() {
                return it.next().getKey();
            }
        };
    }

    public static void main(String[] args) {
        LRU lru = new LRU();

        lru.cache("A",1);
        lru.cache("B",2);
        lru.cache("C",3);

        lru.cache("D",4);
        lru.cache("B",4);

        System.out.println("end <-"+ StreamSupport.stream(lru.spliterator(),false)
                                                    .map(Object::toString)
                                                    .collect(Collectors.joining("<-")));
    }

}
