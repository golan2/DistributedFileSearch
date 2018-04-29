package com.goolge.golan.sherlock.impl.watson;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
* <pre>
* <B>Copyright:</B>   Izik Golan
* <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
* <B>Creation:</B>    17/11/13 21:39
* <B>Since:</B>       BSM 9.21
* <B>Description:</B>
*
* </pre>
*/
class FolderWatsonThreadFactory implements ThreadFactory {
    private static AtomicInteger threadCount = new AtomicInteger(1);


    @Override
    public Thread newThread(Runnable r) {
        Thread result = new Thread(r);
        result.setName("FolderWatsonThreadFactory - " + threadCount.getAndIncrement());
        result.setDaemon(true);
        return result;
    }
}
