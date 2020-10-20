<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardTitle}</title>
</head>
<body>

<h1>글쓰기</h1>
<hr />

<!-- 본문 -->
<form action="${pageUrl}/write_ok/${boardname}" method="post" enctype="multipart/form-data">
<input name="token_csrf" type="hidden" value="" />

<table class="board_tbl">
	<tr>
		<td>작성자명</td>
		<td align="left">
			<input name="author" type="text" value="" />
		</td>
	</tr>
	<tr>
		<td>제목</td>
		<td align="left">
			<input name="subject" type="text" value="" />		
		</td>
	</tr>
	<tr>
		<td>내용</td>
		<td align="left">
			<textarea name="content" cols="40" rows="10"></textarea>
		</td>
	</tr>
	<tr>
		<td>첨부(Attachments)</td>
		<td align="left">
			<input type="file" name="mediaFile" placeholder="파일 선택" multiple/>
			<input type="file" name="mediaFile" placeholder="파일 선택" multiple/>
		</td>
	</tr>
</table>
 <input type="submit" value="업로드">
</form>
</body>
</html>