package com.tigerisland.client;

import com.tigerisland.game.board.Location;

public final class Adapter {

    public static int convertOrientationToDegrees(int code) {
        int degrees = 0;
        switch (code) {
            case(1):
                degrees = 60;
                break;
            case(2):
                degrees = 0;
                break;
            case(3):
                degrees = 300;
                break;
            case(4):
                degrees = 240;
                break;
            case(5):
                degrees = 180;
                break;
            case(6):
                degrees = 120;
                break;
        }
        return degrees;
    }

    public static int convertOrientationToCode(int degrees) {
        int code = 0;
        switch (degrees) {
            case(0):
                code = 2;
                break;
            case(60):
                code = 1;
                break;
            case(120):
                code = 6;
                break;
            case(180):
                code = 5;
                break;
            case(240):
                code = 4;
                break;
            case(300):
                code = 3;
                break;
        }
        return code;
    }

    public static Location convertLocationCubeToAxial(Location cubeLoc) {
        int x = cubeLoc.y * -1;
        int y = cubeLoc.z * -1;
        return new Location(x, y);
    }

    public static Location convertLocationAxialToCube(Location axialLoc) {
        int y = axialLoc.x * -1;
        int z = axialLoc.y * -1;
        int x = (-y) - z;
        return new Location(x, y, z);
    }
}
