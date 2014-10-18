package org.coder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;

public class TestScanner {
    private static final byte[] CONTENT = "mock class content".getBytes(Charsets.UTF_8);
    private static final String INNER_JAR_NAME = "inner_jar";
    private static final String OUTER_JAR_NAME = "outer_jar";

    @Test
    public void testScanJar() throws MalformedURLException, URISyntaxException, IOException {
        String[] innerClassnames = new String[] {"InnerString", "InnerArray"};
        String[] outerClassnames = new String[] {"OuterString", "OuterArray"};
        File jarFile = createNestedJar(innerClassnames, outerClassnames);
        Classes classes = Classes.from(jarFile.toURI().toURL());
        ImmutableSet<String> classNames = classes.getAllClasses();
        for (String name : innerClassnames) {
            Assert.assertTrue(classNames.contains(name));
        }
        for (String name : outerClassnames) {
            Assert.assertTrue(classNames.contains(name));
        }
    }

    File createNestedJar(String[] outClassNames, String[] innerClassnames) throws IOException {
        File outJar = File.createTempFile(OUTER_JAR_NAME, Classes.JAR_PACKAGE_EXTENSION);
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(outJar));

        File innerJar = createJar(INNER_JAR_NAME, innerClassnames);
        FileInputStream in = new FileInputStream(innerJar);

        try {
            jos.putNextEntry(new JarEntry(INNER_JAR_NAME + Classes.JAR_PACKAGE_EXTENSION));
            int avaliable = in.available();
            byte[] buffer = new byte[avaliable];
            while (true) {
                int nRead = in.read(buffer, 0, buffer.length);
                if (nRead <= 0) {
                    break;
                }
                jos.write(buffer, 0, nRead);
            }
            jos.closeEntry();

            for (String name : outClassNames) {
                writeEntry(jos, name + Classes.CLASS_NAME_EXTENSION);
            }
        } finally {
            in.close();
            jos.close();
        }

        return outJar;
    }

    void writeEntry(JarOutputStream jos, String entryName) throws IOException {
        jos.putNextEntry(new JarEntry(entryName));
        jos.write(CONTENT, 0, CONTENT.length);
        jos.closeEntry();
    }

    File createJar(String jarName, String... classNames) throws IOException {
        File file = File.createTempFile(jarName, Classes.JAR_PACKAGE_EXTENSION);
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(file));
        for (String name : classNames) {
            writeEntry(jos, name + Classes.CLASS_NAME_EXTENSION);
        }
        jos.close();
        return file;
    }
}
