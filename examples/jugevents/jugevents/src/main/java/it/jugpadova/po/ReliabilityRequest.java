//Copyright 2006-2007 The Parancoe Team
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
package it.jugpadova.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * Represents informations concerning reliability request.
 * for a specific jugger.
 * @author Enrico Giurin
 *
 */
@Entity
public class ReliabilityRequest extends EntityBase implements Serializable {
	
	public static final int RELIABILITY_REQUIRED = 1;
	public static final int RELIABILITY_GRANTED = 2;
	public static final int RELIABILITY_NOT_GRANTED = 3;
	public static final int RELIABILITY_REVOKED = 4;
	
	private int status = 0;
	private String motivation;
	private String adminResponse;
	private Date dateRequest;
	private Date dateAdminResponse;
	
	public String getAdminResponse() {
		return adminResponse;
	}
	public void setAdminResponse(String adminResponse) {
		this.adminResponse = adminResponse;
	}
	@Temporal(value = TemporalType.DATE)
	public Date getDateAdminResponse() {
		return dateAdminResponse;
	}
	public void setDateAdminResponse(Date dateAdminResponse) {
		this.dateAdminResponse = dateAdminResponse;
	}
	@Temporal(value = TemporalType.DATE)
	public Date getDateRequest() {
		return dateRequest;
	}
	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}
	public String getMotivation() {
		return motivation;
	}
	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
