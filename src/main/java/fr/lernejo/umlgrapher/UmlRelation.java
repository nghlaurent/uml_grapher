package fr.lernejo.umlgrapher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UmlRelation {

    private final List<String[]> relationList = new ArrayList<>();

    public UmlRelation(UmlType umlType) {
        getAllRelation(umlType);
    }

    public void getAllRelation(UmlType umlType) {
        for (Class classElement : umlType.getListOfClass()) {
            getSuperClassRelation(classElement);
            getInstancesRelation(classElement);
            getMethodRelation(classElement);
            getFieldRelation(classElement);
        }
    }

    private void getSuperClassRelation(Class classElement) {
        Class superClass = classElement.getSuperclass();
        if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            this.relationList.add(new String[] {superClass.getSimpleName(), classElement.getSimpleName(), "extends"});
        }
    }

    private void getFieldRelation(Class classElement) {
        for (Field field : classElement.getDeclaredFields()) {
            if (!field.getType().getName().startsWith("java.") && !field.isSynthetic()) {
                String[] array = new String[] {field.getType().getSimpleName(), classElement.getSimpleName(), "returns"};
                if (this.relationList.stream().filter(t -> Arrays.deepEquals(t, array)).toList().isEmpty()) {
                    array[2] = "uses";
                    this.relationList.add(array);
                }
            }
        }
    }

    private void getInstancesRelation(Class classElement) {
        for (Class interfaceElement : classElement.getInterfaces()) {
            this.relationList.add(new String[] {interfaceElement.getSimpleName(), classElement.getSimpleName(), !Modifier.isInterface(classElement.getModifiers()) ? "implements" : "extends"});
        }
    }

    private void getMethodRelation(Class classElement) {
        for (Method method : classElement.getDeclaredMethods()) {
            if (!method.getReturnType().getName().startsWith("java.") && !method.getReturnType().getName().equals("void") && !method.isSynthetic()) {
                this.relationList.add(new String[] {method.getReturnType().getSimpleName(), classElement.getSimpleName(), "returns"});
            }
        }
    }

    public List<String[]> getRelationList() {
        return this.relationList;
    }
}
