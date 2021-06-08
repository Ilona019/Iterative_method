package main;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Ilona
 */
public class CalculationController {

    CalculationController() {
    }
  

    //Вычисление
    public static ObservableList<XYChart.Data> generatingDataForGraphFunction(String selectedParameter, double leftBorderInterval, double rightBordernIterval, int n, int p, double alpha, double betta, double gamma, double delta, double epsilon, double mu, int preparatoryIterations, double x0) {
        ObservableList<XYChart.Data> datasFx = FXCollections.observableArrayList();//неявный вход
        double step = (rightBordernIterval - leftBorderInterval) / 500;
        double currentPositionAB = leftBorderInterval;//неявный вход
        int iter = 0;//неявный вход

        while (currentPositionAB < rightBordernIterval + step) {
            switch (selectedParameter) {
                case "rb_a":
                    alpha = currentPositionAB;//побочный эффект
                    break;
                case "rb_b":
                    betta = currentPositionAB;
                    break;
                case "rb_c":
                    gamma = currentPositionAB;
                    break;
                case "rb_d":
                    delta = currentPositionAB;
                    break;
                case "rb_e":
                    epsilon = currentPositionAB;
                    break;
                case "rb_m":
                    mu = currentPositionAB;
                    break;
            }

            double currentValueFunc = IterativeFunction.calculationPreparatoryIterations(preparatoryIterations, x0, alpha, betta, gamma, delta, epsilon, mu);//неявный выход
            do {
                currentValueFunc = IterativeFunction.calculateNextResultIteration(currentValueFunc, alpha, betta, gamma, delta, epsilon, mu);
                datasFx.add(new XYChart.Data(currentPositionAB, currentValueFunc));

                iter++;
            } while (iter != n);

            iter = 0;
            currentPositionAB += step;
        }
        return datasFx;
    }

    //Вычисление
    public static ObservableList<XYChart.Data> plotDataForEachPointP(ObservableList<XYChart.Data> datas, int p) {
        ObservableList<XYChart.Data> datasPx = FXCollections.observableArrayList();
        int iterP = 0;
        for (XYChart.Data data : datas) {
            if (iterP == p) {
                datasPx.add(new XYChart.Data(data.getXValue(), data.getYValue()));
                iterP = 0;
            }
            iterP++;
        }

        return datasPx;
    }

    //Вычисление
    public static ObservableList<XYChart.Data> removeDublicateY(ObservableList<XYChart.Data> datas, int n) {
        ObservableList<XYChart.Data> pointsArrayList = FXCollections.observableArrayList();
        HashMap<Double, Double> pointsHash = new HashMap<>();
        String format = "%.4f";
        if (n < 300) {
            format = "%f";
        }
        for (int i = 0; i < datas.size(); i++) {
            String temp = String.format(format, ConvertToNumber.convertObjectToDouble(datas.get(i).getYValue()));
            pointsHash.put(ConvertToNumber.convertStringToDouble(temp.replace(',', '.')),  ConvertToNumber.convertObjectToDouble(datas.get(i).getXValue()));
        }

        for (Double y : pointsHash.keySet()) {
            pointsArrayList.add(new XYChart.Data(pointsHash.get(y), y));
        }
        return pointsArrayList;
    }

}
