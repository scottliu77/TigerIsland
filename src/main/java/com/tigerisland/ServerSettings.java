package com.tigerisland;

public class ServerSettings {

    public final static String END_CODE = "END";

    public final static Boolean defaultOffline = true;

    public final static String defaultIPaddress = "localhost";
    public final static int defaultPort = 6539;
    public final static String defaultUsername = "username";
    public final static String defaultPassword = "password";

    public final String IPaddress;
    public final int port;
    public final String username;
    public final String password;


    public final Boolean offline;
    public static Boolean localServerRunning;

    public ServerSettings() {
        this.IPaddress = defaultIPaddress;
        this.port = defaultPort;
        this.username = defaultUsername;
        this.password = defaultPassword;

        this.offline = defaultOffline;
        this.localServerRunning = true;
    }

    public ServerSettings(String IPaddress, int port, String username, String password, Boolean localServerRunning) {
        this.IPaddress = IPaddress;
        this.port = port;
        this.username = username;
        this.password = password;

        this.offline = localServerRunning;
        this.localServerRunning = localServerRunning;
    }

}
