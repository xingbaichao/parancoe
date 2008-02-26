package it.jugpadova.blo;

import com.plink.plextile.*;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

/**
 *
 * @author lucio
 */
public class FilterBo {

    private static TextParser textParser = new TextParser();

    public FilterBo() {
    }

    public void populatePreview(String text, String filter,
            String previewDivId) {
        String result =
                FilterBo.filterText(text, filter, false);
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        Util util = new Util(session);
        util.setValue(previewDivId, result, false);
    }

    public static String filterText(String text, String filter,
            boolean escapeXml) {
        // Only Textile for now
        String result = textParser.parseTextile(text, !escapeXml);
        return result;
    }
}
