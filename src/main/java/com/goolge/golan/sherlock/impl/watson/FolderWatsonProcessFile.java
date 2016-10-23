package com.goolge.golan.sherlock.impl.watson;

import com.goolge.golan.sherlock.impl.sherlock.FolderResult;
import com.goolge.golan.sherlock.log.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
* <pre>
* <B>Copyright:</B>   HP Software IL
* <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
* <B>Creation:</B>    17/11/13 21:41
* <B>Since:</B>       BSM 9.21
* <B>Description:</B>
*
* </pre>
*/
public class FolderWatsonProcessFile extends SimpleFileVisitor<Path> {
    private static final Log log = Log.createLog(FolderWatsonProcessFile.class);



    FolderWatson folderWatson;

    public FolderWatsonProcessFile(FolderWatson folderWatson) {
        this.folderWatson = folderWatson;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
        File file = path.toFile();
        long rank = folderWatson.calcRank(file, path, attributes);
        log.debug("visitFile - rank=["+rank+"] path=["+path.toString()+"] folderWatson=["+folderWatson+"] ");
        if (rank > Long.MIN_VALUE) {
            FileTime createTime = attributes.creationTime();
            FolderResult newResult = new FolderResult(rank, file, createTime);
            folderWatson.addResult(newResult);
            //log.debug("[" + folderWatson.getContext().resultsHolder + "][" + folderWatson + "] addResult - [" + newResult.getRank() + "]" + newResult.getDisplayLabel());
            //folderWatson.getContext().resultsHolder.addResult(newResult);
            //if (folderWatson.getContext().resultsHolder.getResults().size()%10==0) {
            //    log.debug("[" + folderWatson.getContext().resultsHolder.getResults().size() + "] hits under [" + folderWatson.getPath() + "]");
            //}
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return super.visitFileFailed(file, exc);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
