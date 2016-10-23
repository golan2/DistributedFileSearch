package com.goolge.golan.sherlock.impl.tpe;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    19/11/13 21:49
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public interface MyRejectedExecutionHandler {

    void rejectedExecution(Runnable r, MyThreadPoolExecutor executor);
}
