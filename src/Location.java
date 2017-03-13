import java.util.*;

public class Location{
    int x;
    int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Location(Location loc){
        this.x = loc.x;
        this.y = loc.y;
    }

    public static Location add(Location loc1, Location loc2){
        return new Location(loc1.x + loc2.x, loc1.y + loc2.y);
    }

    public Location add(Location addLoc) {
        return new Location(this.x + addLoc.x, this.y + addLoc.y);
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
}
