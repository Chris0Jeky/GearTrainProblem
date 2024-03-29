public class Solution {
    public static int[] solution(int[] pegs) {

        /*
        * This block of code is checking if the input "pegs" is valid, and if it is, it calculates the result using the
        *  "equationBuilder" method and validates it using the "pegListValidation" method. If the input is invalid or
        *  the validation fails, it returns an array with the values -1, -1. If everything is valid, it returns an
        *  array with the calculated result and the value returned by the "equationBuilder" method.
        * */
        int maxN = pegs.length - 1;
        int[] result;
        if (pegs == null || maxN + 1 < 2 || maxN + 1 > 20) {
            result = new int[]{-1, -1};
            return result;
        }
        int equationResult = equationBuilder(pegs)[0];
        boolean isGood = pegListValidation(pegs, equationResult, equationBuilder(pegs)[1]);
        if (!isGood){
            result = new int[]{-1, -1};
            return result;
        } else{
            result = new int[]{equationResult, equationBuilder(pegs)[1]};
            return result;
        }
    }

    /*
    *This block of code is the "equationBuilder" method which takes in an array "pegs" as input. It calculates a value
    * "equRes" using the "getEquRes" method and then checks if the length of the "pegs" array is even or odd.
    *  Depending on the result, it modifies the value of "equRes" and creates an array "equResArr" with the
    *  modified value of "equRes" and another value. It then returns this array.
    * */
    public static int[] equationBuilder(int[] pegs){
        int maxN = pegs.length - 1;
        int[] equResArr;
        int equRes = 0;
        equRes = getEquRes(pegs, maxN, equRes);
        if (pegs.length % 2 == 0){
            if ((int) (equRes % 1.5) == 0) {
                equRes = (int) (equRes / 1.5);
                equResArr = new int[]{equRes, 1};
            }
            else {
                equRes = equRes * 2;
                equResArr = new int[]{equRes, 3};

            }
        }
        else {
            equRes = equRes * 2 * -1;
            equResArr = new int[]{equRes, 1};
        }
        return equResArr;

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
    public static boolean pegListValidation(int[] pegs, int radOfFirstPeg, int denom) {
        double radiusOfPeg = radOfFirstPeg;
        if (radOfFirstPeg < 2) {
            return false;
        }
        if (denom == 3){
            for (int i = 1; i < pegs.length; i++) {
                radiusOfPeg = pegs[i]*3 - pegs[i - 1]*3 - radiusOfPeg;
                if (radiusOfPeg < 0) {
                    return false;
                }
            }
        }
        else {
            for (int i = 1; i < pegs.length; i++) {
                radiusOfPeg = pegs[i] - pegs[i - 1] - radiusOfPeg;
                if (radiusOfPeg < 0) {
                    return false;
                }
            }
        }

        if ((double) radOfFirstPeg / 2 != radiusOfPeg) {
            return false;
        } else {
            return true;
        }
    }
}