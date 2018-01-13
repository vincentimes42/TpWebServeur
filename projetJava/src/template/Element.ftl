<html>
	<head>
	  <title>TP Web Serveur</title>
	</head>
	<body>	
		<h1>Element</h1>
		<ul>
			<#list elements as element>
				<li name="id" value=${element.id}>${element.id}  |  ${element.dateCrea}  |  ${element.getDateModif()?date}  |  ${element.titre}  |  ${element.getDescrip()}</li>
			</#list>
		</ul>
		<form method="POST" action="/Information_par_Element">
			<button type="submit">Listes lié à cet élément</button>
		</form>
		<form method="GET" action="/">
			<button type="submit">Accueil</button>
		</form>
	</body>
</html>