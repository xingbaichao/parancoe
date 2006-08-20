package org.parancoe.bean;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

import org.parancoe.utility.error.ZoneErrorMessageList;

import java.util.Map;


public interface IBean extends Serializable {

  public Map getBeanRules();
  public void setBeanRules(Map getBeanRules);
  /**
   * <p>Restituisce la chiave utilizzata per identificare univocamente il bean</p>
   * @return la chiave del bean
   */
  public String getBeanKey();

  /**
   * <p>Imposta la chiave del bean</p>
   * @param key la chiave del bean
   */
  public void setBeanKey(String key);

  /**
   * <p>Restituisce lo scope del bean (<code>session</code> o <code>request</code>)</p>
   * @return lo scope del bean
   */
  public String getBeanScope();

  /**
   * <p>Imposta lo scope del bean (<code>session</code> o <code>code request</code>)</p>
   * @param scope lo scope del bean
   */
  public void setBeanScope(String scope);

  /**
   * <p>Valida i dati relativi ad attributi del bean della request</p>
   * @param resolver il nome del resolver da utilizzare per la validazione
   * @param request la request con i dati da popolare
   * @param selector selettore della validazione (per effettuare validazioni differenti in uno stesso bean)
   * @return la lista degli errori di validazione
   */
  public ZoneErrorMessageList validate(String resolver, HttpServletRequest request, String selector);

}//{i} IBaseBean
