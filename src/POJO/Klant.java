package POJO;

import Annotations.*;

@Entity (className = "KLANT")
@Table (tableName = "KLANT")
public class Klant {
	
	@ID
	@Column
	private int klantID;
	@Column
    private String voornaam, achternaam, tussenvoegsel, email;

    public int getKlantID() {
        return klantID;
    }

    public void setKlantID(int klantID) {
        this.klantID = klantID;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Klant{" + "klantID=" + klantID + ", voornaam=" + voornaam + 
                ", achternaam=" + achternaam + ", tussenvoegsel=" + tussenvoegsel + ", email=" + email + '}';
    }
}
