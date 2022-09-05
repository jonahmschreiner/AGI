package JavaCLStuff;

import java.util.ArrayList;
import java.util.List;

final class SolidRanges {

    public final long[] offsets, lengths;

    public SolidRanges(long[] offsets, long[] lengths) {
        this.offsets = offsets;
        this.lengths = lengths;
    }

    static class Builder {

        List<Long> offsets = new ArrayList<Long>(), lengths = new ArrayList<Long>();
        long lastOffset = -1, nextOffset = 0;
        int count;

        void add(StructFieldDescription f) {
            long offset = f.byteOffset;
            long length = f.byteLength;

            if (offset == lastOffset) {
		length = Math.max(lengths.get(count - 1), length);
                lengths.set(count - 1, length);
            } else if (offset == nextOffset && count != 0) {
                lengths.set(count - 1, lengths.get(count - 1) + length);
            } else {
                offsets.add(offset);
                lengths.add(length);
                count++;
            }
            lastOffset = offset;
            nextOffset = offset + length;
        }

        SolidRanges toSolidRanges() {
            long[] offsets = new long[count];
            long[] lengths = new long[count];
            for (int i = 0; i < count; i++) {
                offsets[i] = this.offsets.get(i);
                lengths[i] = this.lengths.get(i);
            }
            return new SolidRanges(offsets, lengths);
        }
    }
}
