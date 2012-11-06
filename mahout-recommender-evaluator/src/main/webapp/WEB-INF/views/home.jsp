<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Mahout!
</h1>

<P>
<form:form method="POST" commandName="recommenderForm">
	neighborhoodCount : <form:input path="neighborhoodCount"/>
	trainingPercentage : <form:input path="trainingPercentage"/>
	
	<input type="submit" value="Submit"/>
</form:form>
<br />

<c:if test="${euclideanDistanceSimilarity != null}">
euclideanDistanceSimilarity : ${euclideanDistanceSimilarity}<br />
pearsonCorrelationSimilarity : ${pearsonCorrelationSimilarity}<br />
logLikelihoodSimilarity : ${logLikelihoodSimilarity}<br />
tanimotoCoefficientSimilarity : ${tanimotoCoefficientSimilarity}
</c:if>
 </P>
</body>
</html>
