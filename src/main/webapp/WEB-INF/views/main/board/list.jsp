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
    		게시판 목록 페이지 입니다.
    	</p>
	</div>
</div>

<table class="board_tbl">
	<tr>
		<th style="width:10%;">
			번호(num)
		</th>
		<th style="width:40%;">
			제목(subject)
		</th>
		<th style="width:10%;">
			작성자(author)
		</th>
		<th style="width:10%;">
			조회수(count)
		</th>
		<th style="width:15%; border-right:0px;">
			등록일자(regidate)
		</th>
	</tr>
	<c:forEach items="${boardList}" var="board">
		<tr>
			<td>${board.id }</td>
			<td align="left">
				<a href="${pageUrl}/view.do?boardname=${boardname}&id=${board.id}">${board.subject}</a>
			</td>
			<td>${board.name}</td>
			<td>${board.count}</td>
			<td style="border-right:0px">
				<fmt:formatDate value="${board.regidate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
	</c:forEach>
</table>

<!-- 금액 형식: <fmt:formatNumber value="12341230" /> -->

<!-- 페이징 -->
<div style="text-align:center">
<jsp:include page="/WEB-INF/views/pager/paging.jsp">
	<jsp:param name="customURL" value="${pagingUrl}" />
    <jsp:param name="firstPageNo" value="${paging.firstPageNo}" />
    <jsp:param name="prevPageNo" value="${paging.prevPageNo}" />
    <jsp:param name="startPageNo" value="${paging.startPageNo}" />
    <jsp:param name="pageNo" value="${paging.pageNo}" />
    <jsp:param name="endPageNo" value="${paging.endPageNo}" />
    <jsp:param name="nextPageNo" value="${paging.nextPageNo}" />
    <jsp:param name="finalPageNo" value="${paging.finalPageNo}" />
</jsp:include>
</div>

<!-- 게시판 하단 -->

<!-- 하단 메뉴 -->
<div class="div_box">
	<table class="foot_tbl">
		<tr>
			<td>
				<a href="${pageUrl}/write.do?boardname=${boardname}">등록</a>
			</td>
		</tr>
	</table>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />