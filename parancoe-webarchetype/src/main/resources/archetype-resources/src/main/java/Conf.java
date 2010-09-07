#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.parancoe.util.BaseConf;

/**
 * here you can put configuration specific getters
 */
public class Conf extends BaseConf  {

    public String getMyParam() {
        return getConfiguration().getString("myparam");
    }
}
