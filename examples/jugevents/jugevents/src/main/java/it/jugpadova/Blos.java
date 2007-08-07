// Copyright 2006-2007 The Parancoe Team
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
package it.jugpadova;

import it.jugpadova.blo.EventBo;
import it.jugpadova.blo.JuggerBlo;
import it.jugpadova.blo.SampleBlo;
import it.jugpadova.blo.PersonBo;

public class Blos {
    public SampleBlo sample;
    public PersonBo person;
    public EventBo eventBo;
    public JuggerBlo juggerBlo;
    
    
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

    public EventBo getEventBo() {
        return eventBo;
    }

    public void setEventBo(EventBo eventBo) {
        this.eventBo = eventBo;
    }

	public JuggerBlo getJuggerBO() {
		return juggerBlo;
	}

	public void setJuggerBO(JuggerBlo juggerBlo) {
		this.juggerBlo = juggerBlo;
	}
        
}
