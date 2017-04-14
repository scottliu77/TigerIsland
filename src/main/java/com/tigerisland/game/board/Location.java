package com.tigerisland.game.board;

import java.util.*;

public class Location{

    public final Integer x;
    public final Integer y;
    public final Integer z;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
        this.z = null;
    }

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(Location loc){
        this.x = loc.x;
        this.y = loc.y;
        this.z = loc.z;
    }

    public boolean equals(Location loc){
        if(this.x == loc.x && this.y == loc.y && this.z == loc.z){
            return true;
        }
        return false;
    }


    public static Location rotateHexLeft(Location loc, int rotation) {
        Location shiftLocation = Rotation.calculateRotation(rotation);
        return Location.add(loc, shiftLocation);
    }

    public static Location add(Location loc1, Location loc2){
        return new Location(loc1.x + loc2.x, loc1.y + loc2.y);
    }

    public boolean greaterThan(Location otherLoc){
        if(this.y > otherLoc.y) {
            return true;
        }

        if(this.y == otherLoc.y && this.x > otherLoc.x) {
            return true;
        }

        return false;
    }

    public boolean lessThan(Location otherLoc){
        if(this.y < otherLoc.y) {
            return true;
        }

        if(this.y == otherLoc.y && this.x < otherLoc.x) {
            return true;
        }

        return false;
    }

    public boolean equalTo(Location otherLoc){
        if(this.y == otherLoc.y && this.x == otherLoc.x) {
            return true;
        }
        return false;
    }

    public static void printLocations(ArrayList<Location> list){
        for(int listIndex = 0; listIndex < list.size(); listIndex++){
            String xCoordinate = Integer.toString(list.get(listIndex).x);
            String yCoordinate = Integer.toString(list.get(listIndex).y);

            System.out.println("X=" + xCoordinate + " : Y=" + yCoordinate);
        }
    }

    public ArrayList<Location> getAdjacentLocations(){
        ArrayList<Location> adjacentLocations = new ArrayList<Location>();
        for (int angle = 0; angle <= 300; angle += 60) {
            adjacentLocations.add(Location.rotateHexLeft(this, angle));
        }
        return adjacentLocations;
    }

    public boolean isAdjacentTo(Location otherLocation) {
        int otherX = otherLocation.x;
        int otherY = otherLocation.y;
        if(this.x == otherX - 1 && y == otherY + 1){
            return true;
        }
        if(x == otherX + 1 && y == otherY - 1){
            return true;
        }
        if(x == otherX && y == otherY + 1){
            return true;
        }
        if(x == otherX && y == otherY - 1) {
            return true;
        }
        if(x == otherX + 1 && y == otherY){
            return true;
        }
        if(x == otherX - 1 && y == otherY){
            return true;
        }
        return false;
    }

}
