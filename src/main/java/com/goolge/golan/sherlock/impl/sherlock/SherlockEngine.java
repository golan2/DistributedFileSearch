package com.goolge.golan.sherlock.impl.sherlock;

import com.goolge.golan.sherlock.api.SherlockResultsHolder;
import com.goolge.golan.sherlock.api.SherlockStrategy;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    15/11/13 08:35
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */

public class SherlockEngine {

    public SherlockResultsHolder search(String criteria, SherlockStrategy strategy) {
        SherlockResultsHolder resultsHolder = new SherlockResultsHolderImpl();
        new SherlockEngineSingleSearch(criteria, resultsHolder, strategy).search();
        return resultsHolder;
    }


}
