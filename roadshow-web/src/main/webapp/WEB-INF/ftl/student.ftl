<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Roadshow-Web Homepage</title>
</head>
<body>
<h1>Roadshow-Web Homepage</h1>
<br/><br/>
<h2>Person</h2>
<br/><br/>
persons：

<#if persons??>
<#list persons as person>
${person.name} 
</#list>
</#if>
</body>
</html>