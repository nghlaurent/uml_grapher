package fr.lernejo.umlgrapher;

public class UmlGraph {

    private final Class[] classList;

    public UmlGraph(Class[] classList) {
        this.classList = classList;
    }

    public String as(GraphType graphType) {
        String result = "";
        if (graphType == GraphType.Mermaid) {
            try {
                result = new MermaidFormatter().format(new InternalGraphRepresentation(this.classList));
            } catch (RuntimeException exception) {
                System.out.println("Error: " + exception.getClass() + " - " + exception.getMessage());
            }
        }
        return result;
    }
}
