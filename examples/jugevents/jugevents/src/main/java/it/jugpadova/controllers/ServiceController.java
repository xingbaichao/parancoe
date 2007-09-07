package it.jugpadova.controllers;

import it.jugpadova.Blos;
import it.jugpadova.Daos;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nu.xom.Document;
import nu.xom.Serializer;
import org.apache.log4j.Logger;
import org.parancoe.web.BaseMultiActionController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for providing services to external.
 * @author lucio
 */
public abstract class ServiceController extends BaseMultiActionController {

    private static Logger logger =
            Logger.getLogger(ServiceController.class);

    public ServiceController() {
    }

    public ModelAndView kml(HttpServletRequest req,
            HttpServletResponse res) throws Exception {
        logger.info("Requested kml from "+req.getRemoteAddr());
        Document doc = blo().getJugBo().buildKml();
        res.setHeader("Cache-Control", "no-store");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setContentType("text/xml");
        ServletOutputStream resOutputStream = res.getOutputStream();
        Serializer serializer = new Serializer(resOutputStream);
        serializer.setIndent(4);
        serializer.setMaxLength(64);
        serializer.setLineSeparator("\n");
        serializer.write(doc);
        resOutputStream.flush();
        resOutputStream.close();
        return null;
    }

    public ModelAndView services(HttpServletRequest req,
            HttpServletResponse res) throws Exception {
        return new ModelAndView("service/services");
    }
    
    protected abstract Daos dao();

    protected abstract Blos blo();
}
