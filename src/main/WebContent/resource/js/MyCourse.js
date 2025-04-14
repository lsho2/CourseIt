document.addEventListener("DOMContentLoaded", () => {
    const mapContainer = document.getElementById('map');
    const mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 5
    };
    const map = new kakao.maps.Map(mapContainer, mapOption);
    const placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
    const ps = new kakao.maps.services.Places(map);

    // 마커 관리
    let markers = [];

    // 카테고리 클릭 이벤트
    const categoryItems = document.querySelectorAll("#category li");
    categoryItems.forEach(item => {
        item.addEventListener("click", () => {
            const category = item.id;
            searchCategoryPlaces(category, ps, map, markers, placeOverlay);
        });
    });

    // 키워드 검색 이벤트
    document.getElementById("searchForm").addEventListener("submit", (e) => {
        e.preventDefault();
        const keyword = document.getElementById("keyword").value;
        if (keyword) {
            ps.keywordSearch(keyword, (data, status) => {
                if (status === kakao.maps.services.Status.OK) {
                    displayPlaces(data, map, markers, placeOverlay);
                } else {
                    alert("검색 결과가 없습니다.");
                }
            });
        }
    });
});

// 카테고리 장소 검색
function searchCategoryPlaces(category, ps, map, markers, placeOverlay) {
    ps.categorySearch(category, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
            displayPlaces(data, map, markers, placeOverlay);
        } else {
            alert("해당 카테고리의 결과가 없습니다.");
        }
    }, { useMapBounds: true });
}

// 장소 표시
function displayPlaces(places, map, markers, placeOverlay) {
    markers.forEach(marker => marker.setMap(null)); // 기존 마커 제거
    markers.length = 0;

    const bounds = new kakao.maps.LatLngBounds();
    places.forEach(place => {
        const position = new kakao.maps.LatLng(place.y, place.x);
        const marker = new kakao.maps.Marker({ position });
        markers.push(marker);
        marker.setMap(map);

        // 마커 클릭 이벤트
        kakao.maps.event.addListener(marker, 'click', () => {
            displayPlaceInfo(place, placeOverlay, map);
        });

        bounds.extend(position);
    });
    map.setBounds(bounds);
}

// 장소 정보 표시
function displayPlaceInfo(place, placeOverlay, map) {
    const content = `
        <div class="placeinfo">
            <h4>${place.place_name}</h4>
            <p>${place.road_address_name || place.address_name}</p>
        </div>
    `;
    placeOverlay.setContent(content);
    placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
    placeOverlay.setMap(map);
}
