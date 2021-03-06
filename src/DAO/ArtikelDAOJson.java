/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Interface.ArtikelDao;
import POJO.ArtikelPOJO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.emory.mathcs.backport.java.util.Collections;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jeroen 
 */
public class ArtikelDAOJson implements ArtikelDao{
    private final Gson gson = new Gson();
    private final Type artikelType = new TypeToken<HashMap<Integer, ArtikelPOJO>>() {}.getType();
    private final String fileName = "artikelTabel.json";  
    private final static Logger LOGGER = LoggerFactory.getLogger(ArtikelDAOJson.class);
    
    @Override
    public ArtikelPOJO createArtikel(ArtikelPOJO artikel) {
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (FileReader read = new FileReader(fileName);) {
                  
            catalogus = gson.fromJson(read, artikelType);
            int nieuweID = catalogus.size() + 1;
            if (artikel.getArtikelID() == 0) {
                artikel.setArtikelID(nieuweID);
            }
            catalogus.put(artikel.getArtikelID(), artikel);
            try (FileWriter file = new FileWriter(fileName)) {
                  
		file.write(gson.toJson(catalogus, artikelType));  
                LOGGER.trace("artikel toegeveogd aan catalogus ");
            }
        } catch (IOException ex) {
            LOGGER.error("create input/output " +  ex);
        }
        
        return artikel;
    }

    @Override
    public ArtikelPOJO readArtikel(ArtikelPOJO artikel) {
        return readArtikel( artikel.getArtikelID());
    }

    @Override
    public ArtikelPOJO readArtikel(int artikelID) {
        ArtikelPOJO artikel = null; 
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (FileReader read = new FileReader(fileName);) {
                  
            catalogus = gson.fromJson(read, artikelType);
            artikel = catalogus.get(artikelID);
        } 
        catch (IOException ex) {
            LOGGER.error("read input/output " +  ex);
        }
        return artikel;
    }
    
    @Override
    public void updateArtikel(ArtikelPOJO artikel) {
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (FileReader read = new FileReader(fileName);) {
              
            catalogus = gson.fromJson(read, artikelType);
            catalogus.replace(artikel.getArtikelID() ,artikel);
            try (FileWriter file = new FileWriter(fileName)) {
                  
		file.write(gson.toJson(catalogus, artikelType));  
                LOGGER.info("artikel veranderd in catalogus ");
            }
        } 
        catch (IOException ex) {
            LOGGER.error("update input/output " +  ex);
        }
    }

    @Override
    public List<ArtikelPOJO> findAlleArtikelen() {
        HashMap<Integer, ArtikelPOJO> catalogus;
        List<ArtikelPOJO> catAlsLijst = Collections.emptyList();
        try (FileReader read = new FileReader(fileName);) {
                  
            catalogus = gson.fromJson(read, artikelType);
            catAlsLijst =  new ArrayList<>((catalogus.values()));
            
        }
        catch (IOException ex) {
            LOGGER.error("find alleArtikelen input/output " +  ex);
        }
        return catAlsLijst;
    }

    @Override
    public void deleteArtikel(ArtikelPOJO artikel) {
         // vervang door leeg artikel ipv leegmaken om lengte map en dus ID-values te behouden
        HashMap<Integer, ArtikelPOJO> catalogus;
        try (FileReader read = new FileReader(fileName);) {
            ArtikelPOJO leegArtikel = new ArtikelPOJO();      
            catalogus = gson.fromJson(read, artikelType);
            catalogus.replace(artikel.getArtikelID(), leegArtikel);
            try (FileWriter file = new FileWriter(fileName)) {
                  
		file.write(gson.toJson(catalogus, artikelType));  
                LOGGER.info("artikel verwijderd uit catalogus ");
            }
        } 
        catch (IOException ex) {
            LOGGER.error("update input/output " +  ex);
        }
    }
    
}
