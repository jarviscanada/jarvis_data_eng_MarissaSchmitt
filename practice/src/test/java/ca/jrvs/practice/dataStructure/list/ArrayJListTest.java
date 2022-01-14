package ca.jrvs.practice.dataStructure.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayJListTest {

    @Test
    public void add() {
        List<String> list = new ArrayList<>();
        list.add("first");
        assertEquals("first", list.get(0));
    }

    @Test
    public void toArray() {
        Object[] elementData = new Object[]{"Test"};

    }

    @Test
    public void size() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void indexOf() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void get() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void clear() {
    }
}