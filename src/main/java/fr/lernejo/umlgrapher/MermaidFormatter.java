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
        return mermaidTypeRepresentation(graphRepresentation.getType().getTypeList()) + mermaidRelationRepresentation(graphRepresentation.getRelation().getRelationsList());
    }

    private String mermaidTypeRepresentation(Set<Class> classes) {
        String mermaidSyntax = "classDiagram\n";
        for (Class classe : classes) {
            mermaidSyntax += "class " + classe.getSimpleName();
            String fieldString = mermaidFieldRepresentation(classe);
            String methodString = mermaidMethodRepresentation(classe);
            boolean check = !(fieldString + methodString).equals("") || Modifier.isInterface(classe.getModifiers());
            if (check) mermaidSyntax +=" {\n";
            if (Modifier.isInterface(classe.getModifiers())) {
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

    private String mermaidRelationRepresentation(List<String[]> relations) {
        String mermaidSyntax = "";
        for (String[] relation : relations) {
            mermaidSyntax += relation[0];
            if (relation[2].equals("implements")) {
                mermaidSyntax += " <|.. ";
            } else if (relation[2].equals("extends")) {
                mermaidSyntax += " <|-- ";
            } else {
                mermaidSyntax += " <-- ";
                mermaidSyntax += relation[1] + " : " + relation[2];
                mermaidSyntax += "\n";
            }
        }
        return mermaidSyntax;
    }

    private String mermaidFieldRepresentation(Class classe) {
        String mermaidSyntax = "";
        for (Field field : classe.getDeclaredFields()) {
            if (!field.isSynthetic()) {
                mermaidSyntax += "    " + (Modifier.isPrivate(field.getModifiers()) ? "-" : "+");
                mermaidSyntax += field.getType().getSimpleName() + " " + field.getName();
                mermaidSyntax += (Modifier.isStatic(field.getModifiers()) ? "$" : "") + "\n";
            }
        }
        return mermaidSyntax;
    }

    private String mermaidMethodRepresentation(Class classe) {
        String mermaidSyntax = "";
        for (Method method : classe.getDeclaredMethods()) {
            if (!method.isSynthetic()) {
                mermaidSyntax += "    " + (Modifier.isPrivate(method.getModifiers()) ? "-" : "+");
                mermaidSyntax += method.getName() + "(";
                ArrayList<String> params = new ArrayList<>();
                for (Parameter parameter : method.getParameters()) {
                    params.add(parameter.getType().getSimpleName() + " " + parameter.getName());
                }
                mermaidSyntax += params.stream().collect(Collectors.joining(",")) + ")";
                mermaidSyntax += (Modifier.isStatic(method.getModifiers()) ? "$" : Modifier.isAbstract(method.getModifiers()) ? "*" : "") + " ";
                mermaidSyntax += method.getReturnType().getSimpleName() + "\n";
            }
        }
        return mermaidSyntax;
    }
}
