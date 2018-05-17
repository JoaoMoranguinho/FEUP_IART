/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade_project;

import jess.Console;
import nrc.fuzzy.*;
import nrc.fuzzy.jess.FuzzyRete;

/**
 *
 * @author beerzy
 */
public class FuzzyConsole extends Console {
    public FuzzyConsole(String name){
    super(name,new FuzzyRete());
    }
    
    
    public static void main(String[] args) {
    new FuzzyConsole("Fuzzy Jess Console").execute(args);
    }
    
}
