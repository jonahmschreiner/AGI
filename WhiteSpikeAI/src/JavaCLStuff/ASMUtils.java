package JavaCLStuff;


import static JavaCLStuff.Opcodes.ACC_PUBLIC;
import static JavaCLStuff.Opcodes.ALOAD;
import static JavaCLStuff.Opcodes.ASM4;
import static JavaCLStuff.Opcodes.INVOKESPECIAL;
import static JavaCLStuff.Opcodes.RETURN;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import JavaCLStuff.Pointer;
import JavaCLStuff.ClassReader;
import JavaCLStuff.ClassVisitor;
import JavaCLStuff.ClassWriter;
import JavaCLStuff.FieldVisitor;
import JavaCLStuff.MethodVisitor;

public class ASMUtils {

    public static String typeDesc(java.lang.reflect.Type t) {
        if (t instanceof Class) {
            Class c = (Class) t;
            if (c == Pointer.class) {
                return "Pointer";
            }
            if (c.isPrimitive()) {
                String s = c.getSimpleName();
                return Character.toUpperCase(s.charAt(0)) + s.substring(1);
            } else if (c.isArray()) {
                return typeDesc(c.getComponentType()) + "Array";
            }
            return c.getName().replace('.', '_');
        } else {
            ParameterizedType p = (ParameterizedType) t;
            StringBuilder b = new StringBuilder(typeDesc(p.getRawType()));
            for (java.lang.reflect.Type pp : p.getActualTypeArguments()) {
                b.append("_").append(typeDesc(pp));
            }
            return b.toString();
        }
    }

    public static void addSuperCall(ClassVisitor cv, String superClassInternalName) {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, superClassInternalName, "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    public static <T> Class<? extends T> createSubclassWithSynchronizedNativeMethodsAndNoStaticFields(Class<T> original, ClassDefiner classDefiner) throws IOException {
        String suffix = "$SynchronizedNative";
        final String originalInternalName = JNIUtils.getNativeName(original);
        final String synchronizedName = original.getName() + suffix;
        final String synchronizedInternalName = originalInternalName + suffix;

        ClassWriter classWriter = new ClassWriter(0);
        //TraceClassVisitor traceVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out));
        ClassVisitor cv = new ClassVisitor(ASM4, classWriter) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, synchronizedInternalName, null, originalInternalName, new String[0]);
                addSuperCall(cv, originalInternalName);
            }

            @Override
            public void visitInnerClass(String name, String outerName, String innerName, int access) {
                // Do nothing.
            }

            @Override
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                return null;
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if (!Modifier.isNative(access)) {
                    return null;
                }

                return super.visitMethod(access | Modifier.SYNCHRONIZED, name, desc, signature, exceptions);
            }
        };

        ClassReader classReader = new ClassReader(original.getName());
        classReader.accept(cv, 0);
        return (Class<? extends T>) classDefiner.defineClass(synchronizedName, classWriter.toByteArray());
    }
}
