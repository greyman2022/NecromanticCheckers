public class Pair<E, U> {
    private E key = null;
    private U value = null;

    public Pair(E key, U value) {
        this.key = key;
        this.value = value;
    }
    public Pair(){

    }

    public E getKey() {
        return key;
    }

    public U getValue() {
        return value;
    }

    public void setKey(E key) {
        this.key = key;
    }

    public void setValue(U value) {
        this.value = value;
    }
}