<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Bar</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=9d4fdebbd05e105355e69928d6b9cb28&libraries=services"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/pages/addMember.css">
</head>
<body>
   <%@ include file="/resource/common/Header.jsp" %>
   
<div class="container">
    <h3>참석자 추가👤</h3>
    <div class="input-group">
        <input type="text" id="nameInput" placeholder="참석자 이름 입력">
    </div>
    <div class="input-group">
        <input type="text" id="searchInput" placeholder="어디서 출발하시나요?">
        <button onclick="handleSearch()">검색</button>
    </div>
    <div id="results" class="result-list"></div>
</div>

<script>
    function handleSearch() {
        const keyword = document.getElementById("searchInput").value;
        const resultContainer = document.getElementById("results");

        if (!keyword) {
            alert("검색어를 입력해주세요.");
            return;
        }

        // 검색 결과 초기화
        resultContainer.innerHTML = "";

        // Kakao Places 객체 생성
        const ps = new kakao.maps.services.Places();

        ps.keywordSearch(keyword, function (data, status) {
            if (status === kakao.maps.services.Status.OK) {
                data.slice(0, 5).forEach(place => {
                    const name = place.place_name || "정보 없음";
                    const address = place.road_address_name || place.address_name || "주소 정보 없음";
                    const latitude = place.y;
                    const longitude = place.x;

                    // 새로운 div 요소 생성
                    const resultDiv = document.createElement("div");
                    resultDiv.className = "result";

                    // 제목 생성 (장소 이름)
                    const heading = document.createElement("h3");
                    heading.textContent = name;
                    resultDiv.appendChild(heading);

                    const addressPara = document.createElement("p");
                    addressPara.textContent = address;
                    resultDiv.appendChild(addressPara);

                    // 구분선 생성
                    const hr = document.createElement("hr");
                    resultDiv.appendChild(hr);

                    // 클릭 이벤트 추가
                    resultDiv.onclick = () => savePlace(name, address, latitude, longitude);

                    // 결과 컨테이너에 추가
                    resultContainer.appendChild(resultDiv);
                });
            } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
                alert("검색 결과가 존재하지 않습니다.");
            } else {
                alert("검색 중 오류가 발생했습니다.");
            }
        });
    }

    function savePlace(placeName, address, latitude, longitude) {
        const attendeeName = document.getElementById("nameInput").value; // 참석자 이름 가져오기
        console.log("Saving place:", placeName, address, latitude, longitude, attendeeName); // 디버깅용 로그

        // jQuery AJAX를 사용하여 GET 요청 전송
        $.ajax({
            url: "${pageContext.request.contextPath}/AddMemberServlet",
            type: "GET",
            dataType: "text", // 응답을 TEXT으로 처리
            data: {
                attendee_name: attendeeName, // 참석자 이름 추가
                place_name: placeName,
                address: address,
                latitude: latitude,
                longitude: longitude
            },
            success: function(response) {
                if (response.trim() === "SUCCESS") {
                    alert("장소가 저장되었습니다.");
                    // 저장 완료 후 middleplace.jsp로 리다이렉트
                    window.location.href = "MiddlePlace.jsp";
                } else {
                    alert("저장에 실패했습니다: " + response);
                }
            },
            error: function(xhr, status, error) {
                alert("AJAX 요청 중 오류가 발생했습니다: " + error);
            }
        });
    }
    
</script>
</body>
</html>
