/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade_project.notneeded;

import jade_project.Sensor;
import java.util.ArrayList;

/**
 *
 * @author beerzy
 */
class Room {
    private String roomType;
    private int roomIdentifier;
    private Person roomOwner;
    private ArrayList<Person> allowedPeople;
    private ArrayList<Sensor> roomSensors;
    
    public Room(String typeOfRoom,int roomIdentifier){
        this.roomType=typeOfRoom;
        this.roomIdentifier=roomIdentifier;
}
    
}
