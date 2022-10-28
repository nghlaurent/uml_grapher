package fr.lernejo.umlgrapher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UmlRelation {

    private final List<String[]> relationsList = new ArrayList<>();

    public UmlRelation(UmlType type) {
        getAllRelation(type);
    }

    public void getAllRelation(UmlType uType) {
        for (Class classe : uType.getTypeList()) {
            getSuperClassRelation(classe);
            getInstancesRelation(classe);
            getMethodRelation(classe);
            getFieldRelation(classe);
        }
    }

    private void getSuperClassRelation(Class classe) {
        Class superClass = classe.getSuperclass();

        if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            String[] tab = {superClass.getSimpleName(), classe.getSimpleName(), "extends"};
            this.relationsList.add(tab);
        }
    }

    private void getInstancesRelation(Class classe) {
        String type;

        for (Class inter : classe.getInterfaces()) {
            if (!Modifier.isInterface(classe.getModifiers())) {
                type = "implements";
            } else {
                type = "extends";
            }

            String[] tab = {inter.getSimpleName(), classe.getSimpleName(), type};

            this.relationsList.add(tab);
        }
    }

    private void getMethodRelation(Class classe) {
        for (Method method : classe.getDeclaredMethods()) {
            if (!method.getReturnType().getName().startsWith("java.") && !method.getReturnType().getName().equals("void") && !method.isSynthetic()) {
                String[] tab = {method.getReturnType().getSimpleName(), classe.getSimpleName(), "returns"};
                this.relationsList.add(tab);
            }
        }
    }
    
    private void getFieldRelation(Class classe) {
        for (Field field : classe.getDeclaredFields()) {
            if (!field.getType().getName().startsWith("java.") && !field.isSynthetic()) {
                String[] tab = {field.getType().getSimpleName(), classe.getSimpleName(), "returns"};

                if (this.relationsList.stream().filter(t -> Arrays.deepEquals(t, tab)).toList().isEmpty()) {
                    tab[2] = "uses";
                    this.relationsList.add(tab);
                }
            }
        }
    }

    public List<String[]> getRelationsList() {
        return this.relationsList;
    }
}
