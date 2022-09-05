package JavaCLStuff;


import static JavaCLStuff.StructUtils.alignmentOf;
import static JavaCLStuff.StructUtils.primTypeAlignment;
import static JavaCLStuff.StructUtils.primTypeLength;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import JavaCLStuff.CPPObject;
import JavaCLStuff.CPPType;
import JavaCLStuff.DefaultParameterizedType;
import JavaCLStuff.Utils;

/**
 * Internal metadata on a struct field
 */
public class StructFieldDescription {

    public final List<StructFieldDeclaration> aggregatedFields = new ArrayList<StructFieldDeclaration>();
    public long alignment = -1;
    public long byteOffset = -1, byteLength = -1;
    public long bitOffset = 0;
    public long bitLength = -1;
    public long arrayLength = 1;
    public long bitMask = -1;
    public boolean isArray, isNativeObject;
    public Type nativeTypeOrPointerTargetType;
    public java.lang.reflect.Field field;
    Type valueType;
    Method getter;
    String name;
    boolean isCLong, isSizeT;

    public void offset(long bytes) {
        byteOffset += bytes;
    }

    @Override
    public String toString() {
        return "Field(byteOffset = " + byteOffset + ", byteLength = " + byteLength + ", bitOffset = " + bitOffset + ", bitLength = " + bitLength + (nativeTypeOrPointerTargetType == null ? "" : ", ttype = " + nativeTypeOrPointerTargetType) + ")";
    }

    static Type resolveType(Type tpe, Type structType) {
        if (tpe == null || (tpe instanceof WildcardType)) {
            return null;
        }

        Type ret;
        if (tpe instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) tpe;
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            Type[] resolvedActualTypeArguments = new Type[actualTypeArguments.length];
            for (int i = 0; i < actualTypeArguments.length; i++) {
                resolvedActualTypeArguments[i] = resolveType(actualTypeArguments[i], structType);
            }
            Type resolvedOwnerType = resolveType(pt.getOwnerType(), structType);
            Type rawType = pt.getRawType();
            if ((tpe instanceof CPPType) || CPPObject.class.isAssignableFrom(Utils.getClass(rawType))) // TODO args
            {
                ret = new CPPType(resolvedOwnerType, rawType, (Object[])resolvedActualTypeArguments);
            } else {
                ret = new DefaultParameterizedType(resolvedOwnerType, rawType, resolvedActualTypeArguments);
            }
        } else if (tpe instanceof TypeVariable) {
            TypeVariable<?> tv = (TypeVariable<?>) tpe;
            Class<?> structClass = Utils.getClass(structType);
            TypeVariable<?>[] typeParameters = structClass.getTypeParameters();
            int i = Arrays.asList(typeParameters).indexOf(tv);
            // TODO recurse on pt.getOwnerType() if i < 0.
            if (i >= 0) {
                if (structType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) structType;
                    //Type[] typeParams = CPPRuntime.getTemplateTypeParameters(null, tpe)
                    ret = pt.getActualTypeArguments()[i];
                } else {
                    throw new RuntimeException("Type " + structType + " does not have params, cannot resolve " + tpe);
                }
            } else {
                throw new RuntimeException("Type param " + tpe + " not found in params of " + structType + " (" + Arrays.asList(typeParameters) + ")");
            }
        } else {
            ret = tpe;
        }

        assert !Utils.containsTypeVariables(ret);
//            throw new RuntimeException("Type " + ret + " cannot be resolved");

        return ret;
    }

    static StructFieldDescription aggregateDeclarations(Type structType, List<StructFieldDeclaration> fieldGroup) {
        StructFieldDescription aggregatedField = new StructFieldDescription();
        boolean isMultiFields = fieldGroup.size() > 1;
        aggregatedField.aggregatedFields.addAll(fieldGroup);
        for (StructFieldDeclaration field : fieldGroup) {
            if (field.valueClass.isArray()) {
                throw new RuntimeException("Struct fields cannot be array types : please use a combination of Pointer and @Array (for instance, an int[10] is a @Array(10) Pointer<Integer>).");
            }
            if (field.valueClass.isPrimitive()) {
                if (field.desc.isCLong) {
                    field.desc.byteLength = CLong.SIZE;
                } else if (field.desc.isSizeT) {
                    field.desc.byteLength = SizeT.SIZE;
                } else {
                    field.desc.byteLength = primTypeLength(field.valueClass);
                    if (field.desc.alignment < 0)
                        field.desc.alignment = primTypeAlignment(field.valueClass, field.desc.byteLength);
                }
            } else if (field.valueClass == CLong.class) {
                field.desc.byteLength = CLong.SIZE;
            } else if (field.valueClass == SizeT.class) {
                field.desc.byteLength = SizeT.SIZE;
            } else if (StructObject.class.isAssignableFrom(field.valueClass)) {
                field.desc.nativeTypeOrPointerTargetType = resolveType(field.desc.valueType, structType);
                StructDescription desc = StructIO.getInstance(field.valueClass, field.desc.valueType).desc;
                field.desc.byteLength = desc.getStructSize();
                if (field.desc.alignment < 0)
                    field.desc.alignment = desc.getStructAlignment();
                field.desc.isNativeObject = true;
            } else if (ValuedEnum.class.isAssignableFrom(field.valueClass)) {
                field.desc.nativeTypeOrPointerTargetType = resolveType((field.desc.valueType instanceof ParameterizedType) ? PointerIO.getClass(((ParameterizedType) field.desc.valueType).getActualTypeArguments()[0]) : null, structType);
                Class<?> c = PointerIO.getClass(field.desc.nativeTypeOrPointerTargetType);
                if (IntValuedEnum.class.isAssignableFrom(c)) {
                    field.desc.byteLength = 4;
                } else {
                    throw new RuntimeException("Enum type unknown : " + c);
                }
                //field.callIO = CallIO.Utils.createPointerCallIO(field.valueClass, field.desc.valueType);
            } else if (TypedPointer.class.isAssignableFrom(field.valueClass)) {
                field.desc.nativeTypeOrPointerTargetType = resolveType(field.desc.valueType, structType);
                if (field.desc.isArray) {
                    throw new RuntimeException("Typed pointer field cannot be an array : " + field.desc.name);
                }
                field.desc.byteLength = Pointer.SIZE;
                //field.callIO = CallIO.Utils.createPointerCallIO(field.valueClass, field.desc.valueType);
            } else if (Pointer.class.isAssignableFrom(field.valueClass)) {
                Type tpe = (field.desc.valueType instanceof ParameterizedType) ? ((ParameterizedType) field.desc.valueType).getActualTypeArguments()[0] : null;
                field.desc.nativeTypeOrPointerTargetType = resolveType(tpe, structType);
                if (field.desc.isArray) {
                    field.desc.byteLength = BridJ.sizeOf(field.desc.nativeTypeOrPointerTargetType);
                    if (field.desc.alignment < 0)
                        field.desc.alignment = alignmentOf(field.desc.nativeTypeOrPointerTargetType);
                } else {
                    field.desc.byteLength = Pointer.SIZE;
                }
                //field.callIO = CallIO.Utils.createPointerCallIO(field.valueClass, field.desc.valueType);
            } else if (Buffer.class.isAssignableFrom(field.valueClass)) {
                if (field.valueClass == IntBuffer.class) {
                    field.desc.byteLength = 4;
                } else if (field.valueClass == LongBuffer.class) {
                    field.desc.byteLength = 8;
                } else if (field.valueClass == ShortBuffer.class) {
                    field.desc.byteLength = 2;
                } else if (field.valueClass == ByteBuffer.class) {
                    field.desc.byteLength = 1;
                } else if (field.valueClass == FloatBuffer.class) {
                    field.desc.byteLength = 4;
                } else if (field.valueClass == DoubleBuffer.class) {
                    field.desc.byteLength = 8;
                } else {
                    throw new UnsupportedOperationException("Field array type " + field.valueClass.getName() + " not supported yet");
                }
            } else if (field.valueClass.isArray() && field.valueClass.getComponentType().isPrimitive()) {
                field.desc.byteLength = primTypeLength(field.valueClass.getComponentType());
                if (field.desc.alignment < 0)
                    field.desc.alignment = primTypeAlignment(field.valueClass, field.desc.byteLength);
            } else {
                //throw new UnsupportedOperationException("Field type " + field.valueClass.getName() + " not supported yet");
                StructDescription desc = StructIO.getInstance(field.valueClass, field.desc.valueType).desc;
                long s = desc.getStructSize();
                if (s > 0) {
                    field.desc.byteLength = s;
                } else {
                    throw new UnsupportedOperationException("Field type " + field.valueClass.getName() + " not supported yet");
                }
            }

            aggregatedField.alignment = Math.max(
                    aggregatedField.alignment,
                    field.desc.alignment >= 0
                    ? field.desc.alignment
                    : field.desc.byteLength);

            long length = field.desc.arrayLength * field.desc.byteLength;
            if (length >= aggregatedField.byteLength) {
                aggregatedField.byteLength = length;
            }

            if (field.desc.bitLength >= 0) {
                if (isMultiFields) {
                    throw new RuntimeException("No support for bit fields unions yet !");
                }
                aggregatedField.bitLength = field.desc.bitLength;
                aggregatedField.byteLength = (aggregatedField.bitLength >>> 3) + ((aggregatedField.bitLength & 7) != 0 ? 1 : 0);
            }
        }
        return aggregatedField;
    }

    void computeBitMask() {
        if (bitLength < 0) {
            bitMask = -1;
        } else {
            bitMask = 0;
            for (int i = 0; i < bitLength; i++) {
                bitMask = bitMask << 1 | 1;
            }
            bitMask = bitMask << bitOffset;
        }
    }
}
