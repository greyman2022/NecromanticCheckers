public class Trio<A, B, C> {
    private A key = null;
    private B value = null;
    private C name = null;

    public Trio(A key, B value, C name) {
        this.key = key;
        this.value = value;
        this.name = name;
    }
    public Trio(){

    }

    public A getKey() {
        return key;
    }

    public B getValue() {
        return value;
    }

    public C getName() {
        return name;
    }

    public void setKey(A key) {
        this.key = key;
    }

    public void setValue(B value) {
        this.value = value;
    }

    public void setName(C name) {
        this.name = name;
    }
}