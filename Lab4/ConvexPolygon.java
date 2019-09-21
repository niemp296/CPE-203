import java.awt.Color;
import java.awt.Point;
import java.lang.Math;
import java.util.List;

public class ConvexPolygon implements Shape
{
    private Point[] vertices;
    private Color color;

    public ConvexPolygon(Point[] arr, Color c)
    {
         this.color = c;
         for(int i = 0; i < arr.length; i++)
         {
             vertices[i] = arr[i];
         }

    }
    public Point getVertex(int i)
    {
         return vertices[i];
    }
    public Color getColor()
    {
         return color;   
    }
    public void setColor(Color c)
    {
        color = c;
    }
    public double getArea()
    {
        double area = 0;
        for(int i = 1; i < vertices.length; i++)
        {
             if(i == vertices.length-1)
                 area = area + (vertices[i].x*vertices[0].y) - (vertices[0].x*vertices[i].y);
             else
                 area = area + (vertices[i-1].x*vertices[i].y)-(vertices[i].x*vertices[i-1].y);
        }      
        area = Math.abs(area*0.5);
        return area;    
    }
    public double getPerimeter()
    {
        double peri = 0;
        for(int i=0; i < vertices.length-1; i++)
        {
             peri = peri + vertices[i].distance(vertices[i+1]);  
        }        
        peri = peri + vertices[0].distance(vertices[vertices.length-1]);
        return peri;
    }
    public void translate(Point p)
    {
        for(int i = 0; i < vertices.length; i++)
        {
             vertices[i].translate(p.x, p.y);
        }
    }


}
