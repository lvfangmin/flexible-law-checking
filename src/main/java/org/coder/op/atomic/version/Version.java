package org.coder.op.atomic.version;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class Version implements Comparable<Version> {
    private final String version;
    private final static String VERSION_DELIMITER = ".";

    public Version(String version) {
        this.version = version;
    }

    public boolean isEmpty() {
        return version == null || version.isEmpty();
    }

    public List<String> getValue() {
        return Lists.newArrayList(Splitter.on(VERSION_DELIMITER).split(version));
    }

    @Override
    public int compareTo(Version other) {
        List<String> v1 = this.getValue();
        List<String> v2 = other.getValue();

        long len1 = v1.size(), len2 = v2.size();
        long maxLength = Math.max(len1, len2);

        for (int i = 0; i < maxLength; ++i) {
            Long first  = i < len1 ? new Long(v1.get(i)) : new Long(0L);
            Long second = i < len2 ? new Long(v2.get(i)) : new Long(0L);

            int result = first.compareTo(second);

            if (result != 0) {
                return result;
            }
        }

        return 0;
    }
}
