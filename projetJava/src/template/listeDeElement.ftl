<html>
	<head>
	  <title>TP Web Serveur</title>
	</head>
	<body>
		<h1>TP Web Serveur</h1>
		<p>${elem.titre}</p>
		<p>${elem.getDateCrea()?date}</p>
		<p>${elem.getDateModif()?date}</p>
		<p>${elem.getDescrip()}</p>
	
		<ul>
			<#list lists as list>
				<li>${list.titre}  |  ${list.getDescrip()}</li>
			</#list>
		</ul>
	</body>
</html>
