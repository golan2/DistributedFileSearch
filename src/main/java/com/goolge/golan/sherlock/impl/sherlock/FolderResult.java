package com.goolge.golan.sherlock.impl.sherlock;

import com.goolge.golan.sherlock.api.SherlockResult;

import java.io.File;
import java.nio.file.attribute.FileTime;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 14:42
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class FolderResult implements SherlockResult {
    private long     rank;
    private String   name;
    private String   path;
    private long     size;
    private FileTime creationDate;

    public FolderResult(long rank, File file, FileTime creationDate) {
        this.rank = rank;
        this.name = file.getName();
        this.path = file.getAbsolutePath();
        this.size = file.length();
        this.creationDate = creationDate;
    }

    public long getRank() {
        return rank;
    }

    @Override
    public String getDisplayLabel() {
        return name;
    }

    @Override
    public String toString() {
        return "FolderResult{" +
            "rank=" + rank +
            ", name='" + name + '\'' +
            ", path='" + path + '\'' +
            ", size=" + size +
            ", creationDate=" + creationDate +
            '}';
    }

    @Override
    public int compareTo(SherlockResult rhs) {
        return Long.valueOf(this.rank).compareTo(rhs.getRank());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof FolderResult)) { return false; }

        FolderResult that = (FolderResult) o;

        if (name != null ? !name.equals(that.name) : that.name != null) { return false; }
        if (path != null ? !path.equals(that.path) : that.path != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
