package fr.lernejo.umlgrapher;

public class InternalGraphRepresentation {

    private final UmlType umlType;
    private final UmlRelation umlRelation;

    public InternalGraphRepresentation(Class[] classList) {
        this.umlType = new UmlType(classList);
        this.umlRelation = new UmlRelation(this.umlType);
    }

    public UmlType getUmlType() {
        return this.umlType;
    }

    public UmlRelation getUmlRelation() {
        return this.umlRelation;
    }
}
