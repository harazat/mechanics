
public class SpringArray {

    private static int springIndex=0;
    public static Spring equivalentSpring(String springExpr){
        int count=0;
        for(int i=0; i<springExpr.length()-2; i++){
            if(springExpr.substring(i,i+2).equals("[]"))
                count++;
        }
        Spring[] springs=new Spring[count];
        for(int i=0; i<count; i++){
            springs[i]=new Spring();
        }
        springIndex=0;
        return equivalentSpring(new StringBuilder(springExpr), springs, 0);
    }
    public static Spring equivalentSpring(String springExpr, Spring[] springs){
        springIndex=0;
        return equivalentSpring(new StringBuilder(springExpr), springs, 0);
    }

    public static Spring equivalentSpring(StringBuilder springExpr, Spring[] springs, int exprStartIndex) {
        if(springExpr.substring(exprStartIndex, exprStartIndex+2).equals("[]")){
            return springs[springIndex++];
        }
        int closingIndex=getClosingIndex(springExpr, exprStartIndex+1);
        char scope=springExpr.charAt(exprStartIndex);

        if(isClosing(springExpr.charAt(closingIndex+1)))
            return springs[springIndex++];
        StringBuilder modifiedString=createExpr(springExpr, closingIndex+1, scope);
        if(scope=='{'){
            return equivalentSpring(springExpr, springs, exprStartIndex+1)
                    .inSeries(equivalentSpring(modifiedString, springs, getOpeningIndex(springExpr,closingIndex+1)));
        }
        return equivalentSpring(springExpr, springs, exprStartIndex+1)
                .inParallel(equivalentSpring(modifiedString, springs, getOpeningIndex(springExpr,closingIndex+1)));

    }

    //helper methods
    public static StringBuilder createExpr(StringBuilder expr, int place, char c){
        return expr.insert(place, c);
    }
    public static boolean isClosing(char c){
        return c=='}'||c==']';
    }
    public static int getOpeningIndex(StringBuilder expr, int pos){
        int firstBrace=expr.indexOf("[", pos);
        int firstBracket=expr.indexOf("{", pos);
        if(firstBrace==-1)
            return firstBracket;
        if(firstBracket==-1)
            return firstBrace;
        return Math.min(firstBrace,firstBracket );
    }
    public static int getClosingIndex(StringBuilder expr, int pos){
        String c=expr.substring(pos, pos+1);
        String oppositeChar=getOppositeChar(c);
        int closingIndex=expr.indexOf(oppositeChar,pos+1);
        int firstOpenIndex=expr.indexOf(c,pos+1);
        while(!(firstOpenIndex==-1 || closingIndex<firstOpenIndex)){
            firstOpenIndex=expr.indexOf(c,firstOpenIndex+1);
            closingIndex=expr.indexOf(oppositeChar,closingIndex+1);
        }
        return closingIndex;
    }

    private static String getOppositeChar(String c){
        if(c.equals("{"))
            return "}";
        return "]";
    }
}