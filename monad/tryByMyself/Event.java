package monad.tryByMyself;

/**
 * 实现了一个monad思想的类
 */

import java.net.FileNameMap;
import java.util.stream.Stream;

public class Event<T> {
    T data;

    public Event(T data) {
        this.data = data;
    }

    private static class EventData{

        Integer id;
        String msg;

        public EventData(Integer id, String msg) {
            this.id = id;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "EventData{" +
                    "id=" + id +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    static class Transforms{
        static EventData Transforms(Integer id) {
            switch (id){
                case 0:
                    return new EventData(id,"Start");
                case 1:
                    return new EventData(id,"Running");
                case 2:
                    return new EventData(id,"Done");
                case 3:
                    return new EventData(id,"Fail");
                default:
                    return new EventData(id,"Error");
            }
        }
        static void hhh(){
            System.out.println("hhh");
        }
    }

    //作为monad，需要写出如何描述一个函数
    @FunctionalInterface
    public interface FN<A,B>{
        B apply(A a);
    }

    /**
     * 有个apply方法，所以要指定apply方法，可以用一个函数引过去
     *  monad 思想要保证自函子，讲this.data 的包装品，映射到另一种Event < ?>
     */
    <B> Event<?> map(FN<T,B> f){
        return new Event<B>(f.apply(this.data));
    }

    public static void main(String[] args) {
        Stream<Event<Integer>> s = Stream.of(
                new Event<Integer>(1),
                new Event<Integer>(2),
                new Event<Integer>(3),
                new Event<Integer>(4),
                new Event<Integer>(5)
        );

        /**
         * 为什么这里写
         * s.map(event-> event.map(Transforms::Transforms))
         *                 .forEach(System.out::println);
         * 打印不出来？
         * 要写 e -> System.out::println(e.data); 才行
         */
        s.map(event-> event.map(Transforms::Transforms))
                .forEach(e->System.out.println(e.data));
    }
}
