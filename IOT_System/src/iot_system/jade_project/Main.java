/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade_project;

import jess.JessException;
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
        engine.eval("(printout t \"The answer is \" 42 \"!\" crlf)");
       
    }
    
}
