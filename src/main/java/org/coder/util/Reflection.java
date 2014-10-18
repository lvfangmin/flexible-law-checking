package org.coder.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reflection {

    private static final Logger logger = LoggerFactory.getLogger(Reflection.class);

    /**
     * Scan the folder to find all classes with the given class and annotation type.
     *
     * @param folder the folder to scan, something like "org.coder.op"
     * @param scanClz the class type to scan
     * @param annotationClz the annotation type to scan
     *
     * @return the (Annotation -> Class) map
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws URISyntaxException
     */
    @SuppressWarnings("unchecked")
    public static <C, A extends Annotation> Map<A, Class<? extends C>> scan(String folder,
            Class<C> scanClz, Class<A> annotationClz)
            throws IOException, ClassNotFoundException, URISyntaxException {
        Map<A, Class<? extends C>> clz = new HashMap<A, Class<? extends C>>();

        // get resource path where contains the annotation class
        URL url = annotationClz.getProtectionDomain().getCodeSource().getLocation();
        logger.info("load classes from: {}", url);

        Classes classes = Classes.from(url);
        for (String className : classes.getTopLevelClassesRecursive(folder)) {
            Class<?> c = Class.forName(className);
            if (!scanClz.isAssignableFrom(c) || !c.isAnnotationPresent(annotationClz)) {
                continue;
            }
            A annotation = c.getAnnotation(annotationClz);
            clz.put(annotation, (Class<? extends C>)c);
        }
        return clz;
    }
}
