import java.util.ArrayList;
import java.util.Arrays;

public class ConverterFloat extends Converter{

    public Spring createSpringSystem(Integer[] signals) {
        ArrayList<Spring> springs = new ArrayList<>();
        StringBuilder expr = new StringBuilder("[");
        for (int i = 0; i < signals.length; i++) {
            if (signals[i] == 1) {
                Spring currentSpring = createSpring((int) (Math.pow(2, signals.length - 1 - i)));
                springs.add(currentSpring);
                expr.append("[]");
            }
        }
        expr.append("]");
        Spring[] result = new Spring[springs.size()];
        Spring currentStiffSpring = SpringArray.equivalentSpring(expr.toString(), springs.toArray(result));
        return squareStiffness(currentStiffSpring);
    }

    public static Spring squareStiffness(Spring spring) {
        int stiff = (int) spring.getStiffness();
        StringBuilder expr = new StringBuilder("[");
        expr.append("[]".repeat(Math.max(0, stiff)));
        expr.append("]");
        Spring[] stiffnessArray = new Spring[stiff];
        Arrays.fill(stiffnessArray, new Spring(stiff));
        return SpringArray.equivalentSpring(expr.toString(), stiffnessArray);
    }

    public static Spring createSpring(int stiff) {
        StringBuilder expr = new StringBuilder("[");
        expr.append("[]".repeat(Math.max(0, stiff)));
        expr.append("]");
        return SpringArray.equivalentSpring(expr.toString());
    }

    public int evaluateDecimalValue(Integer[] binaryRep) {
        Spring currentSpring = createSpringSystem(binaryRep);
        double[] oscillations = Converter.getOscillations(currentSpring);
        return FT.getFrequency(oscillations);
    }


    public static void main(String[] args) {


        Integer[][] fl = new Integer[3][2];
        fl[0][0] = 1;
        fl[0][1] = 0;
        fl[1][0] = 0;
        fl[1][1] = 0;
        fl[2][0] = 1;
        fl[2][1] = 1;

        // floating number is 5,1
        Integer[] firstColumn = new Integer[fl.length];
        Integer[] secondColumn = new Integer[fl.length];
        for (int i = 0; i < fl.length; i++) {
            firstColumn[i] = fl[i][0];
            secondColumn[i] = fl[i][1];
        }
        ConverterFloat converter = new ConverterFloat();
        int integerPart = converter.evaluateDecimalValue(firstColumn);
        int floatPart = converter.evaluateDecimalValue(secondColumn);
        float result = (float) integerPart + ((float) floatPart / 10);
        System.out.println(result);


    }


}

