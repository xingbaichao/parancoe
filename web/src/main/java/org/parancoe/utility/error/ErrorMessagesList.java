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


import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.io.Serializable;

import org.parancoe.utility.error.ZoneErrorMessageList;

/**
 * <p>ErrorMessagesList</p>
 * <p>Gestisce una lista di messaggi di errore</p>
 * @author NEC
 * @version 1.0
 */
public class ErrorMessagesList implements Serializable {

    /** Il nome assegnato alla lista degli errori che possono essere mostrati in qualsiasi zona dello schermo */
    public static final String GLOBAL_ERROR = "it.mondadori.we.GLOBAL_ERROR";
    /** Il nome assegnato alla lista degli errori ristretti ad una zona specifica dello schermo quale ad esempio
     * <br>un box incluso nel template principale
     * */
    public static final String BOX_ERROR = "it.mondadori.we.BOX_ERROR";
    /** La collezione di errori <code>ErrorMessage</code> raggruppati per zona */
    protected HashMap errors = new HashMap();

    /**
     * <p>Costruttore vuoto <code>ErrorMessagesList</code></p>
     */
    public ErrorMessagesList() {
      super();
    }//[c] ErrorMessagesList

    /**
     * <p>Crea una collezione di errori copiandone una esistente</p>
     * @param errors La collezione da copiare. Pu&ograve; essere <code>null</code>
     */
    public ErrorMessagesList(ErrorMessagesList errors) {
        super();
        this.add(errors);
    }//[c] ErrorMessagesList

    /**
     * <p>Aggiunge una lista di errori alla lista degli errori specificati per la zona indicata</p>
     * @param zone  Il nome della zona per cui gli error saranno disponibili
     * @param errors   La lista di errori per quella zona
     */
    public void addZoneErrorMessageList(String zone, ZoneErrorMessageList errors) {
    	if (errors==null){
              return;
    	}
    	ZoneErrorMessageList item = (ZoneErrorMessageList) this.errors.get(zone);
        // Se non è la prima volta che aggiungo una lista d'errori per la zona indicata
        // allora devo aggiungere ai precedenti gli errori attuali altrimenti aggiungo il nuovo elemento alla lista degli errori.
        if (item == null) {
          item = new ZoneErrorMessageList();
          item.add(errors);
          this.errors.put(zone, item);
        }
        else {
          item.add(errors);
        }
    }//[m] addZoneErrorMessageList

    /**
     * <p>Aggiunge una lista di errori alla lista degli errori globali</p>
     * @param errors ZoneErrorMessageList La lista di errori globali da aggiungere
     */
    public void addZoneErrorMessageList(ZoneErrorMessageList errors) {
      addZoneErrorMessageList(this.GLOBAL_ERROR, errors);
    }//[m] addZoneErrorMessageList

    /**
     * <p>Aggiunge la lista degli errori passata come parametro, rispettando le zone</p>
     * @param errorMessagesList la lista degli errori
     */
    public void add(ErrorMessagesList errorMessagesList) {
      if (errorMessagesList == null) {
        return;
      }

      // Aggiunge le zona alla lista della collezione degli errori
      Iterator zones = errorMessagesList.zones();
      while (zones.hasNext()) {
        String zone = (String) zones.next();
        // Aggiunge gli errori per la specifica zona
        ZoneErrorMessageList errs = errorMessagesList.get(zone);
        if (errs != null) {
          this.addZoneErrorMessageList(zone, errs);
        }
      }
    }//[m] add

    /**
     * <p>Aggiunge l'errore alla lista degli errori generica per una zona generica.</p>
     * @param error l'errore da aggiungere.
     */
    public void addError(ErrorMessage error) {
      if (error != null) {
        ZoneErrorMessageList zoneError = new ZoneErrorMessageList();
        if (zoneError != null) {
          zoneError.add(error);
          addZoneErrorMessageList(zoneError);
        }
      }
    }//[m] addError

    /**
     * <p>Aggiunge l'errore con chiave <code>property</code> per una zona generica.</p>
     * @param error l'errore da aggiungere.
     */
    public void addError(String property, ErrorMessage error) {
      if (error != null) {
        ZoneErrorMessageList zoneError = new ZoneErrorMessageList();
        if (zoneError != null) {
          zoneError.add(property,error);
          addZoneErrorMessageList(zoneError);
        }
      }
    }//[m] addError


    /**
     * <p>Cancella tutti gli errori di questa collezione</p>
     */
    public void clear() {
        errors.clear();
    }//[m] clear

    /**
     * <p>Restituisce lo stato della lista degli errori globali (se &egrave vuota o no)</p>
     * @return <code>true</code> se ??? vuota, <code>false</code> altrimenti
     */
    public boolean isEmpty() {
        return (errors.isEmpty());
    }//[m] isEmpty

    /**
     * <p>Ritorna il numero di errori presenti nell'intera collezione</p>
     * @return il numero di errori della collezione
     */
    public int size() {
      int total = 0;
      for (Iterator i = errors.values().iterator(); i.hasNext(); ) {
        ZoneErrorMessageList ems = (ZoneErrorMessageList) i.next();
        total += ems.size();
      }
      return (total);
    }//[m] size

    /**
     * <p>Ritorna il numero di errori per la zona indicata</p>
     * @param zone il nome della zona
     * @return il numero di errori per la zona indicata
     */
    public int size(String zone) {
      ZoneErrorMessageList item = (ZoneErrorMessageList) errors.get(zone);
      return (item == null) ? 0 : item.size();
    } //[m] size

    /**
     * <p>Recuera la lista degli errori associati ad una specifica zona
     * Se la lista &egrave vuota viene ritornato <code>null</code></p>
     * @param zone il nome della zona
     * @return la collezione degli errori per la zona indicata
     */
    public ZoneErrorMessageList get(String zone) {
      //this.accessed = true;
      ZoneErrorMessageList item = (ZoneErrorMessageList) errors.get(zone);
      return item;
    } //[m] get

    /**
     * <p>Ritorna l'insieme delle zone presenti nella collezione</p>
     * @return un iterator sulle zone che hanno una lista di errori
     */
    public Iterator zones() {
      if (errors.isEmpty()) {
        return Collections.EMPTY_LIST.iterator();
      }
      return errors.keySet().iterator();
    } //[m] zones

}//{c} ErrorMessagesList
