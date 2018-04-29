package com.goolge.golan.sherlock.impl.sherlock;

import com.goolge.golan.sherlock.api.SherlockResultsHolder;
import com.goolge.golan.sherlock.api.SherlockStrategy;
import com.goolge.golan.sherlock.impl.watson.FolderWatson;
import com.goolge.golan.sherlock.log.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    17/11/13 21:34
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class SherlockEngineSingleSearch implements Runnable {
    private static final Log log = Log.createLog(SherlockEngineSingleSearch.class);


    public static final String folders[] = {"C:\\Users\\golaniz\\Documents\\Izik\\eBooks", "C:\\Users\\golaniz\\Documents\\Izik\\VB", "C:\\Users\\golaniz\\Documents\\Izik\\VC"};

    private final ExecutorService       pool          = Executors.newFixedThreadPool(3, new SherlockEngineThreadFactory());
    private final String                criteria;
    private final SherlockStrategy      strategy;
    private final SherlockResultsHolder resultsHolder;

    //private static class MyEx extends ThreadPoolExecutor {
    //    public MyEx() {
    //        super(3, 3, 0L, TimeUnit.MILLISECONDS, new MyQ(), new SherlockEngineThreadFactory());
    //    }
    //
    //    @Override
    //    public Future<?> submit(Runnable task) {
    //        log.debug("going to super submit: " + task);
    //        Future<?> result = super.submit(task);
    //        log.debug("submit will return " + result);
    //        return result;
    //    }
    //}
    //
    //private static class MyQ extends LinkedBlockingQueue<Runnable> {
    //    @Override
    //    public Runnable poll() {
    //        Runnable result = super.poll();
    //        log.debug("poll will return " + result);
    //        return result;
    //    }
    //
    //    @Override
    //    public boolean offer(Runnable runnable) {
    //        boolean result = super.offer(runnable);
    //        log.debug("offer ["+runnable+"] will return " + result);
    //        return result;
    //    }
    //}


    SherlockEngineSingleSearch(String criteria, SherlockResultsHolder resultsHolder, SherlockStrategy strategy) {
        this.criteria = criteria;
        this.resultsHolder = resultsHolder;
        this.strategy = strategy;
    }

    public void search() {
        log.debug("Submit SherlockEngineSingleSearch to pool");
        new Thread(this).start();
    }

    @Override
    public void run() {
        log.debug("SherlockEngineSingleSearch - run - begin");
        ArrayList<FolderWatson> watsons = new ArrayList<>();
        for (String folder : folders) {
            FolderWatson watson = FolderWatson.FolderWatsonFactory.createWatsonFromScratch(criteria, strategy, folder);
            watsons.add(watson);
            pool.submit(watson);
            log.debug("["+watson+"] was submitted to ["+pool+"]");
        }

        log.debug("SherlockEngineSingleSearch - run - ["+watsons.size()+"] watsons were submitted to ExecutorService");

        boolean done = false;
        while (!done) {
            done = true;
            log.debug("Before iterating watsons for results. resultsHolder.size=["+resultsHolder.getResultSize()+"] pool="+pool);
            for (FolderWatson watson : watsons) {
                if (!watson.isFinished()) done = false;
                this.resultsHolder.merge(watson.getResultsHolder());
            }
            log.debug("After iterating watsons for results. resultsHolder.size=["+resultsHolder.getResultSize()+"] pool="+pool);
            try {
                Thread.sleep(this.strategy.getMergeFrequency());
            }
            catch (InterruptedException e) {
                log.error("Method [run] in class [SherlockEngineSingleSearch] has failed.", e);
            }

        }
        this.resultsHolder.setFinished(true);
    }


}
