package DAO;

import java.sql.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Interface.KlantDAO;
import POJO.Adres;
import POJO.Klant;
import opdracht2.ConnectionFactory;

public class KlantDAOImpl implements KlantDAO {
	static Logger logger = LoggerFactory.getLogger(KlantDAOImpl.class);
   
    
    @Override
    public Klant findKlant(Klant bestaandeKlant) {       
        int klantID = bestaandeKlant.getKlantID();
        String klantVoornaam = bestaandeKlant.getVoornaam();
        String klantAchternaam = bestaandeKlant.getAchternaam();
        
        if (klantID != 0) {        	
        	logger.info("klant zoeken op id -- id was niet nul");
        	return findByID(klantID);
        	}        
        
        else if (klantVoornaam != null && klantVoornaam.length() >= 1 && klantAchternaam.length() >= 1) {
        	logger.info("klant zoeken op voor en achternaam -- namen waren groter dan 0");
        	return findByName(klantVoornaam, klantAchternaam);
        }
        
        else if (klantVoornaam != null && klantVoornaam.length() >= 1) {
        	logger.info("klant zoeken op voornaam -- voornaam was groter dan 0");
        	return FindByName(klantVoornaam);
        	}
        
        else {
        	logger.info("geen bruikbaare velden gevonden om klant te zoeken probeer op andere manier");
        	return null;
        }
    } 
    
    @Override
    public List<Klant> findKlant(Adres klantAdres) {
    	List<Klant> klanten = new ArrayList<>();
        Klant klant = new Klant();
    	int adresId = klantAdres.getAdresID();
    	String query = "SELECT * FROM klant_has_adres WHERE adres_adres_id = " + adresId;
    	try (Connection connection = ConnectionFactory.getMySQLConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(query);
    			ResultSet resultSet = preparedStatement.executeQuery();) {    		
    		
    			while (resultSet.next()) {   
    				int klantID = resultSet.getInt("klant_klant_id");
    				klant = findByID(klantID);
                    klanten.add(klant);
    			}
    	
    	} catch (SQLException ex) {
    		logger.info("gaat iets mis");    		
    	}
    	return klanten;
    }
    
    @Override
    public List<Klant> findAll() {
        List<Klant> klanten = new ArrayList<>();
        Klant klant;
        String query = "SELECT * FROM klant";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        	

            while(resultset.next()) {
                //stop klant object in klanten List
                klant = new Klant();
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
                klanten.add(klant);
            } 
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }     
        logger.info("Klanten gevonden");
        return klanten;
    }

    @Override
    public Klant findByID(int klantID) {
        String query = "SELECT * FROM klant WHERE klant_id = " + klantID;
        Klant klant = new Klant();
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        	
            ///crieer klant en set de gegevens er in            
            if (resultset.next()) {                
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
            }
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        logger.info("Klant gevonden");
        return klant;
    }

    @Override
    public Klant findByName(String voornaam, String achternaam) {
        String query = "SELECT * FROM klant WHERE voornaam = '" + voornaam + "' AND achternaam = '" + achternaam + "'";
        Klant klant = new Klant();
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        
            ///crieer klant en set de gegevens er in           
            if (resultset.next()) {
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
            }
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        logger.info("Klant gevonden");
        return klant;
    }

    @Override
    public Klant FindByName(String voornaam) {
        String query = "SELECT * FROM klant WHERE voornaam = '" + voornaam + "'";
        Klant klant = new Klant();
        try (Connection connection = ConnectionFactory.getMySQLConnection();
        		PreparedStatement stmt = connection.prepareStatement(query);
        		ResultSet resultset = stmt.executeQuery();){
        
            ///crieer klant en set de gegevens er in
            if (resultset.next()) {
                klant.setKlantID(resultset.getInt("klant_ID"));
                klant.setVoornaam(resultset.getString("voornaam"));
                klant.setAchternaam(resultset.getString("achternaam"));
                klant.setTussenvoegsel(resultset.getString("tussenvoegsel"));
                klant.setEmail(resultset.getString("email"));
            }
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }
        logger.info("Klant gevonden");
        return klant;        
    } 
    
    @Override
    public void create(Klant klant) {
        String query = "INSERT INTO klant(voornaam, achternaam, tussenvoegsel, email) VALUES (?, ? ,?, ?)";
        ResultSet resultSet;
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){

            stmt.setString(1, klant.getVoornaam());
            stmt.setString(2, klant.getAchternaam());
            stmt.setString(3, klant.getTussenvoegsel());
            stmt.setString(4, klant.getEmail());
            stmt.executeUpdate();
            
            //wijs door database gegenereerde id toe aan klant
            resultSet = stmt.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                klant.setKlantID(resultSet.getInt(1));
            }            
            logger.info("Klant is aangemaakt");            
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }      
    }

    @Override
    public void update(Klant klant) {
        String query = "UPDATE klant SET voornaam = ?, achternaam = ?, tussenvoegsel = ?, email = ? WHERE klant_id = ?";
        try (Connection connection = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = connection.prepareStatement(query);){

            stmt.setString(1, klant.getVoornaam());
            stmt.setString(2, klant.getAchternaam());
            stmt.setString(3, klant.getTussenvoegsel());
            stmt.setString(4, klant.getEmail());
            stmt.setInt(5, klant.getKlantID());            
            stmt.executeUpdate();
            logger.info("Gegevens zijn succesvol gewijzigd");            
        } catch (SQLException ex) {
        	logger.info("gaat iets mis");
        }     
    }

    @Override
    public void delete(Klant klant){
       
        String query = "DELETE FROM klant_has_adres WHERE Klant_klant_id = " + klant.getKlantID(); 
        String query2 = "DELETE FROM klant WHERE klant_id = " + klant.getKlantID();        
        try (Connection connection = ConnectionFactory.getMySQLConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
        		PreparedStatement stmt2 = connection.prepareStatement(query2);
        		){
        	
            stmt.executeUpdate();
            logger.info("Klant verwijdert uit tussen tabel");
            stmt2.executeUpdate();
            logger.info("Klant verwijderd");
            System.out.println("Klant gegevens zijn succesvol verwijderd");
        } catch (SQLException ex) {
        	logger.info("gaat iets mis in deleteklant" );
                 ex.printStackTrace();
        }   
    }    
}
