import com.goolge.golan.sherlock.api.SherlockResult;
import com.goolge.golan.sherlock.api.SherlockResultsHolder;
import com.goolge.golan.sherlock.api.SherlockStrategy;
import com.goolge.golan.sherlock.impl.sherlock.SherlockEngine;
import com.goolge.golan.sherlock.log.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    14/11/13 14:30
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class SimpleTest {
    private static final Log log = Log.createLog(SimpleTest.class);

    private static final SherlockStrategy STRATEGY = new SherlockStrategy(true, false, 100);

    public static void main(String[] args) throws InterruptedException, IOException {
        testIzik();
    }

    private static void testIzik() throws InterruptedException {SherlockEngine sherlockEngine = new SherlockEngine();
        SherlockResultsHolder holder = sherlockEngine.search("frm", STRATEGY);


        //ExecutorService uses non deamon threads

        while (!holder.isFinished()) {
            log.info("Result Size = " + holder.getResultSize());
            Thread.sleep(1000);
        }

        //Thread.sleep(3000);
        System.out.println("Finished!");

        StringBuilder b = new StringBuilder();
        b.append("<A>");

        for (SherlockResult result : holder.getResults()) {
            b.append("<Result fileName=\""+result.getDisplayLabel()+"\"/>");
        }

        b.append("</A>");

        System.out.println(holder.getResultSize() + " -- " + holder);
        System.out.println(b.toString());
    }

    private static void sortAdd(Integer newResult, LinkedList<Integer> list) {
        System.out.println("Before adding ["+newResult+"] ==> " + list);
        //Empty List
        if (list.isEmpty()) {
            list.add(newResult);
            return;
        }

        //newResult is the smallest rank (i.e. should be first)
        if (newResult.compareTo(list.get(0))<0) {
            list.add(0, newResult);
            return;
        }

        //Find correct place to insert
        ListIterator<Integer> it = list.listIterator();
        while (it.hasNext()) {
            Integer cur = it.next();
            if (newResult.compareTo(cur)<0) {
                it.previous();
                it.add(newResult);
                return;
            }
        }
        it.add(newResult);
    }


}
