package org.parancoe.utility.error;

import java.io.Serializable;

/**
 * <p>ErrorMessage</p>
 * <p>Un contenitore per i messaggi d'errore, usato nella validazione dei bean o altrove nei controller
 * <br>&Egrave; possibile specificare un messaggio direttamente anziche utilizzare una chiave del resurceBundle, per
 * <br>farlo si dovr&agrave; impostare l'attributo <code>resource</code> a [false] altrimenti viene sempre cercata la chiave</p>
 * @author NEC
 * @version 1.0
 */
public class ErrorMessage implements Serializable {

    /** La chiave dell'errore nel ResurceBundle o il messaggio d'errore vero e proprio  */
    protected String key = null;
    /** I valori di rimpiazzo nei messaggi d'errore del ResurceBundle */
    protected Object[] values = null;
    /** Indica se key rappresenta il messaggio d'errore vero e proprio [false] o una chiave del resurceBundle [true] */
    protected boolean resource = true;

    /**
     * <p>Costruisce un errore senza parti variabili</p>
     *  @param key la chiave nel ResurceBundle
     */
    public ErrorMessage(String key) {
        this(key, null);
    }//[c] ErrorMessage

    /**
     * <p>Costruisce un errore con un solo valore di rimpiazzo</p>
     * @param key la chiave nel ResurceBundle
     * @param value la parte variabile da rimpiazzare nel messaggio
     */
    public ErrorMessage(String key, Object value) {
        this(key, new Object[] {value});
    }//[c] ErrorMessage

    /**
     * <p>Costruisce un errore con un array di valori di rimpiazzo</p>
     * @param key la chiave nel ResurceBundle
     * @param values l'array contenente i valori da rimpiazzare nelle parti variabili dell'errore
     */
    public ErrorMessage(String key, Object[] values) {
        this.key = key;
        this.values = values;
        this.resource = true;
    }//[c] ErrorMessage

    /**
     * <p>Costruisce un errore con la possibilit� di specificare il messaggio direttamente e non tramite chiave del resurceBundle</p>
     * @param key la chiave del'errore o il messaggio d'errore
     * @param resource se <code>true</code> indica che <code>key</code> &egrave; una chiave del ResurceBundle altrimenti &egrave; da considerarsi il vero messaggio d'errore
     */
    public ErrorMessage(String key, boolean resource) {
        this.key = key;
        this.resource = resource;
    }//[c] ErrorMessage

    /**
     * <p>La chiave del messaggio d'errore</p>
     * @return La chiave del messaggio d'errore
     */
    public String getKey() {
        return (this.key);
    }//[m] getKey

    /**
     * <p>I valori di rimpiazzo per le parti variabili del messaggio d'errore</p>
     * @return I valori di rimpiazzo per le parti variabili del messaggio d'errore
     */
    public Object[] getValues() {
        return (this.values);
    }//[m] getValues

    /**
     * <p>Indica se la chiave key &egrave; il vero messaggio d'errore [false] oppure una chiave del ResurceBundle [true]</p>
     * @param resource se <code>true</code> indica che <code>key</code> &egrave; una chiave del ResurceBundle altrimenti &egrave; da considerarsi il vero messaggio d'errore
     */
    public boolean isResource() {
        return (this.resource);
    }//[m] isResource

}//{c} ErrorMessage
