

import java.util.Arrays;

public abstract class Converter {

    public  abstract Spring createSpringSystem(Integer[] signals);


    public static double[] getOscillations(Spring spring){
        //Values for t and v0 are obtained after brute testing with other numbers
        return spring.move(256,1,0, spring.getStiffness());

    }

    public static double[] getAmplitudes(double[] oscillations){
        double[] imag=new double[oscillations.length];
        Arrays.fill(imag, 0);
        FT.transform(oscillations,imag);
        return oscillations;
    }


     public abstract int evaluateDecimalValue(Integer[] amplitudes);



}