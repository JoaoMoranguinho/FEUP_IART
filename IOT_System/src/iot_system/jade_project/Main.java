/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade_project;

import jess.Defglobal;
import jess.JessException;
import jess.Value;
import nrc.fuzzy.FuzzyValue;
import nrc.fuzzy.FuzzyVariable;
import nrc.fuzzy.RightLinearFuzzySet;
import nrc.fuzzy.jess.FuzzyRete;

/**
 *
 * @author beerzy
 */
public class Main  {
    public static void main(String[] args) throws JessException{
        
        
        FuzzyRete engine = new FuzzyRete();
        engine.reset();
        
        try{
            engine.batch("/home/beerzy/Documents/JADE_project/src/jade_project/Hello.clp");
        }
        catch (JessException je) { 
        System.err.println(je);
        }
        
        // engine.batch("Hello.clp");
        /*
        listener code
        */
        Defglobal dg =engine.findDefglobal("*airTempFvar*");
     
        //FuzzyValue f1 = new FuzzyValue(engine.findDefglobal("*airTempFvar*").getInitializationValue(),"OK");
       
        System.out.println(engine.findDefglobal("*airTempFvar*").toString());
 
    }
    
}
