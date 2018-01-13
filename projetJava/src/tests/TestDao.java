package projet;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.sql2o.Connection;







public class TestDao{
	DaoBase dao = new DaoBase();
	/*
	@Test()
	public void Test__DaoBase() {
		
	}
	*/
	//test d'absence de caract spécaiux dans le tritre et la description des élément et  des listes afin des soucis lors du passsage en SQL
	
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
    	DaoBase.getElementsByTitre(elem.getTitre());
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void Test__CaractSpe_getElemById(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
    	dao.insert(liste);
		dao.insert(elem,liste);
    	DaoBase.getElementsByID(")");
    }
    
    
    // test pour vérifier que les listes et éléments sans titre et descriptions sont gérés
    
    @Test()  
    public void Test__Vide_insert() { 
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("");
		liste.setDescrip("");
		dao.insert(liste);
		assertTrue(liste.getId().equals(DaoBase.getListesByID(liste.getId()).get(0).getId()));
		
    }
    
    @Test
    public void Test__Vide_insertElem() { 
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
		dao.insert(liste);
		dao.insert(elem,liste);
		assertTrue(elem.getId().equals(DaoBase.getElementsByID(elem.getId()).get(0).getId()));
		
		
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
		elem2.setTitre("");
		elem2.setDescrip("");
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.modif(elem.getId(),elem2);
		assertTrue(elem2.getTitre().equals(DaoBase.getElementsByID(elem.getId()).get(0).getTitre()));
		assertTrue(elem2.getDescrip().equals(DaoBase.getElementsByID(elem.getId()).get(0).getDescrip()));
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
		assertTrue(liste2.getTitre().equals(DaoBase.getListesByID(liste.getId()).get(0).getTitre()));
		assertTrue(liste2.getDescrip().equals(DaoBase.getListesByID(liste.getId()).get(0).getDescrip()));
    }
	
    @Test
    public void Test__Vide_supprimerElemDansListe() {   //pb sql
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.supprimer(elem,liste);
		assertTrue(DaoBase.getElementsByID(elem.getId()).size()==0);
		
		
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
		assertTrue(DaoBase.getElementsByID(elem.getId()).size()==0);
    	
    }
    
    @Test()
    public void Test__Vide_supprListe() {  //pb sql
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	Element elem = new Element();
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.supprimer(liste);
		assertTrue(DaoBase.getListesByID(liste.getId()).size()==0);
    }
    
    @Test()
    public void Test__Vide_getListsByTitre(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	dao.insert(liste);
    	assertEquals(1,dao.getListsByTitre("").size());
    	
    }
    
    
    @Test()
    public void Test__Vide_getElementPerList(){ //pb
    	dao.EffacerTout();
    	Liste listec= new Liste();
    	listec.setTitre("listea");
    	dao.insert(listec);
    	Element elem = new Element();
    	elem.setTitre("testcontenance");
		dao.insert(elem,listec);
    	assertTrue(elem.getTitre()==dao.getElementsPerList(listec).get(0).getTitre());
    }
    
    
    @Test()
    public void Test__Vide_getListPerElement(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	Element elem = new Element();
		elem.setTitre("");
		elem.setDescrip("");
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
    	DaoBase.getElementsByTitre(elem.getTitre());
    	assertTrue("testTitre".equals(DaoBase.getElementsByTitre("testTitre").get(0).getTitre()));
    }
    
    @Test()
    public void Test__Vide_getElemById(){
    	dao.EffacerTout();
    	Liste liste= new Liste();
    	liste.setTitre("list");
    	dao.insert(liste);
    	Element elem = new Element();
		dao.insert(elem,liste);
		assertTrue(elem.getId().equals(DaoBase.getElementsByID(elem.getId()).get(0).getId()));
    }
    
    

}
