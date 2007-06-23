package org.parancoe.persistence.util;

public class DemoBean {
    public String nome;

    private Long id;

    public DemoBean() {
        super();
    }

    public DemoBean(String nome, Long id) {
        this.nome = nome;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Override
    public String toString() {
        return "nome=" + nome + ", id=" + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        // object must be DemoBean at this point
        DemoBean test = (DemoBean) obj;
        return (id == test.getId() || (id != null && id.equals(test.id)))
                && (nome == test.nome || (nome != null && nome.equals(test.nome)));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (id != null) {
            hash += id.hashCode();
        }
        if (nome != null) {
            hash += nome.hashCode();
        }
        return hash == 0 ? super.hashCode() : hash;
    }
}