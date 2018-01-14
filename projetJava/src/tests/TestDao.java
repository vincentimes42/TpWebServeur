package test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;







public class TestDao{
	DaoBase dao = new DaoBase();
	/*
	@Test()
	public void Test__DaoBase() {
		
	}
	*/
	//test d'absence de caract spéciaux dans le tritre et la description des élément et  des listes afin des soucis lors du passsage en SQL
	
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_insertListe() { // test caract spé
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("a1~~~)]");
		liste.setDescrip("ceci est une liste de courses");
		dao.insert(liste);
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_insertElem() { // test caract spé
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	Element elem = new Element();
		elem.setTitre("~~~[");
		elem.setDescrip("3jours");
		dao.insert(liste);
		dao.insert(elem,liste);
			
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_modifElem() { // test caract spé
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	Element elem = new Element();
		elem.setTitre("A");
		elem.setDescrip("3jours");
		Element elem2 = new Element();
		elem2.setTitre("~~~[");
		elem2.setDescrip("3jours");
		dao.insert(liste);
		dao.insert(elem2,liste);
		dao.modif(elem.getId(),elem);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_modif() { // test caract spé
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("liste]");
    	Liste liste2= new Liste();
    	liste2.setTitre("é~~~}]");
		dao.insert(liste);
		dao.modif(liste.getId(),liste2);
    }
	
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_supprimer() { 
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	Element elem = new Element();
		elem.setDescrip("3jours");
		dao.insert(liste);
		elem.setTitre(")");
		dao.supprimer(elem,liste);
		
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_supprElement() {  
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	Element elem = new Element();
		elem.setTitre(")");
		elem.setDescrip("3jours");
		dao.insert(liste);
		dao.supprimer(elem);
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_supprListe() {  
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	Element elem = new Element();
		elem.setTitre("A");
		elem.setDescrip("3jours");
		dao.insert(liste);
		dao.insert(elem,liste);
		liste.setTitre(")");
		dao.supprimer(liste);
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_getListsByTitre(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	liste.setTitre(")");
    	dao.getListsByTitre(liste.getTitre());
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_getElementPerList(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	liste.setTitre(")");
    	dao.getElementsPerList(liste);
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_getListPerElement(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	Element elem = new Element();
		elem.setTitre(")");
		elem.setDescrip("3jours");
		dao.insert(elem,liste);
    	dao.getListPerElement(elem);
    	
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_getElemByTitre(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	Element elem = new Element();
		elem.setTitre("A");
		elem.setDescrip("3jours");
		dao.insert(elem,liste);
		elem.setTitre(")");
    	dao.getElementsByTitre(elem.getTitre());
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_getElemById(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
    	dao.insert(liste);
		dao.insert(elem,liste);
    	dao.getElementsByID(")");
    }
    
    
    // test pour vérifier que les fonctions avec  les listes et éléments fonctionnent  
    
    @Test()  
    public void Test__Vide_insertListe() { 
    	dao.EffacerTout();
    	Liste liste= new Liste();
		dao.insert(liste);
		assertTrue(liste.getId().equals(dao.getListesByID(liste.getId()).get(0).getId()));
		
    }
    
    @Test
    public void Test__Vide_insertElem() { 
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
		dao.insert(liste);
		dao.insert(elem,liste);
		assertTrue(elem.getId().equals(dao.getElementsByID(elem.getId()).get(0).getId()));
		
		
    }	
    	
			
  
    
    @Test
    public void Test__Vide_modifElem() { 
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	Element elem = new Element();
		elem.setTitre("A");
		elem.setDescrip("3jours");
		Element elem2 = new Element();
		elem2.setTitre("B");
		elem2.setDescrip("B");
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.modif(elem.getId(),elem2);
		assertTrue(elem2.getTitre().equals(dao.getElementsByID(elem.getId()).get(0).getTitre()));
		assertTrue(elem2.getDescrip().equals(dao.getElementsByID(elem.getId()).get(0).getDescrip()));
    }
    
    
    @Test
    public void Test___Vide_modif() { 
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("liste");
    	liste.setDescrip("a");
    	Liste liste2= new Liste();
		dao.insert(liste);
		dao.modif(liste.getId(),liste2);
		assertTrue(liste2.getTitre().equals(dao.getListesByID(liste.getId()).get(0).getTitre()));
		assertTrue(liste2.getDescrip().equals(dao.getListesByID(liste.getId()).get(0).getDescrip()));
    }
	
    @Test
    public void Test__Vide_supprimerElemDansListe() {   
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.supprimer(elem,liste);
		assertTrue(dao.getElementsByID(elem.getId()).size()==0);
		
		
    }
    
    @Test()
    public void Test__Vide_supprElement() {  
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	Element elem = new Element();
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.supprimer(elem);
		assertTrue(dao.getElementsByID(elem.getId()).size()==0);
    	
    }
    
    @Test()
    public void Test__Vide_supprListe() {  
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.supprimer(liste);
		assertTrue(dao.getListesByID(liste.getId()).size()==0);
    }
    
    @Test()
    public void Test__Vide_getListsByTitre(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	dao.insert(liste);
    	assertEquals(1,dao.getListsByTitre(liste.getTitre()).size());
    	
    }
    
    
    @Test()
    public void Test__Vide_getElementPerList(){ 
    	dao.EffacerTout();
    	Liste listec= new Liste();
    	listec.setTitre("listea");
    	dao.insert(listec);
    	Element elem = new Element();
		dao.insert(elem,listec);
    	assertTrue(elem.getTitre().equals(dao.getElementsPerList(listec).get(0).getTitre()));
    }
    
    
    @Test()
    public void Test__Vide_getListPerElement(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	Element elem = new Element();
		dao.insert(elem,liste);
		assertTrue(liste.getId().equals(dao.getListPerElement(elem).get(0).getId()));
    	
    }
    
    
    @Test()
    public void Test__Vide_getElemByTitre(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	Element elem = new Element();
		elem.setTitre("testTitre");
		elem.setDescrip("3jours");
		dao.insert(elem,liste);
    	assertTrue("testTitre".equals(dao.getElementsByTitre("testTitre").get(0).getTitre()));
    }
    
    @Test()
    public void Test__Vide_getElemById(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	Element elem = new Element();
		dao.insert(elem,liste);
		assertTrue(elem.getId().equals(dao.getElementsByID(elem.getId()).get(0).getId()));
    }
    
    // tests pour vérifier le comportement des fonctions avec des listes contenats des listes et des éléments dans plusieurs listes
    
    
    @Test()  
    public void Test__MultiListe_insertListe() { 
    	dao.EffacerTout();
    	Liste Surliste= new Liste();
    	Liste Sousliste= new Liste();
    	dao.insert(Surliste);
		dao.insert(Sousliste,Surliste);
		assertTrue(Sousliste.getId().equals(dao.getListPerList(Surliste).get(0).getId())); // on récupère la sous liste contenu dans la surliste
		
    }
    @Test() 
    public void Test__MultiListe_supprListe() {  //on vérifie la supréssion en cascade, en suprimant la surListe on supprime toutes les listes et les éléments 
    	dao.EffacerTout();					//qui ne sont pas liés à d'autres listes ou éléments et on recommence le process pour les souslistes
    	Liste Surliste= new Liste();
    	Liste Sousliste= new Liste();
    	Element elem = new Element();
		dao.insert(Surliste);
		dao.insert(Sousliste,Surliste);
		dao.insert(elem,Sousliste);				//ici on supprime Surlistes et ce quelle contient si ce n'est pas liés à autre chose, 
		dao.supprimer(Surliste);				//si Surliste contient une SousListe la fonction est rappelée récursivement
		assertTrue(dao.getAllLists().size()==0);
		assertTrue(dao.getAllElements().size()==0); 
		
    }
    
    @Test
    public void Test__ElementDansPlsListes_insertElem() { //on vérifie qu'un mêMe élément peux etre inséré dans plusieurs listes 
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Liste liste2= new Liste();
    	Element elem = new Element();
		dao.insert(liste1);
		dao.insert(liste2);
		dao.insert(elem,liste1);
		dao.insert(elem,liste2);
		assertTrue(dao.getElementsPerList(liste2).get(0).getId().equals(dao.getElementsPerList(liste1).get(0).getId()));
		
		
    }
    
    @Test
    public void Test__ElementDansPlsListes_supprimerElemDansListe() {   
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Liste liste2= new Liste();
    	Element elem = new Element();
		dao.insert(liste1);
		dao.insert(liste2);
		dao.insert(elem,liste1);
		dao.insert(elem,liste2);
		dao.supprimer(elem,liste1);
		assertTrue(elem.getId().equals(dao.getElementsPerList(liste2).get(0).getId())); // On test que on a supprimé l'élément de la liste 1 
																						//mais comme il est aussi lié à la liste 2 il est conservé dans la table ELEMENT
		
    }
    
    @Test()
    public void Test__ElementDansPlsListes_supprElement() {  //on test la supression d'un élément et de tous les liens
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Liste liste2= new Liste();
    	Element elem = new Element();
		dao.insert(liste1);
		dao.insert(liste2);
		dao.insert(elem,liste1);
		dao.insert(elem,liste2);
		dao.supprimer(elem);
		assertTrue(dao.getElementsByID(elem.getId()).size()==0);
    	
    }
    
    //test pour les fonctions SelectListes et  SelectElement
    
    @Test()
    public void Test__Titre_selectListe() {
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	liste1.setTitre("test");
    	Liste liste2= new Liste();
    	Liste liste3= new Liste();
		dao.insert(liste1);
		dao.insert(liste2);
		dao.insert(liste3);
		List<Liste> resultat = dao.SelectListe(dao.getListsByTitre(liste1.getTitre()),dao.getListsByDescription(""), dao.getListesByID(""));
		assertTrue(liste1.getTitre().equals(resultat.get(0).getTitre()));
    }
    @Test()
    public void Test__Description_selectListe() {
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	liste1.setDescrip("test");
    	Liste liste2= new Liste();
    	Liste liste3= new Liste();
		dao.insert(liste1);
		dao.insert(liste2);
		dao.insert(liste3);
		List<Liste> resultat = dao.SelectListe(dao.getListsByTitre(""),dao.getListsByDescription(liste1.getDescrip()), dao.getListesByID(""));
		assertTrue(liste1.getDescrip().equals(resultat.get(0).getDescrip()));
    }
    @Test()
    public void Test__ID_selectListe() {
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Liste liste2= new Liste();
    	Liste liste3= new Liste();
		dao.insert(liste1);
		dao.insert(liste2);
		dao.insert(liste3);
		List<Liste> resultat = dao.SelectListe(dao.getListsByTitre(""),dao.getListsByDescription(""), dao.getListesByID(liste1.getId()));
		assertTrue(liste1.getId().equals(resultat.get(0).getId()));
    }
    
    @Test()
    public void Test__TitreDescription_selectListe() {
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	liste1.setTitre("testtitre");
    	liste1.setDescrip("testD");
    	Liste liste2= new Liste();
    	liste2.setTitre("testtitre");
    	liste2.setDescrip("testD");
    	Liste liste3= new Liste();
		dao.insert(liste1);
		dao.insert(liste2);
		dao.insert(liste3);
		List<Liste> resultat = dao.SelectListe(dao.getListsByTitre("testtitre"),dao.getListsByDescription("testD"), dao.getListesByID(""));
		assertEquals(2,resultat.size());
    }
    @Test()
    public void Test__Titre_selectElement() {  
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Element elem = new Element();
    	elem.setTitre("titre_elem");
    	Element elem2 = new Element();
    	elem.setTitre("titre_elem2");
    	Element elem3 = new Element();
    	elem.setTitre("titre_elem3");
		dao.insert(liste1);
		dao.insert(elem,liste1);
		dao.insert(elem2,liste1);
		dao.insert(elem3,liste1);
		List<Element> resultat = dao.SelectElement(dao.getElementsByTitre(elem.getTitre()),dao.getElementsByDescription(""), dao.getElementsByID(""));
		assertTrue(elem.getTitre().equals(resultat.get(0).getTitre()));
    }
    
    @Test()
    public void Test__Descrip_selectElement() {  
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Element elem = new Element();
    	elem.setDescrip("titre_elem");
    	Element elem2 = new Element();
    	Element elem3 = new Element();
		dao.insert(liste1);
		dao.insert(elem,liste1);
		dao.insert(elem2,liste1);
		dao.insert(elem3,liste1);
		List<Element> resultat = dao.SelectElement(dao.getElementsByTitre(""),dao.getElementsByDescription(elem.getDescrip()), dao.getElementsByID(""));
		assertTrue(elem.getDescrip().equals(resultat.get(0).getDescrip()));
    }
    
    @Test()
    public void Test__ID_selectElement() {  
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Element elem = new Element();
    	Element elem2 = new Element();
    	Element elem3 = new Element();
		dao.insert(liste1);
		dao.insert(elem,liste1);
		dao.insert(elem2,liste1);
		dao.insert(elem3,liste1);
		List<Element> resultat = dao.SelectElement(dao.getElementsByTitre(""),dao.getElementsByDescription(""), dao.getElementsByID(elem.getId()));
		assertTrue(elem.getId().equals(resultat.get(0).getId()));
    }
    
    @Test()
    public void Test__TitreDescrip_selectElement() {  
    	dao.EffacerTout();
    	Liste liste1= new Liste();
    	Element elem = new Element();
    	elem.setTitre("titre_elem");
    	elem.setDescrip("Descrip_elem");
    	Element elem2 = new Element();
    	elem2.setDescrip("Descrip_elem");
    	elem2.setTitre("titre_elem");
    	Element elem3 = new Element();
		dao.insert(liste1);
		dao.insert(elem,liste1);
		dao.insert(elem2,liste1);
		dao.insert(elem3,liste1);
		List<Element> resultat = dao.SelectElement(dao.getElementsByTitre("titre_elem"),dao.getElementsByDescription("Descrip_elem"), dao.getElementsByID(""));
		assertEquals(2,resultat.size());
    }
    
    
    //test gestion paramètre "" pour les fonctions Get Attribut qui correspond à l'appelle des fonctions getAll
    
    @Test()
    public void Test__GETALL_getElemByTitre(){
    	dao.EffacerTout();
    	Liste liste = new Liste();
    	dao.insert(liste);
    	Element elem = new Element();
    	Element elem2 = new Element();
		dao.insert(elem2,liste);
		dao.insert(elem,liste);
		List<Element> listetest  = dao.getElementsByTitre("");
		List<Element> listtotale  =dao.getAllElements();
		for(int i = 0; i <listtotale.size(); i++)
			{ assertTrue(listtotale.get(i).getId().equals(listetest.get(i).getId())) ;
			}
    	}
    
    @Test()
    public void Test__GETALL_getElemByDescrip(){
    	dao.EffacerTout();
    	Liste liste = new Liste();
    	dao.insert(liste);
    	Element elem = new Element();
    	Element elem2 = new Element();
		dao.insert(elem2,liste);
		dao.insert(elem,liste);
		List<Element> listetest  = dao.getElementsByDescription("");
		List<Element> listtotale  =dao.getAllElements();
		for(int i = 0; i <listtotale.size(); i++)
			{ assertTrue(listtotale.get(i).getId().equals(listetest.get(i).getId())) ;
			}
	}
    
    
    @Test()
    public void Test__GETALL_getElemById(){
    	dao.EffacerTout();
    	Liste liste = new Liste();
    	dao.insert(liste);
    	Element elem = new Element();
    	Element elem2 = new Element();
		dao.insert(elem2,liste);
		dao.insert(elem,liste);
		List<Element> listetest  = dao.getElementsByID("");
		List<Element> listtotale  =dao.getAllElements();
		for(int i = 0; i <listtotale.size(); i++)
			{ assertTrue(listtotale.get(i).getId().equals(listetest.get(i).getId())) ;
			}
		}
    
    
    @Test()
    public void Test__GETALL_getListeByTitre(){
    	dao.EffacerTout();
    	Liste liste1 = new Liste();
    	Liste liste2 = new Liste();
    	Liste liste3 = new Liste();
    	dao.insert(liste1);
    	dao.insert(liste2);
    	dao.insert(liste3);
		List<Liste> listetest  = dao.getListsByTitre("");
		List<Liste> listtotale  =dao.getAllLists();
		for(int i = 0; i <listtotale.size(); i++)
			{ assertTrue(listtotale.get(i).getId().equals(listetest.get(i).getId())) ;
			}
    	}
    @Test()
    public void Test__GETALL_getListeByDescription(){
    	dao.EffacerTout();
    	Liste liste1 = new Liste();
    	Liste liste2 = new Liste();
    	Liste liste3 = new Liste();
    	dao.insert(liste1);
    	dao.insert(liste2);
    	dao.insert(liste3);
		List<Liste> listetest  = dao.getListsByDescription("");
		List<Liste> listtotale  =dao.getAllLists();
		for(int i = 0; i <listtotale.size(); i++)
			{ assertTrue(listtotale.get(i).getId().equals(listetest.get(i).getId())) ;
			}
    	}
    
    @Test()
    public void Test__GETALL_getListeByID(){
    	dao.EffacerTout();
    	Liste liste1 = new Liste();
    	Liste liste2 = new Liste();
    	Liste liste3 = new Liste();
    	dao.insert(liste1);
    	dao.insert(liste2);
    	dao.insert(liste3);
		List<Liste> listetest  = dao.getListesByID("");
		List<Liste> listtotale  =dao.getAllLists();
		for(int i = 0; i <listtotale.size(); i++)
			{ assertTrue(listtotale.get(i).getId().equals(listetest.get(i).getId())) ;
			}
    	}
	
}
    

