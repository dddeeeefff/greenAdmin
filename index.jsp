<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="_inc/inc_head.jsp" %>
<%
StatisticInfo statisticInfo = (StatisticInfo)request.getAttribute("statisticInfo");
int buy_sum = statisticInfo.getBuy_sum();
int sell_sum = statisticInfo.getSell_sum();
int profit = sell_sum - buy_sum;
LocalDate today = LocalDate.now();
int year = today.getYear();
%>
	<link rel="stylesheet" href="/greenAdmin/src/css/index.css">
	<div class="page-contents">
        <div class="contents-wrapper">
            <div class="text-box">
            	<div class="title">
	                <h3>매출 통계</h3>
	                <h3><%=year %> 년</h3>
	            </div>
                <div class="text-row">
                    <p>순 수익</p>
                    <p><%=String.format("%,d", profit) %> 원</p>
                </div>
                <div class="text-row">
                    <p>판매</p>
                    <p><%=String.format("%,d", sell_sum) %> 원</p>
                </div>
                <div class="text-row">
                    <p>구매</p>
                    <p>-<%=String.format("%,d", buy_sum) %> 원</p>
                </div>
            </div>
            <div class="text-box">
                <h3>회원 수</h3>
                <div class="text-row">
                    <p>전체</p>
                    <p><%=statisticInfo.getIn_member() + statisticInfo.getOut_member() %> 명</p>
                </div>
                <div class="text-row">
                    <p>정상 회원</p>
                    <p><%=statisticInfo.getIn_member() %> 명</p>
                </div>
                <div class="text-row">
                    <p>탈퇴 회원</p>
                    <p><%=statisticInfo.getOut_member() %> 명</p>
                </div>
            </div>
            <div class="text-box">
                <h3>문의 내역</h3>
                <div class="text-row">
                    <p>전체</p>
                    <p><%=statisticInfo.getAnswer_in() + statisticInfo.getAnswer_na() %> 개</p>
                </div>
                <div class="text-row">
                    <p>답변 대기</p>
                    <p><%=statisticInfo.getAnswer_na() %> 개</p>
                </div>
                <div class="text-row">
                    <p>답변 완료</p>
                    <p><%=statisticInfo.getAnswer_in() %> 개</p>
                </div>
            </div>
            <div class="text-box">
                <h3>재고 현황</h3>
                <div class="text-row">
                    <p>전체</p>
                    <p><%=statisticInfo.getStock_enough() + statisticInfo.getStock_na() %> 개</p>
                </div>
                <div class="text-row">
                    <p>여유</p>
                    <p><%=statisticInfo.getStock_enough() %> 개</p>
                </div>
                <div class="text-row">
                    <p>부족</p>
                    <p><%=statisticInfo.getStock_na() %> 개</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>