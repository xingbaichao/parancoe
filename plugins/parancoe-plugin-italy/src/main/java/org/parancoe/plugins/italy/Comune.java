package org.parancoe.plugins.italy;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.ForeignKey;

/**
 * @author Poggiani Alberto poggialb@gmail.com
 * @author Paolo Dona paolo.dona@seesaw.it
 * @author Paolo Foletto paolo.foletto@gmail.com
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Comune.findByPartialNomeAndProvincia",
        query = "from Comune c where upper(c.nome) like upper(?) and upper(c.provincia.targa) like upper(?) order by c.nome asc")
        })
public class Comune implements Serializable {

  private static final long serialVersionUID = 3309180800477877154L;

  private String id;

  private String nome;

  private String codiceErariale;
 
  private Provincia provincia;

  public Comune() {
    super();
  }

  @Id
  @Column(name = "codice_istat", length = 6)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "comune", length = 100)
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Column(name = "codice_erariale", length = 4)
  public String getCodiceErariale() {
      return codiceErariale;
  }
  
  public void setCodiceErariale(String codiceErariale) {
      this.codiceErariale = codiceErariale;
  }
    
  @ManyToOne
  @JoinColumn(name = "codice_provincia")
  @ForeignKey(name="none")
  public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public String toString() {
    return "Comune{" +
            "id='" + id + '\'' +
            ", nome='" + nome + '\'' +
            ", provincia=" + provincia +
            '}';
  }
}