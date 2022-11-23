
public class AlternateFinding {

    private static class Rational {

        private final int numerator;
        private final int denominator;

        public Rational(double floatingNumber) {

            double tolerance = 1.0E-6;

            double numerator = 1;
            double tempNumerator = 0;

            double denominator = 0;
            double tempDenominator = 1;

            double approximateFloatingNumber = floatingNumber;

            do {

                double integralNumber = Math.floor(approximateFloatingNumber);

                double temp = numerator;
                numerator = (integralNumber * numerator) + tempNumerator;
                tempNumerator = temp;

                temp = denominator;
                denominator = (integralNumber * denominator) + tempDenominator;
                tempDenominator = temp;

                approximateFloatingNumber = 1 / (approximateFloatingNumber - integralNumber);

            } while (Math.abs(floatingNumber - (numerator / denominator)) > (floatingNumber * tolerance));

            this.numerator = (int) numerator;
            this.denominator = (int) denominator;
        }
    }

    public static int[] solution(int[] pegs) {

        double firstGearRadius = pegs.length % 2 == 0
                ? getFirstGearRadiusIfEven(pegs)
                : getFirstGearRadiusIfOdd(pegs);

        if (firstGearRadius < 2) {
            return new int[]{-1, -1};
        }
        double currentGearRadius = firstGearRadius;
        for (int i = 1; i < pegs.length; i++) {

            double distance = pegs[i] - pegs[i - 1];
            currentGearRadius = distance - currentGearRadius;
            if (currentGearRadius < 1) {
                return new int[]{-1, -1};
            }
        }
        final Rational firstGearRadiusAsFraction = new Rational(firstGearRadius);

        return new int[]{firstGearRadiusAsFraction.numerator, firstGearRadiusAsFraction.denominator};
    }

    private static double getFirstGearRadiusIfEven(int[] pegs) {

        return (2.0 / 3.0) * getSum(pegs);
    }

    private static double getFirstGearRadiusIfOdd(int[] pegs) {

        return 2.0 * getSum(pegs);
    }

    private static double getSum(int[] pegs) {
        int sum = 0;
        for (int i = 0; i < pegs.length; i++) {
            double sign = i % 2 == 0 ? -1 : 1;
            double multiplier = (i > 0 && i < pegs.length - 1) ? 2 : 1;
            double value = sign * multiplier * pegs[i];
            sum += value;
        }
        return sum;
    }
}
