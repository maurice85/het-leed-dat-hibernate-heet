package builderinsertHenny;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Annotations.*;
import POJO.Adres;
import POJO.Klant;

public class BuildTable {
	static Logger logger = LoggerFactory.getLogger(BuildTable.class);
	
	public String createTable(Object o) {
		logger.info("createTable methode gestart");
		String query = "";
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String tableName = "";
		int variableToInsert = 0;
		
		if (o.getClass().isAnnotationPresent(Table.class) 
				&& o.getClass().getAnnotation(Table.class).tableName().length() > 0) {
			tableName = o.getClass().getAnnotation(Table.class).tableName();
		} else 
			tableName = o.getClass().getSimpleName().toUpperCase();
		
		sb.append("CREATE TABLE " + tableName + " (");
		
		Field[] declaredFields = o.getClass().getDeclaredFields();
		
		for (Field field : declaredFields) {
			try {
				field.setAccessible(true);
				
				if (field.isAnnotationPresent(Column.class)) {
					variableToInsert++;
					
					if (variableToInsert > 1) {
						sb.append(", ");
					}
					if (field.getAnnotation(Column.class).columnName().length() > 0) {
						sb.append("" + field.getAnnotation(Column.class).columnName());
					} else {
						sb.append("" + field.getName());
					}
					if (field.getType().isAssignableFrom(Integer.TYPE)) {
						sb.append(" INT(11) UNSIGNED");
						if (field.isAnnotationPresent(ID.class)) {
							
							sb.append(" AUTO_INCREMENT");							
						}
					}
					else if (field.getType().isAssignableFrom(Long.TYPE)) {
						sb.append(" BIGINT(" + field.getAnnotation(Column.class).columnLength()+ ") UNSIGNED");
					}
					else if (field.getType().isAssignableFrom(String.class)) {
						sb.append(" VARCHAR(" + field.getAnnotation(Column.class).columnLength() + ")");
					}
					else System.out.println("Dit data type wordt niet ondersteund");
				} 
				if (field.isAnnotationPresent(ID.class)) {
					variableToInsert++;
					
					sb2.append(" PRIMARY KEY(" + field.getAnnotation(ID.class).name() + ")");
					
				}
				
			} catch (IllegalArgumentException ex) {
				logger.debug("Fout bij het maken van createTable");
				
			}
		}
		logger.info("createTable gelukt");
		sb.append(sb2);
		sb.append(");");
		query = sb.toString();
		System.out.println(query);
		return query;
	} 
	public static void main (String[] args) {
		
	    		BuildTable instance = new BuildTable();
	    		instance.createTable(new Adres());
	}
	

}
