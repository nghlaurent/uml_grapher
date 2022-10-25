package fr.lernejo.umlgrapher;

public class UmlGraph {

    private final Class[] classes;

    public UmlGraph(Class[] classes) {
        this.classes = classes;
    }

    public String as(GraphType graphType) {
        return """
        classDiagram
        class Machin {
            <<interface>>
        }
        """;
    }
}
