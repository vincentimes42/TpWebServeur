import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Liste {

	private String id;
	private String titre;
	private String description;
	
	
	
	//constructeur
	public Liste(){
		this.id= UUID.randomUUID().toString();
		this.titre= "" ;
		this.description= "";
	}
	
	/*
	public void afficher(){
		for(Iterator<Element> i = liste.iterator(); i.hasNext();){
			Element element = i.next();
			System.out.println(element.getTitre()+" "+element.getDescrip());
		}
	}
	
	public void ajoutElem(Element element){
		liste.add(element);		
	}
	public void ajoutElem(String titre, String description){
		Element element =new Element();
		element.setTitre(titre);
		element.setDescrip(description);
		liste.add(element);		
	}
	public void inserertElem(int index, Element element){
		liste.add(index, element);
	}
	
	public void inserertElem(int index,String titre, String description){
		Element element =new Element();
		element.setTitre(titre);
		element.setDescrip(description);
		liste.add(index,element);
	}
	
	public Element rechercherElem(String id){
		for(Iterator<Element> i = liste.iterator(); i.hasNext();){
			Element element = i.next();
			if (element.getId()==id){
				return element;
			}
		}
		return null;
	}

	
	/*public element suppElem(Element element){
		liste.remove(element);
		return element;
	}*/
	
	
	//accesseurs
	
	public void setTitre(String titre){
		this.titre= titre;
	}
	
	public void setDescrip(String description){
		this.description= description;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getTitre(){
		return this.titre;
	}
	
	public String getDescrip(){
		return this.description;
	}
	
		
}
