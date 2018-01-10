import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DaoBase {
	private static String connection = "jdbc:h2:~/test;";
	private static Sql2o sql2o = new Sql2o(connection, "Clement", "");
	
	public void insertListe(Liste liste){
		final String insertQuery="INSERT INTO LISTE(ID,TITRE,DESCRIPTION) "+ "VALUES(:ID, :TITRE, :DESCRIPTION)";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .addParameter("ID", liste.getId())
		        .addParameter("TITRE", liste.getTitre())
		        .addParameter("DESCRIPTION", liste.getDescrip())
		        .executeUpdate();
			 connect.commit();
		}
	}
	
	public void insertElement(Element element, Liste liste){ // modif param de idliste à liste
		final String insertQuery="INSERT INTO ELEMENT(ID,TITRE,DESCRIPTION,DATECREA,DATEMODIF) "+ "VALUES(:ID, :TITRE, :DESCRIPTION, :DATECREA, :DATEMODIF)";
		final String insertQuery2="INSERT INTO ASSOCIATION(IDLISTE,IDELEM) "+ "VALUES(:IDLISTE,:IDELEM)";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .addParameter("ID", element.getId())
		        .addParameter("TITRE", element.getTitre())
		        .addParameter("DESCRIPTION", element.getDescrip())		        
		        .addParameter("DATECREA", element.getDateCrea())		        
		        .addParameter("DATEMODIF", element.getDateModif())		        
		        .executeUpdate();
			 connect.createQuery(insertQuery2)  //ajout du lien dans la table Association
		        .addParameter("IDLISTE", liste.getId())
		        .addParameter("IDELEM", element.getId())		        
		        .executeUpdate();
			 connect.commit();
		
		}
		
	}
	
	public void modifElement(Element element){
		final String insertQuery="UPDATE ELEMENT SET TITRE=:TITRE,DESCRIPTION=:DESCRIPTION,DATEMODIF=:DATEMODIF WHERE IDELEM=:ID";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .addParameter("TITRE", element.getTitre())
		        .addParameter("DESCRIPTION", element.getDescrip())		        
		        .addParameter("DATEMODIF", new Date())		//problème on fait une modification sans passer par setTitre et setDescripton donc DateModif est pas changée        
		        .addParameter("ID", element.getId())
		        .executeUpdate();
			 connect.commit();
		}
	}
	
	public void modifListe(Liste liste){
		final String insertQuery="UPDATE LISTE SET TITRE=:TITRE,DESCRIPTION=:DESCRIPTION WHERE IDLISTE=:ID";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .addParameter("TITRE", liste.getTitre())
		        .addParameter("DESCRIPTION", liste.getDescrip())		        
		        .addParameter("ID", liste.getId())
		        .executeUpdate();
			 connect.commit();
		}
	}
	
	public void supprElementDansListe(Element element,Liste liste) {
		final String insertQuery="DELETE  FROM ASSOCIATION WHERE IDELEM=:idelem AND IDLIST=: idlist;";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)	        
		        .addParameter("idelem", element.getId())
		        .addParameter("idlist", liste.getId())
		        .executeUpdate();
			 connect.commit();
		}
		if (elementSansListe(element)) supprElement(element)  ;
		
	}
	
	public void supprElement(Element element){
		final String insertQuery="DELETE FROM ELEMENT WHERE ID=:ID;";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)	        
		        .addParameter("ID", element.getId())
		        .executeUpdate();
			 connect.commit();
		}
		
		final String insertQuery2="DELETE FROM ASSOCIATION WHERE IDELEM=:IDELEM;";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery2)	        
		        .addParameter("IDELEM", element.getId())
		        .executeUpdate();
			 connect.commit();
		}
	}
	
	public void supprListe(Liste liste){
		ListIterator<Element> li = getElementsPerList(liste).listIterator();  
		Element element;
		while(li.hasNext()) {      
			element = li.next();
			supprElementDansListe(element,liste);
		}
		
		final String insertQuery="DELETE FROM LISTE WHERE ID=:ID;";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)	        
		        .addParameter("ID", liste.getId())
		        .executeUpdate();
			 connect.commit();
		}
		
	}
	
	public static List<Liste> getAllLists(){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE";

		    return con.createQuery(query)
		        .executeAndFetch(Liste.class);
		  }
	}
	
	public static List<Element> getListsByID(Liste liste){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE ID= :idlist";
		    return con.createQuery(query)
		    		.addParameter("idlist",liste.getId())
		    		.executeAndFetch(Element.class);
		  }
	}
	
	public static List<Liste> getListsByTitre(String titre){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE TITRE = :titre";

		    return con.createQuery(query)
		    		.addParameter("titre", titre)
		    		.executeAndFetch(Liste.class);
		  }
	}
	
	public static List<Liste> getListsByDescription(String description){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE DESCRIPTION = :description";

		    return con.createQuery(query)
		    		.addParameter("description", description)
		    		.executeAndFetch(Liste.class);
		  }
	}
	
	public static List<Element> getElementsPerList(Liste list){ 
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM ELEMENT WHERE ID IN(SELECT IDELEM FROM ASSOCIATION WHERE IDLISTE =:idliste)";
		    		
		    return con.createQuery(query)
	    		.addParameter("idliste", list.getId())
		        .executeAndFetch(Element.class);
		  }
	}
	
	public static boolean elementSansListe(Element element){
		return (getListPerElement(element).size()==0);
	}
	
	public static List<Liste> getListPerElement(Element element){ 
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE ID IN(SELECT IDLISTE FROM ASSOCIATION WHERE IDELEM =:idelem)";
		    		
		    return con.createQuery(query)
	    		.addParameter("idelem", element.getId())
		        .executeAndFetch(Liste.class);
		  }
	}
	
	public static List<Element> getAllElements(){  
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT  * FROM ELEMENT"; // *  à la place des champs ?

		    return con.createQuery(query)
		        .executeAndFetch(Element.class);
		  }
	}
	
	public static List<Element> getElementsByCreateDate(Date dateCrea){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT  * FROM ELEMENT WHERE DATECREA = :dateCrea";

		    return con.createQuery(query)
		        .addParameter("DATECREA", dateCrea).executeAndFetch(Element.class);
		  }
	}
	
	public static List<Element> getElementsByModifDate(Date dateModif){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT  * FROM ELEMENT WHERE DATEMODIF = :dateModif";

		    return con.createQuery(query)
		        .addParameter("DATEMODIF", dateModif).executeAndFetch(Element.class);
		  }
	}
	
	public static List<Element> getElementsByTitre(String titre){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM ELEMENT WHERE TITRE = :titre";

		    return con.createQuery(query)
		    		.addParameter("titre", titre)
		    		.executeAndFetch(Element.class);
		  }
	}
	
	public static List<Element> getElementsByID(Element element){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM ELEMENT WHERE ID= :idelem";
		    return con.createQuery(query)
		    		.addParameter("idelem",element.getId())
		    		.executeAndFetch(Element.class);
		  }
	}
	
}