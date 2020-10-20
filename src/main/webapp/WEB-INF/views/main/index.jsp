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

</head>
<body class="bg-light">

<!-- 상단 메뉴 -->
<jsp:include page="/WEB-INF/views/common/custom_menu.jsp" />

<main role="main" class="container">
		
	<div class="my-3 p-3 bg-white rounded shadow-sm">
		<h6 class="border-bottom border-gray pb-2 mb-0">
			최근 업데이트(Recent updates)
		</h6>
	  	
	  	<!-- 템플릿 영역(최근 업데이트) -->
		<c:forEach items="${boardList}" var="board">
	  		<%
	  			switch ( id ){
	  				case 1:
	  		%>
		<div class="media text-muted pt-3">
	    	<svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"/><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>
	    	<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
	      	<strong class="d-block text-gray-dark">${board.id} - ${board.name} - ${board.regidate }</strong>
	      		${board.subject}<br / >
	      		${board.memo}
	    	</p>
	  	</div>
	  		<%	
	  					break;
	  				case 2:
	  		%>
	  	<div class="media text-muted pt-3">
	    	<svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#e83e8c"/><text x="50%" y="50%" fill="#e83e8c" dy=".3em">32x32</text></svg>
	    	<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
	      	<strong class="d-block text-gray-dark">${board.id} - ${board.name} - ${board.regidate }</strong>
	      		${board.subject}<br / >
	      		${board.memo}
	    	</p>
	  	</div>
	  		<%
	  					break;
	  				case 3:
	  		%>
	  	<div class="media text-muted pt-3">
	    	<svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#6f42c1"/><text x="50%" y="50%" fill="#6f42c1" dy=".3em">32x32</text></svg>
	    	<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
	      	<strong class="d-block text-gray-dark">${board.id} - ${board.name} - ${board.regidate }</strong>
	      		${board.subject}<br / >
	      		${board.memo}
	    	</p>
	  	</div>
	  		<!-- 템플릿 영역(최근 업데이트) -->
	  		<%
	  					break;
	  			}
	  		%>
	  		
	  		<%
	  			if ( id > 3){
	  				id = 1;
	  			}else{
	  				id++;
	  			}
	  		%>
	  		
		</c:forEach>
		
	  	<small class="d-block text-right mt-3">
		    <a href="#">All updates</a>
		</small>
		
	</div>
	
	<div class="my-3 p-3 bg-white rounded shadow-sm">
	  <h6 class="border-bottom border-gray pb-2 mb-0">Suggestions</h6>
	  <div class="media text-muted pt-3">
	    <svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"/><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>
	    <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
	      <div class="d-flex justify-content-between align-items-center w-100">
	        <strong class="text-gray-dark">Full Name</strong>
	        <a href="#">Follow</a>
	      </div>
	      <span class="d-block">@username</span>
	    </div>
	  </div>
	  <div class="media text-muted pt-3">
	    <svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"/><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>
	    <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
	      <div class="d-flex justify-content-between align-items-center w-100">
	        <strong class="text-gray-dark">Full Name</strong>
	        <a href="#">Follow</a>
	      </div>
	      <span class="d-block">@username</span>
	    </div>
	  </div>
	  <div class="media text-muted pt-3">
	    <svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"/><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>
	    <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
	      <div class="d-flex justify-content-between align-items-center w-100">
	        <strong class="text-gray-dark">Full Name</strong>
	        <a href="#">Follow</a>
	      </div>
	      <span class="d-block">@username</span>
	    </div>
	  </div>
	  <small class="d-block text-right mt-3">
	    <a href="#">All suggestions</a>
	  </small>
  		</div>
	</main>
	
<jsp:include page="/WEB-INF/views/common/footer.jsp" />