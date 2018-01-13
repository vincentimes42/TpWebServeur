<html>
	<head>
	  <title>TP Web Serveur</title>
	</head>
	<body>
		<h1>Insertion</h1>
		<form method="POST" action="/Insertion">
			<select name="choisir">
				<option value="liste">Liste</option>
				<option value="element">Element</option>
			</select>
            <p>
	            <label for="titre">Titre</label>
	            <input type="text" name="titre">
			</p>
			
            <p>
	            <label for="description">Description</label>
	            <input type="text" name="description">
			</p>
			<select name="choixListe">
			<#list lists as liste>
				<option  value=${liste.id}>${liste.titre}  |  ${liste.getDescrip()}</option>
			</#list>
			</select>
			<button type="submit">Valider</button>
		</form>
		<form method="GET" action="/">
			<button type="submit">Accueil</button>
		</form>
	</body>
</html>