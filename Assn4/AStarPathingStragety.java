import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStragety implements PathingStrategy {


    public List <Point> computePath(Point start, Point end,
                                    Predicate <Point> canPassThrough,
                                    BiPredicate <Point, Point> withinReach,
                                    Function <Point, Stream <Point>> potentialNeighbors) {
        int g = 0;
        int h = h_distance(start,end);

        Map<Point, Node> openList = new HashMap<>();
        Map<Point, Node> closedList = new HashMap <>();
        //List of path
        List<Point> path = new ArrayList <>();
        //List of the points sort by F
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getF));
        //add start node to the open list and mark it as the current node
        Node startNode = new Node(start,g, h,null);
        open.add(startNode);
        Node currentNode = null;

        while (!open.isEmpty()) {
            //pop the smallestF node from the open, assigned as currentNode and remove it from open
            currentNode = open.remove();

            //check if it reaches the goal
            if (withinReach.test(currentNode.getPoint(), end)) {
                return Path(currentNode, path);
            }
            //analyze all valid adjacent points
            List <Point> validAdjacent = potentialNeighbors.apply(currentNode.getPoint())
                    .filter(canPassThrough)
                    .filter(p -> !p.equals(start) && !p.equals(end))
                    .collect(Collectors.toList());

            //evaluating each neighbor
            for(Point point: validAdjacent)
            {
                if(!closedList.containsKey(point)) {
                    int newG = currentNode.getG() + 1;
                   //if adj isnt in open list make a new node
                    if (!openList.containsKey(point)) {
                        h = Math.abs(point.x - end.x) + Math.abs(point.y - end.y);
                        Node newNode = new Node(point, newG, h, currentNode);
                        openList.put(point, newNode);
                        open.add(newNode);

                    }
                    //else that means its in the open list and not evaluated
                    if (newG < openList.get(point).getG()) {

                        g = newG;
                        h = Math.abs(point.x - end.x) + Math.abs(point.y - end.y);
                        Node smallerGNode = new Node(point, g, h,currentNode);
                        openList.replace(point,smallerGNode);
                        open.add(smallerGNode);
                        open.remove(openList.get(point));
                    }
                }

            }
            closedList.put(currentNode.getPoint(),currentNode);
        }

        return Path(currentNode, path);
    }

    //Find path
    public List<Point> Path(Node node, List<Point> points)
    {
        if(node.getPriorSquare() != null) {
            points.add(node.getPoint());
            //reverse the path
            Collections.reverse(points);
            return Path(node.getPriorSquare(),points);
        }
        else
            return points;

    }

    //find h distance
    int h_distance (Point start, Point end)
    {
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }



}