package org.parancoe.plugins.italy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.ForeignKey;

/**
 * POJO
 *
 * @author Poggiani Alberto poggialb@gmail.com
 * @author Dona' Paolo paolo.dona@seesaw.it
 */
@Entity
public class Regione implements Serializable {

  private static final long serialVersionUID = -3629773053122916443L;

  private String id;

  private String nome;

  private List<Provincia> provincia;

  public Regione() {
    super();
  }

  @Id
  @Column(name = "codice_istat", length = 2)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "regione", length = 50)
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @OneToMany(mappedBy = "regione")
  @ForeignKey(name="none")
  public List<Provincia> getProvincie() {
    return provincia;
  }

  public void setProvincie(List<Provincia> provincia) {
    this.provincia = provincia;
  }

  public void addProvincia(Provincia provincia) {
    provincia.setRegione(this);
    getProvincie().add(provincia);
  }

  public void removeProvincia(Provincia provincia) {
    getProvincie().remove(provincia);
    provincia.setRegione(null);
  }

  public String toString() {
    return "Regione{" +
            "id='" + id + '\'' +
            ", nome='" + nome + '\'' +
            ", provincia=" + provincia +
            '}';
  }
}