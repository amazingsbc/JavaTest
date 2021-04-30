package coding.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

public class ObjectFactory {

    public static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        //拿到反射，runtime时候的才能拿到
        var annotations = clazz.getAnnotations();

        var aspects = new LinkedList<IAspect>();
        for(var annotation: annotations){
            if(annotation instanceof Aspect){
                /**
                 * aspect的type是一个class类型，是在使用注解时候写进去的 注解使用例子:
                 * @Aspect(type =TimeUsageAspect.class)
                 */
                var type = ((Aspect) annotation).type();
                var aspect = (IAspect)(type.getConstructor().newInstance());
                aspects.push(aspect);
            }
        }

        var inst = clazz.getConstructor().newInstance();
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        aspects.forEach(aspect ->aspect.before());
                        var result = method.invoke(inst);
                        aspects.forEach(aspect ->aspect.after());
                        return result;
                    }
                }
        );
    }
}
