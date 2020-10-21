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
		position:absolute;
		left:50%;
	}
	
	.board_tbl{
		border-top:1px solid #666;
		width:900px;
		
		font-family:Arial;
		font-size:15px;
		margin:auto;
		text-align:center;
		
	}
	
	.board_tbl td{
		border-right:1px solid #969696;
		border-bottom:1px solid #969696;
		height:40px;
		text-align:center;
	}
	
	.board_tbl th{
		background-color:#e6e6e6;
		border-bottom:1px solid #969696;
		border-right:1px solid #969696;
		height:40px;
	}
	
	.board_title{
		font-size:20px;
		text-align:center;
	}
	
	// 밑줄 없애기
	a {
		text-decoration:none;
	} 
	
 	a:link { 
 		color: black; text-decoration: none;
 	}
 	a:visited { 
 		color: black; text-decoration: none;
 	}
 	a:hover { 
 		color: black; text-decoration: underline;
 	}
</style>

<link href="${contextPath}/resources/myhome/form-validation.css" rel="stylesheet">
</head>

<body class="bg-light">

<!-- 상단 메뉴 -->
<jsp:include page="/WEB-INF/views/common/custom_menu.jsp" />

<div class="container">
  	<div class="py-5 text-center">
    <h2>${boardname}</h2>
    	<p class="lead">
    		게시판 글쓰기 페이지 입니다.
    	</p>
	</div>
</div>


<!-- 본문 -->
<form action="${pageUrl}/write_ok.do" method="POST" enctype="multipart/form-data">

<input name="boardname" type="hidden" value="${boardname}" />
<input name="id" type="hidden" value="${boardView.id}" />
<input name="token_csrf" type="hidden" value="" />

<table class="board_tbl">
	<tr>
		<td>작성자명</td>
		<td align="left">
			<input name="name" type="text" value="${boardView.name}" style="width:100%;" />
		</td>
	</tr>
	<tr>
		<td>제목</td>
		<td align="left">
			<input name="subject" type="text" value="${boardView.subject}" style="width:100%;"/>		
		</td>
	</tr>
	<tr>
		<td>내용</td>
		<td align="left">
			<textarea name="memo" cols="40" rows="10" style="width:100%;">${boardView.memo}</textarea>
		</td>
	</tr>
	<tr>
		<td>첨부(Attachments)</td>
		<td align="left">
			
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<button class="btn btn-primary btn-lg btn-block" type="submit" style="width:100%">
				수정(Modified)
		 	</button>
		</td>
	</tr>
</table>

</form>


<jsp:include page="/WEB-INF/views/common/footer.jsp" />