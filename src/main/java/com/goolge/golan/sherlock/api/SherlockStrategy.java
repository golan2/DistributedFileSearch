package com.goolge.golan.sherlock.api;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 14:35
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class SherlockStrategy {
    private final boolean searchInFileName;
    private final boolean searchFileContent;
    private long mergeFrequency;

    public SherlockStrategy(boolean searchInFileName, boolean searchFileContent, long mergeFrequency) {
        this.searchInFileName = searchInFileName;
        this.searchFileContent = searchFileContent;
        this.mergeFrequency = mergeFrequency;
    }

    public boolean isSearchInFileName() {
        return searchInFileName;
    }

    public boolean isSearchFileContent() {
        return searchFileContent;
    }

    public long getMergeFrequency() {
        return mergeFrequency;
    }
}
