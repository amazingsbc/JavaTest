package coding.buffer;

public class Order implements IOrder{

//    状态
    int state = 0;
    @Override
    public void pay() throws InterruptedException {
        Thread.sleep(50); // 模拟需要耗费时间
        this.state = 1;
    }

    @Override
    public void show() {

        System.out.println("order status:" + this.state);
    }

}
