import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

class Tester {

    public static void test() {
        System.out.println("=== Basic Test Cases ===");
        printOutput(Solution.solution(new int[]{4, 30, 50}));              // [12, 1] Base Test Case
        printOutput(Solution.solution(new int[]{4, 17, 50}));              // [-1, -1] Negative Test
        
        System.out.println("\n=== 2 Peg Cases ===");
        printOutput(Solution.solution(new int[]{1, 100}));                 // [66, 1] 2 Pegs
        printOutput(Solution.solution(new int[]{10, 50}));                 // [80/3, 1] = 26.666...
        printOutput(Solution.solution(new int[]{5, 10}));                  // [10/3, 1] = 3.333...
        
        System.out.println("\n=== Even Number of Pegs (4) ===");
        printOutput(Solution.solution(new int[]{375, 3850, 7328, 8630}));  // [866, 1] Even
        printOutput(Solution.solution(new int[]{10, 30, 40, 60}));         // [50/3, 1] = 16.666...
        printOutput(Solution.solution(new int[]{5, 10, 25, 40}));          // [20, 1]
        
        System.out.println("\n=== Odd Number of Pegs (5) ===");
        printOutput(Solution.solution(new int[]{13, 130, 234, 327, 394})); // [78, 1] Odd
        printOutput(Solution.solution(new int[]{10, 20, 30, 40, 50}));     // [20, 1]
        printOutput(Solution.solution(new int[]{5, 15, 45, 135, 405}));    // [540, 1]
        
        System.out.println("\n=== Fractional Results ===");
        printOutput(Solution.solution(new int[]{9377, 9447, 9569, 9646})); // [50, 3] = 16.666...
        printOutput(Solution.solution(new int[]{6854, 8431, 9073, 9930})); // [50, 3] = 16.666...
        
        System.out.println("\n=== Special Patterns ===");
        printOutput(Solution.solution(new int[]{4, 8, 16, 32, 64}));       // Powers of 2
        printOutput(Solution.solution(new int[]{100, 200, 300}));          // Linear progression
        printOutput(Solution.solution(new int[]{11, 23, 47, 97}));         // Prime-based gaps
        
        System.out.println("\n=== Large Scale Cases ===");
        printOutput(Solution.solution(new int[]{1000, 2000, 3000, 4000})); // Thousands
        printOutput(Solution.solution(new int[]{100, 300, 900, 2700}));    // Exponential growth
        
        System.out.println("\n=== Edge Cases ===");
        printOutput(Solution.solution(new int[]{10, 11, 12}));             // [-1, -1] Too close
        printOutput(Solution.solution(new int[]{1, 2, 3, 4, 5}));          // [-1, -1] Unit spacing
        
        System.out.println("\n=== Complex Valid Cases ===");
        printOutput(Solution.solution(new int[]{7414, 7790, 8470, 9126, 9451, 9476})); // 6 pegs



        /*findValidTestSets(6, true, true);

        findValidTestSets(6, true, false);

        findValidTestSets(6, false, true);

        findValidTestSets(7, true, true);

        findValidTestSets(7, false, true);

        findValidTestSets(7, true, false);

        findValidTestSets(7, false, false);
*/
    }

    private static void printOutput(int[] output) {

        System.out.println(
                Arrays.stream(output)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(",", "[", "]"))
        );
    }

    // To come up with test cases
    private static void findValidTestSets(int numberOfPegs,
                                          boolean mustBeValid,
                                          boolean mustBeAHardFraction) {

        while (true) {

            int[] pegs = new int[numberOfPegs];
            pegs[0] = getRandomInteger(1, 10000);
            for (int pegIndex = 1; pegIndex < numberOfPegs; pegIndex++) {
                pegs[pegIndex] = getRandomInteger(pegs[pegIndex - 1], 10000);
            }

            int[] sol = Solution.solution(pegs);
            if (mustBeValid && sol[0] != -1) {
                if (mustBeAHardFraction && sol[1] != 1) {
                    System.out.println(Arrays.toString(pegs));
                    printOutput(sol);
                    break;
                }
            }
        }
    }

    private static int getRandomInteger(int low, int high) {
        return new Random().nextInt(high-low) + low;
    }
}