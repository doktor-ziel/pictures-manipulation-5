package pl.backlog.green;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "Test05",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Simple program to show capabilities of picocli library"
)
public class Test05 implements Callable<Integer> {
    @CommandLine.Option(names = "-a", description = "first option without value, not mandatory")
    boolean simpleClusteredOption;

    @CommandLine.Option(names = "-b", required = true, description = "second option with value, mandatory")
    String value01;

    @CommandLine.Option(names = "-c", paramLabel = "INT", description = "second option with value, not mandatory")
    String value02;

    @CommandLine.Parameters(index = "0", paramLabel = "FILE", description = "argument of program")
    String value03;

    @Override
    public Integer call() {
        if (simpleClusteredOption) {
            System.out.println("Podałeś opcję 'a'");
        } else {
            System.out.println("Nie podałeś opcji 'a'");
        }
        System.out.println("Wartość 'b': " + value01);
        System.out.println("Wartość 'c': " + value02);
        System.out.println("Argument: " + value03);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Test05()).execute(args);
        System.exit(exitCode);
    }
}
