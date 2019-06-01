<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>편지 작성</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>편지 작성</h2>
	<form action="./app/letter/send" method="post">
		<p>받는 사람 번호:</p>
		<p>
			<input type="text" name="receiverId" value="${letter.receiverId }" maxlength="100" style="width: 100%;" required>
		</p>
		<p>받는 사람 :</p>
		<p>
			<input type="text" name="receiverName" value="${letter.receiverName }" maxlength="100" style="width: 100%;" required>
		</p>
		<p>제목 :</p>
		<p>
			<input type="text" name="title" maxlength="100" style="width: 100%;" required>
		</p>
		<p>내용 :</p>
		<p>
			<textarea name="content" style="width: 100%; height: 200px;" required></textarea>
		</p>
		<p>
			<button type="submit">전송</button>
		</p>
		<input type="hidden" name="senderId" value="${member.memberId }">
		<input type="hidden" name="senderName" value="${member.name }">
	</form>
</body>
</html>