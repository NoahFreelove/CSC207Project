package com.project.entity.core;

public class Tuple <T, E> implements Cloneable {
    private T first;
    private E second;

    public Tuple(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(E second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Tuple)) {
            return false;
        }

        Tuple<?, ?> other = (Tuple<?, ?>) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ", " + second.toString() + ")";
    }

}
