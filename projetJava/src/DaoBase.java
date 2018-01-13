package projet;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;


import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DaoBase {
	private static  String connection = "jdbc:h2:~/test;";
	private static  Sql2o sql2o = new Sql2o(connection, "Clement", "");
	
	/*
	public DaoBase() {
		final String insertQuery="DROP TABLE IF EXISTS LISTE;" + 
				"DROP TABLE IF EXISTS ELEMENT;" + 
				"DROP TABLE IF EXISTS ASSOCIATION;" + 
				"CREATE TABLE LISTE(ID UUID PRIMARY KEY, TITRE VARCHAR(255), DESCRIPTION VARCHAR(255));" + 
				"CREATE TABLE ELEMENT(ID UUID PRIMARY KEY, DATECREA DATE, DATEMODIF DATE, TITRE VARCHAR(255), DESCRIPTION VARCHAR(255));" + 
				"CREATE TABLE ASSOCIATION(IDLISTE UUID, IDELEM UUID,TYPE BIT,PRIMARY KEY (IDLISTE,IDELEM));";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
			 	.executeUpdate();
			 connect.commit();
		}
	}
	
	*/
 	public static  boolean verifCaract(String str ) {
		return str.matches("[a-zA-Z_0-9-]*");
	}
	
 	public void EffacerTout(){
		
		final String insertQuery="DELETE  FROM ASSOCIATION; DELETE FROM ELEMENT; DELETE FROM LISTE";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .executeUpdate();
			 connect.commit();
		}
	}
 	
	public void insert(Liste liste){
		if (verifCaract(liste.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de la liste");
		if (verifCaract(liste.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans description liste");
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
	
	public void insert(Liste listeInsert, Liste listeRecept){ // modif param de idliste � liste
		if (verifCaract(listeInsert.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre");
		if (verifCaract(listeInsert.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre");
		if (verifCaract(listeRecept.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de la liste");
		if (verifCaract(listeRecept.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans description liste");
		if (listeRecept.getId()==listeInsert.getId()) throw new IllegalArgumentException("M�me Id pour les deux listes");
		
		final String insertQuery="INSERT INTO LISTE(ID,TITRE,DESCRIPTION) "+ "VALUES(:ID, :TITRE, :DESCRIPTION) "+ "VALUES(:ID, :TITRE, :DESCRIPTION)";
		final String insertQuery2="INSERT INTO ASSOCIATION(IDLISTE,IDELEM) "+ "VALUES(:IDLISTE,:IDELEM)" + "VALUES(:TYPE,:type)";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .addParameter("ID", listeInsert.getId())
		        .addParameter("TITRE", listeInsert.getTitre())
		        .addParameter("DESCRIPTION", listeInsert.getDescrip())		        
		        		        
		        .executeUpdate();
			 connect.createQuery(insertQuery2)  //ajout du lien dans la table Association
		        .addParameter("IDLISTE", listeRecept.getId())
		        .addParameter("IDELEM", listeRecept.getId())
		        .addParameter("type", 0)
		        .executeUpdate();
			 connect.commit();
		}
	}
	
	
	public void insert(Element element, Liste liste){ // modif param de idliste � liste
		if (verifCaract(element.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre");
		if (verifCaract(element.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre");
		if (verifCaract(liste.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de la liste");
		if (verifCaract(liste.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans description liste");
		
		final String insertQuery="INSERT INTO ELEMENT(ID,DATECREA,DATEMODIF,TITRE,DESCRIPTION) "+ "VALUES(:ID, :DATECREA, :DATEMODIF, :TITRE, :DESCRIPTION)";
		final String insertQuery2="INSERT INTO ASSOCIATION(IDLISTE,IDELEM,TYPE) "+ "VALUES(:IDLISTE,:IDELEM,:type)";
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
		        .addParameter("type", 1)
		        .executeUpdate();
			 connect.commit();
		
		}
		
	}
	
	public void modif(String Idelem, Element element){
		if (verifCaract(element.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de l'elem");
		if (verifCaract(element.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre de l'elem");
		final String insertQuery="UPDATE ELEMENT SET TITRE=:TITRE,DESCRIPTION=:DESCRIPTION,DATEMODIF=:DATEMODIF WHERE ID=:Idelem";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .addParameter("TITRE", element.getTitre())
		        .addParameter("DESCRIPTION", element.getDescrip())		        
		        .addParameter("DATEMODIF", new Date())		//probl�me on fait une modification sans passer par setTitre et setDescripton donc DateModif est pas chang�e        
		        .addParameter("Idelem", Idelem)
		        .executeUpdate();
			 connect.commit();
		}
	}
	
	public void modif(String IdListeModif,Liste liste){
		if (verifCaract(IdListeModif)== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre");
		if (verifCaract(liste.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre");
		if (verifCaract(liste.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre");
		final String insertQuery="UPDATE LISTE SET TITRE=:TITRE,DESCRIPTION=:DESCRIPTION WHERE ID=:ID";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)
		        .addParameter("TITRE", liste.getTitre())
		        .addParameter("DESCRIPTION", liste.getDescrip())		        
		        .addParameter("ID", IdListeModif)
		        .executeUpdate();
			 connect.commit();
		}
	}
	
	
	public void supprimer(Element element,Liste liste) {
		if (verifCaract(element.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de l'elem");
		if (verifCaract(element.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre de l'elem");
		if (verifCaract(liste.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de la liste");
		if (verifCaract(liste.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans description liste");
		
		final String insertQuery="DELETE  FROM ASSOCIATION WHERE IDELEM=:IDE AND IDLISTE=:IDL;";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)	        
		        .addParameter("IDE", element.getId())
		        .addParameter("IDL", liste.getId())
		        .executeUpdate();
			 connect.commit();
		}
		if (elementSansListe(element)) {
			final String insertQuery2="DELETE FROM ELEMENT WHERE ID=:ID;";
			try(Connection connect = sql2o.beginTransaction()){
				 connect.createQuery(insertQuery2)	        
			        .addParameter("ID", element.getId())
			        .executeUpdate();
				 connect.commit();
			}
		}
	}
	public void supprimer(Liste sousliste,Liste liste) {
		if (verifCaract(sousliste.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de l'elem");
		if (verifCaract(sousliste.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre de l'elem");
		if (verifCaract(liste.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de la liste");
		if (verifCaract(liste.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans description liste");
		
		final String insertQuery="DELETE  FROM ASSOCIATION WHERE IDELEM=: IDE AND IDLISTE=: IDL AND TYPE=0;";
		try(Connection connect = sql2o.beginTransaction()){
			 connect.createQuery(insertQuery)	        
		        .addParameter("IDE", sousliste.getId())
		        .addParameter("IDL", liste.getId())
		        .executeUpdate();
			 connect.commit();
		}
			
		supprimer(sousliste);
			
		
	}
	
	public void supprimer(Element element){
		if (verifCaract(element.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de l'elem");
		if (verifCaract(element.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre de l'elem");
		
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
	
	public void supprimer(Liste liste){
		if (verifCaract(liste.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de la liste");
		if (verifCaract(liste.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans description liste");
		ListIterator<Element> li = getElementsPerList(liste).listIterator();  
		Element element;
		while(li.hasNext()) {
				 element = li.next();
				 supprimer(element,liste);
		}
		
		ListIterator<Liste> liL = getListPerList(liste).listIterator();  
		Liste sousListe;	
		while(liL.hasNext()) {
			 sousListe = liL.next();
			 supprimer(sousListe);
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

	public  List<Liste> getListsByTitre(String titre){
		if (verifCaract(titre)== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre ");;
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE TITRE = :titre";

		    return con.createQuery(query)
		    		.addParameter("titre", titre)
		    		.executeAndFetch(Liste.class);
		  }
	}
	
	public  List<Liste> getListsByDescription(String description){
		
		if (verifCaract(description)== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans description liste");
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE DESCRIPTION = :description";

		    return con.createQuery(query)
		    		.addParameter("description", description)
		    		.executeAndFetch(Liste.class);
		  }
	}
	
	
	public  List<Element> getElementsPerList(Liste list){ 
		if (verifCaract(list.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre");
		if (verifCaract(list.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre");
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM ELEMENT WHERE ID IN(SELECT IDELEM FROM ASSOCIATION WHERE IDLISTE =:idliste AND TYPE=1)";
		    		
		    return con.createQuery(query)
	    		.addParameter("idliste", list.getId())
		        .executeAndFetch(Element.class);
		  }
	}
	public  List<Liste> getListPerList(Liste list){ 
		if (verifCaract(list.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre");
		if (verifCaract(list.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le tritre");
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE ID IN(SELECT IDELEM FROM ASSOCIATION WHERE IDLISTE =:idliste AND TYPE=0)";
		    		
		    return con.createQuery(query)
	    		.addParameter("idliste", list.getId())
		        .executeAndFetch(Liste.class);
		  }
	}
	
	public  boolean elementSansListe(Element element){
		return (getListPerElement(element).size()==0);
	}
	
	public  List<Liste> getListPerElement(Element element){ 
		if (verifCaract(element.getTitre() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre de l'elem");
		if (verifCaract(element.getDescrip() )== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans descript de l'elem");
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE ID IN(SELECT IDLISTE FROM ASSOCIATION WHERE IDELEM =:idelem)";
		    		
		    return con.createQuery(query)
	    		.addParameter("idelem", element.getId())
		        .executeAndFetch(Liste.class);
		  }
	}
	
	public  List<Element> getAllElements(){  
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT  * FROM ELEMENT"; // *  � la place des champs ?

		    return con.createQuery(query)
		        .executeAndFetch(Element.class);
		  }
	}
	
	public  List<Element> getElementsByCreateDate(Date dateCrea){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT  * FROM ELEMENT WHERE DATECREA = :dateCrea";

		    return con.createQuery(query)
		        .addParameter("DATECREA", dateCrea)
		        .executeAndFetch(Element.class);
		  }
	}
	
	public  List<Element> getElementsByModifDate(Date dateModif){
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT  * FROM ELEMENT WHERE DATEMODIF = :dateModif";

		    return con.createQuery(query)
		        .addParameter("DATEMODIF", dateModif)
		        .executeAndFetch(Element.class);
		    	
		  }
	}
	
	public static List<Element> getElementsByTitre(String titre){
		if (verifCaract(titre)== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans le titre");
		
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM ELEMENT WHERE TITRE = :titre";

		    return con.createQuery(query)
		    		.addParameter("titre", titre)
		    		.executeAndFetch(Element.class);
		  }
	}
	
	public static List<Element> getElementsByID(String Idelem){
		if (verifCaract(Idelem)== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans param");
		
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM ELEMENT WHERE ID= :idelem";
		    return con.createQuery(query)
		    		.addParameter("idelem",Idelem)
		    		.executeAndFetch(Element.class);
		  }
	}

	public static List<Liste> getListesByID(String Idliste){
		if (verifCaract(Idliste)== false)  throw new IllegalArgumentException("caract�re sp�ciale utilis� dans param");
		try (Connection con = sql2o.open()) {
		    final String query =
		        "SELECT * FROM LISTE WHERE ID= :idliste";
		    return con.createQuery(query)
		    		.addParameter("idliste",Idliste)
		    		.executeAndFetch(Liste.class);
		  }
	}
	
}
