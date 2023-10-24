<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<title>애니타임</title>
<meta charset="utf-8">
<link type="text/css"href="${pageContext.request.contextPath}/resources/css/common/common.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.partial.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/calculator/calculator.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.underscore-min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery.flot.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery.flot.pie.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery.flot.resize.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/js/credit/calculator.index.js"></script>  
</head>
<body style="">

	<jsp:include page="../common/header.jsp" />

	<div id="container" class="calculator">
		<input type="hidden" id="userId" value=""> <input
			type="hidden" id="userRequiredCredit" value="150"> <input
			type="hidden" id="userGradeType" value="">
		<aside class="none">
			<div class="title">
				<a class="hamburger"></a>
				<h1>학점 계산기</h1>
			</div>
		</aside>
		<div class="section">
			<div class="chart">
				<article class="overview">
					<div class="column gpa">
						<h4>전체 평점</h4>
						<p class="value">3.81</p>
						<p class="total">/ 4.5</p>
					</div>
					<div class="column major">
						<h4>전공 평점</h4>
						<p class="value">3.9</p>
						<p class="total">/ 4.5</p>
					</div>
					<div class="column acquisition">
						<h4>취득 학점</h4>
						<p class="value">90</p>
						<p class="total" title="졸업 학점 설정">/ 150</p>
					</div>
				</article>
				<article class="semester">
					<div class="series">
						<div class="legend">
							<table style="font-size: smaller; color: #545454">
								<tbody>
									<tr>
										<td class="legendColorBox"><div
												style="border: 1px solid transparent; padding: 1px">
												<div
													style="width: 4px; height: 0; border: 5px solid rgb(198, 41, 23); overflow: hidden"></div>
											</div></td>
										<td class="legendLabel"><span
											style="color: rgb(198, 41, 23)">전체</span></td>
										<td class="legendColorBox"><div
												style="border: 1px solid transparent; padding: 1px">
												<div
													style="width: 4px; height: 0; border: 5px solid rgb(166, 166, 166); overflow: hidden"></div>
											</div></td>
										<td class="legendLabel"><span
											style="color: rgb(166, 166, 166)">전공</span></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div id="plot" class="plot" style="padding: 0px; position: relative;">
							<canvas class="flot-base" width="328" height="116"
								style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 365px; height: 129px;"></canvas>
							<div class="flot-text"
								style="position: absolute; inset: 0px; font-size: smaller; color: rgb(84, 84, 84);">
								<div class="flot-x-axis flot-x1-axis xAxis x1Axis"
									style="position: absolute; inset: 0px; display: block;">
								 
								<div
										style="position: absolute; max-width: 73px; top: 105px; font: 400 10px/12px&amp; quot; 맑은 고딕&amp;quot; , 돋움 , &amp;quot; Apple SD Gothic Neo&amp;quot; , tahoma; color: rgb(166, 166, 166); left: 19px; text-align: center;">
										1학년<br>1학기
									</div>
								 	
								</div>
								<div class="flot-y-axis flot-y1-axis yAxis y1Axis"
									style="position: absolute; inset: 0px; display: block;">
									<div
										style="position: absolute; top: 70px; font: 400 12px/14px&amp; quot; 맑은 고딕&amp;quot; , 돋움 , &amp;quot; Apple SD Gothic Neo&amp;quot; , tahoma; color: rgb(166, 166, 166); left: 0px; text-align: right;">2.0</div>
									<div
										style="position: absolute; top: 43px; font: 400 12px/14px&amp; quot; 맑은 고딕&amp;quot; , 돋움 , &amp;quot; Apple SD Gothic Neo&amp;quot; , tahoma; color: rgb(166, 166, 166); left: 0px; text-align: right;">3.0</div>
									<div
										style="position: absolute; top: 16px; font: 400 12px/14px&amp; quot; 맑은 고딕&amp;quot; , 돋움 , &amp;quot; Apple SD Gothic Neo&amp;quot; , tahoma; color: rgb(166, 166, 166); left: 0px; text-align: right;">4.0</div>
								</div>
							</div>
							<canvas class="flot-overlay" width="328" height="116"
								style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 365px; height: 129px;"></canvas>
						</div>
					</div>
					<ul class="ratioplot">
						<li><span class="grade">A+</span>
						<div class="ratiowrapper">
								<div class="ratiobar"
									style="width: calc(68.4211%); height: 4px; background-color: rgb(242, 133, 114);"></div>
								<span class="ratiotext"
									style="left: calc(68.4211%); color: rgb(242, 133, 114);">34%</span>
							</div></li>
						<li><span class="grade">B+</span>
						<div class="ratiowrapper">
								<div class="ratiobar"
									style="width: calc(52.6316%); height: 4px; background-color: rgb(236, 197, 92);"></div>
								<span class="ratiotext"
									style="left: calc(52.6316%); color: rgb(236, 197, 92);">26%</span>
							</div></li>
						<li><span class="grade">P</span>
						<div class="ratiowrapper">
								<div class="ratiobar"
									style="width: calc(26.3158%); height: 4px; background-color: rgb(160, 198, 97);"></div>
								<span class="ratiotext"
									style="left: calc(26.3158%); color: rgb(160, 198, 97);">13%</span>
							</div></li>
						<li><span class="grade">A-</span>
						<div class="ratiowrapper">
								<div class="ratiobar"
									style="width: calc(15.7895%); height: 4px; background-color: rgb(130, 209, 194);"></div>
								<span class="ratiotext"
									style="left: calc(15.7895%); color: rgb(130, 209, 194);">8%</span>
							</div></li>
						<li><span class="grade">B0</span>
						<div class="ratiowrapper">
								<div class="ratiobar"
									style="width: calc(10.5263%); height: 4px; background-color: rgb(122, 158, 224);"></div>
								<span class="ratiotext"
									style="left: calc(10.5263%); color: rgb(122, 158, 224);">5%</span>
							</div></li>
					</ul>
				</article>
			</div>
			<div class="menu">
				<ol>
					<li class=""><a>1학년 1학기</a></li>
					<li class="" ><a>1학년 2학기</a></li>
					<li class=""><a>2학년 1학기</a></li>
					<li class=""><a>2학년 2학기</a></li>
					<li class=""><a>3학년 1학기</a></li>
					<li class=""><a>3학년 2학기</a></li>
					<li class=""><a>4학년 1학기</a></li>
					<li class=""><a>4학년 2학기</a></li>
					<li class=""><a>5학년 1학기</a></li>
					<li class=""><a>5학년 2학기</a></li>
					<li class=""><a>6학년 1학기</a></li>
					<li class=""><a>6학년 2학기</a></li>
					<li class=""><a>기타 학기</a></li>
				</ol>
			</div>
			<table class="subjects">
				<caption>
					<h3>4학년 1학기</h3>
					<dl class="information">
						<dt>평점</dt>
						<dd class="gpa"></dd>
						<dt>전공</dt>
						<dd class="major"></dd>
						<dt>취득</dt>
						<dd class="acquisition"></dd>
					</dl>
					<a class="import" style="display: inline;">시간표 불러오기</a>
				</caption>
				<thead>
					<tr>
						<th class="name">과목명</th>
						<th class="credit">학점</th>
						<th class="grade">성적</th>
						<th class="major">전공</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0" ></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
					<tr>
						<td><input name="name" maxlength="50"></td>
						<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>
						<td><select name="grade"><option value="A+"
									selected="selected">A+</option>
								<option value="A0">A0</option>
								<option value="A-">A-</option>
								<option value="B+">B+</option>
								<option value="B0">B0</option>
								<option value="B-">B-</option>
								<option value="C+">C+</option>
								<option value="C0">C0</option>
								<option value="C-">C-</option>
								<option value="D+">D+</option>
								<option value="D0">D0</option>
								<option value="D-">D-</option>
								<option value="F">F</option>
								<option value="P">P</option>
								<option value="NP">NP</option></select></td>
						<td><label><input name="major" type="checkbox"><span></span></label></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4"><a class="new">더 입력하기</a> <a class="reset">초기화</a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<hr>
		<form id="importForm" class="modal"
			style="margin-left: -200px; margin-top: -93px; display: none;"
			data-gtm-form-interact-id="0">
			<a title="닫기" class="close"></a>
			<h3>내 시간표 가져오기</h3>
			<p>
				<label>시간표 선택</label> <select name="semester" data-gtm-form-interact-field-id="0">
				    <option value="42644511">2023년 겨울학기 (시간표 1)</option>
					<option value="42662455">2023년 2학기 (시간표 1)</option>
					<option value="40480064">2023년 여름학기 (시간표 1)</option>
					<option value="40480070">2023년 1학기 (시간표 1)</option>
					<option value="42644989">2022년 1학기 (시간표 1)</option>
					<option value="42644991">2021년 여름학기 (시간표 1)</option>
					<option value="24372243">2021년 1학기 (시간표)</option>
					<option value="24372222">2020년 겨울학기 (시간표)</option>
					<option value="22897232">2020년 2학기 (플랜B)</option>
					<option value="21323777">2020년 여름학기 (시간표)</option>
					<option value="17984283">2020년 1학기 (시간표)</option>
					<option value="17563162">2019년 겨울학기 (시간표)</option>
					<option value="16278583">2019년 2학기 (2019년2학기 시간표)</option>
					<option value="42743667">2018년 2학기 (시간표 1)</option>
					<option value="42644988">2017년 2학기 (시간표 1)</option>
					<option value="42730422">2017년 1학기 (시간표 1)</option>
					<option value="3533599">2016년 2학기 (시간표)</option>
					<option value="40567616">2016년 1학기 (시간표 1)</option>
					<option value="42743666">2015년 여름학기 (시간표 1)</option>
					<option value="42785680">2015년 1학기 (시간표 1)</option>
					<option value="42644986">2013년 여름학기 (시간표 1)</option>
					<option value="42743665">2012년 여름학기 (시간표 1)</option>
					<option value="42644510">2011년 1학기 (시간표 1)</option></select>
			</p>
			<input type="submit" value="가져오기" class="button">
		</form>
		<form id="requiredCreditForm" class="modal">
			<a title="닫기" class="close"></a>
			<h3>졸업 학점 설정</h3>
			<p>
				<label>졸업 학점</label> <input type="number" name="required_credit"
					maxlength="3" class="text">
			</p>
			<input type="submit" value="저장" class="button">
		</form>
	</div>
	<div id="bottom">
		<ul class="links">
			<li><a href="/page/serviceagreement">이용약관</a></li>
			<li class="privacy"><a href="/page/privacy">개인정보처리방침</a></li>
			<li><a href="/page/youthpolicy">청소년보호정책</a></li>
			<li><a href="/page/rules">커뮤니티이용규칙</a></li>
			<li><a href="/notice">공지사항</a></li>
			<li><a href="/page/faq">문의하기</a></li>
			<li class="copyright"><a href="/">© 애니타임</a></li>
		</ul>
		<span>직업정보제공사업 신고번호 : J1204020230008</span>
	</div>
	<script type="text/javascript">
		var _serverTime = 1696549810737;
		var _clientTime = new Date().getTime();
		var _diffTime = _clientTime - _serverTime;
		//var _apiServerUrl = 'https://api.everytime.kr';
		window._screenName = '시간표 - 학점계산기';
	</script>
	<script async=""
		src="https://www.googletagmanager.com/gtag/js?id=G-85ZNEFVRGL"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag() {
			dataLayer.push(arguments);
		}
		gtag('js', new Date());
		gtag('config', 'G-85ZNEFVRGL', {
			'send_page_view' : false
		});
		_gfn.logPageView();
	</script>
	<iframe allow="join-ad-interest-group" data-tagging-id="G-85ZNEFVRGL"
		data-load-time="1696552191914" height="0" width="0"
		style="display: none; visibility: hidden;"
		src="https://td.doubleclick.net/td/ga/rul?tid=G-85ZNEFVRGL&amp;gacid=2075506928.1696405846&amp;gtm=45je3a40&amp;aip=1&amp;fledge=1&amp;z=533221291"></iframe>

</body>
</html>