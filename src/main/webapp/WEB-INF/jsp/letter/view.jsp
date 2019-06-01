<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>게시판</title>
<script type="text/javascript">
	function confirmDelete() {
		if (confirm("삭제하시겠습니까?"))
			return true;
		else
			return false;
	}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>메일 보기</h2>
	<p>
		<a href="./app/letter/sendForm?receiverId=${letter.receiverId }&receiverName=${letter.receiverName }">편지 작성</a>
		<a href="./app/letter/delete?letterId=${letter.letterId }"
			onclick="return confirmDelete();">편지 삭제</a>
	</p>
	<hr />
	<c:choose>
		<c:when test="${letter.receiverId == sessionScope.MEMBER.memberId}">
			<p>
				<span>${letter.letterId }</span> | <span style="font-weight: bold;">${letter.title }</span>
			</p>
			<p>
				<span>${letter.cdate }</span> | <span>${letter.senderId }</span> | <span>${letter.senderName }</span>
			</p>
			<hr />
			<p>${letter.contentHtml }</p>
		</c:when>
		<c:when test="${letter.senderId == sessionScope.MEMBER.memberId}">
			<p>
				<span>${letter.letterId }</span> | <span style="font-weight: bold;">${letter.title }</span>
			</p>
			<p>
				<span>${letter.cdate }</span> | <span>${letter.receiverId }</span> | <span>${letter.receiverName }</span>
			</p>
			<hr />
			<p>${letter.contentHtml }</p>
		</c:when>
	</c:choose>
	<hr />
</body>
</html>