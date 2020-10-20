<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body class="bg-light">
		<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
		<a class="navbar-brand mr-auto mr-lg-0" href="${contextPath}/main">
			메인(Main)
		</a>
  <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="${contextPath}/">홈 <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${contextPath}/main">알림</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${contextPath}/main/member/profile.do">프로필</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">공지사항</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        	환경설정
        </a>
        <div class="dropdown-menu" aria-labelledby="dropdown01">
          <a class="dropdown-item" href="${contextPath}/member/logout.do">로그아웃</a>
          <a class="dropdown-item" href="#">Another action</a>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="text" placeholder="검색" aria-label="검색">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
    </form>
  </div>
</nav>

<div class="nav-scroller bg-white shadow-sm">
  <nav class="nav nav-underline">
    <a class="nav-link active" href="#">Dashboard</a>
    <a class="nav-link" href="#">
      Friends
      <span class="badge badge-pill bg-light align-text-bottom">27</span>
    </a>
    <a class="nav-link" href="#">Explore</a>
    <a class="nav-link" href="#">Suggestions</a>
    <a class="nav-link" href="${contextPath}/main/board/list/notice">Notice</a>
    <a class="nav-link" href="${contextPath}/main/board/list/sample">Sample</a>
    <a class="nav-link" href="#">Link</a>
    <a class="nav-link" href="#">Link</a>
    <a class="nav-link" href="#">Link</a>
  </nav>
</div>