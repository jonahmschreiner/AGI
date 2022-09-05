package JavaCLStuff;


import static JavaCLStuff.DyncallLibrary.DC_SIGCHAR_CHAR;
import static JavaCLStuff.DyncallLibrary.DC_SIGCHAR_DOUBLE;
import static JavaCLStuff.DyncallLibrary.DC_SIGCHAR_FLOAT;
import static JavaCLStuff.DyncallLibrary.DC_SIGCHAR_INT;
import static JavaCLStuff.DyncallLibrary.DC_SIGCHAR_LONGLONG;
import static JavaCLStuff.DyncallLibrary.DC_SIGCHAR_POINTER;
import static JavaCLStuff.DyncallLibrary.DC_SIGCHAR_SHORT;
import static JavaCLStuff.DyncallLibrary.DEFAULT_ALIGNMENT;
import static JavaCLStuff.DyncallLibrary.dcCloseStruct;
import static JavaCLStuff.DyncallLibrary.dcFreeStruct;
import static JavaCLStuff.DyncallLibrary.dcNewStruct;
import static JavaCLStuff.DyncallLibrary.dcStructField;
import static JavaCLStuff.DyncallLibrary.dcStructSize;
import static JavaCLStuff.DyncallLibrary.dcSubStruct;

import java.lang.reflect.Type;
import java.util.List;

import JavaCLStuff.DyncallLibrary.DCstruct;
import JavaCLStuff.Utils;

class DyncallStructs {

    Pointer<DCstruct> struct;

    static int toDCAlignment(long structIOAlignment) {
        return structIOAlignment <= 0 ? DEFAULT_ALIGNMENT : (int) structIOAlignment;
    }

    public static Pointer<DCstruct> buildDCstruct(StructDescription desc) {
        if (!BridJ.Switch.StructsByValue.enabled) {
            return null;
        }

        List<StructFieldDescription> aggregatedFields = desc.getAggregatedFields();
        @SuppressWarnings("deprecation")
        Pointer<DCstruct> struct = dcNewStruct(aggregatedFields.size(), toDCAlignment(desc.getStructAlignment())).withReleaser(new Pointer.Releaser() {
            public void release(Pointer<?> p) {
                dcFreeStruct(p.as(DCstruct.class));
            }
        });
        fillDCstruct(desc.structType, struct, aggregatedFields);
        dcCloseStruct(struct);

        long expectedSize = desc.getStructSize();
        long size = dcStructSize(struct);

        if (expectedSize != size) {
            BridJ.error("Struct size computed for " + Utils.toString(desc.structType) + " by BridJ (" + expectedSize + " bytes) and dyncall (" + size + " bytes) differ !");
            return null;
        }
        return struct;
    }

    protected static void fillDCstruct(Type structType, Pointer<DCstruct> struct, List<StructFieldDescription> aggregatedFields) {
        for (StructFieldDescription aggregatedField : aggregatedFields) {
            StructFieldDeclaration field = aggregatedField.aggregatedFields.get(0);
            Type fieldType = field.desc.nativeTypeOrPointerTargetType;
            if (fieldType == null) {
                fieldType = field.desc.valueType;
            }
            Class<?> fieldClass = Utils.getClass(fieldType);

            int alignment = toDCAlignment(aggregatedField.alignment);
            long arrayLength = field.desc.arrayLength;

            if (StructObject.class.isAssignableFrom(fieldClass)) {
                StructIO subIO = StructIO.getInstance(fieldClass, fieldType);
                List<StructFieldDescription> subAggregatedFields = subIO.desc.getAggregatedFields();

                dcSubStruct(struct, subAggregatedFields.size(), alignment, arrayLength);
                try {
                    fillDCstruct(subIO.desc.structType, struct, subAggregatedFields);
                } finally {
                    dcCloseStruct(struct);
                }
            } else {
                int dctype;
                if (fieldClass == int.class) {
                    dctype = DC_SIGCHAR_INT;
                } else if (fieldClass == long.class || fieldClass == Long.class) {
                    // TODO handle weird cases
                    dctype = DC_SIGCHAR_LONGLONG;
                } else if (fieldClass == short.class || fieldClass == char.class || fieldClass == Short.class || fieldClass == Character.class) {
                    dctype = DC_SIGCHAR_SHORT;
                } else if (fieldClass == byte.class || fieldClass == boolean.class || fieldClass == Byte.class || fieldClass == Boolean.class) {
                    dctype = DC_SIGCHAR_CHAR; // handle @IntBool annotation ?
                } else if (fieldClass == float.class || fieldClass == Float.class) {
                    dctype = DC_SIGCHAR_FLOAT;
                } else if (fieldClass == double.class || fieldClass == Double.class) {
                    dctype = DC_SIGCHAR_DOUBLE;
                } else if (Pointer.class.isAssignableFrom(fieldClass)) {
                    dctype = DC_SIGCHAR_POINTER;
                } else {
                    throw new IllegalArgumentException("Unable to create dyncall struct field for type " + Utils.toString(fieldType) + " in struct " + Utils.toString(structType));
                }

                dcStructField(struct, dctype, alignment, arrayLength);
            }
        }
    }
}