package com.goolge.golan.sherlock.api;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 14:13
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public interface SherlockResult extends Comparable<SherlockResult>{
    public long getRank();
    public String getDisplayLabel();
}
