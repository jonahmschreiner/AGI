package JavaCLStuff;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import JavaCLStuff.Struct;

/**
 * Interface for type customizers that can be used to perform platform-specific
 * type adjustments or other hacks.<br>
 * A type customizer can be specified with {@link Struct#customizer() }.<br>
 * Each implementation must have a default constructor, and an unique instance
 * of each implementation class will be cached by {@link StructCustomizer#getInstance(java.lang.Class)
 * }.
 *
 * @deprecated The StructIO API is subject to future changes. Use this with care
 * and be prepared to migrate your code...
 */
@Deprecated
public class StructCustomizer {

    /**
     * Last chance to remove field declarations
     */
    public void beforeAggregation(StructDescription desc, List<StructFieldDeclaration> fieldDecls) {
    }

    /**
     * Last chance to remove aggregated fields
     */
    public void beforeLayout(StructDescription desc, List<StructFieldDescription> aggregatedFields) {
    }

    /**
     * This method can alter the aggregated fields and may even call again the
     * performLayout(aggregatedFields) method. This is before field offsets and
     * sizes are propagated to field declarations.
     */
    public void afterLayout(StructDescription desc, List<StructFieldDescription> aggregatedFields) {
    }

    /**
     * Called after everything is setup in the StructIO.<br>
     * It is the most dangerous callback, here it's advised to only call the
     * prependBytes, appendBytes and setFieldOffset methods.
     */
    public void afterBuild(StructDescription desc) {
    }
    private static StructCustomizer dummyCustomizer = new StructCustomizer();
    private static ConcurrentHashMap<Class<?>, StructCustomizer> customizers = new ConcurrentHashMap<Class<?>, StructCustomizer>();

    static StructCustomizer getInstance(Class<?> structClass) {
        StructCustomizer c = customizers.get(structClass);
        if (c == null) {
            Struct s = structClass.getAnnotation(Struct.class);
            if (s != null) {
                Class<? extends StructCustomizer> customizerClass = s.customizer();
                if (customizerClass != null && customizerClass != StructCustomizer.class) {
                    try {
                        c = customizerClass.newInstance();
                    } catch (Throwable th) {
                        throw new RuntimeException("Failed to create customizer of class " + customizerClass.getName() + " for struct class " + structClass.getName() + " : " + th, th);
                    }
                }
            }
            if (c == null) {
                c = dummyCustomizer;
            }
            StructCustomizer existingConcurrentCustomizer =
                    customizers.putIfAbsent(structClass, c);
            if (existingConcurrentCustomizer != null) {
                return existingConcurrentCustomizer;
            }
        }
        return c;
    }
}
