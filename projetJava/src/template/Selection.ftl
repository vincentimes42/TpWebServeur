<html>
	<head>
	  <title>TP Web Serveur</title>
	</head>
	<body>
		<h1>TP Web Serveur</h1>
		
		<form method="POST" action="/Information_General">
			<select name="choisir">
				<option value="liste">Liste</option>
				<option value="element">Element</option>
			</select>
            
            <p>
	            <label for="id">id</label>
	            <input type="text" name="id">
			</p>
			
            <p>
	            <label for="titre">Titre</label>
	            <input type="text" name="titre">
			</p>
			
            <p>
	            <label for="description">Description</label>
	            <input type="text" name="description">
			</p>
			
			<button type="submit">Valider</button>
		</form>
	
		<form method="GET" action="/Insertion">
			<button type="submit">Insertion</button>
		</form>
		<ul>
			<#list lists as liste>
				<li name="id" value=${liste.id}>${liste.id}  |  ${liste.titre}  |  ${liste.getDescrip()}</li>		
			</#list>
		</ul>	
		<ul>
			<#list elements as element>
				<li name="id" value=${element.id}>${element.id}  |  ${element.dateCrea}  |  ${element.getDateModif()?date}  |  ${element.titre}  |  ${element.getDescrip()}</li>
			</#list>
		</ul>
		<form method="POST" action="/Information_par_Liste">
			<button type="submit">Liste d'élément</button>
		</form>
		<form method="POST" action="/Information_par_Element">
			<button type="submit">Listes de cet élément</button>
		</form>
	</body>
</html>