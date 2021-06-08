package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Илона
 */
public class FXMLDocFuncController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField xmin;
    @FXML
    private TextField xmax;
    @FXML
    private TextField ymin;
    @FXML
    private TextField ymax;

    @FXML
    private TextField a;
    @FXML
    private TextField b;
    @FXML
    private TextField c;
    @FXML
    private TextField d;
    @FXML
    private TextField e;
    @FXML
    private TextField m;

    @FXML
    private ToggleGroup toggleGroupFixParameters;
    @FXML
    private Group group;

    @FXML
    private RadioButton fix_a;
    @FXML
    private RadioButton fix_b;
    @FXML
    private RadioButton fix_c;
    @FXML
    private RadioButton fix_d;
    @FXML
    private RadioButton fix_e;
    @FXML
    private RadioButton fix_m;
    @FXML
    private TextField x0;

    @FXML
    private TextField iterationsResults;

    @FXML
    private TextField preparatoryIterations;

    @FXML
    private TextField eachP;

    private ScatterChart<Number, Number> scatterChart;
    private NumberAxis x;
    private NumberAxis y;
    private int fixIndex;

    @FXML //Действие
    private void handleButtonAction(ActionEvent event) {
        String message = generatingErrorMessage(xmin.getText(), xmax.getText(), ymin.getText(), ymax.getText(), x0.getText(), a.getText(), b.getText(), c.getText(), d.getText(), e.getText(), m.getText(), iterationsResults.getText(), preparatoryIterations.getText(), eachP.getText());//вычисление
        if (!isInputValid(message)) {//
            clearOutput();//действие

            double A = ConvertToNumber.convertStringToDouble(xmin.getText());//неявный вход
            double B = ConvertToNumber.convertStringToDouble(xmax.getText());//неявный вход
            double C = ConvertToNumber.convertStringToDouble(ymin.getText());//неявный вход
            double D = ConvertToNumber.convertStringToDouble(ymax.getText());//неявный вход
            int eachP = ConvertToNumber.convertStringToInteger(this.eachP.getText());//неявный вход
            int N = ConvertToNumber.convertStringToInteger(iterationsResults.getText());//неявный вход
            double alpha = ConvertToNumber.convertStringToDouble(a.getText());//неявный вход
            double betta = ConvertToNumber.convertStringToDouble(b.getText());//неявный вход
            double gamma = ConvertToNumber.convertStringToDouble(c.getText());//неявный вход
            double delta = ConvertToNumber.convertStringToDouble(d.getText());//неявный вход
            double epsilon = ConvertToNumber.convertStringToDouble(e.getText());//неявный вход
            double mu = ConvertToNumber.convertStringToDouble(m.getText());//неявный вход
            int preparatoryIterations = ConvertToNumber.convertStringToInteger(this.preparatoryIterations.getText());//неявный вход
            double x0 = ConvertToNumber.convertStringToDouble(this.x0.getText());//неявный вход
            
            settingIntervalsTickUnit(x, y, A, B, C, D);//действие
            
            String selectedParameter = fixingFunctionParameter();//действие

            ObservableList<XYChart.Data> dataFx = CalculationController.generatingDataForGraphFunction(selectedParameter, A, B, N, eachP, alpha, betta, gamma, delta, epsilon, mu, preparatoryIterations, x0);
            ObservableList<XYChart.Data> dataPx = CalculationController.plotDataForEachPointP(dataFx, eachP);
            CalculationController.removeDublicateY(dataFx, N);
            CalculationController.removeDublicateY(dataPx, N);
            displayGraphs(dataFx, dataPx, scatterChart, N);//действие
        } else {
            showMessage(message).showAndWait();//действие
        }
    }

    //Действие
    private void settingIntervalsTickUnit(NumberAxis x, NumberAxis y, double xMin, double xMax, double yMin, double yMax) {
        x.setLowerBound(xMin);
        x.setUpperBound(xMax);
        y.setLowerBound(yMin);
        y.setUpperBound(yMax);

        if (Math.abs(xMax - xMin) <= 0.01) {
            x.setTickUnit(0.0001);
        } else if (Math.abs(yMax - yMin) <= 0.01) {
            y.setTickUnit(0.0001);
        } else if (Math.abs(xMax - xMin) <= 0.1) {
            x.setTickUnit(0.001);
        } else if (Math.abs(yMax - yMin) <= 0.1) {
            y.setTickUnit(0.001);
        } else if (Math.abs(xMax - xMin) < 1) {
            x.setTickUnit(0.01);
        } else if (Math.abs(yMax - yMin) < 1) {
            y.setTickUnit(0.01);
        } else if (Math.abs(xMax - xMin) > 1) {
            y.setTickUnit(0.2);
        }
    }

    //Действие
    private String fixingFunctionParameter() {
        if (fix_a.isSelected()) {
            return "rb_a";
        } else if (fix_b.isSelected()) {
            return "rb_b";
        } else if (fix_c.isSelected()) {
            return "rb_c";
        } else if (fix_d.isSelected()) {
            return "rb_d";
        } else if (fix_e.isSelected()) {
            return "rb_e";
        } else if (fix_m.isSelected()) {
            return "rb_m";
        }
        return "rb_a";
    }

    //Вычисление
    private boolean isInputValid(String msg) {
        if (!"".equals(msg)) {
            return true;
        }
        return false;
    }

    //Вычисление
    public String generatingErrorMessage(String xmin, String xmax, String ymin, String ymax, String x0, String a, String b, String c, String d, String e, String m, String iterationsResults, String preparatoryIterations, String eachP) {
        String errors = "";
        if (!isValidParameter(xmin, "double", "-100", "100")) {
            errors += "You incorrectly input the coordinate of the point A on the OX! It is number. -100 <= A <= 100\n";
        } else if (!isValidParameter(xmax, "double", "-100", "100")) {
            errors += "You incorrectly input the coordinate of the point B on the OX! It is number. -100 <= B <= 100\n";
        } else if (!isValidParameter(ymin, "double", "-100", "100")) {
            errors += "You incorrectly input the coordinate of the point C on the OY! It is number. -100 <= C <= 100\n";
        } else if (!isValidParameter(ymax, "double", "-100", "100")) {
            errors += "You incorrectly input the coordinate of the point D on the OY! It is number. -100 <= D <= 100\n";
        } else if (!isValidParameter(x0, "double", ymin, ymax)) {
            errors += "You incorrectly input parameter x0!" + ymin + " <= x0 <= " + ymax + ".\n";
        } else if (!isValidParameter(a, "double", "-100", "100")) {
            errors += "You incorrectly input parameter α! -100 <= α <= 100.\n";
        } else if (!isValidParameter(b, "double", "-100", "100")) {
            errors += "You incorrectly input parameter ⲃ ! -100 <= ⲃ  <= 100.\n";
        } else if (!isValidParameter(c, "double", "-100", "100")) {
            errors += "You incorrectly input parameter γ! -100 <= γ <= 100.\n";
        } else if (!isValidParameter(d, "double", "-100", "100")) {
            errors += "You incorrectly input parameter δ! -100 <= δ <= 100.\n";
        } else if (!isValidParameter(e, "double", "-100", "100")) {
            errors += "You incorrectly input parameter ε! -100 <= ε <= 100.\n";
        } else if (!isValidParameter(m, "double", "-100", "100")) {
            errors += "You incorrectly input parameter μ! -100 <= μ <= 100.\n";
        } else if (!isValidParameter(iterationsResults, "natural", "1", "500")) {
            errors += "You incorrectly input parameter n! It is natural number: 0 < n <= 500.\n";
        } else if (!isValidParameter(preparatoryIterations, "natural", "1", "500")) {
            errors += "You incorrectly input parameter m! It is natural number: 0 < m <= 500.\n";
        } else if (!isValidParameter(eachP, "natural", "1", iterationsResults)) {
            errors += "You incorrectly input parameter p! It is natural number: 0 < p <= " + iterationsResults + ".\n";
        }

        return errors;
    }

    //Вычисление
    private boolean isValidParameter(String valueField, String typeNumber, String leftBorder, String rightBorder) {
        if (isNumber(valueField, typeNumber) && isNumber(leftBorder, typeNumber) && isNumber(leftBorder, typeNumber) && isInInterval(valueField, leftBorder, rightBorder)) {
            return true;
        }
        return false;
    }

    //Вычисление
    private boolean isNumber(String valueField, String typeNumber) {
        if (typeNumber.equals("double") && valueField.matches("[\\+-]?[0-9]+[.]?+[0-9]*")) {
            return true;
        } else if (typeNumber.equals("natural") && valueField.matches("[\\+]?[0-9]+")) {
            return true;
        }
        return false;
    }

    //Вычисление
    private boolean isInInterval(String numberStr, String leftBorder, String rightBorder) {
        if ((ConvertToNumber.convertStringToDouble(numberStr) >= ConvertToNumber.convertStringToDouble(leftBorder)) && (ConvertToNumber.convertStringToDouble(numberStr) <= ConvertToNumber.convertStringToDouble(rightBorder))) {
            return true;
        }
        return false;
    }

    //Действие
    private Alert showMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);//неявный вход
        alert.setTitle("Data file error");
        alert.setHeaderText("Data entry error");
        alert.setContentText(s);
        return alert;
    }

    //Действие
    private void clearOutput() {
        scatterChart.getData().clear();
    }
    
    //Действие
    private XYChart.Series addFunctionToSeries(String title, ObservableList<XYChart.Data> datas, int n) {
        XYChart.Series series = new XYChart.Series();
        series.setName(title);
        series.setData(datas);
        return series;
    }

    //Действие
    private void addSeriesToChart(XYChart.Series series, ScatterChart<Number, Number> scatterChart) {
        scatterChart.getData().add(series);
    }
    
    //Действие
    private void displayGraphs(ObservableList<XYChart.Data> datasFx, ObservableList<XYChart.Data> datasPx, ScatterChart<Number, Number> scatterChart, int n) {
        addSeriesToChart(addFunctionToSeries("f(x)", datasFx, n), scatterChart);//действие
        addSeriesToChart(addFunctionToSeries("p(x)", datasPx, n), scatterChart);//действие
    }

    //Действие
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        x = new NumberAxis(0.0, 5.0, 0.1);
        y = new NumberAxis(0.0, 5.0, 0.1);
        scatterChart = new ScatterChart<>(x, y);//создали полотно для графика
        x.setLabel("X");
        y.setLabel("F(X)");

        scatterChart.setLayoutX(370);
        scatterChart.setLayoutY(60);
        scatterChart.setMinHeight(950);//размеры координатной плоскости
        scatterChart.setMinWidth(1200);
        group.getChildren().get(0).setDisable(true);//По умолчанию фиксированин параметр лямбда.
        fixIndex = 0;

        toggleGroupFixParameters.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldValue, Toggle newValue) {

                RadioButton selectedBtn = (RadioButton) newValue;
                group.getChildren().get(fixIndex).setDisable(false);
                for (int i = 0; i < toggleGroupFixParameters.getToggles().size(); i++) {
                    if (toggleGroupFixParameters.getToggles().get(i) == selectedBtn) {
                        fixIndex = i;
                        group.getChildren().get(fixIndex).setDisable(true);
                    }
                }
            }
        });
        pane.getChildren().add(scatterChart);
    }
}
