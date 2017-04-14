package com.tigerisland.settings;

public class ServerSettings {

    public final static String END_CODE = "END";

    public final static Boolean defaultOffline = true;

    public final static String defaultIPaddress = "localhost";
    public final static int defaultPort = 6539;

    public final static String defaultTournamentPassword = "tournamentPassword";
    public final static String defaultUsername = "username";
    public final static String defaultPassword = "password";

    public final String IPaddress;
    public final int port;
    public final String tournamentPassword;
    public final String username;
    public final String password;

    private String playerID = null;
    private String opponentID = null;

    public final Boolean offline;
    public static Boolean localServerRunning;

    public ServerSettings() {
        this.IPaddress = defaultIPaddress;
        this.port = defaultPort;
        this.tournamentPassword = defaultTournamentPassword;
        this.username = defaultUsername;
        this.password = defaultPassword;

        this.offline = defaultOffline;
        this.localServerRunning = true;
    }

    public ServerSettings(String IPaddress, int port, String tournamentPassword, String username, String password, Boolean offline) {
        this.IPaddress = IPaddress;
        this.port = port;

        this.tournamentPassword = tournamentPassword;
        this.username = username;
        this.password = password;

        this.offline = offline;
        this.localServerRunning = offline;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setOpponentID(String opponentID) {
        this.opponentID = opponentID;
    }

    public String getOpponentID() {
        return opponentID;
    }
}
