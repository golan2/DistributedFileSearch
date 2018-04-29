package com.goolge.golan.sherlock.impl.sherlock;

import com.goolge.golan.sherlock.api.SherlockResult;
import com.goolge.golan.sherlock.api.SherlockResultsHolder;
import com.goolge.golan.sherlock.log.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 15:25
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class SherlockResultsHolderImpl implements SherlockResultsHolder {
    private static final Log log = Log.createLog(SherlockResultsHolderImpl.class);

    private final List<SherlockResult> results   = new LinkedList<>();
    private final Set<SherlockResult>  resultSet = new HashSet<>();        //to avoid duplications during merge
    private       Set<Exception>       errors    = Collections.emptySet();
    private       boolean              finished;


    @Override
    public boolean isFinished() {
        return finished;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    synchronized public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    synchronized public List<SherlockResult> getResults() {
        return Collections.unmodifiableList(results);
    }
    
    @Override
    public int getResultSize() {
        return results.size();
    }

    @Override
    synchronized public void addResults(List<SherlockResult> sherlockResults) {
        for (SherlockResult r : sherlockResults) {
            addResult(r);
        }
    }

    @Override
    synchronized public void addResult(SherlockResult sr) {


        if (resultSet.contains(sr)) {
            log.debug("addResult - end - Result already there: " + sr.getDisplayLabel());
            return;
        }

        //Empty List
        if (results.isEmpty()) {
            results.add(sr);
            resultSet.add(sr);
            log.debug("addResult - end - ["+sr.getRank()+"]" + sr.getDisplayLabel());
            return;
        }

        //sr is the smallest rank (i.e. should be first)
        if (sr.compareTo(results.get(0))<0) {
            results.add(0, sr);
            resultSet.add(sr);
            log.debug("addResult - end - ["+sr.getRank()+"]" + sr.getDisplayLabel());
            return;
        }

        //Find correct place to insert
        ListIterator<SherlockResult> it = results.listIterator();
        while (it.hasNext()) {
            SherlockResult cur = it.next();
            if (sr.compareTo(cur)<0) {
                it.previous();
                it.add(sr);
                resultSet.add(sr);
                log.debug("addResult - end - ["+sr.getRank()+"]" + sr.getDisplayLabel());
                return;
            }
        }

        //add to the end
        it.add(sr);
        resultSet.add(sr);
        log.debug("addResult - end - ["+sr.getRank()+"]" + sr.getDisplayLabel());
    }

    @Override
    synchronized public Collection<Exception> getErrors() {
        return Collections.unmodifiableSet(errors);
    }

    @Override
    synchronized public void addError(Exception e) {
        if (errors.isEmpty()) {
            this.errors = new HashSet<>();
        }
        this.errors.add(e);
    }

    @Override
    synchronized public void merge(SherlockResultsHolder resultsHolder) {
        log.debug("merge - begin - size=["+this.results.size()+"]");
        addResults(resultsHolder.getResults());
        this.errors.addAll(resultsHolder.getErrors());
        log.debug("merge - end - size=["+this.results.size()+"]");
    }

    public static SherlockResultsHolder emptyResult() {
        return EmptyResult.EMPTY_RESULT;
    }


    private static class EmptyResult implements SherlockResultsHolder {
        public static final SherlockResultsHolder EMPTY_RESULT = new EmptyResult();

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public List<SherlockResult> getResults() {
            return Collections.emptyList();
        }

        @Override
        public int getResultSize() {
            return 0;
        }

        @Override
        public void addResults(List<SherlockResult> sherlockResults) {
            throw new IllegalStateException("Can't add results to the EmptyResult class!!!");
        }

        @Override
        public void addResult(SherlockResult objectSherlockResult) {
            throw new IllegalStateException("Can't add results to the EmptyResult class!!!");
        }

        @Override
        public Collection<Exception> getErrors() {
            return Collections.emptySet();
        }

        @Override
        public void addError(Exception e) {
            throw new IllegalStateException("Can't add errors to the EmptyResult class!!!");
        }

        @Override
        public void setFinished(boolean finished) {
            throw new IllegalStateException("Empty result is by-definition finished so you can't set this value");
        }

        @Override
        public void merge(SherlockResultsHolder resultsHolder) {
            throw new IllegalStateException("Can't add results to the EmptyResult class!!!");
        }
    }
}
