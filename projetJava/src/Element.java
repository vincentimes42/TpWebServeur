import java.util.Date;
import java.util.UUID;

public class Element {

	private String id;
	private Date dateCrea;
	private Date dateModif;
	private String titre;
	private String description;
	
	//constructeur
	
	public Element(){
		this.id = UUID.randomUUID().toString();
		dateCrea = new Date();
		dateModif = new Date();
		this.titre= "";
		this.description= "";
	}
	
	public void afficher(){
		System.out.println("Id: " + id);
		System.out.println("Date creation: " + dateCrea);
		System.out.println("Date modification: "+ dateModif);
		System.out.println("Titre: " + titre);
		System.out.println("Description: " + description);
	}
	
	//accesseur
	public void setTitre(String titre){
		this.dateModif= new Date();
		this.titre= titre;
	}
	
	public void setDescrip(String description){
		this.dateModif= new Date();
		this.description= description;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getTitre(){
		return this.titre;
	}
	
	public Date getDateCrea(){
		return this.dateCrea;
	}
	
	public Date getDateModif(){
		return this.dateModif;
	}
	
	public String getDescrip(){
		return this.description;
	}

	public boolean equals(Element element){
		return (this.titre == element.getTitre() && this.description == element.getDescrip() && this.dateCrea == element.getDateCrea() && this.dateModif == element.getDateModif());
	}
	
	  
}