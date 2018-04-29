package com.goolge.golan.sherlock.impl.watson;

import com.goolge.golan.sherlock.log.Log;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    17/11/13 21:42
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class ParallelFolderWatsonProcessFile extends FolderWatsonProcessFile {
    private static final Log log = Log.createLog(ParallelFolderWatsonProcessFile.class);



    public ParallelFolderWatsonProcessFile(FolderWatson folderWatson) {
        super(folderWatson);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {
        if (!isRootFolder(path)) {
            folderWatson.submitSubWatson(path.toString());
            log.debug("preVisitDirectory: [SKIP_SUBTREE] " + path.toString());
            return FileVisitResult.SKIP_SUBTREE;
        }
        else {
            log.debug("preVisitDirectory: [CONTINUE] " + path.toString());
            return FileVisitResult.CONTINUE;
        }
    }

    private boolean isRootFolder(Path path) {
        return path.toString().equals(folderWatson.getPath());
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return super.visitFileFailed(file, exc);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
