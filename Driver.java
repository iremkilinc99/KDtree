import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Driver {
    static ArrayList<Double> pointX = new ArrayList<>();
    static ArrayList<Double> pointY = new ArrayList<>();
    static KDTree2D<String> kdTree2D;

    public static void main(String args[]) throws IOException {
        readDirectiveFile();
    }

    static void readFile(String file) throws IOException {
        BufferedReader pointFile = new BufferedReader(new FileReader(file));
        String point = "";
        String xy[];
        while (pointFile.ready()){
            point = pointFile.readLine();
            xy = point.split("\t");
            pointX.add(Double.parseDouble(xy[0]));
            pointY.add(Double.parseDouble(xy[1]));
        }

        pointX.stream().sorted();
    }

    static void readDirectiveFile() throws IOException {
        BufferedReader directiveFile = new BufferedReader(new InputStreamReader(System.in));
        String directive = "";
        String directives[];
        while (directiveFile.ready()) {
            directive = directiveFile.readLine();
            directives = directive.split("\t");

            if(directives[0].equals("build-kdtree")) {
                readFile(directives[1]);
                kdTree2D = buildKDtree();
                System.out.println();
            }

            if(directives[0].equals("display-tree")) {
                kdTree2D.displayTree();
                System.out.println();
            }

            if(directives[0].equals("display-points")) {
                kdTree2D.displayPoints();
                System.out.println();
            }

            if(directives[0].equals("find-min-x")) {
                kdTree2D.findMin(0);
                System.out.println();
            }

            if(directives[0].equals("find-min-y")) {
                kdTree2D.findMin(1);
                System.out.println();
            }

            if(directives[0].equals("find-max-x")) {
                kdTree2D.findMax(0);
                System.out.println();
            }

            if(directives[0].equals("find-max-y")) {
                kdTree2D.findMax(1);
                System.out.println();
            }

            if(directives[0].equals("print-range")){
                Point2D<Double> lowerLeft = new Point2D<>(Double.parseDouble(directives[1]), Double.parseDouble(directives[2]), null,  null, null);
                Point2D<Double> upperRight = new Point2D<>(Double.parseDouble(directives[3]), Double.parseDouble(directives[4]), null,  null, null);
                kdTree2D.printRange(lowerLeft, upperRight);
                System.out.println();
            }

            if(directives[0].equals("quit"))
                kdTree2D.quit();

            if(directives[0].equals("search")){
                Point2D<Double> point2D = new Point2D<>(Double.parseDouble(directives[1]), Double.parseDouble(directives[2]), null,null,null);
                Point2D<Double> returned = kdTree2D.search(point2D);
                System.out.println((returned.getParent() !=null ? "Found (" : "Not found (" )+ point2D.getX() +"," + point2D.getY() + ")");
                System.out.println();
            }

            if(directives[0].equals("remove")){
                Point2D<Double> point2D = new Point2D<>(Double.parseDouble(directives[1]), Double.parseDouble(directives[2]), null,null,null);
                kdTree2D.remove(point2D);
                System.out.println();
            }

            if(directives[0].equals("insert")){
                insertPoint(kdTree2D, Double.parseDouble(directives[1]), Double.parseDouble(directives[2]));
                System.out.println();
            }
        }
    }

    static KDTree2D buildKDtree(){
        KDTree2D kdTree2D = new KDTree2D();
        insertPoints(kdTree2D, 0, 0, pointX.size()-1);
        return kdTree2D;
    }

    static void insertPoints(KDTree2D kdTree2D,int depth, int start, int end) {
        if(start >= end) {
            kdTree2D.insert(pointX.get(start), pointY.get(start), false);
            return;
        }
        int medianX, medianY;
        if (depth % 2 == 0) {
            medianX = (int) (pointX.get(start) + pointX.get(end)) / 2;
            medianY = (int) (pointY.get(start) + pointY.get(end)) / 2;
            kdTree2D.insert(medianX, medianY, false);
            insertPoints(kdTree2D, ++depth, start, (start+end)/2);
            insertPoints(kdTree2D, depth, (start+end)/2+1, end);
        }else{
            medianX = (int) (pointX.get(start) + pointX.get(end)) / 2;
            medianY = (int) (pointY.get(start) + pointY.get(end)) / 2;
            kdTree2D.insert(medianX, medianY, false);
            insertPoints(kdTree2D, ++depth,start, (start+end)/2);
            insertPoints(kdTree2D, depth, (start+end)/2+1, end);
        }
    }

    static void insertPoint(KDTree2D kdTree2D, double x, double y) {
        kdTree2D.insert(x, y, true);
    }
}