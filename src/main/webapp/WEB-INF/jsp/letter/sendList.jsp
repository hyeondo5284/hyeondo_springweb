<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>보낸 편지함</title>
<style type="text/css">
table {
	margin-top: 10px;
	border-collapse: collapse;
	border-top: 1px solid gray;
	border-bottom: 1px solid gray;
	width: 100%;
}
th, td {
	padding: 5px 0;
}
th {
	border-bottom: 1px solid gray;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>보낸 편지 목록</h2>
	<p>전체 ${totalCount }건</p>
	<form action="./app/letter/sendList">
		<input type="number" name="page" value="${param.page }" placeholder="페이지"
			min="1" max="${totalCount / 10 + 1 }" step="1" style="width: 50px;">
		<button type="submit">조회</button>
	</form>
	<table>
		<thead>
			<tr>
				<th>받는이</th>
				<th>제목</th>
				<th>보낸시간</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="letter" items="${letters}">
				<tr>
					<td><a
						href="./app/letter/sendForm?receiverId=${letter.receiverId }&receiverName=${letter.receiverName}">${letter.receiverId }:${letter.receiverName }</a></td>
					<td><a href="./app/letter/view?letterId=${letter.letterId }&mode=SENT">${letter.title }</a></td>
					<td>${letter.cdate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</head>
</html>