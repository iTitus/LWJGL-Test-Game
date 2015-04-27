package main.game.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ReflectionUtil {

    public static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (String path : getPathesFromClasspath()) {
            File fileOrDir = new File(path);
            if (fileOrDir.isDirectory()) {
                classes.addAll(getClassesFromDir(fileOrDir, packageName));
            }
            if (fileOrDir.isFile()) {
                String name = fileOrDir.getName().toLowerCase();
                if (name.endsWith(".jar") || name.endsWith(".zip")) {
                    classes.addAll(getClassesFromJarOrZip(fileOrDir, packageName));
                }
            }
        }
        return classes;
    }

    public static List<Class<?>> getClassesFromDir(File dir, String packageName) {
        if (packageName == null) {
            packageName = "";
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        File dirSearched = new File(dir.getPath() + File.separator + packageName.replace(".", "/"));
        if (dirSearched.isDirectory()) {
            getClassesFromFileOrDirIntern(true, dirSearched, packageName, classes);
        }
        return classes;
    }

    private static void getClassesFromFileOrDirIntern(boolean first, File fileOrDir, String packageName, List<Class<?>> classes) {
        if (fileOrDir.isDirectory()) {
            if (!first) {
                packageName = (packageName + "." + fileOrDir.getName()).replaceAll("^\\.", "");
            }
            for (String subFileOrDir : fileOrDir.list()) {
                getClassesFromFileOrDirIntern(false, new File(fileOrDir, subFileOrDir), packageName, classes);
            }
        } else {
            if (fileOrDir.getName().toLowerCase().endsWith(".class")) {
                String classFile = fileOrDir.getName();
                classFile = packageName + "." + classFile.substring(0, classFile.length() - ".class".length());
                try {
                    Class<?> clazz = Class.forName(classFile);
                    classes.add(clazz);
                } catch (Throwable t) {
                    Logger.logThrowable("Unable to get class '" + classFile + "'", t);
                }
            }
        }
    }

    public static List<Class<?>> getClassesFromJarOrZip(File jarOrZipFile, String packageName) {
        if (packageName == null) {
            packageName = "";
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String dirSearched = packageName.replace(".", "/");
        try (ZipFile zipFile = new ZipFile(jarOrZipFile)) {

            for (Enumeration<? extends ZipEntry> zipEntries = zipFile.entries(); zipEntries.hasMoreElements();) {
                String entryName = zipEntries.nextElement().getName();
                if (!entryName.startsWith(dirSearched) || !entryName.toLowerCase().endsWith(".class")) {
                    continue;
                }
                entryName = entryName.substring(0, entryName.length() - ".class".length());
                entryName = entryName.replace("/", ".");
                try {
                    Class<?> clazz = Class.forName(entryName);
                    classes.add(clazz);
                } catch (Throwable t) {
                    Logger.logThrowable("Unable to get class '" + entryName + "' from zip or jar '" + jarOrZipFile + "'", t);
                }
            }
        } catch (Throwable t) {
            Logger.logThrowable("Unable to read from jar or zip '" + jarOrZipFile + "'", t);
        }
        return classes;

    }

    public static Method getMethod(Class<?> cls, String methodName, Class<?>... args) {
        try {
            return cls.getDeclaredMethod(methodName, args);
        } catch (Throwable t) {
            Logger.logThrowable("Unable to get method '" + cls + "." + methodName + "'", t);
            return null;
        }
    }

    public static List<Method> getMethodInClassWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationClass, int... modifiers) {
        List<Method> methods = new ArrayList<Method>();
        for (Method m : cls.getDeclaredMethods()) {
            if (m.isAnnotationPresent(annotationClass) && hasAllModifiers(m, modifiers)) {
                methods.add(m);
            }
        }
        return methods;
    }

    public static Method getMethodSilently(Class<?> cls, String methodName, Class<?>... args) {
        try {
            return cls.getDeclaredMethod(methodName, args);
        } catch (Throwable t) {
            return null;
        }
    }

    public static List<Method> getMethodsInClassesWithAnnotation(ArrayList<Class<?>> classes, Class<? extends Annotation> annotationClass, int... modifiers) {
        List<Method> methods = new ArrayList<Method>();
        for (Class<?> cls : classes) {
            methods.addAll(getMethodInClassWithAnnotation(cls, annotationClass, modifiers));
        }
        return methods;
    }

    public static List<String> getPathesFromClasspath() {
        StringTokenizer tokenizer = new StringTokenizer(System.getProperty("java.class.path"), System.getProperty("path.separator"));
        List<String> pathes = new ArrayList<String>();
        while (tokenizer.hasMoreElements()) {
            pathes.add(tokenizer.nextToken());
        }
        return pathes;
    }

    public static boolean hasAllModifiers(Method m, int... modifiers) {
        boolean ret = true;
        for (int mod : modifiers) {
            if ((mod & m.getModifiers()) == 0) {
                ret = false;
            }
        }
        return ret;
    }

    public static Void invoke(Object o, Method m, Object... args) {
        return invokeWithReturn(o, m, args);
    }

    public static void invokeStatic(Method m, Object... args) {
        invokeStaticWithReturn(m, args);
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeStaticWithReturn(Method m, Object... args) {
        try {
            return (T) m.invoke(null, args);
        } catch (Throwable t) {
            Logger.logThrowable("Unable to invoke method '" + m + "'", t);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeWithReturn(Object o, Method m, Object... args) {
        try {
            return (T) m.invoke(o, args);
        } catch (Throwable t) {
            Logger.logThrowable("Unable to invoke method '" + m + "'", t);
            return null;
        }
    }

    public static <T> T newInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Throwable t) {
            Logger.logThrowable("Unable to instantiate class '" + cls + "'", t);
            return null;
        }
    }

    public static <T> T newInstance(Class<T> cls, Object... args) {
        try {
            Class<?>[] clazzes = new Class<?>[args.length];
            for (int i = 0; i < clazzes.length; i++) {
                clazzes[i] = args[i].getClass();
            }
            return cls.getConstructor(clazzes).newInstance(args);
        } catch (Throwable t) {
            Logger.logThrowable("Unable to instantiate class '" + cls + "'", t);
            return null;
        }
    }

    private ReflectionUtil() {
        // NO-OP
    }
}
