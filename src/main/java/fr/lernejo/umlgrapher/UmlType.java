package fr.lernejo.umlgrapher;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

public class UmlType {

    private final Set<Class> typeList = new TreeSet<>(Comparator.<Class, String>comparing(Class::getSimpleName).thenComparing(Class::getPackageName));

    public UmlType(Class[] classes) {
        getAllClass(classes);
    }

    private void getAllClass(Class[] classes) {
        for (Class classe : classes) {
            recursionSearch(classe);
        }
    }

    private void getAllChild(Class classe) {
        Reflections reflections;

        try {
            reflections = new Reflections(new ConfigurationBuilder().forPackage("").forPackage("", classe.getClassLoader()));
        } catch (RuntimeException e) {
            System.out.println("Fail class loader");
            return;
        }


        Set<Class<?>> subTypes = reflections.get(Scanners.SubTypes.get(classe).asClass(this.getClass().getClassLoader(), classe.getClassLoader()));

        for (Class classeType : subTypes) {
            if (!this.typeList.contains(classeType)) {
                this.typeList.add(classeType);
            }
        }
    }

    private void recursionSearch(Class classe) {
        Class superClass = classe.getSuperclass();

        if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            recursionSearch(superClass);
        }

        for (Class inter : classe.getInterfaces()) {
            recursionSearch(inter);
        }

        getAllChild(classe);
        this.typeList.add(classe);
    }

    public Set<Class> getTypeList() {
        return this.typeList;
    }
}
