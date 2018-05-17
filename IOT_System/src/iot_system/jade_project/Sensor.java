/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade_project;

import java.util.ArrayList;

/**
 *
 * @author beerzy
 */
class Sensor {
    private String type;
    private String data;
    boolean stateOn;
    public Sensor(String type, String data,boolean onOff){
        this.type=type;
        this.data=data;
        this.stateOn=onOff;
    }
    
}
