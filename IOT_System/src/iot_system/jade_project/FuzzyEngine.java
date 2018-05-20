package jade_project;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import nrc.fuzzy.FuzzyException;
import nrc.fuzzy.FuzzyRule;
import nrc.fuzzy.FuzzyValue;
import nrc.fuzzy.FuzzyValueVector;
import nrc.fuzzy.FuzzyVariable;
import nrc.fuzzy.LFuzzySet;
import nrc.fuzzy.LeftLinearFunction;
import nrc.fuzzy.RFuzzySet;
import nrc.fuzzy.RightLinearFunction;
import nrc.fuzzy.TriangleFuzzySet;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



public class FuzzyEngine {

	JFormattedTextField tmp,tmp1;
	double fanRpm=402.55;
	int temp=5;
    public ArrayList<Double> crispTemp;
    public ArrayList<Double> fanRpmV;
    public int counter=0;
     public ArrayList<Pair<String,Pair<Integer,Integer>>> tempRange=null,fanRange=null;
    /**
     * Creates ublinew form HomePage
     */
    public FuzzyEngine() {
        crispTemp= new ArrayList<Double>();
        fanRpmV = new ArrayList<Double>();
        callFuzzy();
       
    }

    public ArrayList<Double> getTemps(){
        return crispTemp;
    }
    public ArrayList<Double> getFanValue(){
        return fanRpmV;
    }
    /**
     * http://rorchard.github.io/FuzzyJ/fuzzyJDocs/FuzzyVariable.html
     */
    public void callFuzzy(){
     FuzzyRule coldTemp = new FuzzyRule(); FuzzyRule OKTemp = new FuzzyRule();
     FuzzyRule hotTemp = new FuzzyRule(); FuzzyValueVector fanSpeedfvv = null;
     FuzzyValue globalFanSpeedfv = null, currentTempfv = null;
     FuzzyVariable airTemp = null, fanSpeed = null;
     RightLinearFunction rlf = new RightLinearFunction();
     LeftLinearFunction llf = new LeftLinearFunction();
     try
     { // define the fuzzy variables for air temperature and fan speed
     // with their associated linguistic terms
     airTemp = new FuzzyVariable("airTemperature", 0.0, 100.0, "Deg C");
     airTemp.addTerm("cold", new RFuzzySet(0.0, 20.0, rlf));
     airTemp.addTerm("OK", new TriangleFuzzySet(0.0, 20.0, 35.0));
     airTemp.addTerm("hot", new LFuzzySet(20.0, 35.0, llf));
     fanSpeed = new FuzzyVariable("fanSpeed", 0.0, 1000.0, "RPM");
     fanSpeed.addTerm("low", new RFuzzySet(0.0, 500.0, rlf));
     fanSpeed.addTerm("medium", new TriangleFuzzySet(250.0, 500.0, 750.0));
     fanSpeed.addTerm("high", new LFuzzySet(500.0, 1000.0, llf));
     Pair<Integer,Integer> range;
     Pair<String,Pair<Integer,Integer>> termNameRange;
     
     // define the 3 fuzzy rules each with a single antecedent and
     // a single conclusion fuzzy value
     coldTemp.addAntecedent(new FuzzyValue(airTemp,"cold"));
     coldTemp.addConclusion(new FuzzyValue(fanSpeed,"low"));
     OKTemp.addAntecedent(new FuzzyValue(airTemp,"OK"));
     OKTemp.addConclusion(new FuzzyValue(fanSpeed,"medium"));
     hotTemp.addAntecedent(new FuzzyValue(airTemp,"hot"));
     hotTemp.addConclusion(new FuzzyValue(fanSpeed,"high"));
     }
     catch (FuzzyException fe)
     { System.err.println("Error initializing fuzzy variables/rules\n" + fe);
     System.exit(100);
     }
     System.out.println("Fan Fuzzy Sets:\n");
     for (double t = 0.0; t <= 40.0; t = t + 2.0)
     { // since each rule that fires can generate a fuzzy value for the
     // fan speed we need to assimilate all of the fuzzy value outputs
     // into a single global fuzzy value (globalFanSpeedfv)
         int i=0;
     globalFanSpeedfv = null;
     // clear inputs to rules on each iteration so we can set the new ones
     coldTemp.removeAllInputs(); OKTemp.removeAllInputs();
     hotTemp.removeAllInputs();
     try
     { // add inputs to rules; note that we convert the crisp temperature
     // to a fuzzy value - this process is known as fuzzification
     currentTempfv = new FuzzyValue(airTemp, new TriangleFuzzySet(t,t,t));
     coldTemp.addInput(currentTempfv); OKTemp.addInput(currentTempfv);
     hotTemp.addInput(currentTempfv);
     // execute the 3 rules and determine fan speed in global FuzzyValue
     // globalFanSpeedfv; for each rule test to see if rule input(s) match
     // antecedent(s) and if so execute rule; if rule fires add resultant fan
     // speed fuzzy value to global fan speed by performing a fuzzy union
     if (coldTemp.testRuleMatching())
     { fanSpeedfvv = coldTemp.execute();
     if (globalFanSpeedfv == null)
     globalFanSpeedfv = fanSpeedfvv.fuzzyValueAt(0);
     else
     globalFanSpeedfv =
     globalFanSpeedfv.fuzzyUnion(fanSpeedfvv.fuzzyValueAt(0));
     }
     if (OKTemp.testRuleMatching())
     { fanSpeedfvv = OKTemp.execute();
     if (globalFanSpeedfv == null)
     globalFanSpeedfv = fanSpeedfvv.fuzzyValueAt(0);
     else
     globalFanSpeedfv =
     globalFanSpeedfv.fuzzyUnion(fanSpeedfvv.fuzzyValueAt(0));
     }
     if (hotTemp.testRuleMatching())
     { fanSpeedfvv = hotTemp.execute();
     if (globalFanSpeedfv == null)
     globalFanSpeedfv = fanSpeedfvv.fuzzyValueAt(0);
     else
     globalFanSpeedfv =
     globalFanSpeedfv.fuzzyUnion(fanSpeedfvv.fuzzyValueAt(0));
     }
     // output the result for the given air temperature – the fan speed
     // fuzzy value is defuzzified to give a crisp result
     crispTemp.add(t);
     fanRpmV.add(globalFanSpeedfv.momentDefuzzify());
     
     System.out.println(globalFanSpeedfv.getFuzzySet().toString() + " " + globalFanSpeedfv.getFuzzyVariable().getName());
     Enumeration a1= globalFanSpeedfv.getFuzzyVariable().findTermNames();
     while(a1.hasMoreElements())
         System.out.println(a1.nextElement().toString());
     
     }
     catch (FuzzyException fe)
     { System.err.println(fe); System.exit(100);}
    }
     // iterate over a range of temperatures determining the fan speed that
     // should be selected for that temperature
     
    }
}
