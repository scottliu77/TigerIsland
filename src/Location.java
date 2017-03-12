import java.util.*;

public class Location{
    int x;
    int y;

    public Location(int x,int y){
        this.x = x;
        this.y = y;
    }
    public Location(Location loc){
        this.x = loc.x;
        this.y = loc.y;
    }

    public static Location add(Location l1, Location l2){
        return new Location(l1.x+l2.x, l1.y+l2.y);
    }

    public boolean greaterThan(Location otherLoc){
        if(this.y>otherLoc.y)
            return true;
        if(this.y==otherLoc.y && this.x>otherLoc.x)
            return true;
        return false;
    }

    public boolean lessThan(Location otherLoc){
        if(this.y<otherLoc.y)
            return true;
        if(this.y==otherLoc.y && this.x<otherLoc.x)
            return true;
        return false;
    }

    public boolean equalTo(Location otherLoc){
        if(this.y==otherLoc.y && this.x==otherLoc.x)
            return true;
        return false;
    }

    public static void printLocations(ArrayList<Location> list){
        for(int ii=0;ii<list.size();ii++){
            System.out.println("X="+Integer.toString(list.get(ii).x)+" : Y="+Integer.toString(list.get(ii).y));
        }
    }
}
