// Copyright 2006 The Parancoe Team
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.utility.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;

/**
 * <p>ErrorMessagesList</p>
 * <p>Rappresenta la collezione degli errori. Raccoglie errori globali o per una specifica propriet&agrave di un bean
 * <br>oppure errori relativi ad una zona precisa dello schermo</p>
 * @author NEC
 * @version 1.0
 */
public class ZoneErrorMessageList  implements Serializable {

    /**
     * <p>Compara oggetti della ZoneErrorMessageList.</p>
     */
    private static final Comparator errorItemComparator = new Comparator() {
      public int compare(Object o1, Object o2) {
        return ( (ZoneErrorMessageItem) o1).getOrder() -
            ( (ZoneErrorMessageItem) o2).getOrder();
      }
    };

    /**
     * <p>Il nome assegnato alla lista degli errori tranne nel caso in cui si utilizzi
     * <br>un opportuno nome associato alla propriet&agave; di un bean</p>
     */
    public static final String GENERIC_ERROR = "it.mondadori.we.GENERIC_ERROR";

    /**
     * <p>Flag che permette di identificare se gli errori sono gi&agrave; stati letti</p>
     * <br>Il controller utilizzer&agrave; questo flag per determinare se rimuovere o meno gli errori</p>
     */
    protected boolean accessed = false;

    /**
     * <p>La collezione di errori <code>ErrorMessage</code> di una specifica zona (rappresentati come un ArrayList) per
     * <br>ogni propriet&agrave;, archiviati con il nome della propriet&grave;</p>
     */
    protected HashMap errors = new HashMap();

    /**
     * <p>La posizione con cui &egrave; stata inserita la chiave per una propriet&agrave;
     * Viene usato per mantenere l'ordine nella visualizzazione degli errori</p>
     */
    protected int iCount = 0;

    /**
     * <p>Costruttore vuoto <code>ZoneErrorMessageList</code>.</p>
     */
    public ZoneErrorMessageList() {
        super();
    }//[c] ZoneErrorMessageList

    /**
     * <p>Crea una collezione di errori copiandone una esistente.</p>
     * @param errors La collezione da copiare. Pu&ograve; essere <code>null</code>.
     */
    public ZoneErrorMessageList(ZoneErrorMessageList errors) {
        super();
        this.add(errors);
    }//[c] ZoneErrorMessageList

    /**
     * <p>Aggiunge un errore alla lista degli errori specificati per la propriet&agrave; indicata.
     * <br>Viene mantenuto l'ordinamento nell inserimento.</p>
     * @param property il nome della propriet&agrave; (oppure ZoneErrorMessageList.GENERIC_ERROR).
     * @param error  il messaggio da aggiungere.
     */
    public void add(String property, ErrorMessage error) {

        ZoneErrorMessageItem item = (ZoneErrorMessageItem) this.errors.get(property);
        List list = null;
        // Se è la prima volta che aggiungo un errore alla proprietà indicata
        // allora creo una lista per gli errori di questa proprietà ed aggiungo il nuovo elemento alla lista degli errori.
        if (item == null) {
            list = new ArrayList();
            item = new ZoneErrorMessageItem(list, this.iCount++, property);
            this.errors.put(property, item);
        } else {
            list = item.getList();
        }
        list.add(error);
    }//[m] add

    /**
     * <p>Aggiunge un errore generico alla lista degli errori.
     * <br>Viene mantenuto l'ordinamento nell inserimento.</p>
     * @param error  il messaggio da aggiungere.
     */
    public void add( ErrorMessage error) {
      add(this.GENERIC_ERROR,error);
    } //[m] add

    /**
     * <p>Aggiunge una lista degli errori di zona alla lista globale.</p>
     * @param errorMessages la lista degli errori.
     */
    public void add(ZoneErrorMessageList errorMessages) {
      if (errorMessages == null) {
        return;
      }
      // Aggiunge le proprietà alla lista della collezione degli errori.
      Iterator props = errorMessages.properties();
      while (props.hasNext()) {
        String property = (String) props.next();

        // Aggiunge gli errori per la specifica propriet???
        Iterator errs = errorMessages.get(property);
        while (errs.hasNext()) {
          ErrorMessage msg = (ErrorMessage) errs.next();
          this.add(property, msg);
        }
      }
    } //[m] add

    /**
     * <p>Cancella tutti gli errori di questa collezione.</p>
     */
    public void clear() {
        errors.clear();
    }//[m] clear

    /**
     * <p>Verifica se la collezione contiene o meno errori.</p>
     * @return <code>true</code> se non ci sono errori in questa collezione.
     *          <code>false</code> altrimenti.
     */
    public boolean isEmpty() {
        return (errors.isEmpty());
    }//[m] isEmpty

    /**
     * <p>Restituisce <code>true</code> se ho gi&agrave; fatto accesso alla collezione con <code>get()</code> oppure
     * <br><code>get(String)</code>.</p>
     * @return <code>true</code> se ho gi&agrave; recuperato questa collezione.
     */
    public boolean isAccessed() {
        return this.accessed;
    }//[m] isAccessed

    /**
     * <p>Ritorna l'insieme di tutti gli errori della collezione,senza distinzione delle propriet&agrave di appartenenza.
     * <br>Se la collezione &egrave vuota viene ritornata una enumeration vuota.</p>
     * @return un iteratore sugli errori di tutte le propriet&agrave;.
     */
    public Iterator get() {

        this.accessed = true;

        if (errors.isEmpty()) {
            return Collections.EMPTY_LIST.iterator();
        }

        ArrayList finalList = new ArrayList();
        ArrayList errorItems = new ArrayList();

        for (Iterator i = errors.values().iterator(); i.hasNext();) {
            errorItems.add(i.next());
        }

        // Ordina gli errori in base al campo iOrder con cui sono stati inseriti nella proprietà.
        Collections.sort(errorItems, errorItemComparator);

        // aggiunge alla lista finale gli errori ordinati
        for (Iterator i = errorItems.iterator(); i.hasNext();) {
            ZoneErrorMessageItem emi = (ZoneErrorMessageItem) i.next();

            for (Iterator msgsIter = emi.getList().iterator(); msgsIter.hasNext();) {
                    finalList.add(msgsIter.next());
            }
        }

        return finalList.iterator();
    }//[m] get

    /**
     * <p>Recupera la lista degli errori associati ad una specifica propriet&agrave
     * Se la lista &egrave vuota viene ritornata una enumeration vuota</p>
     * @param property Il nome della propriet&agrave; o ZoneErrorMessageList.GENERIC_ERROR
     * @return un iteratore sugli errori della propriet&agrave indicata
     */
    public Iterator get(String property) {

      this.accessed = true;

      ZoneErrorMessageItem item = (ZoneErrorMessageItem) errors.get(property);

      if (item == null) {
        return (Collections.EMPTY_LIST.iterator());
      }
      else {
        return (item.getList().iterator());
      }

    } //[m] get

    /**
     * <p>Ritorna l'insieme ordinato delle propriet&agrave; presenti nella collezione</p>
     * @return un iteratore sulle propriet&agrave che hanno una lista di errori
     */
    public Iterator properties() {
      if (errors.isEmpty()) {
        return Collections.EMPTY_LIST.iterator();
      }

      ArrayList finalList = new ArrayList();
      ArrayList errorItems = new ArrayList();

      for (Iterator i = errors.values().iterator(); i.hasNext(); ) {
        errorItems.add(i.next());
      }
      //Ordina la lista delle proprietà presenti nella collezione.
      Collections.sort(errorItems, errorItemComparator);

      for (Iterator i = errorItems.iterator(); i.hasNext(); ) {
        ZoneErrorMessageItem ami = (ZoneErrorMessageItem) i.next();
        finalList.add(ami.getProperty());
      }
      return finalList.iterator();
    }//[m] properties

    /**
     * <p>Ritorna il numero di errori presenti nell'intera collezione</p>
     * @return il numero di errori della collezione
     */
    public int size() {
      int total = 0;
      for (Iterator i = errors.values().iterator(); i.hasNext(); ) {
        ZoneErrorMessageItem emi = (ZoneErrorMessageItem) i.next();
        total += emi.getList().size();
      }
      return (total);
    } //[m] size

    /**
     * <p>Ritorna il numero di errori per la propriet&agrave; indicata</p>
     * @param property il nome della propriet&agrave; o ZoneErrorMessageList.GENERIC_ERROR
     * @return il numero di errori per la propriet&agrave; indicata
     */
    public int size(String property) {
        ZoneErrorMessageItem item = (ZoneErrorMessageItem) errors.get(property);
        return (item == null) ? 0 : item.getList().size();
    }//[m] size


    /**
     * <p>Generico errore della collezione associato ad una propriet&agrave;</p>
     */
    protected class ZoneErrorMessageItem implements Serializable {

      /** La lista contenente gli errori <code>ErrorMessage</code> */
      protected List list = null;
      /** La posizione dell'errore nella lista */
      protected int iOrder = 0;
      /** La propriet&agrave; associata all'<code>ErrorMessage</code> */
      protected String property = null;

      /**
       * <p>Costruttore</p>
       * @param list la lista degli errori <code>ErrorMessage</code>
       * @param iOrder la posizione dell'errore nella lista.
       * @param property la propriet&agrave; associata al <code>ErrorMessage</code>
       */
      public ZoneErrorMessageItem(List list, int iOrder, String property) {
        this.list = list;
        this.iOrder = iOrder;
        this.property = property;
      }//[c] ZoneErrorMessageItem

      /**
       * <p>Recupera la lista degli errori associata a questo elemento</p>
       * @return la lista degli errori associati a questo elemento
       */
      public List getList() {
        return list;
      }//[m] getList

      /**
       * <p>Assegna la lista degli errori a questo elemento</p>
       * @param la lista degli errori associati a questo elemento
       */
      public void setList(List list) {
        this.list = list;
      }//[m] setList

      /**
       * <p>Recupera la posizione dell'errore nella lista degli errori di questo elemento</p>
       * @return la posizione dell'errore nella lista degli errori di questo elemento
       */
      public int getOrder() {
        return iOrder;
      }//[m] getOrder

      /**
       * <p>Assegna la posizione dell'errore nella lista degli errori di questo elemento</p>
       * @param iOrder la posizione dell'errore nella lista degli errori di questo elemento
       */
      public void setOrder(int iOrder) {
        this.iOrder = iOrder;
      }//[m] setOrder

      /**
       * <p>Recupera la propriet&agrave; associata a questo elemento</p>
       * @return la propriet&agrave; associata a questo elemento
       */
      public String getProperty() {
        return property;
      }//[m] getProperty

      /**
       * <p>Assegna la propriet&agrave; associata a questo elemento</p>
       * @param property La propriet&agrave; associata a questo elemento
       */
      public void setProperty(String property) {
        this.property = property;
      }//[m] setPropertys

      /**
       * <p>Converte la lista degli errori in stringa</p>
       * @return uuna stringa che rappresenta la lista degli errori
       */
      public String toString() {
        return this.list.toString();
      } //[m] toString

    } //{c} ZoneErrorMessageItem

}//{c} ZoneErrorMessageList
