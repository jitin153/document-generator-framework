<html>
<body>
<table style="width:100%;border: 1px solid black;">
<tr>
<td><center><h2>${data.college}</h2></center></td>
</tr>
</table>
<table style="width:100%;border: 1px solid black;">
<tr>
<td>Name</td>
<td>:</td>
<td><b>${data.name}</b></td>
</tr>
<tr>
<td>Roll Number</td>
<td>:</td>
<td>${data.rollNumber}</td>
</tr>
<tr>
<td>Percentage</td>
<td>:</td>
<td>${data.percentage} %</td>
</tr>
<tr>
<td>Is Pass</td>
<td>:</td>
<td><b><#if data.isPass ==true>Yes<#else>No</#if></b></td>
</tr>
<tr>
<td>Address</td>
<td>:</td>
<td>
<#assign address=""/>
<#if data.address??>
	<#if data.address.housenumber??>
		<#assign address=data.address.housenumber/>
	</#if>
	<#if data.address.street??>
		<#assign address=address+", "+data.address.street/>
	</#if>
	<#if data.address.city??>
		<#assign address=address+", "+data.address.city/>
	</#if>
	<#if data.address.state??>
		<#assign address=address+", "+data.address.state/>
	</#if>
</#if>
<i>${address}</i>
</td>
</tr>
</table>
</body>
</html>