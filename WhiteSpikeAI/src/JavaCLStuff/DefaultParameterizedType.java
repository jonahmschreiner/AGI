package JavaCLStuff;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DefaultParameterizedType implements ParameterizedType {

    private final Type[] actualTypeArguments;
    private final Type ownerType;
    private final Type rawType;

    public DefaultParameterizedType(Type ownerType, Type rawType, Type[] actualTypeArguments) {
        this.ownerType = ownerType;
        this.actualTypeArguments = actualTypeArguments;
        this.rawType = rawType;
    }

    public DefaultParameterizedType(Type rawType, Type... actualTypeArguments) {
        this(null, rawType, actualTypeArguments);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (ownerType != null) {
            b.append(Utils.toString(ownerType)).append(".");
        }
        b.append(rawType);
        if (actualTypeArguments.length > 0) {
            b.append("<");
            for (int i = 0; i < actualTypeArguments.length; i++) {
                if (i > 0) {
                    b.append(", ");
                }
                b.append(Utils.toString(actualTypeArguments[i]));
            }
            b.append(">");
        }
        return b.toString();
    }

    /**
     * Builds a parameterized type with the provided raw type and type
     * arguments.<br>
     * For instance,
     * <code>paramType(Pointer.class, Integer.class)</code> gives you the type
     * of
     * <code>Pointer&lt;Integer&gt;</code>.
     */
    public static Type paramType(Type rawType, Type... actualTypeArguments) {
        return new DefaultParameterizedType(rawType, actualTypeArguments);
    }

    //@Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments.clone();
    }

    //@Override
    public java.lang.reflect.Type getOwnerType() {
        return ownerType;
    }

    //@Override
    public java.lang.reflect.Type getRawType() {
        return rawType;
    }

    //@Override
    public int hashCode() {
        int h = getRawType().hashCode();
        if (getOwnerType() != null) {
            h ^= getOwnerType().hashCode();
        }
        for (int i = 0, n = actualTypeArguments.length; i < n; i++) {
            Type arg = actualTypeArguments[i];
            if (arg != null) {
                h ^= arg.hashCode();
            }
        }
        return h;
    }

    static boolean eq(Object a, Object b) {
        if ((a == null) != (b == null)) {
            return false;
        }
        if (a != null && !a.equals(b)) {
            return false;
        }
        return true;
    }
    //@Override

    public boolean equals(Object o) {
        if (o == null || !(o instanceof DefaultParameterizedType)) {
            return false;
        }

        DefaultParameterizedType t = (DefaultParameterizedType) o;
        if (!eq(getRawType(), t.getRawType())) {
            return false;
        }
        if (!eq(getOwnerType(), t.getOwnerType())) {
            return false;
        }

        Object[] tp = t.actualTypeArguments;
        if (actualTypeArguments.length != tp.length) {
            return false;
        }

        for (int i = 0, n = actualTypeArguments.length; i < n; i++) {
            if (!eq(actualTypeArguments[i], tp[i])) {
                return false;
            }
        }

        return true;
    }
}