// Sign_up.js

$(document).ready(function () {
    // 아이디 중복 확인 버튼 클릭 이벤트
    $(".check-btn").on("click", function () {
        var userId = $("#userId").val().trim();

        // 입력값 유효성 검사
        if (!validateUserId(userId)) {
            return;
        }

        // AJAX 요청으로 중복 확인
        checkUserId(userId);
    });

    // 비밀번호와 비밀번호 확인 비교
    $("#pwdre").on("input", function () {
        var userPw = $("#userPw").val();
        var pwdre = $("#pwdre").val();

        if (userPw !== pwdre) {
            $("#pwdreFeedback")
                .text("비밀번호가 다르다구욧!")
                .css("color", "red");
        } else {
            $("#pwdreFeedback")
                .text("비밀번호 일치 했습니다:)")
                .css("color", "black");
        }
    });
});

// 아이디 유효성 검사 함수
function validateUserId(userId) {
    if (!userId) {
        alert("아이디를 입력해주세요.");
        return false;
    }
    if (userId.length < 4 || userId.length > 16) {
        alert("아이디는 4~16자만 허용됩니다.");
        return false;
    }
    if (!/^[a-zA-Z0-9]+$/.test(userId)) {
        alert("아이디는 영문과 숫자만 허용됩니다.");
        return false;
    }
    return true;
}

// 서버로 아이디 중복 확인 요청 함수
function checkUserId(userId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/checkId.do", // contextPath는 JSP에서 전달됨
        data: { userId: userId },
        success: function (response) {
            if (response === "DUPLICATE") {
                alert("이미 사용 중인 아이디입니다.");
                $("#userIdFeedback")
                    .text("이미 사용 중인 아이디입니다.")
                    .css("color", "red");
            } else if (response === "AVAILABLE") {
                alert("사용 가능한 아이디입니다.");
                $("#userIdFeedback")
                    .text("사용 가능한 아이디입니다.")
                    .css("color", "green");
            }
        },
        error: function (xhr, status, error) {
            console.error("Error during AJAX request:", error);
            alert("아이디 중복 확인 중 오류가 발생했습니다.");
        },
    });
}
