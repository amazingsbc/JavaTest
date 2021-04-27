package coding.test;

import org.junit.Test;

import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicExamples {


    @Test
    public void test_mapfilter(){
        var stream = Stream.of("My","Mine")
                .flatMap(str -> str.chars().mapToObj(i ->(char)i))
                .collect(Collectors.toSet()).stream().collect(Collectors.toList());
        stream.forEach(System.out::println);
//        String hello = "abcasdasd";
//        for (int i = 0; i < hello.length(); i++) {
//            System.out.println(hello.charAt(i));
//        }
//        hello.chars().forEach(System.out::println);
    }

    @Test
    public void test_parallel(){
//        var r = new Random();
//        var list = IntStream.range(0,1_000_000)
//                .map(x -> r.nextInt(100000000))
//                .boxed()
//                .collect(Collectors.toList());
//        var t0 = System.currentTimeMillis();
//        list.stream().max((a,b) -> a-b).stream().forEach(System.out::println);
//        System.out.println("time:" + (System.currentTimeMillis() - t0));
//
//        var t1 = System.currentTimeMillis();
//        list.parallelStream().max((a,b)->a-b).stream().forEach(System.out::println);
//        System.out.println("time:" + (System.currentTimeMillis() - t1));
//
//        System.out.println(Runtime.getRuntime().availableProcessors());

        Optional<Integer> x = Optional.empty();
        var y = x.map(a -> a * a);
        System.out.println(y);
    }
}


