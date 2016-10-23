package com.goolge.golan.sherlock.impl.sherlock;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    17/11/13 21:34
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
class SherlockEngineThreadFactory implements ThreadFactory {
    private static AtomicInteger threadCount = new AtomicInteger(1);


    @Override
    public Thread newThread(Runnable r) {
        Thread result = new Thread(r);
        result.setName("SherlockEngineSingleSearch - " + threadCount.getAndIncrement());
        result.setDaemon(true);
        return result;
    }
}
