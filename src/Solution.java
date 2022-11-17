public class Solution {
    public static int[] solution(int[] pegs) {
        int maxN = pegs.length - 1;
        int[] result;
        if (pegs == null || maxN + 1 < 2) {
            result = new int[]{-1, -1};
            return result;
        }
        result = new int[]{equationBuilder(pegs), 1};
        return result;
    }
    public static int equationBuilder(int[] pegs){
        int maxN = pegs.length - 1;
        int equRes = 0;
        equRes = getEquRes(pegs, maxN, equRes);
        if (pegs.length % 2 == 0){
            equRes = (int) (equRes / 1.5);
        }
        else {
            equRes = equRes * -1;
        }
        return equRes;
    }

    // given the following equation if the number of pegs is odd:
    // peg1 - 2*peg2 + 2*peg3 - 2*peg4 (...) + pegN
    // the function beneath alternates a positive and a negative value of double the indexed peg
    // except for the first and last peg
    private static int getEquRes(int[] pegs, int maxN, int equRes) {
        for (int i = 0; i < maxN + 1; i++) {
            System.out.println("Peg no." + i + "\nEquation Result so far: " + equRes + "\nPeg position: " + pegs[i]);
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
}