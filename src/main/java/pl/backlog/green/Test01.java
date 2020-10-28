package pl.backlog.green;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "Test01",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Simple program to show capabilities of picocli library"
)
public class Test01 implements Callable<Integer> {
    @CommandLine.Option(names = "-a", description = "first option without value, not mandatory")
    boolean simpleClusteredOption;

    @Override
    public Integer call() {
        if (simpleClusteredOption) {
            System.out.println("Podałeś opcję 'a'");
        } else {
            System.out.println("Nie podałeś opcji 'a'");
        }
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Test01()).execute(args);
        System.exit(exitCode);
    }
}
