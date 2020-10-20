<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int id = 1;
%>
  
<!-- 상단 모듈 -->
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<!-- Custom styles for this template -->

<style type="text/css">
	.div_box{
		width:700px;
		text-align:center;
		margin:auto;
	}
</style>
<script type="text/javascript" src="${contextPath}/resources/myhome/member-checker.js"></script>

<script type="text/javascript">

	function actionModifiedOk(){
		
		var result = true;
		
		document.getElementById("result_passwd").innerText = "";
		
		// 비밀번호 일치 여부
		if ( passwdChecker("passwd1", "passwd2") == false ){
			document.getElementById("result_passwd").innerText = "비밀번호 일치하지 않음";
			result = false;
		}
		
		// 비밀번호 길이 체크
		if ( passwdLengthChecker("passwd1") == false &&
			 passwdLengthChecker("passwd2") == false){
			document.getElementById("result_passwd").innerText = "비밀번호 길이 짧음";
			result = false;
		}
		
		return result;
	}
	
	function actionRemoveOk(){
		
		// var result = true;
	
		var answer = window.confirm("정말로 탈퇴하시겠습니까?");
		
		if (answer) {
		    return true;
		}
		else {
		    return false;
		}
		
	}

</script>

<link href="${contextPath}/resources/myhome/form-validation.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- 상단 메뉴 -->
<jsp:include page="/WEB-INF/views/common/custom_menu.jsp" />

<div class="container">
  	<div class="py-5 text-center">
    <h2>프로필</h2>
    	<p class="lead">
    		프로필 웹 페이지입니다.
    	</p>
	</div>
</div>

<form action="${contextPath}/main/member/profile_ok.do" method="POST" onsubmit="return actionModifiedOk();">
<input type="hidden" name="idx" value="${memberVO.idx}">
<input type="hidden" name="type" value="m">
<table class="div_box">
	<tbody>
	<tr>	
		<td>
			<label for="username">이메일주소</label>
		</td>
		<td colspan="3">
			<input type="text" class="form-control" name="username" id="username" placeholder="" 
					value="${memberVO.username }" required readonly>
		</td>
	</tr>
	
	<tr>
		<td colspan="4" style="height:15px">
		</td>
	</tr>
	
	<tr>
		<td>
			<label for="passwd1">비밀번호</label>
		</td>
		<td>
			<input type="text" class="form-control" name="passwd1" id="passwd1" placeholder="" 
					value="" required>
		</td>
		
		<td>
			<label for="passwd2">비밀번호 확인</label>
		</td>
		<td>
			<input type="text" class="form-control" id="passwd2" placeholder=""
					value="" required>
		</td>
	</tr>
	
	<tr>	
		<td colspan="4" style="height:15px">
			<label id="result_passwd"></label>
		</td>
	</tr>
		
	<tr>
		<td>
			<label for="ip">IP 주소</label>
		</td>
		
		<td colspan="3">
			<input type="text" class="form-control" id="username" placeholder="" 
					value="${memberVO.ip}" required readonly>
		</td>
	</tr>
	</tbody>
	<tfoot>
	<tr>
		<td colspan="4" style="height:15px">
		</td>
	</tr>
	<tr>
		<td colspan="4" style="height:40px">
        	<button class="btn btn-primary btn-lg btn-block" type="submit">
        		수정(Modified)
        	</button>
		</td>
	</tr>
	</tfoot>
</table>
</form>
<!-- 삭제 기능 구현 -->
<form action="${contextPath}/main/member/profile_ok.do" method="POST" onsubmit="return actionRemoveOk();">
<input type="hidden" name="idx" value="${memberVO.idx}">
<input type="hidden" name="type" value="r">
<table class="div_box">
	<tbody>
	<tr>
		<td colspan="4" style="height:40px">
        	<button class="btn btn-primary btn-lg btn-block" type="submit">
        		삭제(Removed)
        	</button>
		</td>
	</tr>
	</tbody>
</table>
</form>
<!-- 
<select class="custom-select d-block w-100" id="state" required>
	<option value="">선택...</option>
	<option>California</option>
</select>
-->
 

<jsp:include page="/WEB-INF/views/common/footer.jsp" />