/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Italy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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