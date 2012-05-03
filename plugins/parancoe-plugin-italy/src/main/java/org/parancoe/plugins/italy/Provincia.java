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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.ForeignKey;

/**
 * @author Poggiani Alberto poggialb@gmail.com
 * @author Paolo Dona paolo.dona@seesaw.it
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Provincia.findByPartialNome",
        query = "from Provincia p where upper(p.nome) like upper(?) order by p.nome asc"),
@NamedQuery(name = "Provincia.findByPartialTarga",
        query = "from Provincia p where upper(p.targa) like upper(?) order by p.targa asc")
        })
public class Provincia implements Serializable {

  private static final long serialVersionUID = -8469022923445394832L;

  private String id;

  private String targa;

  private String nome;

  private List<Comune> comune;

  private Regione regione;

  public Provincia() {
    super();
  }

  @Id
  @Column(name = "codice_istat", length = 3)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "provincia", length = 50, unique = true)
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @OneToMany(mappedBy = "provincia")
  @ForeignKey(name="none")
  public List<Comune> getComuni() {
    return comune;
  }

  public void setComuni(List<Comune> comune) {
    this.comune = comune;
  }

  public void addComune(Comune comune) {
    comune.setProvincia(this);
    getComuni().add(comune);
  }

  public void removeComune(Comune comune) {
    getComuni().remove(comune);
    comune.setProvincia(null);
  }

  @ManyToOne
  @JoinColumn(name = "codice_regione")
  @ForeignKey(name="none")
  public Regione getRegione() {
    return regione;
  }

  public void setRegione(Regione regione) {
    this.regione = regione;
  }

  @Column(name = "sigla", length = 2, unique = true)
  public String getTarga() {
    return targa;
  }

  public void setTarga(String targa) {
    this.targa = targa;
  }

  public String toString() {
    return "Provincia{" +
            "id='" + id + '\'' +
            ", targa='" + targa + '\'' +
            ", nome='" + nome + '\'' +
            ", regione=" + regione +
            '}';
  }
}