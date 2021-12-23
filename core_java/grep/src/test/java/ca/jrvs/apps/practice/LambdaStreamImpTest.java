package ca.jrvs.apps.practice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LambdaStreamImpTest {
    LambdaStreamImp lambdaStreamImp;

    @BeforeAll
    void setUp(){
        lambdaStreamImp = new LambdaStreamImp();
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Create String Stream Test")
    void createStrStream() {
        String str1 = "abc";
        String str2 = "def";
        String str3 = "ghi";
        Stream<String> values = lambdaStreamImp.createStrStream(str1, str2, str3);
        assertEquals(values.count(), 3);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("To Upper Case Test")
    void toUpperCase() {
        assertEquals("TEST", lambdaStreamImp.toUpperCase("test"));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Filter Test")
    void filter() {
        assertEquals("", lambdaStreamImp.filter());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Int Stream Test")
    void createIntStream() {
        assertEquals("", lambdaStreamImp.createIntStream());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("To List Test")
    void toList() {
        assertEquals("", lambdaStreamImp.toList());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Create Int Stream Test")
    void testCreateIntStream() {
        assertEquals("", lambdaStreamImp.createIntStream());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Square Root Int Stream Test")
    void squareRootIntStream() {
        assertEquals("", lambdaStreamImp.squareRootIntStream());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Get Odd Test")
    void getOdd() {
        assertEquals("", lambdaStreamImp.getOdd());
    }
}