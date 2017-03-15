import java.util.*;

public class Location{
    protected int x;
    protected int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Location(Location loc){
        this.x = loc.x;
        this.y = loc.y;
    }

    public static Location rotateHexRight(Location loc, int rotation) {
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

    public ArrayList<Location> adjacentLocations(){
        ArrayList<Location> adjacentLoc = new ArrayList<Location>();
        Location firstLoc = new Location(x, y-1);
        Location secondLoc = new Location(x+1, y-1);
        Location thirdLoc = new Location(x-1, y+1);
        Location fourthLoc = new Location(x, y+1);
        Location fifthLoc = new Location(x-1, y);
        Location sixthLoc = new Location(x+1, y);
        adjacentLoc.add(firstLoc);
        adjacentLoc.add(secondLoc);
        adjacentLoc.add(thirdLoc);
        adjacentLoc.add(fourthLoc);
        adjacentLoc.add(fifthLoc);
        adjacentLoc.add(sixthLoc);
        return adjacentLoc;
    }
}
