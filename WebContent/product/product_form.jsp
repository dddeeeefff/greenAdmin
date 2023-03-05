<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ProductInfo pi = (ProductInfo)request.getAttribute("pi");
//화면에서 보여줄 상품 정보들을 저장한 ProductInfo형 인스턴스 pi를 받아옴
int price = pi.getPi_min();
if (pi.getPi_dc() > 0) {
	price = pi.getPi_min() * (100 - pi.getPi_dc()) / 100;
}

%>
<style>
#page-contents { width:1000px; margin:50px 0px 50px 500px; }
h2 { text-align:center; }
.productInfo { margin:30px 0; font-size:25px;}
#optCal { width:100px; font-size:20px; }
#rank, #color, #memory { width:120px; font-size:20px; }
#productFormSub { 
	margin:50px 0;
	display:flex; justify-content: space-between; 
}
#productFormImg { 
	width:400px; list-style-type: none; 
	display:flex; justify-content: space-between; 
}
#productFormImg div { width:150px; }
#productFormImg img { margin-bottom:30px; }
#showB, #showW { 
	width:150px; height:190px; 
	margin-bottom:20px; pointer-events:none; 
}
.showPic { 
	width:150px; border:1px solid black; display:inline-block; 
	text-align:center;	background-color:#f0f0f0;
	cursor:pointer; font-size:25px;
	}
#productFormCal { width:300px; font-size:20px; }
#productFormCal input { width:100px; font-size:20px; margin:10px 0; }
#priceA { 
	display:inline-block; width:100px; 
	font-size:20px; border:1px solid black; 
}
.btnbottom input { font-size:20px; }
</style>
<script>
function onlyNum(obj) {
	if (isNaN(obj.value)) {
		obj.value = "";
	}
}

function showPicB(input) {	// file이 업로드되면 image가 나타남
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			document.getElementById("showB").src = e.target.result;
		};
		reader.readAsDataURL(input.files[0]);
	}
}

function showPicW(input) {	// file이 업로드되면 image가 나타남
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			document.getElementById("showW").src = e.target.result;
		};
		reader.readAsDataURL(input.files[0]);
	}
}

function autoCal() {	// 할인율을 입력하면 자동으로 계산
	var frm = document.frm2;
	var pidc = parseInt(frm2.pidc.value);
	var pimin = parseInt(frm2.pimin.value);
	
	if (pidc >=0 && pidc <= 100) {
		$("#priceA").text(Math.round(pimin * (1 - pidc / 100)));
	} else if (isNaN(pidc)) {
		$("#priceA").text(0);
	} else {
		alert("0 ~ 100 사이의 숫자를 입력하세요.");	return;
	}
}

function priceInc() {	// 각 콤보박스의 옵션을 선택하고 '옵션 선택'을 누를시 증가율이 적용되어 계산됨
	var frm = document.frm1;
	var frm2 = document.frm2;
	
	var color = frm.color.value;
	var memory = frm.memory.value;
	var rank = frm.rank.value;
	var pimin = parseInt(frm2.pimin.value);
	var pidc = parseInt(frm2.pidc.value);
	
	$.ajax({
		type : "POST", 
		url : "product_form_inc", 
		data : { "piid" : "<%=pi.getPi_id() %>", "color" : color, "rank" : rank, "memory" : memory }, 
		success : function(chkRs) {
			var inc = parseInt(chkRs);
			alert("chkRs = " + chkRs + ", inc = " + inc);
			
//			var priceA = Math.round((pimin * (1 + inc / 100)) * (1 - pidc / 100));
			
			frm2.priceA = priceA;
			$("#priceA").text(priceA);
		}
	})
}
</script>
<div id="page-contents">
	<h2>판매 상품 수정</h2>
	<form name="frm1">
	<div class="productInfo">
		<p>No.&nbsp;<%=pi.getPi_id() %></p>
	</div>
	<div class="productInfo">
		<p>
		브랜드 : <%=pi.getPb_name() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		시리즈 : <%=pi.getPs_name() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		제품명 : <%=pi.getPi_name() %>
		</p>
	</div>
	<div class="productInfo">
		등급 : 
		<select name="rank" id="rank">
			<option value="b">B</option>
			<option value="a">A</option>
			<option value="s">S</option>
		</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		색상 : 
		<select name="color" id="color">
			<option value="b">블랙</option>
			<option value="w">화이트</option>
		</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		용량 : 
		<select name="memory" id="memory">
			<option value="128">128 GB</option>
			<option value="256">256 GB</option>
			<option value="512">512 GB</option>
		</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" id="optCal" value="옵션 적용" onclick="priceInc();" />
	</div>
	</form>
	<hr />
	<form name="frm2" action="product_form_up" method="post">
	<input type="hidden" name="piid" value="<%=pi.getPi_id() %>" />	
	<div id="productFormSub">
		<div id="productFormImg">
			<div>
				<img src="/greenAdmin/product/pdt_img/<%=pi.getPi_img1() %>" name="showB" id="showB" />
				<input type="file" name="piimg1" id="piimg1" accept="image/png" onchange="showPicB(this);" style="display:none;" />
				<label for="piimg1"><span class="showPic">선택</span></label>
			</div>
			<div>
				<img src="/greenAdmin/product/pdt_img/<%=pi.getPi_img2() %>" name="showW" id="showW" />
				<input type="file" name="piimg2" id="piimg2" accept="image/png" onchange="showPicW(this);" style="display:none;" />
				<label for="piimg2"><span class="showPic">선택</span></label>
			</div>
		</div>
		<div id="productFormCal">
			<p>
				정가 : &nbsp;&nbsp;
				<input type="text" name="pimin" id="pimin" value="<%=pi.getPi_min() %>" readonly="readonly" />&nbsp;원
			</p>
			<p>할인율 : <input type="text" name="pidc" id="pidc" value="<%=pi.getPi_dc() %>" onkeyup="onlyNum(this);autoCal();" />&nbsp;%</p>
			<p>할인가 : <span id="priceA"><%=price %></span>&nbsp;원</p>
		</div>
	</div>
		<p class="btnbottom" align="center">
			<input type="button" value="취소" style="width:100px; height:30px;" onclick="location.href='product_list';" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="수정" style="width:100px; height:30px;" />
		</p>
	</form>
</div>
</body>
</html>
