package fr.lernejo.umlgrapher;

public class UmlGraph {
    public UmlGraph(Class<?> machinClass) {
    }

    public String as(GraphType type) {
        return """
            classDiagram
            class Machin {
                <<interface>>
            }
            """;
    }
}
