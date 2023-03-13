package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc{

    @Override
    public Stream<String> createStrStream(String... strings) {
        return Stream.of(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return Stream.of(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(str -> !str.contains(pattern));
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return Arrays.stream(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start,end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(i -> i % 2 != 0);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return message -> System.out.println(prefix + message + suffix);
    }

    @Override
    public void printMessage(String[] messages, Consumer<String> printer) {
        for (String message : messages){
            printer.accept(message);
        }

    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        intStream.filter(n -> n % 2 != 0).mapToObj(String::valueOf).forEach(printer::accept);

    }

    @Override
//    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
//        return ints.flatMap(List::stream).map(n -> n *n);
//    }
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints){
        List<Integer> result = new ArrayList<>();
        ints.forEach(list -> list.forEach(n -> result.add(n*n)));
        return result.stream();
    }

    public static void main(String[] args) {
        LambdaStreamExcImp lse = new LambdaStreamExcImp();

        // Test createStrStream
        Stream<String> strStream = lse.createStrStream("foo", "bar", "baz");
        System.out.println("createStrStream: " + lse.toList(strStream));

        // Test toUpperCase
        Stream<String> upperStrStream = lse.toUpperCase("foo", "bar", "baz");
        System.out.println("toUpperCase: " + lse.toList(upperStrStream));

        // Test filter
        Stream<String> strStream1 = lse.createStrStream("foo", "bar", "baz");
        Stream<String> filteredStrStream = lse.filter(strStream1, "a");
        System.out.println("filter: " + lse.toList(filteredStrStream));

        // Test createIntStream
        int[] arr = {1, 2, 3, 4, 5};
        IntStream intStream = lse.createIntStream(arr);
        System.out.println("createIntStream: " + lse.toList(intStream));

        // Test toList
        IntStream intStream2= lse.createIntStream(arr);
        List<Integer> intList = lse.toList(intStream2);
        System.out.println("toList: " + intList);

        // Test createIntStream with range
        IntStream rangeIntStream = lse.createIntStream(0, 5);
        System.out.println("createIntStream with range: " + lse.toList(rangeIntStream));

        // Test squareRootIntStream
        IntStream intStream3 = lse.createIntStream(arr);
        DoubleStream sqrtIntStream = lse.squareRootIntStream(intStream3);
        System.out.println("squareRootIntStream: " + lse.toList(sqrtIntStream.boxed()));

        // Test getOdd
        IntStream intStream4 = lse.createIntStream(arr);
        IntStream oddIntStream = lse.getOdd(intStream4);
        System.out.println("getOdd: " + lse.toList(oddIntStream));

        // Test getLambdaPrinter
        Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
        String[] messages = {"a", "b", "c"};
        lse.printMessage(messages, printer);

        // Test printOdd
        IntStream intStream5= lse.createIntStream(arr);
        lse.printOdd(intStream5, printer);

        // Test flatNestedInt
        List<List<Integer>> nestedInts = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4));
        Stream<List<Integer>> nestedIntStream = nestedInts.stream();
        System.out.println("flatNestedInt: " + lse.toList(lse.flatNestedInt(nestedIntStream)));
    }


}
