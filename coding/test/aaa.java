package coding.test;

import java.util.function.IntSupplier;

public class aaa implements IntSupplier {
    public Integer aaa() {
        return 1;
    }


    @Override
    public int getAsInt() {
        return 1;
    }
}
