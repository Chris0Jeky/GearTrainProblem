public class Solution {
    public static int[] solution(int[] pegs) {
        int maxN = pegs.length - 1;
        int[] result;
        if (pegs == null || maxN + 1 < 2) {
            result = new int[]{-1, -1};
            return result;
        }
        int equationResult = equationBuilder(pegs);
        boolean isGood = pegListValidation(pegs, equationResult);
        if (!isGood){
            return result = new int[]{-1, -1};
        } else{
            result = new int[]{equationResult, 1};
            return result;
        }
    }

    public static int equationBuilder(int[] pegs){
        int maxN = pegs.length - 1;
        int equRes = 0;
        equRes = getEquRes(pegs, maxN, equRes);
        if (pegs.length % 2 == 0){
            equRes = (int) (equRes / 1.5);
        }
        else {
            equRes = equRes * 2 * -1;
        }
        if (equRes % 2 != 0){
            return -1;
        }
        else {
            return equRes;
        }
    }


    // given the following equation if the number of pegs is odd:
    // peg1 - 2*peg2 + 2*peg3 - 2*peg4 (...) + pegN
    // the function beneath alternates a positive and a negative value of double the indexed peg
    // except for the first and last peg
    private static int getEquRes(int[] pegs, int maxN, int equRes) {
        for (int i = 0; i < maxN + 1; i++) {
            if (i != 0 && i != maxN) {
                if (pegs.length % 2 == 0){
                    if (i % 2 == 0) {
                        equRes = equRes - (pegs[i] * 2);
                    } else {
                        equRes = equRes + (pegs[i] * 2);
                    }
                }
                else {
                    if (i % 2 == 0) {
                        equRes = equRes + (pegs[i] * 2);
                    } else {
                        equRes = equRes - (pegs[i] * 2);
                    }
                }
            }
            if (pegs.length % 2 == 0) {
                if (i == 0) {
                    equRes = equRes - pegs[i];
                }
                if (i == maxN) {
                    equRes = equRes + pegs[i];
                }
            }
            else {
                if (i == 0) {
                    equRes = equRes + pegs[i];
                }
                if (i == maxN) {
                    equRes = equRes + pegs[i];
                }
            }
        }
        return equRes;
    }
    public static boolean pegListValidation(int[] pegs, int radOfFirstPeg){
        int radiusOfPeg = radOfFirstPeg;
        for (int i = 1; i < pegs.length; i++){
            System.out.println("Radius of Current Peg " + radiusOfPeg);
            System.out.println("Radius of First Peg " + radOfFirstPeg);
            System.out.println("Current Peg " + pegs[i]);
            System.out.println("Previous Peg " + pegs[i-1]);
            System.out.println();
            System.out.println("Radius of current peg = \n" + pegs[i] + " - " + pegs[i-1] + " - " + radiusOfPeg);
            radiusOfPeg = pegs[i] - pegs[i - 1] - radiusOfPeg;
            if (radiusOfPeg < 0){
                return false;
            }
        }
        if (radOfFirstPeg / 2 != radiusOfPeg){
            System.out.println("Radius of First Peg " + radOfFirstPeg);
            System.out.println("Radius of Current Peg " + radiusOfPeg);
            return false;
        }
        System.out.println(radOfFirstPeg);
        System.out.println(radiusOfPeg);
        return true;
    }
}