package JavaCLStuff;


import java.util.Date;
import java.util.List;

import JavaCLStuff.Field;
import JavaCLStuff.Struct;

public final class TimeT extends AbstractIntegral {
  private static final long serialVersionUID = 1L;
		public static final int SIZE = Platform.TIME_T_SIZE;

    public TimeT(long value) {
        super(value);
    }

    @Override
    public int byteSize() {
    	return SIZE;
    }

    public Date toDate() {
        return new Date(value);
    }

    public static TimeT valueOf(long value) {
        return new TimeT(value);
    }

    public static TimeT valueOf(Date value) {
        return valueOf(value.getTime());
    }

    @Override
    public String toString() {
        return "TimeT(value = " + value + ", time = " + toDate() + ")";
    }

    @Struct(customizer = timeval_customizer.class)
    public static class timeval extends StructObject {

        static {
            BridJ.register();
        }

        public long getTime() {
            return seconds() * 1000 + milliseconds();
        }

        @Field(0)
        public long seconds() {
            return this.io.getCLongField(this, 0);
        }

        @Field(0)
        public timeval seconds(long seconds) {
            this.io.setCLongField(this, 0, seconds);
            return this;
        }

        public final long seconds_$eq(long seconds) {
            seconds(seconds);
            return seconds;
        }

        @Field(1)
        public int milliseconds() {
            return this.io.getIntField(this, 1);
        }

        @Field(1)
        public timeval milliseconds(int milliseconds) {
            this.io.setIntField(this, 1, milliseconds);
            return this;
        }

        public final int milliseconds_$eq(int milliseconds) {
            milliseconds(milliseconds);
            return milliseconds;
        }
    }

    @SuppressWarnings("deprecation")
    public static class timeval_customizer extends StructCustomizer {

        @Override
        public void beforeLayout(StructDescription desc, List<StructFieldDescription> aggregatedFields) {
            StructFieldDescription secondsField = aggregatedFields.get(0);
            if (Platform.isWindows() || !Platform.is64Bits()) {
                secondsField.byteLength = 4;
            } else {
                secondsField.byteLength = 8;
            }

            secondsField.alignment = secondsField.byteLength;
        }
    }
}