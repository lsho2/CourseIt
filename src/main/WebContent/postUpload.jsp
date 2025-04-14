<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이로그 작성</title>
    <link rel="stylesheet" type="text/css" href="resource/css/pages/postupload.css">

</head>
<body class="main-body">

<%@ include file="/resource/common/Header.jsp" %>

<div class="main-container" style="margin-left: 20vw; padding: 20px; max-width: 800px; background-color: #ffffff;">
    <h1 class="title">마이로그 작성</h1>
    <form action="upload.do" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">

        
        <!-- 제목 입력 -->
        <label for="title">제목</label>
        <input type="text" id="title" name="title" placeholder="제목을 입력하세요" class="input-title" required>

			<!-- 사진 업로드 영역 -->
			<div class="photo-upload-wrapper">
			    <div class="file-upload-container">
			        <!-- 첫 번째 파일 -->
			        <label class="file-box" for="file1">
			            <img id="preview1" src="" alt="미리보기" style="display: none; width: 100%; height: 100%; object-fit: cover;">
			            <span id="text1">+ 사진 추가<br>1/3</span>
			            <input type="file" id="file1" name="files[]" accept="image/*" onchange="previewImage(event, 'preview1', 'text1')">
			        </label>
			        <!-- 두 번째 파일 -->
			        <label class="file-box" for="file2">
			            <img id="preview2" src="" alt="미리보기" style="display: none; width: 100%; height: 100%; object-fit: cover;">
			            <span id="text2">+ 사진 추가<br>2/3</span>
			            <input type="file" id="file2" name="files[]" accept="image/*" onchange="previewImage(event, 'preview2', 'text2')">
			        </label>
			        <!-- 세 번째 파일 -->
			        <label class="file-box" for="file3">
			            <img id="preview3" src="" alt="미리보기" style="display: none; width: 100%; height: 100%; object-fit: cover;">
			            <span id="text3">+ 사진 추가<br>3/3</span>
			            <input type="file" id="file3" name="files[]" accept="image/*" onchange="previewImage(event, 'preview3', 'text3')">
			        </label>
			    </div>
			</div>


<script>
function previewImage(event, previewId, textId) {
    const file = event.target.files[0]; // 선택된 파일 가져오기
    if (file && file.type.startsWith('image/')) { // 이미지 파일인지 확인
        const reader = new FileReader(); // FileReader 객체 생성
        reader.onload = function(e) {
            const imgElement = document.getElementById(previewId); // 이미지 요소
            const textElement = document.getElementById(textId); // 텍스트 요소

            imgElement.src = e.target.result; // 이미지 src에 파일 내용 설정
            imgElement.style.display = 'block'; // 이미지 보이기
            textElement.style.display = 'none'; // 텍스트 숨기기
        };
        reader.readAsDataURL(file); // 파일을 Data URL로 읽기
    } else {
        alert('이미지 파일만 업로드할 수 있습니다!');
        event.target.value = ''; // 잘못된 파일 선택 시 리셋
    }
}
</script>



        <!-- 내용 입력 -->
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="6" placeholder="공간에서의 경험이나 정보를 자세히 작성할수록&#10;다른 코스잇 유저들에게 큰 도움이 될 거예요." class="description-input" required></textarea>

        <!-- 제출 버튼 -->
		<button type="submit" class="upload-button">마이로그 업로드</button>


    </form>
</div>
<script>
function validateForm() {
    const title = document.getElementById('title').value.trim();
    const content = document.getElementById('content').value.trim();
    const file1 = document.getElementById('file1').files[0];

    if (!title || !content || !file1) {
        alert('제목, 내용, 그리고 이미지를 모두 입력해주세요.');
        return false;
    }
    return true;
}
</script>

</body>
</html>
