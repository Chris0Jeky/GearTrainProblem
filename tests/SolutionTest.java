import java.util.Arrays;

/** Dependency-free regression suite: javac -d /tmp/gear-tests src/*.java tests/SolutionTest.java. */
public class SolutionTest {
    private static int checked;

    public static void main(String[] args) {
        String group = args.length == 0 ? "all" : args[0];
        if (group.equals("all") || group.equals("minimum")) {
            expect(new int[]{1, 3, 4}, new int[]{-1, -1}); // zero-sized middle gear
            expect(new int[]{1, 4, 5, 7}, new int[]{-1, -1}); // radii 8/3, 1/3, 2/3, 4/3
            expect(new int[]{1, 3}, new int[]{-1, -1}); // first and last radii below limits
            expect(new int[]{1, 4}, new int[]{2, 1}); // last radius exactly one
            expect(new int[]{1, 4, 6}, new int[]{2, 1}); // middle and last exactly one
            expect(new int[]{1, 5}, new int[]{8, 3}); // valid fractional radius
            expect(new int[]{1, 6}, new int[]{10, 3}); // fractional remainder must not be truncated
            expect(new int[]{4, 30, 50}, new int[]{12, 1});
            expect(new int[]{4, 17, 50}, new int[]{-1, -1});
            expect(new int[]{1, 10000}, new int[]{6666, 1});
        }
        if (group.equals("all") || group.equals("null")) {
            expect(null, new int[]{-1, -1});
            expect(new int[]{}, new int[]{-1, -1});
            expect(new int[]{1}, new int[]{-1, -1});
            expect(new int[21], new int[]{-1, -1});
        }
        if (group.equals("all") || group.equals("exhaustive")) {
            for (int length = 2; length <= 6; length++) {
                enumerate(new int[length], 0, 1);
            }
        }
        System.out.println("Passed " + checked + " gear configurations (" + group + ")");
    }

    private static void enumerate(int[] pegs, int index, int next) {
        if (index == pegs.length) {
            expect(pegs, bruteForce(pegs));
            return;
        }
        for (int peg = next; peg <= 12; peg++) {
            pegs[index] = peg;
            enumerate(pegs, index + 1, peg + 1);
        }
    }

    // Independently try every possible radius in thirds, checking each meshing gear.
    // The linear meshing equations give denominators of either one or three.
    private static int[] bruteForce(int[] pegs) {
        for (int first = 6; first <= 3 * (pegs[1] - pegs[0] - 1); first++) {
            int radius = first;
            boolean valid = true;
            for (int i = 1; i < pegs.length; i++) {
                radius = 3 * (pegs[i] - pegs[i - 1]) - radius;
                if (radius < 3) {
                    valid = false;
                    break;
                }
            }
            if (valid && first == 2 * radius) {
                return first % 3 == 0 ? new int[]{first / 3, 1} : new int[]{first, 3};
            }
        }
        return new int[]{-1, -1};
    }

    private static void expect(int[] pegs, int[] expected) {
        int[] actual = Solution.solution(pegs);
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError(Arrays.toString(pegs) + ": expected "
                    + Arrays.toString(expected) + ", got " + Arrays.toString(actual));
        }
        checked++;
    }
}
