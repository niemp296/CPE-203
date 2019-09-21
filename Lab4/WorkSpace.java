import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.lang.Math;
import java.util.ArrayList;
import java.util.*;

public class WorkSpace
{
    private List<Shape> vertices;
    
    public WorkSpace()
    {
        vertices = new ArrayList<Shape>();
    }
    public void add(Shape shape)
    {
        vertices.add(shape);
    }
    public Shape get(int index)
    {
        return vertices.get(index);
    }
    public int size()
    {
        return vertices.size();
    }
    public List<Circle> getCircles()
    {
        List<Circle> circle = new ArrayList<Circle>();
        for(int i=0; i < vertices.size(); i++)
        {
            if(vertices.get(i) instanceof Circle)
            {
               circle.add((Circle) vertices.get(i));
            }
        }
        return circle;
    }
    public List<Rectangle> getRectangles()
    {
        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        for(int i=0; i < vertices.size(); i++)
        {
            if(vertices.get(i) instanceof Rectangle)
            {
               rectangles.add((Rectangle) vertices.get(i));
            }
        }
        return rectangles;
    }
    public List<Triangle> getTriangles()
    {
        List<Triangle> triangles = new ArrayList<Triangle>();
        for(int i=0; i < vertices.size(); i++)
        {
            if(vertices.get(i) instanceof Triangle)
            {
               triangles.add((Triangle) vertices.get(i));
            }
        }
        return triangles;
    }
    public List<ConvexPolygon> getConvexPolygons()
    {
        List<ConvexPolygon> poly = new ArrayList<ConvexPolygon>();
        for(int i=0; i < vertices.size(); i++)
        {
            if(vertices.get(i) instanceof ConvexPolygon)
            {
               poly.add((ConvexPolygon) vertices.get(i));
            }
        }
        return poly;
    }
    public List<Shape> getShapesByColor(Color color)
    {
        List<Shape> shape = new ArrayList<Shape>();
        for(int i=0; i < vertices.size(); i++)
        {
            if(vertices.get(i).getColor() == color)
            {
               shape.add(vertices.get(i));
            }
        }
        return shape;
    }
    public double getAreaOfAllShapes()
    {
       double sum = 0;
       for(int i=0; i < vertices.size(); i++)
       {
            sum = sum + vertices.get(i).getArea();
       }
       return sum;
    }
    public double getPerimeterOfAllShapes()
    {
       double sum = 0;
       for(int i=0; i < vertices.size(); i++)
       {
            sum = sum + vertices.get(i).getPerimeter();
       }
       return sum;
    }

}
