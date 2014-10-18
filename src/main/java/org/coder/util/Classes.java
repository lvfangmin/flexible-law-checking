package org.coder.util;

import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Currently guava's @{link ClassPath} cannot handle nested jar, this is the reason why we
 * implement this util.
 *
 * This class mirrors from guava's @{link ClassPath}, most of the part are same, except:
 *
 * <ul>
 * <li> 1. Support scanning from nested Jar files. This is needed in API module, which is an
 *         OSGI bundle, all the dependencies are nested in it. </li>
 * <li> 2. With simplify features, actually we don't want to re-invent the wheel, so finally
 *         this class will be replaced by guava's @{link ClassPath} when the @Beta
 *         @{Code ClassPath} become more mature and powerful.
 *         Here we only want to maintain a simplified one.</li>
 * </ul>
 */
public class Classes {

    private static final Logger logger = LoggerFactory.getLogger(Classes.class);

    public static final String CLASS_NAME_EXTENSION = ".class";
    public static final String JAR_PACKAGE_EXTENSION = ".jar";
    public static final char INNER_CLASS_PREFIX = '$';

    // check whether the class is top level
    private static final Predicate<String> IS_TOP_LEVEL = new Predicate<String>() {
        @Override
        public boolean apply(String className) {
            return className.indexOf(INNER_CLASS_PREFIX) == -1;
        }
    };

    private final ImmutableSet<String> resources;

    private Classes(final ImmutableSet<String> resources) {
        this.resources = resources;
    }

    /**
     * Returns a {@code Classes} representing all class in url.
     *
     * @param url the location to load classes from
     * @return
     * @throws URISyntaxException  if this URL is not formatted strictly according
     *      to RFC2396 and cannot be converted to a URI.
     * @throws IOException if the attempt to read class path resources failed.
     */
    public static Classes from(final URL url) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(url);
        scanner.scan();
        return new Classes(scanner.getResources());
    }

    /**
     * Returns all top level classes whose package name is {@code packageName} or
     * starts with {@code packageName} followed by a '.'.
     *
     * @param packageName
     * @return
     */
    public Set<String> getTopLevelClassesRecursive(final String packageName) {
        String packagePrefix = packageName + '.';
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (String cn : Sets.filter(resources, IS_TOP_LEVEL)) {
            if (cn.startsWith(packagePrefix)) {
                builder.add(cn);
            }
        }
        return builder.build();
    }

    public ImmutableSet<String> getAllClasses() {
        return resources;
    }

    /**
     * Scan given path (directory or jar) to get all class resources on this path.
     */
    public static final class Scanner {
        private final ImmutableSet.Builder<String> resources = ImmutableSet.<String>builder();
        private final URL url;

        public Scanner(URL url) {
            this.url = url;
        }

        /**
         * Call @{code scanDirectory} or @{code scanJar} according to the url type.
         *
         * @throws URISyntaxException
         * @throws IOException
         */
        public void scan() throws URISyntaxException, IOException {
            File file = new File(url.toURI());
            if (!file.exists()) {
                logger.error("File {} not exist!", url);
                return;
            }

            if (file.isDirectory()) {
                scanDirectory(file, "", ImmutableSet.<File>of());
            } else {
                scanJar(file);
            }
        }

        public ImmutableSet<String> getResources() {
            return resources.build();
        }

        /**
         * Recursively scan the directory, the function has self-protection mechanism to
         * avoid infinite loop with cycle link.
         *
         * @param directory
         * @param packagePrefix
         * @param ancestors
         */
        private void scanDirectory(final File directory,
                                   final String packagePrefix,
                                   final ImmutableSet<File> ancestors) throws IOException {

            File canonical = directory.getCanonicalFile();
            if (ancestors.contains(canonical)) {
                // cycle reference, maybe encounter a symbolic link
                return;
            }
            File[] files = directory.listFiles();
            if (files == null) {
                logger.warn("Cannot read directory {}", directory);
                // IO error, just skip the directory
                return;
            }

            ImmutableSet<File> newAncestors = ImmutableSet.<File>builder()
                                                          .addAll(ancestors)
                                                          .add(canonical)
                                                          .build();
            for (File f : files) {
                String name = packagePrefix + f.getName();
                if (f.isDirectory()) {
                    scanDirectory(f, name + "/", newAncestors);
                } else {
                    if (name.endsWith(CLASS_NAME_EXTENSION)) {
                        resources.add(getNameWithoutExtension(name));
                    }
                }
            }
        }

        /**
         * Get the pure class name without extension.
         *
         * @param name
         * @return
         */
        private String getNameWithoutExtension(String name) {
            return name.substring(0, name.lastIndexOf(CLASS_NAME_EXTENSION)).replaceAll("/", ".");
        }

        /**
         * Scan jar class files without follow Class-Path.
         *
         * @param file
         * @throws IOException
         */
        private void scanJar(File file) throws IOException {
            JarFile jarFile;

            try {
                jarFile = new JarFile(file);
            } catch (IOException e) {
                logger.error("Error while open jar file", e);
                return;
            }

            try {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    readJarEntry(jarFile, entry);
                }
            } finally {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    // ignore
                    logger.warn("Error while close jar file", e);
                }
            }
        }

        /**
         * Follow nested jar entry if exist.
         *
         * NOTE: need to check cycle reference if there is the case.
         *
         * @param jarFile
         * @param entry
         * @throws IOException
         */
        private void readJarEntry(JarFile jarFile, JarEntry entry) throws IOException {
            String name = entry.getName();
            if (name.endsWith(CLASS_NAME_EXTENSION)) {
                resources.add(getNameWithoutExtension(name));
            } else if (name.endsWith(JAR_PACKAGE_EXTENSION)) {
                InputStream is = jarFile.getInputStream(entry);
                if (is == null) {
                    logger.warn("Read unavailable jar file, {}", name);
                    return;
                }
                JarInputStream jis = new JarInputStream(is);
                try {
                    JarEntry en = jis.getNextJarEntry();
                    while (en != null) {
                        readJarEntry(jarFile, en);
                        en = jis.getNextJarEntry();
                    }
                } finally {
                    jis.close();
                }
            }
        }
    }
}
