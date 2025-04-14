document.addEventListener("DOMContentLoaded", () => {
    const exhibitionList = document.querySelector(".exhibition-list");

    // 사용자 찜한 전시 데이터 가져오기
    const fetchFavorites = async () => {
        try {
            const response = await fetch(`${contextPath}/MyfavoriteExhibition.do`, {
                method: "GET",
                credentials: "include",
            });
            if (!response.ok) throw new Error(`Failed to fetch favorites: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error("Error fetching favorites:", error);
            return [];
        }
    };

    // 찜 데이터 렌더링
    const renderFavorites = async () => {
        const favorites = await fetchFavorites();

        if (favorites.length === 0) {
            exhibitionList.innerHTML = `
                <p>찜한 전시가 없습니다. <a href="RealExhibition.jsp">전시 목록 보러 가기</a></p>
            `;
            return;
        }

        exhibitionList.innerHTML = favorites.map(item => `
            <div class="exhibition-item">
                <a href="${item.link}" target="_blank" rel="noopener noreferrer">
                    <img src="${item.image}" alt="${item.title}">
                    <div class="exhibition-info">
                        <h3 class="title">${item.title}</h3>
                        <p class="schedule">📆 ${item.date}</p>
                        <p class="location">🚩 ${item.place}</p>
                    </div>
                </a>
                <button 
                    class="favorite-btn" 
                    data-title="${item.title}" 
                    aria-label="찜 취소"
                    style="background-color: red;">
                    ❤️
                </button>
            </div>
        `).join("");

        attachFavoriteEventHandlers();
    };

    // 버튼에 이벤트 핸들러 추가
    const attachFavoriteEventHandlers = () => {
        const buttons = document.querySelectorAll(".favorite-btn");
        buttons.forEach(button => {
            button.addEventListener("click", async (event) => {
                const title = event.target.getAttribute("data-title");

                try {
                    await removeFavoriteFromServer(title);
                    event.target.parentElement.remove(); // DOM에서 제거
                    console.log(`Removed favorite: ${title}`);
                } catch (error) {
                    console.error("Error removing favorite:", error);
                }
            });
        });
    };

    // 서버와 동기화 (찜 해제)
    const removeFavoriteFromServer = async (title) => {
        const url = `${contextPath}/removeFavorite.do`;
        try {
            const response = await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ title }),
            });
            if (!response.ok) throw new Error(`Failed to remove favorite: ${response.status}`);
        } catch (error) {
            console.error("Error syncing favorite with server:", error);
            throw error;
        }
    };

    // 초기 렌더링
    renderFavorites();
});
