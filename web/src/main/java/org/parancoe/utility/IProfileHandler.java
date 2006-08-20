package org.parancoe.utility;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

public interface IProfileHandler {

    /**
      * <p>Imposta il profilo dell'utente attualmente autenticato nella
      * applicazione</p>
      *
      * @param userProfile Object Oggetto che contiene i dati del profilo.
      */
     public void setUserProfile(HttpServletRequest request, Principal userProfile);

     /**
      * Restituisce l'oggetto con i dati del profilo per l'utente attualmente
      * autenticato nell'applicaazione.
      *
      * @return Principal Oggetto che contiene i dati del profilo.
      */
     public Principal getUserProfile(HttpServletRequest request);

     /**
      * Rimuove il profilo utente dalla sessione.
      *
      * @param request la richiesta tramite la quale recuperare la sessione.
      */
      public void removeUserProfile(HttpServletRequest request);

     /**
      * Verifica se l'utente attualmente autenticato � in possesso di una determinata
      * regola. Possedere una regola significa che l'utente � abilitato
      * all'esecuzione di una operazione nell'applicazione. Es: inserimento anagrafica
      * � una regola.
      *
      * @param ruleCode String Codice univoco della regola.
      * @return boolean True se l'utente possiede la regola.
      */
     public boolean hasUserRule(HttpServletRequest request, String ruleCode);

     /**
      * Verifica se l'utente corrente � logato.
      *
      * @return boolean True se l'utente � logato.
      */
     public boolean isUserLogged(HttpServletRequest request);


}
