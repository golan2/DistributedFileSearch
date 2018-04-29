package com.goolge.golan.sherlock.impl.watson;

import com.goolge.golan.sherlock.api.SherlockResultsHolder;
import com.goolge.golan.sherlock.api.SherlockStrategy;
import com.goolge.golan.sherlock.api.Watson;
import com.goolge.golan.sherlock.impl.sherlock.FolderResult;
import com.goolge.golan.sherlock.log.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 14:20
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class FolderWatson implements Watson {
    private static final Log log = Log.createLog(FolderWatson.class);

    private       boolean             finished;
    private final String              path;
    private final List<FolderWatson>  subWatsons;
    private final FolderWatsonContext context;



    private FolderWatson(String criteria, SherlockStrategy strategy, String path) {
        this(new FolderWatsonContext(criteria, strategy), path);
    }

    private FolderWatson(FolderWatsonContext context, String path) {
        this.context      = context;
        this.finished     = false;
        this.path         = path;
        this.subWatsons   = Collections.synchronizedList(new ArrayList<FolderWatson>());
    }

    private FolderWatson(FolderWatson parentWatson, String path) {
        this(parentWatson.context, path);
    }

    @Override
    public void run() {
        try {
            //log.debug("FolderWatson - run - BEGIN - path=["+path+"] pool=["+context.getPool()+"]" + path);
            Files.walkFileTree(Paths.get(path), new ParallelFolderWatsonProcessFile(this));
            //log.debug("FolderWatson - run - END - path=["+path+"] pool=["+context.getPool()+"]" + path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    long calcRank(File file, Path path, BasicFileAttributes attributes) {
        if (context.getStrategy().isSearchInFileName()) {
            if (file.getName().contains(this.context.getCriteria())) {
                return calcRank(file.getName());
            }
        }

        if (context.getStrategy().isSearchFileContent()) {
            try {
                Scanner in = new Scanner(new FileReader(file));
                while(in.hasNextLine()) {
                    if(in.nextLine().contains(context.getCriteria())) {
                        return calcRank(file.length());
                    }
                }
            }
            catch (FileNotFoundException e) {
                log.error("Failed to check content of file [" + file.getName() + "][" + file.getPath() + "]", e);
            }
        }
        return Long.MIN_VALUE;
    }

    private long calcRank(long length) {
        return Long.MAX_VALUE - length;
    }

    private long calcRank(String fileName) {
        return Math.max(100-fileName.length(), 0);         //shorter file name gets higher rank because the criteria is a majority
    }

    @Override
    public boolean isFinished() {
        boolean result = context.getPool().isShutdown();
        log.debug("isFinished - END - result=["+result+"] pool=["+context.getPool()+"] ");
        return result;
        //return finished;
    }

    @Override
    public SherlockResultsHolder getResultsHolder() {
        return context.getResultsHolder();
    }

    @Override
    public String toString() {
        return "FolderWatson{" +
            "path='" + path + '\'' +
            '}';
    }

    /*package*/ FolderWatsonContext getContext() {
        return context;
    }

    public String getPath() {
        return path;
    }

    public void submitSubWatson(String fullPath) {
        FolderWatson subWatson = FolderWatson.FolderWatsonFactory.createSubWatson(this, fullPath);
        //log.debug("Submitting ["+subWatson+"] to ["+ this.context.getPool() +"]");
        this.subWatsons.add(subWatson);
        this.context.getPool().submit(subWatson);
    }

    public void addResult(FolderResult newResult) {
        SherlockResultsHolder resultsHolder = this.context.getResultsHolder();
        log.debug("addResult - [" + newResult.getRank() + "]" + newResult.getDisplayLabel());
        resultsHolder.addResult(newResult);
        if (resultsHolder.getResultSize()%10==0) {
            log.debug("[" + resultsHolder.getResultSize() + "] hits under [" + this.path + "]");
        }

    }

    public static class FolderWatsonFactory {

        public static FolderWatson createWatsonFromScratch(String criteria, SherlockStrategy strategy, String path) {
            return new FolderWatson(criteria, strategy, path);
        }

        public static FolderWatson createSubWatson(FolderWatson parentWatson, String path){
            return new FolderWatson(parentWatson, path);
        }
    }

}
