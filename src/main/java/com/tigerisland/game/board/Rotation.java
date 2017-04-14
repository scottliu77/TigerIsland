package com.tigerisland.game.board;

public final class Rotation {

    public static Location calculateRotation(int rotation) {
        int xShift=0;
        int yShift=0;
        rotation %= 360;
        switch(rotation){
            case 0:
                xShift = 1;
                yShift = 0;
                break;
            case 60:
                xShift = 0;
                yShift = 1;
                break;
            case 120:
                xShift = -1;
                yShift = 1;
                break;
            case 180:
                xShift = -1;
                yShift = 0;
                break;
            case 240:
                xShift = 0;
                yShift = -1;
                break;
            case 300:
                xShift = 1;
                yShift = -1;
                break;

        }

        return new Location(xShift, yShift);
    }

}
