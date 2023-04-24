import java.util.Stack;
//-----------------------------------_Task 2 START---------------------------
public class SpringArray {

    public static Spring equivalentSpring(String springExpr) {
        Stack<Spring> seriesStack = new Stack<>();
        Stack<Spring> parallelStack = new Stack<>();

        for (char c : springExpr.toCharArray()) {
            if (c == '{') {
                seriesStack.push(new Spring());
            } else if (c == '[') {
                parallelStack.push(new Spring());
            } else if (c == '}') {
                Spring seriesSpring = new Spring();
                while (!seriesStack.isEmpty()) {
                    Spring prevSpring = seriesStack.pop();
                    seriesSpring = seriesSpring.inSeries(prevSpring);
                }
                parallelStack.push(seriesSpring);
            } else if (c == ']') {
                Spring parallelSpring = new Spring();
                while (!parallelStack.isEmpty()) {
                    Spring prevSpring = parallelStack.pop();
                    parallelSpring = parallelSpring.inParallel(prevSpring);
                }
                seriesStack.push(parallelSpring);
            }
        }

        Spring result = new Spring();
        while (!seriesStack.isEmpty()) {
            result = result.inSeries(seriesStack.pop());
        }
        while (!parallelStack.isEmpty()) {
            result = result.inParallel(parallelStack.pop());
        }
        return result;
    }

    public static Spring equivalentSpring(String springExpr, Spring[] springs) {
        Stack<Spring> seriesStack = new Stack<>();
        Stack<Spring> parallelStack = new Stack<>();

        for (char c : springExpr.toCharArray()) {
            if (c == '{') {
                seriesStack.push(springs[0]);
            } else if (c == '[') {
                parallelStack.push(springs[0]);
            } else if (c == '}') {
                Spring seriesSpring = springs[0];
                while (!seriesStack.isEmpty()) {
                    Spring prevSpring = seriesStack.pop();
                    seriesSpring = seriesSpring.inSeries(prevSpring);
                }
                parallelStack.push(seriesSpring);
                springs = shiftArray(springs, 1);
            } else if (c == ']') {
                Spring parallelSpring = springs[0];
                while (!parallelStack.isEmpty()) {
                    Spring prevSpring = parallelStack.pop();
                    parallelSpring = parallelSpring.inParallel(prevSpring);
                }
                seriesStack.push(parallelSpring);
                springs = shiftArray(springs, 1);
            }
        }

        Spring result = new Spring();
        while (!seriesStack.isEmpty()) {
            result = result.inSeries(seriesStack.pop());
        }
        while (!parallelStack.isEmpty()) {
            result = result.inParallel(parallelStack.pop());
        }
        return result;
    }

    private static Spring[] shiftArray(Spring[] arr, int shift) {
        Spring[] shiftedArr = new Spring[arr.length - shift];
        for (int i = 0; i < shiftedArr.length; i++) {
            shiftedArr[i] = arr[i + shift];
        }
        return shiftedArr;
    }
}


//-----------------------------------Task 2 END--------------------------------------
