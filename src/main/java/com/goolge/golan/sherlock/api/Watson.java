package com.goolge.golan.sherlock.api;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 14:16
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public interface Watson extends Runnable {
    public boolean isFinished();
    public SherlockResultsHolder getResultsHolder();
}
