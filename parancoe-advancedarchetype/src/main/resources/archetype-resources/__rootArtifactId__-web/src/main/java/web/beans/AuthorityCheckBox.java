#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.beans;

import ${package}.core.po.Authority;

/**
 * A bean for storing the state of a checkbox for an Authority.
 * 
 * @author Lucio Benfante
 */
public class AuthorityCheckBox {

    private Authority authority;
    private boolean checked;

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
