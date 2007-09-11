/**
 *
 */
package it.jugpadova.util;

import org.parancoe.plugins.security.User;
import org.parancoe.plugins.world.Country;
import it.jugpadova.bean.JuggerCaptcha;
import it.jugpadova.po.JUG;
import it.jugpadova.po.Jugger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Defines useful functions.
 * @author Enrico Giurin
 *
 */
public class Utilities {


    /**
     * Returns an instance of JuggerCaptcha with beans attributes set.
     * @return
     */
    public static JuggerCaptcha newJuggerCaptcha() {
        JuggerCaptcha jc = new JuggerCaptcha();
        jc.getJugger().setUser(new User());
        jc.getJugger().setJug(new JUG());
        jc.getJugger().getJug().
                setCountry(new Country());
        return jc;
    }

    /**
     * Compose the base URL from an HttpRequest.
     */
    public static String getBaseUrl(HttpServletRequest req) {
        return "http://" + req.getServerName() + ":" + req.getServerPort() +
                req.getContextPath();
    }

    /**
     * Build a ModelAndView for the message page.
     * 
     * @param messageCode The code of the localized message
     * @param messageArguments The arguments to insert in the message
     * @return The message view
     */
    public static ModelAndView getMessageView(String messageCode,
            String... messageArguments) {
        ModelAndView mv =
                new ModelAndView("redirect:/home/message.html");
        mv.addObject("messageCode", messageCode);
        addMessageArguments(mv, messageArguments);
        return mv;
    }
    
    /**
     * Add the messageArguments parameter, used by spring:message in the message page.
     * 
     * @param mv The view
     * @param messageArguments The arguments to insert in the message
     */
    public static void addMessageArguments(ModelAndView mv, String... messageArguments) {
        if (messageArguments.length > 0) {
            StringBuilder arguments = new StringBuilder();
            boolean first = true;
            for (String arg : messageArguments) {
                if (!first) {
                    arguments.append(',');
                } else {
                    first = false;
                }
                arguments.append(arg);
            }
            mv.addObject("messageArguments", arguments);
        }
    }
    
}
