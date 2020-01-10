package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Codec;
import org.geekhub.crypto.exception.FileProcessingFailedException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassParser {
    private static final Pattern pattern = Pattern.compile("(org).*");
    private static final int INDEX_OF_DOT = 6;

    static final List<Class<?>> matchingClasses = getClasses();

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
            throw new FileProcessingFailedException(e.getMessage());
        }
        return annotatedClasses;
    }

    private static List<Class<?>> getClassFiles(File[] listOfFiles) {
        String pathDivider = "/";
        String packageDivider = ".";
        List<Class<?>> appropriateClasses = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    appropriateClasses.addAll(getClassFiles(listFiles));
                }
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
                    throw new FileProcessingFailedException(e.getMessage());
                }
            }
        }
        return appropriateClasses;
    }

    private static String cropFullClassName(String fullClassName) {
        return fullClassName.substring(0, fullClassName.length() - INDEX_OF_DOT);
    }
}