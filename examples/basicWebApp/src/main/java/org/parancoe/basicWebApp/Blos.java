package org.parancoe.basicWebApp;

import org.parancoe.basicWebApp.blo.SampleBlo;
import org.parancoe.basicWebApp.blo.PersonBo;

public class Blos {
    public SampleBlo sample;
    public PersonBo person;

    public SampleBlo getSample() {
        return sample;
    }

    public void setSample(SampleBlo sample) {
        this.sample = sample;
    }

    public PersonBo getPerson() {
        return person;
    }

    public void setPerson(PersonBo person) {
        this.person = person;
    }
}
