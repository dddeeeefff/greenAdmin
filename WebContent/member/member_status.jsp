<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<style>
.main {
	width: 500px;
	display: flex;
	flex-direction: column;
	align-items: center;
	margin: 0 auto;
	font-size: 1.8em;
	
}
.text {
	width: 100%;
	font-size: 0.9em;
}
.btn {
	font-size: 0.9em;
	width: 100px;
	margin: 30px;
}
</style>
<body>

	<div class="main">
		<h2>정말 탈퇴 시키겠습니까 ?</h2>
		<input type="text" class="text" placeholder="탈퇴 사유를 입력해 주세요."/>
		<div>
			<input type="button" value="예" class="btn" />
			<input type="button" value="아니요" class="btn" />
		</div>
	</div>
</body>
</html>