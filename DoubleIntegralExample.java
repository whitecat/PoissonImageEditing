/*      Example of the use of the class Integration to demonstrate the nesting
        of the IntegralFunction in the evaluation of a double integral.

        Integral of x*x*{integral of y*y} for x between 0 and 1 and for y between x and 5

        Gaussian Quadrature used as the numerical procedure

        Michael Thomas Flanagan

        30 April 2009
*/

import flanagan.integration.*;

//  Outer integral function
class DoubleIntegralFunction1 implements IntegralFunction{

    private double lowerLimit = 0.0;    // inner integration lower limit
    private double upperLimit = 0.0;    // inner integration upper limit
    private int nPoints = 0;            // inner integration - number of points

    public double function(double x){

        // Create an instance of the inner integral function
        DoubleIntegralFunction2 funct2 = new DoubleIntegralFunction2();

        // Set lower limit
        this.setLowerLimit(x);

        // Return outer integral value at x
        return x*x*Integration.gaussQuad(funct2, lowerLimit, upperLimit, nPoints);
    }

    // Set lower limit to inner integration
    public void setLowerLimit(double lowerLimit){
        this.lowerLimit = lowerLimit;
    }

    // Set upper limit to inner integration
    public void setUpperLimit(double upperLimit){
        this.upperLimit = upperLimit;
    }

    // Set number of points in the inner integration
     public void setNumberOfPoints(int nPoints){
        this.nPoints = nPoints;
    }
}

//  Inner integral function
class DoubleIntegralFunction2 implements IntegralFunction{

    public double function(double x){

        // Return inner integral value at x
        return x*x;
    }
}

// Double integral example class
public class DoubleIntegralExample{

    public static void main(String[] args){

        double lowerLimit1 = 0;     // outer integration lower limit
        double upperLimit1 = 1;     // outer integration upper limit
        int nPoints1 = 128;         // outer integration - number of points

        double lowerLimit2 = 2;     // inner integration lower limit
        double upperLimit2 = 5;     // inner integration upper limit
        int nPoints2 = 128;         // inner integration - number of points

        // Create an instance of the outer integral function
        DoubleIntegralFunction1 funct1 = new DoubleIntegralFunction1();

        // Set inner integration parameters within the outer integration function
        funct1.setUpperLimit(upperLimit2);
        funct1.setNumberOfPoints(nPoints2);

        // Integration
        double sum = Integration.gaussQuad(funct1, lowerLimit1, upperLimit1, nPoints1);
        System.out.println("Integral sum = " + sum);
    }
}
