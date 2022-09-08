package JavaCLStuff;


import static JavaCLStuff.StructUtils.computeStructLayout;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import JavaCLStuff.Virtual;
import JavaCLStuff.Utils;

public class StructDescription {

    protected volatile StructFieldDescription[] fields;
    protected long structSize = -1;
    protected long structAlignment = -1;
    protected final Class<?> structClass;
    protected final Type structType;
    protected boolean hasFieldFields;

    public void prependBytes(long bytes) {
        build();
        for (StructFieldDescription field : fields) {
            field.offset(bytes);
        }
        structSize += bytes;
    }

    public void appendBytes(long bytes) {
        build();
        structSize += bytes;
    }

    public void setFieldOffset(String fieldName, long fieldOffset, boolean propagateChanges) {
        build();

        long propagatedOffsetDelta = 0;
        long originalOffset = 0;
        for (StructFieldDescription field : fields) {
            if (field.name.equals(fieldName)) {
                originalOffset = field.byteOffset;
                propagatedOffsetDelta = fieldOffset - field.byteOffset;
                field.offset(propagatedOffsetDelta);
                if (!propagateChanges) {
                    long minSize = fieldOffset + field.byteLength;
                    structSize = structSize < minSize ? minSize : structSize;
                    return;
                }
            }
        }
        structSize += propagatedOffsetDelta;
        for (StructFieldDescription field : fields) {
            if (!field.name.equals(fieldName) && field.byteOffset > originalOffset) {
                field.offset(propagatedOffsetDelta);
            }
        }
    }
    @SuppressWarnings("deprecation")
    StructCustomizer customizer;

    public StructDescription(Class<?> structClass, Type structType, @SuppressWarnings("deprecation") StructCustomizer customizer) {
        this.structClass = structClass;
        this.structType = structType;
        this.customizer = customizer;
        if (Utils.containsTypeVariables(structType)) {
            throw new RuntimeException("Type " + structType + " contains unresolved type variables!");
        }
        // Don't call build here, for recursive initialization cases.
    }

    boolean isVirtual() {
        for (Method m : structClass.getMethods()) {
            if (m.getAnnotation(Virtual.class) != null) {
                return true;
            }
        }
        return false;
    }

    public Class<?> getStructClass() {
        return structClass;
    }

    public Type getStructType() {
        return structType;
    }

    @Override
    public String toString() {
        return "StructDescription(" + Utils.toString(structType) + ")";
    }

    /// Call whenever an instanceof a struct that depends on that StructIO is created
    @SuppressWarnings("deprecation")
    void build() {
        if (fields == null) {
            synchronized (this) {
                if (fields == null) {
                    computeStructLayout(this, customizer);
                    customizer.afterBuild(this);
                    if (BridJ.debug) {
                        BridJ.info(describe());
                    }
                }
            }
        }
    }

    public final long getStructSize() {
        build();
        return structSize;
    }

    public final long getStructAlignment() {
        build();
        return structAlignment;
    }
    private List<StructFieldDescription> aggregatedFields;

    public void setAggregatedFields(List<StructFieldDescription> aggregatedFields) {
        this.aggregatedFields = aggregatedFields;
    }

    public List<StructFieldDescription> getAggregatedFields() {
        build();
        return aggregatedFields;
    }
    SolidRanges solidRanges;

    public SolidRanges getSolidRanges() {
        build();
        return solidRanges;
    }

    public final String describe(StructObject struct) {
        return StructUtils.describe(struct, structType, fields);
    }

    public final String describe() {
        StringBuilder b = new StringBuilder();
        b.append("// ");
        b.append("size = ").append(structSize).append(", ");
        b.append("alignment = ").append(structAlignment);
        b.append("\nstruct ");
        b.append(StructUtils.describe(structType)).append(" { ");
        for (int iField = 0, nFields = fields.length; iField < nFields; iField++) {
            StructFieldDescription fd = fields[iField];
            b.append("\n\t");
            b.append("@Field(").append(iField).append(") ");
            if (fd.isCLong) {
                b.append("@CLong ");
            } else if (fd.isSizeT) {
                b.append("@Ptr ");
            }
            b.append(StructUtils.describe(fd.valueType)).append(" ").append(fd.name).append("; ");

            b.append("// ");
            b.append("offset = ").append(fd.byteOffset).append(", ");
            b.append("length = ").append(fd.byteLength).append(", ");
            if (fd.bitOffset != 0) {
                b.append("bitOffset = ").append(fd.bitOffset).append(", ");
            }
            if (fd.bitLength != -1) {
                b.append("bitLength = ").append(fd.bitLength).append(", ");
            }
            if (fd.arrayLength != 1) {
                b.append("arrayLength = ").append(fd.arrayLength).append(", ");
            }
            if (fd.alignment != 1) {
                b.append("alignment = ").append(fd.alignment);//.append(", ");
            }
        }
        b.append("\n}");
        return b.toString();
    }
}
