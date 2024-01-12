<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
  <title>Title</title>
  <meta name="_csrf" content="${_csrf.token}">
  <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/memberQnA.css">
<style>
  .content_area {
    width: 1000px;
  }

  .qna_info {
    text-align: center;
  }
</style>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
  <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
  <div class="content_area">
    <div class="memberQnAList_header content_header">
      <h1>채팅 문의</h1>
      <button type="button" id="startChat">문의 하기</button>
    </div>
    <c:choose>
      <c:when test="${qList eq null}">
        <h2 class="qna_info">문의 내역이 없습니다.</h2>
      </c:when>
      <c:otherwise>
        <div class="memberQnAList_content content_data">
          <table class="tbl_default" border="1">
            <thead>
            <tr>
              <th>상태</th>
              <th>날짜</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${qList}" var="list">
              <tr>
                <c:choose>
                  <c:when test="${list.ChatStat == 0}">
                    <td>문의 중</td>
                  </c:when>
                  <c:when test="${list.QStat == 1}">
                    <td>문의 완료</td>
                  </c:when>
                </c:choose>
                <td>
                  <a href="<c:url value="/myPage/chatDetail/${list.qno}"/>"><c:out value="${list.CreatedAt}"/> 문의 내역</a>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
          <div class="paging">
            <ul class="pagination">
              <c:if test="${pageMaker.prev}">
                <li class="paginate_button previous">
                  <a href="${pageMaker.startPage - 1}">Prev</a>
                </li>
              </c:if>

              <c:if test="${pageMaker.endPage != 1}">
                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                  <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                    <a href="${num}">${num}</a>
                  </li>
                </c:forEach>
              </c:if>

              <c:if test="${pageMaker.next}">
                <li class="paginate_button next">
                  <a href="${pageMaker.endPage + 1}">Next</a>
                </li>
              </c:if>
            </ul>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
  <form id="pageActionForm" action="/myPage/memberQnAList" method="get">
    <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
    <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
  </form>
</div>
</div>
</div>
</body>
</html>