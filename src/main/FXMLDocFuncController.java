package main;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
    IterativeFunction func;

    private ScatterChart<Number, Number> scatterChart;
    private NumberAxis x;
    private NumberAxis y;
    private String selected_parameter;
    private RadioButton selectedBtn;
    private int fixIndex;
    private Task<Void> task;
    

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (!isInputValid()) {
            clearOutput();
            if (fix_a.isSelected()) {
                selected_parameter = "rb_a";
            } else if (fix_b.isSelected()) {
                selected_parameter = "rb_b";
            } else if (fix_c.isSelected()) {
                selected_parameter = "rb_c";
            } else if (fix_d.isSelected()) {
                selected_parameter = "rb_d";
            } else if (fix_e.isSelected()) {
                selected_parameter = "rb_e";
            } else if (fix_m.isSelected()) {
                selected_parameter = "rb_m";
            }
            func = new IterativeFunction(a, b, c, d, e, m, preparatoryIterations, x0);
            x.setLowerBound(Double.parseDouble(xmin.getText()));
            x.setUpperBound(Double.parseDouble(xmax.getText()));
            y.setLowerBound(Double.parseDouble(ymin.getText()));
            y.setUpperBound(Double.parseDouble(ymax.getText()));
            if (Math.abs((Double.parseDouble(xmax.getText())) - Double.parseDouble(xmin.getText())) <= 0.01) {
                x.setTickUnit(0.0001);
            } else if (Math.abs((Double.parseDouble(ymax.getText())) - Double.parseDouble(ymin.getText())) <= 0.01) {
                y.setTickUnit(0.0001);
            } else if (Math.abs((Double.parseDouble(xmax.getText())) - Double.parseDouble(xmin.getText())) <= 0.1) {
                x.setTickUnit(0.001);
            } else if (Math.abs((Double.parseDouble(ymax.getText())) - Double.parseDouble(ymin.getText())) <= 0.1) {
                y.setTickUnit(0.001);
            } else if (Math.abs((Double.parseDouble(xmax.getText())) - Double.parseDouble(xmin.getText())) < 1) {
                x.setTickUnit(0.01);
            } else if (Math.abs((Double.parseDouble(ymax.getText())) - Double.parseDouble(ymin.getText())) < 1) {
                y.setTickUnit(0.01);
            } else if (Math.abs((Double.parseDouble(xmax.getText())) - Double.parseDouble(xmin.getText())) > 1) {
                y.setTickUnit(0.2);
            }
        
            drawFx();
        }
    }
    
    private boolean isInputValid() {
        String message = isInputABCD();
        if (!"".equals(message)) {
            showMessage(message);
            return true;
        }
        return false;
    }

    public String isInputABCD() {
        String errors = "";
        if (!isNumber(xmin, -100, 100)) {
            errors += "You incorrectly input the coordinate of the point A on the OX! It is number. -100 <= A <= 100\n";
        } else if (!isNumber(xmax, -100, 100)) {
            errors += "You incorrectly input the coordinate of the point B on the OX! It is number. -100 <= B <= 100\n";
        } else if (!isNumber(ymin, -100, 100)) {
            errors += "You incorrectly input the coordinate of the point C on the OY! It is number. -100 <= C <= 100\n";
        } else if (!isNumber(ymax, -100, 100)) {
            errors += "You incorrectly input the coordinate of the point D on the OY! It is number. -100 <= D <= 100\n";
        }else if(!isNumber(x0, Double.parseDouble(ymin.getText()), Double.parseDouble(ymax.getText()))){
            errors += "You incorrectly input parameter x0!"+Double.parseDouble(ymin.getText())+" <= x0 <= "+Double.parseDouble(ymax.getText())+".\n";
        }else if (!isNumber(a, -100, 100)) {
            errors += "You incorrectly input parameter α! -100 <= α <= 100.\n";
        } else if (!isNumber(b, -100, 100)) {
            errors += "You incorrectly input parameter ⲃ ! -100 <= ⲃ  <= 100.\n";
        } else if (!isNumber(c, -100, 100)) {
            errors += "You incorrectly input parameter γ! -100 <= γ <= 100.\n";
        } else if (!isNumber(d, -100, 100)) {
            errors += "You incorrectly input parameter δ! -100 <= δ <= 100.\n";
        } else if (!isNumber(e, -100, 100)) {
            errors += "You incorrectly input parameter ε! -100 <= ε <= 100.\n";
        } else if (!isNumber(m, -100, 100)) {
            errors += "You incorrectly input parameter μ! -100 <= μ <= 100.\n";
        } else if (!isIntegerNumber(iterationsResults, 0, 500)) {
            errors += "You incorrectly input parameter n! 0 < n <= 500.\n";
        } else if (!isIntegerNumber(preparatoryIterations, 0, 500)) {
            errors += "You incorrectly input parameter m! 0 < m <= 500.\n";
        } else if (!isIntegerNumber(eachP, -1, Integer.parseInt(iterationsResults.getText()))) {
            errors += "You incorrectly input parameter p! 0 <= p <= "+Integer.parseInt(iterationsResults.getText())+".\n";
        }
        
        return errors;

    }
    
    private boolean isNumber(TextField text, double a, double b) {
        if (text.getText().matches("[\\+-]?[0-9]+[.]?+[0-9]*") && (Double.parseDouble(text.getText()) >= a ) && (Double.parseDouble(text.getText()) <= b)) {
            return true;
        }
        return false;
    }
    
    private boolean isIntegerNumber(TextField text, int a, int b) {
      if (text.getText().matches("[\\+-]?[0-9]+[.]?+[0-9]*") && (Integer.parseInt(text.getText()) > a ) && (Integer.parseInt(text.getText()) <= b)) {
             return true;
         }
        return false;
    }
    
    private void showMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Data file error");
        alert.setHeaderText("Data entry error");
        alert.setContentText(s);
        alert.showAndWait();
    }

    private void clearOutput() {
        scatterChart.getData().clear();
    }

    
    public void drawFx() {
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas2 = FXCollections.observableArrayList();
        double A = Double.parseDouble(xmin.getText());
        double B = Double.parseDouble(xmax.getText());
        double step = (B - A) / 500;
        double currentPositionAB = A;
        int iter = 0;
        int iterP = 0;
        int n = Integer.parseInt(iterationsResults.getText());
        int p = Integer.parseInt(eachP.getText());
        int count = 0;
       
        
        while (currentPositionAB < B + step) {           
            update_selected_parameter(func, currentPositionAB);
            func.calculationPreparatoryIterations();
            
            do {
                func.calculateNextResultIteration();
                datas.add(new XYChart.Data(currentPositionAB,func.getCurrentValueFunction()));
                iterP++;
                if(iterP == p) {
                    datas2.add(new XYChart.Data(currentPositionAB, func.getCurrentValueFunction()));
                    iterP = 0;
                }
                iter++;
                
            } while(iter != n);
            
            iter = 0;
            iterP = 0;
            func.regainCurrentValueFuncX0();
            currentPositionAB += step;
        }
        addFunctionToChart("f(x)", datas);
        addFunctionToChart("p(x)", datas2);
    }
    
    //Изменить значение выделенного параметра на value из [A ; B]
    private void update_selected_parameter(IterativeFunction func, double value){
        switch(selected_parameter){
            case "rb_a":
                func.setAlfa(value);
                break;
            case "rb_b":
                func.setBetta(value);
                break;
            case "rb_c":
                func.setGamma(value);
                break;   
            case "rb_d":
                func.setDelta(value);
                break;
            case "rb_e":
                func.setEpsilon(value);
                break;
            case "rb_m":
                func.setMu(value);
                break;
        }
    }
    
    private ObservableList<XYChart.Data> removeDublicate(ObservableList<XYChart.Data> datas, int n)
    {
        ObservableList<XYChart.Data> pointsArrayList = FXCollections.observableArrayList();
        HashMap<Double, Double> pointsHash = new HashMap<>();
        String format = "%.4f";
        if (n < 300)
            format = "%f";
        for (int i = 0; i < datas.size(); i++)
        {
            String temp = String.format(format, (double)datas.get(i).getYValue());
            pointsHash.put(Double.parseDouble(temp.replace(',', '.')), (double)datas.get(i).getXValue());
        }

        for (Double y : pointsHash.keySet())
        {
            pointsArrayList.add(new XYChart.Data(pointsHash.get(y), y));
        }
        return pointsArrayList;
    }

    
    private void addFunctionToChart(String title, ObservableList<XYChart.Data> datas) {
        XYChart.Series series = new XYChart.Series();//серии данных для рисования нескольких графиков
        series.setName(title);
        datas = removeDublicate(datas, Integer.parseInt(iterationsResults.getText()));
        series.setData(datas);//помещаем данные на полотно
        scatterChart.getData().add(series);
    }
    
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
        
        toggleGroupFixParameters.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldValue, Toggle newValue){
                selectedBtn = (RadioButton) newValue;
                group.getChildren().get(fixIndex).setDisable(false);
                for(int i = 0; i < toggleGroupFixParameters.getToggles().size(); i++)
                if(toggleGroupFixParameters.getToggles().get(i) ==  selectedBtn){
                    fixIndex = i;
                    group.getChildren().get(fixIndex).setDisable(true);
                }
            }
        });
        pane.getChildren().add(scatterChart);
    }
}
