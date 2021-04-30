package coding.buffer;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BufferExamples {

    @Test
    public void gen() throws IOException{
        Random r = new Random();
        var filename = "word";

        var BufferSize = 4 * 1024;
        var fout = new BufferedOutputStream(new FileOutputStream(filename),BufferSize);
//        var fout = new FileOutputStream(filename);

        var start = System.currentTimeMillis();

        for(int i = 0; i < 100000000; i++){
            for(int j = 0; j <5; j++){
                fout.write(97 + r.nextInt(5));
            }
        }
        fout.close();
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 和nio相比，withBuffer不一定慢，但是nio提供了一种规范化的操作。
     * @throws IOException
     */
    @Test
    public void read_test_withBuffer() throws IOException {
        var fileName = "word";
        var in = new BufferedInputStream(new FileInputStream(fileName));

        var start = System.currentTimeMillis();

        int b;
//        var sb = new StringBuilder();
        var bytes = new byte[1024*8];
        while((b = in.read(bytes)) != -1) {
        }

        var end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        in.close();
    }


    @Test
    public void read_test_nio() throws IOException{

        // New IO
        var fileName = "word";
        var channel = new FileInputStream(fileName).getChannel();

        var buff = ByteBuffer.allocate(1024);

        var start = System.currentTimeMillis();

        while(channel.read(buff) != -1){
            buff.flip();
            // 读取数据
//            System.out.println(new String(buff.array()));
//            break;
            buff.clear();
        }

        System.out.format("%dms\n",System.currentTimeMillis() - start);
    }


    @Test
    public void test_async_read() throws IOException, ExecutionException, InterruptedException {
        var filename = "word";
        var channel =
                AsynchronousFileChannel.open(Path.of(filename), StandardOpenOption.READ);

        var buf = ByteBuffer.allocate(1024*8);
//        while ()
        Future<Integer> operation = channel.read(buf, 0);
        var numReads = operation.get();

        buf.flip();
        var chars = new String(buf.array());
        buf.clear();
    }

    @Test
    public void test_chinese(){
        var raw = "长坂桥头杀气生，横枪立马眼圆睁。一声好似轰雷震，独退曹家百万兵。";
        var charset = StandardCharsets.UTF_8;
        var bytes = charset.encode(raw).array();
        var bytes2 = Arrays.copyOfRange(bytes,0,11);

        var bbuf = ByteBuffer.allocate(12);
        var cbuf = CharBuffer.allocate(12);

        bbuf.put(bytes2);
        bbuf.flip();

        charset.newDecoder().decode(bbuf,cbuf,true);

        cbuf.flip();

        var tmp = new char[cbuf.length()];
        if(cbuf.hasRemaining()){
            cbuf.get(tmp);
            System.out.println("here:" + new String(tmp));
        }
    }
}
