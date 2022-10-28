package fr.lernejo.umlgrapher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MermaidFormatter {

    public String format(InternalGraphRepresentation graphRepresentation) {
        return mermaidUmlTypeRepresentation(graphRepresentation.getUmlType().getListOfClass()) + mermaidUmlRelationRepresentation(graphRepresentation.getUmlRelation().getRelationList());
    }

    private String mermaidUmlTypeRepresentation(Set<Class> classlist) {
        String mermaidSyntax = "classDiagram\n";
        for (Class classElement : classlist) {
            mermaidSyntax += "class " + classElement.getSimpleName();
            String fieldString = mermaidFieldRepresentation(classElement);
            String methodString = mermaidMethodRepresentation(classElement);
            boolean check = !(fieldString + methodString).equals("") || Modifier.isInterface(classElement.getModifiers());
            if (check) mermaidSyntax += " {\n";
            if (Modifier.isInterface(classElement.getModifiers())) {
                mermaidSyntax +="    <<interface>>\n";
                check = true;
            }
            mermaidSyntax += fieldString;
            mermaidSyntax += methodString;
            if (check) mermaidSyntax += "}";
            mermaidSyntax += "\n";
        }
        return mermaidSyntax;
    }

    private String mermaidUmlRelationRepresentation(List<String[]> relationList) {
        String mermaidSyntax = "";
        for (String[] relation : relationList) {
            mermaidSyntax += relation[0];
            if (relation[2].equals("implements")) mermaidSyntax += " <|.. ";
            else if (relation[2].equals("extends")) mermaidSyntax += " <|-- ";
            else mermaidSyntax += " <-- ";
            mermaidSyntax += relation[1] + " : " + relation[2];
            mermaidSyntax += "\n";
        }
        return mermaidSyntax;
    }

    private String mermaidFieldRepresentation(Class classElement) {
        String mermaidSyntax = "";
        for (Field field : classElement.getDeclaredFields()) {
            if (!field.isSynthetic()) {
                mermaidSyntax += "    " + (Modifier.isPrivate(field.getModifiers()) ? "-" : "+");
                mermaidSyntax += field.getType().getSimpleName() + " " + field.getName();
                mermaidSyntax += (Modifier.isStatic(field.getModifiers()) ? "$" : "") + "\n";
            }
        }
        return mermaidSyntax;
    }

    private String mermaidMethodRepresentation(Class classElement) {
        String mermaidSyntax = "";
        for (Method method : classElement.getDeclaredMethods()) {
            if (!method.isSynthetic()) {
                mermaidSyntax += "    " + (Modifier.isPrivate(method.getModifiers()) ? "-" : "+");
                mermaidSyntax += method.getName() + "(";
                ArrayList<String> parameterList = new ArrayList<>();
                for (Parameter parameter : method.getParameters()) {
                    parameterList.add(parameter.getType().getSimpleName() + " " + parameter.getName());
                }
                mermaidSyntax += parameterList.stream().collect(Collectors.joining(",")) + ")";
                mermaidSyntax += (Modifier.isStatic(method.getModifiers()) ? "$"
                    : Modifier.isAbstract(method.getModifiers()) ? "*" : "") + " ";
                mermaidSyntax += method.getReturnType().getSimpleName() + "\n";
            }
        }
        return mermaidSyntax;
    }
}
