package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Codec;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassParser {

    public static final List<Class<?>> matchingClasses = getClasses();

    private static List<Class<?>> getClasses() {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            List<URL> roots = Collections.list(classLoader.getResources(""));
            for (URL url : roots) {
                File root = new File(url.getPath());
                File[] listOfFiles = root.listFiles();
                if (listOfFiles != null) {
                    List<Class<?>> foundedClasses = getClassFiles(listOfFiles);
                    annotatedClasses.addAll(foundedClasses);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return annotatedClasses;
    }

    private static List<Class<?>> getClassFiles(File[] listOfFiles) {
        String pathDivider = "/";
        String packageDivider = ".";
        List<Class<?>> appropriateClasses = new ArrayList<>();
        Pattern pattern = Pattern.compile("(org).*");
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                appropriateClasses.addAll(getClassFiles(file.listFiles()));
            } else {
                try {
                    String fileName = file.getPath();
                    Matcher packageName = pattern.matcher(fileName);
                    if (packageName.find()) {
                        String name = packageName.group(0).replace(pathDivider, packageDivider);
                        name = cropFullClassName(name);
                        Class<?> classInstance = Class.forName(name);
                        Annotation annotation = classInstance.getAnnotation(Codec.class);
                        if (annotation != null) {
                            appropriateClasses.add(classInstance);
                        }
                    }
                } catch (ClassNotFoundException | IllegalStateException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return appropriateClasses;
    }

    private static String cropFullClassName(String fullClassName) {
        int indexOfDot = 6;
        return fullClassName.substring(0, fullClassName.length() - indexOfDot);
    }
}