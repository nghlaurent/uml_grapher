package fr.lernejo.umlgrapher;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class UmlType {

    private final Set<Class> typeList = new TreeSet<>(Comparator.<Class, String>comparing(Class::getSimpleName).thenComparing(Class::getPackageName));

    public UmlType(Class[] classList) {
        getAllClass(classList);
    }

    private void getAllClass(Class[] classList) {
        for (Class classElement : classList) {
            recursionSearch(classElement);
        }
    }

    private void getAllChild(Class classElement) {
        Reflections reflections;
        try {
            reflections = new Reflections(new ConfigurationBuilder().forPackage("").forPackage("", classElement.getClassLoader()));
        } catch (RuntimeException exception) {
            System.out.println("Fail class loader");
            return;
        }
        Set<Class<?>> subTypeList = reflections.get(Scanners.SubTypes.get(classElement).asClass(this.getClass().getClassLoader(), classElement.getClassLoader()));
        for (Class subTypeElement : subTypeList) {
            getAllChild(subTypeElement);
            if (!this.typeList.contains(subTypeElement)) this.typeList.add(subTypeElement);
        }
    }
    private void recursionSearch(Class classElement) {
        Class superClass = classElement.getSuperclass();
        if (superClass != null && !superClass.getSimpleName().equals("Object")) recursionSearch(superClass);

        for (Class interfaceElement : classElement.getInterfaces()) {
            recursionSearch(interfaceElement);
        }

        getAllChild(classElement);
        this.typeList.add(classElement);
    }

    public Set<Class> getListOfClass() {
        return this.typeList;
    }
}
