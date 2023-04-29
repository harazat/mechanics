import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Converter2 {



        //Assuming we have only springs with stiffness 1
        public static Spring createSpringSystem(Integer[] signals){
            validateInput(signals);
            ArrayList<Spring> springs=new ArrayList<>();
            StringBuilder expr=new StringBuilder("[");
            for (int i=0; i<signals.length; i++) {
                if (signals[i] == 1){
                    Spring currentSpring=createSpring((int)(Math.pow(2,7-i)));
                    springs.add(currentSpring);
                    expr.append("[]");
                }
            }
            expr.append("]");
            Spring[] result=new Spring[springs.size()];
            Spring currentStiffSpring=SpringArray.equivalentSpring(expr.toString(),springs.toArray(result));
            return squareStiffness(currentStiffSpring);
        }
        public static Spring createSpring(int stiff){
            StringBuilder expr=new StringBuilder("[");
            expr.append("[]".repeat(Math.max(0, stiff)));
            expr.append("]");
            return SpringArray.equivalentSpring(expr.toString());
        }
        public static Spring squareStiffness(Spring spring){
            int stiff=(int)spring.getStiffness();
            StringBuilder expr=new StringBuilder("[");
            expr.append("[]".repeat(Math.max(0, stiff)));
            expr.append("]");
            Spring[] stiffnessArray=new Spring[stiff];
            Arrays.fill(stiffnessArray, new Spring(stiff));
            return SpringArray.equivalentSpring(expr.toString(), stiffnessArray);
        }
        private static void validateInput(Integer[] signals){
            if(signals.length!=8)
                throw new IllegalArgumentException("The length should be 8");
            for (int signal : signals) {
                if (signal != 0 && signal != 1)
                    throw new IllegalArgumentException("Signals should be either 0 or 1");
            }
        }
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

        public static int getFrequency(double[] oscillations){
            HashMap<Integer, Integer> map=new HashMap<>();
            double[] frequencies = Converter2.getAmplitudes(oscillations);
            for(double d : frequencies){
                int num=(int)Math.ceil(Math.abs(d));
                if(map.containsKey(num)){
                    map.put(num, map.get(num)+1);
                }
                else
                    map.put(num, 1);
            }
            return findMostFrequent(map);
        }

        public static int findMostFrequent(HashMap<Integer, Integer> map){
            int maxKey=0;
            int maxValue=0;
            for(int key : map.keySet()){
                if(map.get(key)>maxValue) {
                    maxKey=key;
                    maxValue = map.get(key);
                }
            }
            return maxKey;
        }

        public static int findDecimalValue(Integer[] binaryRep){
            Spring currentSpring= Converter2.createSpringSystem(binaryRep);
            double[] oscillations = Converter2.getOscillations(currentSpring);
            return Converter2.getFrequency(oscillations);
        }


    private static String getBinaryString(Integer[] binaryRep) {
        StringBuilder s=new StringBuilder();
        for(int i: binaryRep)
            s.append(i);
        return s.toString();
    }

    /**
     * Took the code from https://www.geeksforgeeks.org/generate-all-the-binary-strings-of-n-bits/
     * and modified for my case
     */

    // Function to generate all binary strings
    public static void generateAllBinaryStrings(int n, Integer arr[], int i, List<Integer[]> finalResult)
    {
        if (i == n)
        {
            finalResult.add(Arrays.copyOf(arr, arr.length));
            return;
        }
        // First assign "0" at ith position
        // and try for all other permutations
        // for remaining positions
        arr[i] = 0;
        generateAllBinaryStrings(n, arr, i + 1,finalResult);
        // And then assign "1" at ith position
        // and try for all other permutations
        // for remaining positions
        arr[i] = 1;
        generateAllBinaryStrings(n, arr, i + 1,finalResult);
    }

    public static int getDecimalFromBinary(Integer[] bits){
        int result=0;
        for(int i=bits.length-1; i>=0; i--){
            if(bits[i]==1)
                result+=(int)Math.pow(2,bits.length-1-i);
        }
        return result;
    }

    public static void main(String[] args) {
//        Integer[] bits1 = new Integer[]{1, 0, 1, 0, 0, 1, 0, 1};
//        Converter8Bit converter = new Converter8Bit();
//        System.out.println(converter.createSpringSystem(bits1));


        // List<Integer[]> binaryInputs=new ArrayList<>();
        //  generateAllBinaryStrings(8, new Integer[8], 0, binaryInputs);
        //  System.out.println(Arrays.toString(binaryInputs.toArray()));
//        System.out.println(binaryInputs.get(0));
//        Integer[] binaryRep=binaryInputs.get(1);

        //int result= converter.evaluateDecimalValue(bits1);


//        System.out.println("Binary input: " + Arrays.toString(bits1));
//        double decimalValue1 = converter.evaluateDecimalValue(bits1);
//        System.out.println("Decimal output: " + decimalValue1);

//
        List<Integer[]> binaryInputs=new ArrayList<>();
        generateAllBinaryStrings(8, new Integer[8], 0, binaryInputs);
        System.out.printf("%-20s %-20s %-20s ", "Binary Input", "Decimal Value", "Program Output");

//
        for(int i=1; i<256; i++){
            Integer[] binaryRep=binaryInputs.get(i);
            int result= Converter2.findDecimalValue(binaryRep);

            int trueValue= getDecimalFromBinary(binaryRep);
            String binaryString=getBinaryString(binaryRep);

            System.out.printf(binaryString, trueValue,result);
            System.out.println();
        }

//
//
    }
    }


