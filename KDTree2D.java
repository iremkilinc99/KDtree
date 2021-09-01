public class KDTree2D<E> extends AbstractTree<E> {

    private Point2D<Double> root;
    private Point2D<Double> lastPointAdded;
    Point2D<Double> currentPoint;
    private int size;

    public KDTree2D() {
        root = null;
        size = 0;
    }

    protected Point2D<Double> createPoint2D(double X, double Y, Point2D<Double> parent, Point2D<Double> leftchild, Point2D<Double> rightchild) {
        lastPointAdded = new Point2D<Double>(X, Y, parent, leftchild, rightchild);
        return lastPointAdded;
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isEmpty() {
        return size == 0;
    }

    @Override
    protected Point2D<Double> root() {
        return root;
    }

    protected void add(Point2D<Double> point1, Point2D<Double> point2, boolean onlyPoint) throws IllegalArgumentException {
        if(onlyPoint && isExternal(point1)) {
            if(depth(point1) %2 == 0) {
                Point2D<Double> l = createPoint2D((point1.getX() + point2.getX()) /2, (point1.getY() + point2.getY()) /2, null, null, null);
                l.setParent(point1.getParent());

                if(point1.getParent().getLeft() == point1) point1.getParent().setLeft(l);
                else point1.getParent().setRight(l);

                if (point1.getX() < point2.getX()) {
                    l.setLeft(point1);
                    l.setRight(point2);
                } else {
                    l.setLeft(point2);
                    l.setRight(point1);
                }

                point1.setParent(l);
                point2.setParent(l);

            }else{

                Point2D<Double> l = createPoint2D((point1.getX() + point2.getX()) /2, (point1.getY() + point2.getY()) /2, null, null, null);
                l.setParent(point1.getParent());

                if(point1.getParent().getLeft() == point1) point1.getParent().setLeft(l);
                else point1.getParent().setRight(l);

                if (point1.getY() < point2.getY()) {
                    l.setLeft(point1);
                    l.setRight(point2);
                } else {
                    l.setLeft(point2);
                    l.setRight(point1);
                }

                point1.setParent(l);
                point2.setParent(l);

            }

            System.out.println("Inserted : (" + point2.getX() + " , " + point2.getY() + ")");
            return;
        }

        if(depth(point1) %2 == 0) {
            if (point1.getX() < point2.getX()) {
                point1.setRight(point2);
                point2.setParent(point1);
            } else {
                point1.setLeft(point2);
                point2.setParent(point1);
            }
        }else{
            if (point1.getY() < point2.getY()) {
                point1.setRight(point2);
                point2.setParent(point1);
            } else {
                point1.setLeft(point2);
                point2.setParent(point1);
            }
        }

        System.out.println("Inserted : (" + point2.getX() + " , " + point2.getY() + ")");
        size++;
    }

    protected boolean insert(double x, double y, boolean onlyPoint) {
        return insertAux(x, y, root, root, onlyPoint);
    }

    protected boolean insertAux(double x, double y , Point2D<Double> p, Point2D<Double> lastSearchedPoint, boolean onlyPoint) {
        if (isEmpty()) {
            Point2D<Double> newest = createPoint2D(x, y, null, null, null);
            root = newest;
            System.out.println("Inserted : (" + newest.getX() + " , " + newest.getY() + ")");
            size++;
            return true;

        } else {

            if (p == null) {
                Point2D<Double> newest = createPoint2D(x, y, null, null, null);
                add(lastSearchedPoint, newest, onlyPoint);
                return true;
            } else if (depth(p) % 2 == 0) {

                if (x <= p.getX() ) {
                    lastSearchedPoint = p;
                    return insertAux(x, y, left(p), lastSearchedPoint, onlyPoint);
                }else {
                    lastSearchedPoint = p;
                    return insertAux(x, y, right(p), lastSearchedPoint, onlyPoint);
                }

            } else {
                if (y <= p.getY()) {
                    lastSearchedPoint = p;
                    return insertAux(x, y, left(p), lastSearchedPoint, onlyPoint);
                } else {
                    lastSearchedPoint = p;
                    return insertAux(x, y, right(p), lastSearchedPoint, onlyPoint);
                }
            }
        }
    }

    protected Point2D<Double> search(Point2D<Double> point) {
        return searchAux(point, root());
    }

    protected Point2D<Double> searchAux(Point2D<Double> point, Point2D<Double> currentPoint ) {
        if(currentPoint == null) return null;

        if(isExternal(currentPoint)) {
            if(point.getX() == currentPoint.getX() && point.getY() == currentPoint.getY()) return currentPoint;
        }

        for (Point2D<Double> c : children(currentPoint))
            point = searchAux(point, c);

        return point;
    }

    protected void displayTree() { preorder(false); }

    protected void displayPoints() {
        for (Point2D<Double> p : preorder(true)) {
            System.out.println("(" + p.getX() + " " + p.getY() + ")");
        }
    }

    protected boolean remove(Point2D<Double> point2D) throws IllegalArgumentException {
        Point2D pointToBeRemoved  = search(point2D);

        if(pointToBeRemoved.getParent().getLeft() == pointToBeRemoved) {
            if(pointToBeRemoved.getParent().getParent().getLeft() == pointToBeRemoved.getParent()) {
                if(hasRight(pointToBeRemoved.getParent())) {
                    pointToBeRemoved.getParent().getRight().setParent(pointToBeRemoved.getParent().getParent());
                    pointToBeRemoved.getParent().getParent().setLeft(pointToBeRemoved.getParent().getRight());
                    return true;
                }else{
                    pointToBeRemoved.getParent().getParent().setLeft(null);
                    pointToBeRemoved.getParent().setParent(null);
                    return true;
                }
            }else{
                if(hasRight(pointToBeRemoved.getParent())) {
                    pointToBeRemoved.getParent().getRight().setParent(pointToBeRemoved.getParent().getParent());
                    pointToBeRemoved.getParent().getParent().setRight(pointToBeRemoved.getParent().getRight());
                    return true;
                }else{
                    pointToBeRemoved.getParent().getParent().setRight(null);
                    pointToBeRemoved.getParent().setParent(null);
                    return true;
                }
            }
        }else{
            if(pointToBeRemoved.getParent().getParent().getLeft() == pointToBeRemoved.getParent()) {
                if(hasLeft(pointToBeRemoved.getParent())) {
                    pointToBeRemoved.getParent().getLeft().setParent(pointToBeRemoved.getParent().getParent());
                    pointToBeRemoved.getParent().getParent().setLeft(pointToBeRemoved.getParent().getLeft());
                    return true;
                }else{
                    pointToBeRemoved.getParent().getParent().setLeft(null);
                    pointToBeRemoved.getParent().setParent(null);
                    return true;
                }
            }else{
                if(hasLeft(pointToBeRemoved.getParent())) {
                    pointToBeRemoved.getParent().getLeft().setParent(pointToBeRemoved.getParent().getParent());
                    pointToBeRemoved.getParent().getParent().setRight(pointToBeRemoved.getParent().getLeft());
                    return true;
                }else{
                    pointToBeRemoved.getParent().getParent().setRight(null);
                    pointToBeRemoved.getParent().setParent(null);
                    return true;
                }
            }
        }
    }

    public String toString() {
        for (Point2D<Double> p : preorder(true)) {
            for (int i = 0; i < depth(p); i++)
                System.out.print(".");
            System.out.println(p.getX() + " " + p.getY());
        }
        return " ";
    }

    protected Point2D<Double> findMin(int d) {
        Point2D<Double> Point = findMinAux(root(), d);
        System.out.println("minimum-" + (d%2 == 0 ? "x" : "y")  + " is (" + currentPoint.getX() + "," + currentPoint.getY() + ")");
        return Point;
    }

    protected Point2D<Double> findMinAux(Point2D<Double> p, int d) {
        if (p == null) return null;
        if (d == 0 && isExternal(p)) {
            if(currentPoint == null) currentPoint = p;

            else if (p.getX() < currentPoint.getX()) {
                currentPoint = p;
                return currentPoint;
            }

        } else if (d == 1 && isExternal(p)) {
            if(currentPoint == null) currentPoint = p;

            else if (p.getY() < currentPoint.getY()) {
                currentPoint = p;
                return currentPoint;
            }
        }

        if (p.getLeft() != null)
            findMinAux(p.getLeft(), d);
        if (p.getRight() != null)
            findMinAux(p.getRight(), d);

        return currentPoint;
    }

    protected Point2D<Double> findMax(int d) {
        Point2D<Double> Point = findMaxAux(root(), d);
        System.out.println("maximum-" + (d%2 == 0 ? "x" : "y")  + " is ("  + currentPoint.getX() + "," + currentPoint.getY() + ")");
        return Point;
    }

    protected Point2D<Double> findMaxAux(Point2D<Double> p, int d) {
        if (p == null) return null;

        if (d == 0 && isExternal(p)) {
            if(currentPoint == null) currentPoint = p;

            if (p.getX() >= currentPoint.getX()) {
                currentPoint = p;
                return currentPoint;
            }
        } else if (d == 1 && isExternal(p)) {
            if(currentPoint == null) currentPoint = p;

            if (p.getY() >= currentPoint.getY()) {
                currentPoint = p;
                return currentPoint;
            }
        }

        if (p.getLeft() != null)
            findMaxAux(p.getLeft(), d);
        if (p.getRight() != null)
            findMaxAux(p.getRight(), d);

        return currentPoint;
    }

    protected void printRange(Point2D<Double> ll, Point2D<Double> ur) {
        printRangeAux(ll,ur,root());
    }

    protected void printRangeAux(Point2D<Double> ll, Point2D<Double> ur, Point2D<Double> currPos) {
        if (isInternal(currPos)) {
            if (depth(currPos) % 2 == 0) {
                if (ll.getX() <= currPos.getX()) {
                    if (currPos.getX() <= ur.getX()) {
                        printRangeAux(ll, ur, currPos.getLeft());
                        printRangeAux(ll, ur, currPos.getRight());
                    } else {
                        printRangeAux(ll, ur, currPos.getLeft());
                    }
                } else {
                    printRangeAux(ll, ur, currPos.getRight());
                }
            } else {
                if (ll.getY() <= currPos.getY()) {
                    if (currPos.getY() <= ur.getY()) {
                        printRangeAux(ll, ur, currPos.getLeft());
                        printRangeAux(ll, ur, currPos.getRight());

                    }else{
                        printRangeAux(ll, ur, currPos.getLeft());
                    }
                } else {
                    printRangeAux(ll, ur, currPos.getRight());
                }

            }
        }

        if(isExternal(currPos)) {
            if (ll.getX() <= currPos.getX() && currPos.getX() <= ur.getX() && ll.getY() <= currPos.getY() && currPos.getY() <= ur.getY()) {
                System.out.println("in range P: (" + currPos.getX() + "," + currPos.getY() + ")");
            }
        }
    }

    protected void quit() {
        System.out.println("https://open.spotify.com/track/0rUNZQuYQvOz6A6zwyT6tM?si=b9bf0874aa7d485a");
        System.exit(0);
    }
}