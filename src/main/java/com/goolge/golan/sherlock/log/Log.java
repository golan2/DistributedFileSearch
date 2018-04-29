package com.goolge.golan.sherlock.log;

import com.goolge.golan.sherlock.impl.sherlock.SherlockEngineSingleSearch;
import com.goolge.golan.sherlock.impl.sherlock.SherlockResultsHolderImpl;
import com.goolge.golan.sherlock.impl.watson.FolderWatson;
import com.goolge.golan.sherlock.impl.watson.FolderWatsonProcessFile;
import com.goolge.golan.sherlock.impl.watson.ParallelFolderWatsonProcessFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    15/11/13 15:46
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Log {
    public enum LOGLEVEL {DEBUG, INFO, ERROR}


    private static final Log logDebug = new Log(LOGLEVEL.DEBUG);
    private static final Log logInfo  = new Log(LOGLEVEL.INFO );
    private static final Log logError = new Log(LOGLEVEL.ERROR);


    private static final Class[]    debugClasses_ = {
        //SherlockResultsHolderImpl.class,
        FolderWatson.class,
        //FolderWatsonProcessFile.class,
        //ParallelFolderWatsonProcessFile.class,
        //SherlockEngineSingleSearch.class
    };
    private static final Set<Class> debugClasses  = new HashSet<>(Arrays.asList(debugClasses_));


    private final boolean isDebugEnabled;
    private final boolean isInfoEnabled;

    public static Log createLog(Class clazz) {
        if (debugClasses.contains(clazz)) {
            return logDebug;
        }
        else {
            return logInfo;
        }
    }

    private Log(LOGLEVEL loglevel) {
        if (LOGLEVEL.DEBUG==loglevel) {
            isInfoEnabled = true;
            isDebugEnabled = true;
        }
        else if (LOGLEVEL.INFO==loglevel) {
            isInfoEnabled = true;
            isDebugEnabled = false;
        }
        else if (LOGLEVEL.ERROR==loglevel) {
            isInfoEnabled = false;
            isDebugEnabled = false;
        }
        else {
            throw new IllegalArgumentException("Unexpected value of loglevel {"+loglevel+"}");
        }
    }


    public void debug(String s) {
        if (isDebugEnabled) {
            System.out.println(generatePrefix() + " - " + s);
        }
    }

    public void error(String s) {
        System.out.println(generatePrefix() + " - " + s);
    }

    public void error(String s, Throwable e) {
        System.out.println(generatePrefix() + " - " + s);
        e.printStackTrace(System.out);
    }

    public void info(String s) {
        if (isInfoEnabled) {
            System.out.println(generatePrefix() + " - " + s);
        }
    }

    private String generatePrefix() {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
        return System.currentTimeMillis() + "~ [" + Thread.currentThread().getName() + "]~" + " (" + ste.getFileName() + ":" + ste.getLineNumber() + ")~ ";
    }

}
