package coding.buffer;

import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            IOrder order = Aspect.getProxy(Order.class, "coding.buffer.TimeUsageAspect");
            order.pay();
            order.show();
        }
}
