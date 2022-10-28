package fr.lernejo.umlgrapher;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class Launcher implements Callable<Integer> {

    @Option(names = {"-c", "--classes"}, description = "Fill in the classes used for the analysis.", required = true)
    private final Class[] classList = null;

    @Option(names = {"-g", "--graph-type"}, description = "Allows you to select the type of graph you want.")
    private final GraphType graphType = GraphType.Mermaid;

    @Override
    public Integer call() {
        UmlGraph umlGraph = new UmlGraph(this.classList);
        System.out.println(umlGraph.as(this.graphType));
        return 0;
    }

    public static void main(String... args) {
        try {
            System.exit(new CommandLine(new Launcher()).execute(args));
        } catch (RuntimeException exception) {
            exception.printStackTrace();
        }


    }
}
