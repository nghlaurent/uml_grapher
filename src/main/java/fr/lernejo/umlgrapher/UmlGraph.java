package fr.lernejo.umlgrapher;

public class UmlGraph {

    private final Class[] classes;

    public UmlGraph(Class[] classes) {
        this.classes = classes;
    }

    public String as(GraphType graphType) {
        String result = "";

        if (graphType == GraphType.Mermaid) {
            InternalGraphRepresentation graph = new InternalGraphRepresentation(this.classes);
            result = new MermaidFormatter().format(graph);
        }

        return result;
    }
}
