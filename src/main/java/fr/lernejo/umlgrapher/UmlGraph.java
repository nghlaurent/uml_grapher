package fr.lernejo.umlgrapher;

public class UmlGraph {

    private final Class[] classes;

    public UmlGraph(Class[] classes) {
        this.classes = classes;
    }

    public String as(GraphType graphType) {
        String result = "";

        if (graphType == GraphType.Mermaid) {
            try {
                InternalGraphRepresentation graph = new InternalGraphRepresentation(classes);
                result = new MermaidFormatter().format(graph);
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getClass() + " - " + e.getMessage());
            }
        }

        return result;
    }
}
