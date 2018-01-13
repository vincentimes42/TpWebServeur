<html>
	<head>
	  <title>TP Web Serveur</title>
	</head>
	<body>
		<h1>Liste</h1>
		<ul>
			<#list lists as liste>
				<li name="id" value=${liste.id}>${liste.id}  |  ${liste.titre}  |  ${liste.getDescrip()}</li>		

			</#list>
		</ul>
		<form method="POST" action="/Information_par_Liste">
			<button type="submit">Liste d'élément</button>
		</form>
		<form method="GET" action="/">
			<button type="submit">Accueil</button>
		</form>
	</body>
</html>