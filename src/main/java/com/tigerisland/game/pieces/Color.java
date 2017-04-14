package com.tigerisland.game.pieces;

public enum Color {

    BLACK("Black"),
    WHITE("White"),
    RED("Red"),
    ORANGE("Orange"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLUE("Blue"),
    PURPLE("Purple");

    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColorString() {
        return color;
    }
}
