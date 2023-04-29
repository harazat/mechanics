import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converter8Bit extends Converter {

    @Override
    public  Spring createSpringSystem(Integer[] signals) {
        ArrayList<Spring> springs = new ArrayList<>();
        StringBuilder expr = new StringBuilder("[");
        for (int i = 0; i < signals.length; i++) {
            if (signals[i] == 1) {
                Spring currentSpring = createSpring((int) (Math.pow(2, 7 - i)));
                springs.add(currentSpring);
                expr.append("[]");
            }
        }
        expr.append("]");
        Spring[] result = new Spring[springs.size()];
        Spring currentStiffSpring = SpringArray.equivalentSpring(expr.toString(), springs.toArray(result));
        return squareStiffness(currentStiffSpring);
    }
    public static Spring createSpring(int stiff) {
        StringBuilder expr = new StringBuilder("[");
        expr.append("[]".repeat(Math.max(0, stiff)));
        expr.append("]");
        return SpringArray.equivalentSpring(expr.toString());
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

    @Override
    public  int evaluateDecimalValue(Integer[] binaryRep) {
        Spring currentSpring = createSpringSystem(binaryRep);
        double[] oscillations = Converter.getOscillations(currentSpring);
        return FT.getFrequency(oscillations);
    }



    //For testing
    public static int getDecimalFromBinary(Integer[] bits) {
        int result = 0;
        for (int i = bits.length - 1; i >= 0; i--) {
            if (bits[i] == 1) {
                result += (int) Math.pow(2, bits.length - 1 - i);
            }
        }
        return result;
    }


    private static String getBinaryString(Integer[] binaryRep) {
        StringBuilder s = new StringBuilder();
        for (int i : binaryRep) {
            s.append(i);
        }
        return s.toString();
    }

    /**
     * The code is taken from https://www.geeksforgeeks.org/generate-all-the-binary-strings-of-n-bits/
     */

    // Function to generate all binary strings
    public static void generateAllBinaryStrings(int n, Integer arr[], int i, List<Integer[]> finalResult) {
        if (i == n) {
            finalResult.add(Arrays.copyOf(arr, arr.length));
            return;
        }

        arr[i] = 0;
        generateAllBinaryStrings(n, arr, i + 1, finalResult);
        arr[i] = 1;
        generateAllBinaryStrings(n, arr, i + 1, finalResult);
    }


    public static void main(String[] args) {
        Converter8Bit converter = new Converter8Bit();
        List<Integer[]> binaryInputs=new ArrayList<>();
        generateAllBinaryStrings(8, new Integer[8], 0, binaryInputs);
        System.out.printf("%-20s %-20s %-20s ", "Binary Input", "Decimal Value", "Program Output");
        System.out.println();
        for(int i=1; i<256; i++){
            Integer[] binaryRep=binaryInputs.get(i);
            int result= converter.evaluateDecimalValue(binaryRep);

            int trueValue= getDecimalFromBinary(binaryRep);
            String binaryString=getBinaryString(binaryRep);
            System.out.printf("%-20s %-20d %-20d ", binaryString, trueValue,result);
            System.out.println();
        }

    }




}




