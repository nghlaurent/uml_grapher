package fr.lernejo.umlgrapher;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class Launcher implements Callable<Integer> {

    @Option(names = {"-c", "--classes"}, description = "Will make it possible to inform the classes from which to start the analysis.")
    private final Class[] classes = null;

    @Option(names = {"-g", "--graph-type"}, description = "Will allow you to select the type of graph you want as output")
    private final GraphType graphType = GraphType.Mermaid;

    @Override
    public Integer call() {
        UmlGraph graph = new UmlGraph(this.classes);

        String output = graph.as(this.graphType);

        System.out.println(output);

        return 0;
    }

    public static void main(String... args) {
        System.exit(new CommandLine(new Launcher()).execute(args));
    }
}
