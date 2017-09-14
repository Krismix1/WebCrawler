package logic;

// Taken from https://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples
public class Pair<L, R> {

    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) return false;
        Pair pairO = (Pair) obj;
        return this.left.equals(pairO.left) &&
                this.right.equals(pairO.right);
    }

    @Override
    public String toString() {
        return "[ " + left.toString() + ", " + right.toString() + "]";
    }

}