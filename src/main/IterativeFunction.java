
package main;

import javafx.scene.control.TextField;

/**
 *
 * @author Илона
 */
public class IterativeFunction {
    private double alfa;
    private double betta;
    private double gamma;
    private double delta;
    private double epsilon;
    private double mu;
    private final double x0;
    private int preparatoryIterations;
    private double currentValueFunc;
    
    public IterativeFunction(TextField alfa,TextField  betta, TextField gamma, TextField delta, TextField epsilon, TextField mu, TextField preparatoryIterations, TextField x0) {
        this.alfa = Double.parseDouble(alfa.getText());
        this.betta = Double.parseDouble(betta.getText());
        this.gamma = Double.parseDouble(gamma.getText());
        this.delta = Double.parseDouble(delta.getText());
        this.epsilon = Double.parseDouble(epsilon.getText());
        this.mu = Double.parseDouble(mu.getText());
        this.preparatoryIterations = Integer.parseInt(preparatoryIterations.getText());
        this.x0 = Double.parseDouble(x0.getText());
        this.currentValueFunc = Double.parseDouble(x0.getText());
        
    }
    
    
    public double f(double x) {
        if(gamma - x == 0 || mu - x == 0)
            x+=0.001;
        return (alfa * Math.sin( betta /((x-gamma)*(x-gamma)) )  +  delta * Math.cos( epsilon/((x-mu)*(x-mu)) ));
    }
    
    public void calculationPreparatoryIterations() {
        int iter = 0;
        
        do {
            iter++;
            calculateNextResultIteration();
        } while (iter != this.preparatoryIterations);
    }
    
    public void calculateNextResultIteration(){
        this.currentValueFunc = f(this.currentValueFunc);
    }
    
    public void regainCurrentValueFuncX0() {
        this.currentValueFunc = this.x0;
    }
    
    public void setPrearatoryIterations(int el) {
        this.preparatoryIterations = el;
    }
    
    public void setAlfa(double el) {
        this.alfa = el;
    }

    public void setBetta(double el) {
        this.betta = el;
    }

    public void setGamma(double el) {
        this.gamma = el;
    }

    public void setDelta(double el) {
        this.delta = el;
    }

    public void setEpsilon(double el) {
        this.epsilon= el;
    }
    
    public void setMu(double el) {
        this.mu = el;
    }
    
    public double getAlfa(){
        return this.alfa;
    }

    public double geBetta() {
        return this.betta;
    }

    public double getGamma() {
        return this.gamma;
    }

    public double getDelta() {
        return this.delta;
    }

    public double getEpsilon() {
        return this.epsilon;
    }
    
    public double getPreparatoryIterations(){
        return this.preparatoryIterations;
    }
    
    public double getCurrentValueFunction() {
        return this.currentValueFunc;
    }
}
