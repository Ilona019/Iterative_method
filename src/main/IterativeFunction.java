package main;

/**
 *
 * @author Илона
 */
public class IterativeFunction {

    public IterativeFunction() {
    }

    //вычисление
    public static double f(double x, double alpha, double betta, double gamma, double delta, double epsilon, double mu) {
        if (gamma - x == 0 || mu - x == 0) {
            x += 0.001;
        }
        return (alpha * Math.sin(betta / ((x - gamma) * (x - gamma))) + delta * Math.cos(epsilon / ((x - mu) * (x - mu))));
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
