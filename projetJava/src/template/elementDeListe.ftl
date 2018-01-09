<html>
	<head>
	  <title>TP Web Serveur</title>
	</head>
	<body>
		<h1>TP Web Serveur</h1>
		<p>${list.titre}</p>
		<p>${list.getDescrip()}</p>
	
		<ul>
			<#list elements as element>
				<li>${element.dateCrea}  |  ${element.getDateModif()?date}  |  ${element.titre}  |  ${element.getDescrip()}</li>
			</#list>
		</ul>
	</body>
</html>
