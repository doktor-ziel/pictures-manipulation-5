package pl.backlog.green;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "Test04",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Simple program to show capabilities of picocli library"
)
public class Test04 implements Callable<Integer> {
    @CommandLine.Option(names = "-a", required = true, description = "first option without value, mandatory")
    boolean simpleClusteredOption;

    @CommandLine.Option(names = "-b", description = "second option with value, not mandatory")
    String value01;

    @CommandLine.Option(names = "-c", required = true, paramLabel = "INT", description = "second option with value, mandatory")
    String value02;

    @CommandLine.Option(names = {"-p", "--path"}, required = true, paramLabel = "PATH", description = "second option with value, not mandatory")
    Path path;

    @Override
    public Integer call() {
        if (simpleClusteredOption) {
            System.out.println("Podałeś opcję 'a'");
        } else {
            System.out.println("Nie podałeś opcji 'a'");
        }
        System.out.println("Wartość 'b': " + value01);
        System.out.println("Wartość 'c': " + value02);
        System.out.println("Wartość 'p': " + path.toString());
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Test04()).execute(args);
        System.exit(exitCode);
    }
}
