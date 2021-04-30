package coding.buffer;

import org.junit.Test;

public class TimeUsageAspect implements Aspect{

    long start;

    @Override
    public void before() {
        start = System.currentTimeMillis();
    }

    @Override
    public void after() {
        var usage = System.currentTimeMillis() - start;
//        System.out.println(usage);
        System.out.format("time usage : %dms\n", usage);
    }
}

