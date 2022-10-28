package fr.lernejo.umlgrapher;

public class InternalGraphRepresentation {

    private final UmlRelation relation;
    private final UmlType type;

    public InternalGraphRepresentation(Class[] classes) {
        this.type = new UmlType(classes);
        this.relation = new UmlRelation(this.type);
    }

    public UmlRelation getRelation() {
        return this.relation;
    }

    public UmlType getType() {
        return this.type;
    }
}
