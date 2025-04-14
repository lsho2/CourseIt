<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Midpoint and Members</title>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9d4fdebbd05e105355e69928d6b9cb28&libraries=services"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resource/css/pages/goSpot.css">
</head>
<body>
    <%@ include file="/resource/common/Header.jsp" %>
   <!-- 다시 하기 버튼 -->
    <button class="retry-button" onclick="window.location.href='MiddlePlace.jsp'">다시 하기</button>
    
    <div id="map"></div>
    <script>
        const mapContainer = document.getElementById('map');
        const mapOption = {
            center: new kakao.maps.LatLng(37.5665, 126.978), // 기본 중심 좌표
            level: 3
        };
        const map = new kakao.maps.Map(mapContainer, mapOption);

        $.ajax({
            url: '/CourseIt/FindMidpointServlet',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                if (!data || !Array.isArray(data.members)) {
                    console.error('Invalid data structure or members not found');
                    return;
                }

                // 중간 지점 계산
                const midpoint = new kakao.maps.LatLng(data.midpointLatitude, data.midpointLongitude);

                // 가장 가까운 지하철역 찾기
                $.ajax({
                    url: 'https://dapi.kakao.com/v2/local/search/keyword.json',
                    type: 'GET',
                    data: {
                        query: '지하철역',
                        x: midpoint.getLng(),
                        y: midpoint.getLat(),
                        radius: 10000 // 반경 설정
                    },
                    headers: {
                        Authorization: 'KakaoAK 676ae9f87e9169483e6cbd17eb3c6b17' // API 키
                    },
                    success: function(response) {
                        if (response.documents.length > 0) {
                            const subway = response.documents[0]; // 가장 가까운 역 하나만 사용
                            const subwayPosition = new kakao.maps.LatLng(subway.y, subway.x);

                            // 새로운 div 요소 생성
                            const subwayDiv = document.createElement("div");
                            const subwayName = document.createElement("h3");
                            subwayName.textContent = subway.place_name || "정보 없음";
                            subwayDiv.appendChild(subwayName);
                            subwayDiv.className = 'custom-overlay';

                            // 지하철역 오버레이 생성
                            const subwayOverlay = new kakao.maps.CustomOverlay({
                                position: subwayPosition,
                                content: subwayDiv,
                                map: map
                            });

                            // 지도 중심을 지하철역으로 이동
                            map.setCenter(subwayPosition);
                        } else {
                            console.log('No subway station found near the midpoint.');
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                        alert('지하철역을 불러오는 데 실패했습니다.');
                    }
                });

                // 멤버들의 오버레이 생성
                data.members.forEach((member) => {
                    const position = new kakao.maps.LatLng(member.latitude, member.longitude);
                    const div = document.createElement("div");
                    const heading = document.createElement("h3");
                    heading.textContent = member.name || "정보 없음";
                    div.appendChild(heading);
                    div.className = 'custom-overlay';

                    const overlay = new kakao.maps.CustomOverlay({
                        position: position,
                        content: div,
                        map: map
                    });
                });
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('데이터를 불러오는 데 실패했습니다.');
            }
        });
    </script>
</body>
</html>