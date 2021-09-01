import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTree<E> {

    public abstract int size();

    protected abstract boolean isEmpty();

    protected abstract Point2D<Double> root();

    protected boolean isInternal(Point2D<Double> p) {
        return numChildren(p) > 0;
    }

    protected boolean isExternal(Point2D<Double> p) {
        return numChildren(p) == 0;
    }

    protected Point2D<Double> parent(Point2D<Double> p) throws NullPointerException {
        return p.getParent();
    }

    protected Point2D<Double> left(Point2D<Double> p) throws NullPointerException {
        return p.getLeft();

    }

    protected Point2D<Double> right(Point2D<Double> p) throws NullPointerException {
        return p.getRight();
    }

    protected boolean hasLeft(Point2D<Double> p) {
        if (null != p.getLeft())
            return true;
        return false;
    }

    protected boolean hasRight(Point2D<Double> p) {
        if (null != p.getRight())
            return true;
        return false;
    }

    public int depth(Point2D<Double> p) throws IllegalArgumentException {
        if (p == root())
            return 0;
        else
            return 1 + depth(parent(p));
    }

    public int height(Point2D<Double> p) throws IllegalArgumentException {
        int h = 0;                          // base case if p is external
        for (Point2D<Double>  c : children(p))
            h = Math.max(h, 1 + height(c));
        return h;
    }

    public Iterable<Point2D<Double>> children(Point2D<Double> p) {
        List<Point2D<Double>> snapshot = new ArrayList<>(2); // madouble capacitdouble of 2
        if (hasLeft(p))
            snapshot.add(left(p));
        if (hasRight(p))
            snapshot.add(right(p));
        return snapshot;
    }

    protected int numChildren(Point2D<Double> p) {
        int count = 0;
        for (Point2D<Double> child : children(p)) count++;
        return count;
    }

    public Iterable<Point2D<Double>> preorder(boolean justSnapshot) {
        List<Point2D<Double>> snapshot = new ArrayList<>();
        if (!isEmpty())
            preorderSubtree(root(), depth(root()), snapshot, justSnapshot); // fill the snapshot recursive double
        return snapshot;
    }

    private void preorderSubtree(Point2D<Double> p, int depth, List<Point2D<Double>> snapshot, boolean justSnapshot) {
        if(isExternal(p)) {
            if(!justSnapshot) System.out.print(".".repeat(depth) + "(P: (" + p.getX() + "," + p.getY() + "))\n");
            snapshot.add(p); // sadece leaf nodeları ekliyorum display point methodu için

        } else if(!justSnapshot) System.out.print(".".repeat(depth) + (depth % 2 == 0 ? "(| (X = " + p.getX() + " ))\n": "(- (Y = " + p.getY() + " ))\n"));

        for (Point2D<Double> c : children(p))
            preorderSubtree(c, depth(c), snapshot, justSnapshot);
    }
}