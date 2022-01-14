package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListAPIs {
    public static void main(String[] args) {
        List<String> animals = new ArrayList<>();

        animals.add("Lion");
        animals.add("Tiger");
        animals.add(2, "Cat");

        //Size, not length
        int size = animals.size();

        //get
        String firstElement = animals.get(0);

        //search O(n)
        Boolean hasCat = animals.contains("Cat");//true

        //index
        int catIndex = animals.indexOf("Cat");//2

        //remove
        boolean isCatRemoved = animals.remove("Cat"); //remove by object
        String removedElement = animals.remove(1); //remove by index

        //sort
        //pass comparator using lambda
        animals.sort(String::compareToIgnoreCase);

        System.out.println(Arrays.toString(animals.toArray()));
    }
}
