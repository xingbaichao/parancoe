package org.parancoe.plugins.italy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.parancoe.persistence.po.hibernate.EntityBase;

@Entity
@NamedQueries({
@NamedQuery(name = "Procura.findByComune",
        query = "from Procura p where upper(p.comune) = upper(?) order by p.comune asc"),
@NamedQuery(name = "Procura.findByPartialComune",
        query = "from Procura p where upper(p.comune) like (upper(?) || '%') order by p.comune asc")
        })
/**
 * @author paolo.dona@seesaw.it
 */
public class Procura extends EntityBase {

    private static final long serialVersionUID = 171932473369840618L;

    private String nome;
    private String indirizzo;
    private String cap;
    private String comune;
    private String provincia;
    private String telefono;
    private String mail;
    private String fax;
    private String titoloReferente;
    private String referente;

    public Procura() {
    }

    public Procura(String nome, String indirizzo, String cap, String comune, String provincia, String telefono, String mail, String fax) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.comune = comune;
        this.provincia = provincia;
        this.telefono = telefono;
        this.mail = mail;
        this.fax = fax;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Column(length = 500)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }


    public String getTitoloReferente() {
        return titoloReferente;
    }

    public void setTitoloReferente(String titoloReferente) {
        this.titoloReferente = titoloReferente;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }

    public String toString() {
        return "Procura{" +
                "nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", cap='" + cap + '\'' +
                ", comune='" + comune + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
