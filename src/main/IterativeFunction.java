package main;

import java.util.function.Function;

/**
 *
 * @author Илона
 */
public class IterativeFunction {

    public IterativeFunction() {
    }

    @FunctionalInterface
    public interface CalculateArgument<A, B, C, R> {

        R apply(A a, B b, C c);
    }

    private static CalculateArgument<Double, Double, Double, Double> argument = (a, x, c) -> {
            return a / ((x - c) * (x - c));
    };

    //вычисление
    public static double f(double x, double alpha, double betta, double gamma, double delta, double epsilon, double mu) {
        Function<Double, Double> deltaX = z -> z + 0.001;

        if (gamma - x == 0 || mu - x == 0) {
            x = deltaX.apply(x);
        }
        return (alpha * Math.sin(argument.apply(betta, x, gamma)) + delta * Math.cos(argument.apply(epsilon, x, mu)));
    }

    //вычисление
    public static double calculationPreparatoryIterations(int preparatoryIterations, double x0, double alpha, double betta, double gamma, double delta, double epsilon, double mu) {
        int iter = 0;
        double currentValueFunc = x0;

        do {
            iter++;
            currentValueFunc = calculateNextResultIteration(currentValueFunc, alpha, betta, gamma, delta, epsilon, mu);
        } while (iter != preparatoryIterations);
        return currentValueFunc;
    }

    //вычисление
    public static double calculateNextResultIteration(double currentValueFunc, double alpha, double betta, double gamma, double delta, double epsilon, double mu) {
        return f(currentValueFunc, alpha, betta, gamma, delta, epsilon, mu);
    }

}
