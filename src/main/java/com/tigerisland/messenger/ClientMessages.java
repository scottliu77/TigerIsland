package com.tigerisland.messenger;

import java.util.regex.Pattern;

public class ClientMessages {

    public static final Pattern enterTournamentPattern = Pattern.compile("ENTER THUNDERDOME \\w+");
    public static final Pattern authenticationPattern = Pattern.compile("I AM \\w+ \\w+");

    public static final Pattern gameIDPattern = Pattern.compile("GAME \\w+");
    public static final Pattern moveIDPattern = Pattern.compile("MOVE \\w+");
    public static final Pattern currentPlayerIDPattern = Pattern.compile("PLAYER \\w+");

    public static final Pattern placeTilePattern = Pattern.compile("PLACE(D)? \\w+[\\+ ]?\\w+ AT -?\\d+ -?\\d+ -?\\d+ -?\\d+");

    public static final Pattern foundSettlementPattern = Pattern.compile("FOUND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern expandSettlementPattern = Pattern.compile("EXPAND(ED)? SETTLEMENT AT -?\\d+ -?\\d+ -?\\d+ \\w+");
    public static final Pattern buildTotoroPattern = Pattern.compile("BUIL[DT] TOTORO SANCTUARY AT -?\\d+ -?\\d+ -?\\d+");
    public static final Pattern buildTigerPattern = Pattern.compile("BUIL[DT] TIGER PLAYGROUND AT -?\\d+ -?\\d+ -?\\d+");

    public static final Pattern clientLostUnableToBuildPattern = Pattern.compile("GAME \\w+ MOVE \\w+ UNABLE TO BUILD");

}
