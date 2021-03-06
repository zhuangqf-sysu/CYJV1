<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/14
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>制定采购计划</title>
    <link rel="stylesheet" href="/resources/css/admin.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/main.css" type="text/css">
    <link rel="stylesheet" href="/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<h4 class="title_h4">制定采购计划</h4>
<form action="/admin/addPlan" method="post">
    <c:forEach var="list" items="${nowPage.data}">
        <div class="row">
        <c:forEach var="item" items="${list}">
            <div class="col-xs-6 col-md-3">
                <div class="thumbnail">
                    <input type="checkbox" name="selectGoods" value="${item.get("goodsModel").id}">
                    <img src="/resources/${item.get("goodsModel").url}.jpg" alt="${item.get("goodsModel").gname}" class="img-circle goodsLogo">
                    <div class="caption">
                        <h3><a href="/admin/goodsDetail?gid=${item.get("goodsModel").id}">${item.get("goodsModel").gname}</a></h3>
                        <p>现有库存：${item.get("stockModel").nowNumber}</p>
                        <p>最小库存：${item.get("stockModel").minStore}</p>
                        <p>最大库存：${item.get("stockModel").maxStore}</p>
                        <p>未发布计划:${item.get("scheduleModel").buyNumber}</p>
                        <p>已发布个数:${item.get("publishModel").publishNumber}</p>
                        <div>采购个数：<input type="number" name="${item.get("goodsModel").id}"></div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
    </c:forEach>
    <input hidden type="number" name="pageNum" value="${nowPage.pageNumber}">
    <div>
        <button type="submit">批量加入计划</button>
    </div>
</form>

<div class="foot">
    <p>
        总共【${nowPage.totalPage}】页 &nbsp;&nbsp;
        ${nowPage.pageNumber}/${nowPage.totalPage}&nbsp;&nbsp;
        <a href="/admin/makePurchasePlan?pageNum=1">首页</a>
        <c:choose>
            <c:when test="${requestScope.nowPage.pageNumber > 1}"><a href="/admin/makePurchasePlan?pageNum=${nowPage.pageNumber-1}">上一页</a></c:when>
            <c:otherwise>
                上一页
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${nowPage.pageNumber < nowPage.totalPage}"><a href="/admin/makePurchasePlan?pageNum=${nowPage.pageNumber+1}">下一页</a></c:when>
            <c:otherwise>
                下一页
            </c:otherwise>
        </c:choose>
        <a href="/admin/makePurchasePlan?pageNum=${nowPage.totalPage}">尾页</a>
    </p>
</div>
</body>
</html>
