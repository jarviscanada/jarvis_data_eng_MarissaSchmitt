package ca.jrvs.practice.dataStructure.stackQueue;

/**
 * This is a simplified version of java.util.Deque
 *
 *
 */
public class JDeque<E> implements JStack<E>, JQueue<E>{

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E peek() {
        return null;
    }
}
