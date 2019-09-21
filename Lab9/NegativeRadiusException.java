import java.util.Scanner;

public class NegativeRadiusException extends CircleException
{
    private double radius;
    
    public NegativeRadiusException(double radius)
    {
        super("negative radius");
	this.radius = radius;
    }
    public double radius()
    {  /*
	Scanner reader = new Scanner(System.in);
	System.out.println("Enter a radius: ");
	this.radius  = reader.nextDouble();
       */
	return radius;
    }

}
