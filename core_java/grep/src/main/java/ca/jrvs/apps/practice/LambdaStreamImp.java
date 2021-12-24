package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamImp implements LambdaStreamExc {

    @Override
    public Stream<String> createStrStream(String... strings) {
        //Stream<String> stringStream = (string) -> stringStream.collect(Collectors.toList());
        Stream<String> stringStream = Arrays.stream(strings);
        return stringStream;
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        Stream<String> upperCaseStrings = Arrays.stream(strings).map(s -> s.toUpperCase());
        return upperCaseStrings;
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        Stream<String> filteredResult = stringStream.filter(s -> s.matches(pattern));
        return filteredResult;
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        IntStream intStream = Arrays.stream(arr);
        return intStream;
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        List<E> list = stream.collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        //List<Integer> intList = intStream.collect(Collectors.toList());

        return null;
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        IntStream intStream = IntStream.range(start,end);
        return intStream;
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        DoubleStream doubleStream = intStream.mapToDouble(i -> (double)i).map(i -> Math.sqrt(i));
        return doubleStream;
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        IntStream oddNumStream = intStream.filter(n -> n%2 != 0);
        return oddNumStream;
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        //LambdaStreamExc lse = new LambdaStreamImp();
        //Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
        //Consumer<String> printer = lse.getLambdaPrinter(prefix, suffix);
        //printer.accept("Message body");
        //Consumer<String> printer = ;
        return null;
        //return printer;
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {

    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {

    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return null;
    }
}
