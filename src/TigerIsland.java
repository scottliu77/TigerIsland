import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class TigerIsland {

    private Namespace parsedArguments;
    protected Boolean offline = false;

    public TigerIsland() {}

    protected void parseArguments(String[] args) throws ArgumentParserException {

        ArgumentParser parser = ArgumentParsers.newArgumentParser("Offline")
                .defaultHelp(true)
                .description("Toggle the system's offline testing mode.");
        parser.addArgument("-o", "--offline").type(Arguments.booleanType());

        this.parsedArguments = parser.parseArgs(args);

        this.offline = this.parsedArguments.get("offline");
    }

    private void run() {
        System.out.println("Ran system");
    }

    public static void main(String[] args) throws Exception {

        TigerIsland tigerIsland = new TigerIsland();

        try {
            tigerIsland.parseArguments(args);
        } catch (ArgumentParserException exception) {
            System.out.println(exception);
            return;
        }

        tigerIsland.run();

    }

}


