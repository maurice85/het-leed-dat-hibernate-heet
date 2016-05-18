package opdracht2;

import java.sql.*;
import java.util.List;

public interface AdresDao {
	public Adres findAdres(String straatnaam, String postcode, int huisnummer,
			String toevoeging, String woonplaats) throws SQLException;
	public Adres findAdres(String straatnaam) throws SQLException;
	public Adres findAdres(String postcode, int huisnummer) throws SQLException;
	public Adres findAdres(Klant klant) throws SQLException;	
	public void update(int klant_id, Adres adres) throws SQLException;
	public List<Adres> findAll() throws SQLException;
	
	
}
