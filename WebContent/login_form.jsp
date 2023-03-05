<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/greenAdmin/src/css/login_form.css">
</head>
<body>
    <div class="page-contents">
        <div class="login-wrapper">
            <div class="login-box">
                <h2>로그인</h2>
                <div class="ctrl-wrapper">
                    <form name="frmLogin" action="login" method="post">
                        <div class="input-wrapper">
                            <div class="input_row">
                                <label for="uid">아이디 </label>
                                <input type="text" name="uid" id="uid" class="input_text" value="test1" />
                            </div>
                            <div class="input_row">
                                <label for="pwd">비밀 번호 </label>
                                <input type="password" name="pwd" id="pwd" class="input_text" value="1234" />
                            </div>
                        </div>
                        <p class="submit">
                            <input type="submit" class="btn" value="로그인" />
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>