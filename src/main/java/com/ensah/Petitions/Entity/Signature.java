package com.ensah.Petitions.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
 
@Document(collection = "signatures")
@CompoundIndex(def = "{'cin':1, 'case_id':1}", name = "compound_index")
public class Signature {

    @Id
    private String id;
    @Indexed(unique=true)
    private String cin;
    private String fullName;
    private String email;
    private String idCase;
    

	public Signature() {}
	public Signature(String cin, String fullName, String email, String id_case) {
		this.cin = cin;
		this.fullName = fullName;
		this.email = email;
		this.idCase = id_case;
	}
	
	public String getIdCase() {
		return idCase;
	}

	public void setIdCase(String idCase) {
		this.idCase = idCase;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    
}
