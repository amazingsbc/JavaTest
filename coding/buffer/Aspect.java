package coding.buffer;

import monad.Try;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public interface Aspect<R,V> {
    void before();
    void after();
//    static<R, V> R getProxy(Class r,V v){
//        Constructor con = r.;
//        return new r.;
//    };


    //使用的时候做了推导，在Test类里面测试的时候第一个参数传入的是Order.class，自动推导出来T为Order，所以能够转化成IOrder
    static <T> T getProxy(Class<T> cls, String... aspects) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //多个切面，通过流的方式保存到一个List中.
        var aspectInsts=Arrays.stream(aspects).map(name -> Try.ofFailable(() ->{
            var clazz = Class.forName(name);
            return (Aspect)clazz.getConstructor().newInstance();
        }))
                .filter(aspect -> aspect.isSuccess())
                .collect(Collectors.toList());

        var inst = cls.getConstructor().newInstance();
        return (T)Proxy.newProxyInstance(
                cls.getClassLoader(),
                cls.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        for(var aspect : aspectInsts){
                            aspect.get().before();
                        }
                        //接口本身就是一些方法，通过invoke指向具体的实现接口的类（也就是说，接口是可以解耦的，接口方法的this可以指定）
                        var result=method.invoke(inst);
                        for(var aspect : aspectInsts){
                            aspect.get().after();
                        }
                        return result;
                    }
                }
        );
    }
}
