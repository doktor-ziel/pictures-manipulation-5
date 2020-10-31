package pl.backlog.green;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "Test02",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Simple program to show capabilities of picocli library"
)
public class Test02 implements Callable<Integer> {
    @CommandLine.Option(names = "-a", description = "first option without value, not mandatory")
    boolean simpleClusteredOption;

    @CommandLine.Option(names = "-b", description = "second option with value, not mandatory")
    String value01;

    @CommandLine.Option(names = "-c", paramLabel = "INT", description = "second option with value, not mandatory")
    int value02;

    @Override
    public Integer call() {
        if (simpleClusteredOption) {
            System.out.println("Podałeś opcję 'a'");
        } else {
            System.out.println("Nie podałeś opcji 'a'");
        }
        System.out.println("Wartość 'b': " + value01);
        System.out.println("Wartość 'c': " + value02);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Test02()).execute(args);
        System.exit(exitCode);
    }
}
