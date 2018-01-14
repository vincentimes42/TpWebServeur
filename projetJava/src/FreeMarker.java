
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

import static spark.Spark.*;

public class FreeMarker {
	
    private static DaoBase dao = new DaoBase();
    
    // 1. Configure FreeMarker
    private static Configuration cfg = new Configuration();
    
	public static void main(String[] args) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(FreeMarker.class, "templates");

        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 2. Proccess template(s)
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

		Liste liste= new Liste();
		
		Element elem = new Element();
		elem.setTitre("tomate");
		elem.setDescrip("3jours");
		
		Element elem1 = new Element();
		elem1.setTitre("tomate");
		elem1.setDescrip("2jours");
		
		Element elem2 = new Element();
		elem2.setTitre("tomate");
		elem2.setDescrip("1jours");
		
		Element elem3 = new Element();
		elem3.setTitre("salade");
		elem3.setDescrip("fraiche");
		
		Element elem4 = new Element();
		elem4.setTitre("tomate");
		elem4.setDescrip("0jours");
		
		liste.setTitre("liste courses");
		liste.setDescrip("ceci est une liste de courses");
		
		dao.insert(liste);
		dao.insert(elem,liste);
		dao.insert(elem1,liste);
		dao.insert(elem2,liste);
		dao.insert(elem3,liste);
		dao.insert(elem4,liste);
		
		allElementListe();
		elementDeListe(liste);
		ListeDeElement(elem2);
    }
	
	public static void allElementListe() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
        Map<String, Object> input = new HashMap<String, Object>();
		List<Liste> lists = dao.getAllLists();
        input.put("lists", lists);
		
        List<Element> elements = dao.getAllElements();
        input.put("elements", elements);
        
        // 2.2. Get the template
        Template template = cfg.getTemplate("FreeMarker.ftl");

        // 2.3. Generate the output
        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // For the sake of example, also write output into a file:
        Writer fileWriter = new FileWriter(new File("Gestion_des_listes.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
	}
	
	public static void elementDeListe(Liste uneListe) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("list", uneListe);
		
        List<Element> elements = dao.getElementsPerList(uneListe);
        input.put("elements", elements);
        
        // 2.2. Get the template
        Template template = cfg.getTemplate("elementDeListe.ftl");

        // 2.3. Generate the output
        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // For the sake of example, also write output into a file:
        Writer fileWriter = new FileWriter(new File("Element_d'une_liste.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
	}

	public static void ListeDeElement(Element unElement) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("elem", unElement);
		
        List<Liste> listes = dao.getListPerElement(unElement);
        input.put("lists", listes);
        
        // 2.2. Get the template
        Template template = cfg.getTemplate("listeDeElement.ftl");

        // 2.3. Generate the output
        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // For the sake of example, also write output into a file:
        Writer fileWriter = new FileWriter(new File("Liste_d'un_element.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
	}
}
