document.addEventListener("DOMContentLoaded", () => {
    const exhibitionList = document.querySelector(".exhibition-list");

    // 전시 데이터를 가져오는 함수
    const fetchExhibitions = async () => {
        try {
            const response = await fetch(`${contextPath}/resource/exhibitData/finalExhibitions.json`);
            if (!response.ok) throw new Error(`Failed to fetch exhibitions: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error("Error fetching exhibitions:", error);
            return [];
        }
    };

    // 사용자 찜 데이터를 가져오는 함수 (로그인 상태에서만 호출)
    const fetchFavorites = async () => {
        if (!isLoggedIn) return [];
        try {
            const response = await fetch(`${contextPath}/MyfavoriteExhibition.do`, {
                method: 'GET',
                credentials: 'include',
            });
            if (!response.ok) throw new Error(`Failed to fetch favorites: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error("Error fetching favorites:", error);
            return [];
        }
    };

    // 전시 데이터를 렌더링하는 함수
    const renderExhibitions = async () => {
        const exhibitions = await fetchExhibitions();
        const favorites = isLoggedIn ? await fetchFavorites() : [];
        const favoriteTitles = new Set(favorites.map(fav => fav.title));

        if (exhibitions.length === 0) {
            exhibitionList.innerHTML = "<p>전시 데이터를 불러올 수 없습니다.</p>";
            return;
        }

		exhibitionList.innerHTML = exhibitions.map(item => {
		    const isFavorited = favoriteTitles.has(item.title);
		    return `
		        <div class="exhibition-item">
		            <a href="${item.link}" target="_blank" rel="noopener noreferrer">
		                <img src="${item.image}" alt="${item.title}">
		                <div class="exhibition-info">
		                    <h3 class="title">${item.title}</h3>
		                    <p class="schedule">📆 ${item.date}</p>
		                    <p class="location">🚩 ${item.place}</p>
		                </div>
		            </a>
		            ${isLoggedIn ? `
		                <button 
		                    class="favorite-btn" 
		                    data-title="${item.title}" 
		                    data-link="${item.link}" 
		                    data-image="${item.image}" 
		                    data-date="${item.date}" 
		                    data-place="${item.place}" 
		                    aria-label="${isFavorited ? '찜 취소' : '찜하기'}"
		                    style="background-color: ${isFavorited ? 'red' : 'gray'};"
		                >
		                    ${isFavorited ? '🤍' : '❤️'}
		                </button>
		            ` : ""}
		        </div>
		    `;
		}).join("");

        if (isLoggedIn) attachFavoriteEventHandlers();
    };

    // 찜 버튼에 이벤트 핸들러 연결
	const attachFavoriteEventHandlers = () => {
	    const buttons = document.querySelectorAll(".favorite-btn");
	    buttons.forEach(button => {
	        button.addEventListener("click", async (event) => {
	            const item = {
	                title: event.target.getAttribute("data-title"),
	                link: event.target.getAttribute("data-link"),
	                image: event.target.getAttribute("data-image"),
	                date: event.target.getAttribute("data-date"),
	                place: event.target.getAttribute("data-place"),
	            };
	            const isAdding = event.target.getAttribute("aria-label") === "찜하기";
	
	            try {
	                await syncFavoriteWithServer(item, isAdding);
	                // Update button styles and text
	                if (isAdding) {
	                    event.target.style.backgroundColor = "red";
	                    event.target.setAttribute("aria-label", "찜 취소");
	                    event.target.innerHTML = "🤍";
	                } else {
	                    event.target.style.backgroundColor = "gray";
	                    event.target.setAttribute("aria-label", "찜하기");
	                    event.target.innerHTML = "❤️";
	                }
	            } catch (error) {
	                console.error("Error updating favorite:", error);
	            }
	        });
	    });
	};


    // 서버와 찜 데이터 동기화
    const syncFavoriteWithServer = async (item, isAdding) => {
        const url = isAdding ? `${contextPath}/addFavorite.do` : `${contextPath}/removeFavorite.do`;
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(item),
            });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(`Failed to sync favorite: ${response.status}, ${errorData.message || 'Unknown error'}`);
            }
        } catch (error) {
            console.error("Error syncing favorite with server:", error);
            throw error;
        }
    };

    // 초기 렌더링
    renderExhibitions();
});
