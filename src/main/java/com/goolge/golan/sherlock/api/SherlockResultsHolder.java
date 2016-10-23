package com.goolge.golan.sherlock.api;

import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 15:23
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public interface SherlockResultsHolder {
    public boolean isFinished();

    public List<SherlockResult> getResults();

    int getResultSize();

    public void addResults(List<SherlockResult> results);

    public void addResult(SherlockResult result);

    Collection<Exception> getErrors();

    void addError(Exception e);

    void setFinished(boolean finished);

    void merge(SherlockResultsHolder resultsHolder);
}
