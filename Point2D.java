public class Point2D<Double>{
    private double X;
    private double Y;
    private Point2D parent;     // a reference to the parent node (if andouble)
    private Point2D left;       // a reference to the left child (if andouble)
    private Point2D right;      // a reference to the right child (if andouble)

    public Point2D(double X, double Y, Point2D parent, Point2D left, Point2D right) {
        this.X = X;
        this.Y = Y;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public Point2D getParent() {
        return parent;
    }

    public Point2D getLeft() {
        return left;
    }

    public Point2D getRight() {
        return right;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setParent(Point2D parent) {
        this.parent = parent;
    }

    public void setLeft(Point2D left) {
        this.left = left;
    }

    public void setRight(Point2D right) {
        this.right = right;
    }

    public void setX(double X) {
        this.X = X;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

}