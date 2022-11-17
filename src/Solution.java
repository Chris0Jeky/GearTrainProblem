public class Solution {
    public static int[] solution(int[] pegs) {
        int maxN = pegs.length - 1;
        int[] result;
        if (pegs == null || maxN + 1 < 2) {
            result = new int[]{-1, -1};
            return result;
        }

        return pegs;
    }
    public static int equationBuilder(int[] pegs){
        int maxN = pegs.length - 1;
        int equRes = 0;
        if (pegs.length % 2 == 0){
            for (int i = 0; i < maxN + 1; i++) {
                if (i != 0 || i != maxN) {
                    if (i % 2 == 0) {
                        equRes = equRes - pegs[i];
                    }
                    else {
                        equRes = equRes + pegs[i];
                    }
                }
                if (i == 0) {
                    equRes = equRes - pegs[i];
                }
                if (i == maxN){
                    equRes = equRes + pegs[i];
                }
            }
            equRes = (int) (equRes / 1.5);
        }
        else {
            for (int i = 0; i < maxN + 1; i++) {
                
            }
        }
        return equRes;
    }
}