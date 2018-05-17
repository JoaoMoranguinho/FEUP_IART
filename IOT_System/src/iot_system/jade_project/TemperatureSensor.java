/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade_project;

import java.util.logging.Level;
import java.util.logging.Logger;
import nrc.fuzzy.FuzzyException;
import nrc.fuzzy.FuzzyRule;
import nrc.fuzzy.FuzzyValue;
import nrc.fuzzy.FuzzyVariable;
import nrc.fuzzy.FuzzyVariableException;
import nrc.fuzzy.InvalidFuzzyVariableNameException;
import nrc.fuzzy.InvalidFuzzyVariableTermNameException;
import nrc.fuzzy.InvalidLinguisticExpressionException;
import nrc.fuzzy.InvalidUODRangeException;
import nrc.fuzzy.PIFuzzySet;
import nrc.fuzzy.SFuzzySet;
import nrc.fuzzy.XValueOutsideUODException;
import nrc.fuzzy.XValuesOutOfOrderException;
import nrc.fuzzy.YValueOutOfRangeException;
import nrc.fuzzy.ZFuzzySet;



/**
 *
 * @author beerzy
 */
public class TemperatureSensor extends Sensor {
    private int currentTemperature;
    public double xHot[];
    public double yHot[];
    public double xCold[];
    public double yCold[];
    public FuzzyVariable temp;
    public TemperatureSensor(String type, String data,String powerLine, String termometer, boolean onOff) {
        super(type, data,onOff);

      
            this.xHot[0]=25;
            this.xHot[1]=35;
            this.yHot[0]=0;
            this.yHot[1]=1;
            this.xCold[0]=5;
            this.xCold[1]=15;
            this.yCold[0]=0;
            this.yCold[1]=1;
            String message;
     
   
    }
    
    public int getTemp(){return this.currentTemperature;}
    
}
