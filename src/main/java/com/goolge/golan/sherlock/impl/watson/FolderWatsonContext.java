package com.goolge.golan.sherlock.impl.watson;

import com.goolge.golan.sherlock.api.SherlockResultsHolder;
import com.goolge.golan.sherlock.api.SherlockStrategy;
import com.goolge.golan.sherlock.impl.sherlock.SherlockResultsHolderImpl;
import com.goolge.golan.sherlock.impl.tpe.MyThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class FolderWatsonContext {
    private final String criteria;
    private final SherlockStrategy strategy;
    private final ExecutorService pool;
    private final SherlockResultsHolder resultsHolder;

    public FolderWatsonContext(String criteria, SherlockStrategy strategy) {
        this(criteria, strategy, createPool(), createResultsHolder());
    }

    public FolderWatsonContext(String criteria, SherlockStrategy strategy, ExecutorService pool, SherlockResultsHolder resultsHolder) {
        this.criteria      = criteria;
        this.strategy      = strategy;
        this.pool          = pool;
        this.resultsHolder = resultsHolder;
    }

    private static SherlockResultsHolderImpl createResultsHolder() {return new SherlockResultsHolderImpl();}

    private static ExecutorService createPool() {
        return new MyThreadPoolExecutor(getPoolFixedSize(), getPoolFixedSize(), 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new FolderWatsonThreadFactory());
        //return Executors.newFixedThreadPool(getPoolFixedSize(), new FolderWatsonThreadFactory());
    }

    public static int getPoolFixedSize() {
        return 3;
    }

    public String getCriteria() {
        return criteria;
    }

    public SherlockStrategy getStrategy() {
        return strategy;
    }

    public ExecutorService getPool() {
        return pool;
    }

    public SherlockResultsHolder getResultsHolder() {
        return resultsHolder;
    }

}