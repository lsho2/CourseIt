<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="resource/css/pages/main.css">
<title> course it! Main page </title>
</head>
<body>
    <%@ include file="resource/common/Header.jsp" %>

    
    <section id="main">
    	<!-- 오늘의 데이로그 섹션 시작 -->
	    <section id="daylog">
	        <div class="daylog-header">
	            <h2>오늘의 데이로그</h2>
	            <a href="Daylog.jsp" class="view-all">전체보기 ▶</a>
	        </div>
	        
	        <div class="daylog-slider">
	            <button id="left-button">&lt;</button>
	            <div class="daylog-posts">
	                <!-- 포스터 3-4개를 담을 div, 개별 포스터는 img로 표시 -->
	                <div class="post">
					    <a href="">
					        <img src="resource/img/posts/post1.jpg" alt="카페투어">
					        <div class="post-text">서촌 한옥 골목골목<br> 감성 카페 투어</div>
					    </a>
					</div>
	                <div class="post">
					    <a href="">
					        <img src="resource/img/posts/post2.jpg" alt="서울근교 가을나들이">
					        <div class="post-text">가을 나들이 가기 좋은<br> 서울 근교 사진 명소</div>
					    </a>
					</div>					
	                <div class="post">
					    <a href="">
					        <img src="resource/img/posts/post3.jpg" alt="시드니 명소">
					        <div class="post-text">시드니에서 꼭!<br> 들러야하는 필수 코스!!</div>
					    </a>
					</div>
					<div class="post">
					    <a href="Recommand4.jsp">
					        <img src="resource/img/posts/post4.jpg" al="서울 크리스마스 행사">
					        <div class="post-text">서울 크리스마스<br> 행사일정</div>
					    </a>
					</div>	
					<div class="post">
					    <a href="">
					        <img src="resource/img/posts/post5.jpg" alt="가을 단풍 드라이브">
					        <div class="post-text">드라이브 하기 좋은<br> 가을 단풍 길</div>
					    </a>
					</div>						
	            </div>
	            <button id="right-button">&gt;</button>
	        </div>
	    <section/>
	    <div id="perform-promotion">   
		    <section id="exhibition-info">
			    <div class="exhibition-header">
			        <h2>12월의 전시, 행사 일정</h2>
			        <a href="RealExhibition.jsp" class="view-all">전체보기 ▶</a>
			    </div>
			    
			    <div class="exhibition-grid">
			        <!-- 전시행사 아이템 예시 (8개 표시) -->
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/24013928">
			                <img src="resource/img/exhibition1.jpg" alt="지킬앤하이드">
			                <div class="exhibition-info">
			                    <span class="type">뮤지컬</span>
			                    <h3 class="title">지킬앤하이드 (Jekyll & Hyde)-20주년</h3>
			                    <p class="schedule">📆 2024.11.29 ~ 2025.05.18</p>
			                    <p class="location">🚩 블루스퀘어 신한카드홀</p>
			                </div>
			            </a>
			        </div>
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/24012498">
			                <img src="resource/img/exhibition2.jpg" alt="알라딘">
			                <div class="exhibition-info">
			                    <span class="type">뮤지컬</span>
			                    <h3 class="title">알라딘</h3>
			                    <p class="schedule">📆 2024.11.22 ~ 2025.06.22</p>
			                    <p class="location">🚩 샤롯데씨어터</p>
			                </div>
			            </a>
			        </div>
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/24002802">
			                <img src="resource/img/exhibition3.jpg" alt="불편한 편의점">
			                <div class="exhibition-info">
			                    <span class="type">연극</span>
			                    <h3 class="title">뮤직드라마 〈불편한 편의점〉 - 올웨이즈씨어터</h3>
			                    <p class="schedule">📆 2023.04.08 ~ 2024.12.31</p>
			                    <p class="location">🚩 올웨이즈씨어터</p>
			                </div>
			            </a>
			        </div>
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/24011855">
			                <img src="resource/img/exhibition4.jpg" alt="디즈니 100년 기념전">
			                <div class="exhibition-info">
			                    <span class="type">전시회</span>
			                    <h3 class="title">디즈니 100년 특별전</h3>
			                    <p class="schedule">📆 2024.10.18 ~ 2024.12.31</p>
			                    <p class="location">🚩 K현대미술관</p>
			                </div>
			            </a>
			        </div>
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/24013237">
			                <img src="resource/img/exhibition5.jpg" alt="기억의순환">
			                <div class="exhibition-info">
			                    <span class="type">전시회</span>
			                    <h3 class="title">미나 페르호넨 디자인 여정: 기억의 순환</h3>
			                    <p class="schedule">📆 2024.09.12 ~ 2025.02.06</p>
			                    <p class="location">🚩 동대문디자인플라자(DDP) 전시 1관</p>
			                </div>
			            </a>
			        </div>
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/24011935">
			                <img src="resource/img/exhibition6.jpg" alt="광화문연가">
			                <div class="exhibition-info">
			                    <span class="type">뮤지컬</span>
			                    <h3 class="title">뮤지컬 〈광화문연가〉</h3>
			                    <p class="schedule">📆 2024.10.23 ~ 2025.01.05</p>
			                    <p class="location">🚩 디큐브 링크아트센터</p>
			                </div>
			            </a>
			        </div>
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/24013174">
			                <img src="resource/img/exhibition7.jpg" alt="우연히 웨스 앤더슨 2">
			                <div class="resource/exhibition-info">
			                    <span class="type">전시회</span>
			                    <h3 class="title">우연히 웨스 앤더슨 2</h3>
			                    <p class="schedule">📆 2024.10.18 ~ 2025.04.13</p>
			                    <p class="location">🚩 그라운드시소 센트럴</p>
			                </div>
			            </a>
			        </div>
			        <div class="exhibition-item">
			            <a href="https://tickets.interpark.com/goods/22014277">
			                <img src="resource/img/exhibition8.jpg" alt="한뼘사이">
			                <div class="exhibition-info">
			                    <span class="type">연극</span>
			                    <h3 class="title">연극 한뼘사이</h3>
			                    <p class="schedule">📆 2017.03.01 ~ 2024.12.31</p>
			                    <p class="location">🚩 라온아트홀</p>
			                </div>
			            </a>
			        </div>
			        
			    </div>
			</section>
	
			<!-- 프로모션 섹션 -->
		    <section id="promotion">
		        <div class="promo-box">
		            <p>마이로그 작성하고<br>경품 응모권 받기!!</p>
		        </div>
		        <div class="promo-box">
		            <p>마이로그 작성하고<br>경품 응모권 받기!!</p>
		        </div>
		    </section>
		</div>    

    </section>
    
    
    <%@ include file="/resource/common/Footer.jsp" %>
</body>
</html>