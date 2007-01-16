package org.parancoe.basicWebApp;

import org.parancoe.basicWebApp.dao.PersonDao;
import org.parancoe.basicWebApp.dao.SampleDao;

public class Daos {
    public PersonDao person;
    public SampleDao sample;

    public PersonDao getPerson() {
        return person;
    }

    public void setPerson(PersonDao person) {
        this.person = person;
    }

    public SampleDao getSample() {
        return sample;
    }

    public void setSample(SampleDao sample) {
        this.sample = sample;
    }
}