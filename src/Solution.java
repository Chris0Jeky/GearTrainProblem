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

    private static int getEquRes(int[] pegs, int maxN, int equRes) {
        for (int i = 0; i < maxN + 1; i++) {
            System.out.println(equRes);
            if (i != 0 || i != maxN) {
                if (i % 2 == 0) {
                    equRes = equRes - pegs[i] * 2;
                }
                else {
                    equRes = equRes + pegs[i] * 2;
                }
            }
            if (i == 0) {
                equRes = equRes - pegs[i];
            }
            if (i == maxN){
                equRes = equRes + pegs[i];
            }
        }
        return equRes;
    }
}