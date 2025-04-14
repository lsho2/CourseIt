package beans.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import db.JDBCUtil;

@WebServlet("/upload.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class PostUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "uploads"; // 업로드 디렉토리

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String appPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(appPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String title = null;
        String content = null;
        StringBuilder imagePaths = new StringBuilder(); // 이미지 경로를 저장

        for (Part part : request.getParts()) {
            String contentDisp = part.getHeader("content-disposition");

            if (contentDisp.contains("filename")) { // 파일 처리
                String fileName = extractFileName(part);
                if (fileName != null && !fileName.isEmpty()) {
                    String filePath = appPath + File.separator + fileName;
                    part.write(filePath);
                    imagePaths.append(UPLOAD_DIR).append("/").append(fileName).append(","); // 경로 추가
                }
            } else { // 일반 필드 처리
                if (part.getName().equals("title")) {
                    title = readPartValue(part);
                } else if (part.getName().equals("content")) {
                    content = readPartValue(part);
                }
            }
        }

        // 마지막 콤마 제거
        if (imagePaths.length() > 0) {
            imagePaths.setLength(imagePaths.length() - 1);
        }

        // 디버깅: 값 확인
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("Image Paths: " + imagePaths);

        if (title == null || content == null) {
            response.getWriter().println("Title or Content cannot be null.");
            return;
        }

        // DB 저장
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "INSERT INTO posts (userId, title, content, image_url) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, userId);
                pstmt.setString(2, title);
                pstmt.setString(3, content);
                pstmt.setString(4, imagePaths.toString()); // 경로들 저장
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database Error: " + e.getMessage());
            return;
        }

        response.sendRedirect("Mypagemain.jsp");
    }


    // 파일 이름 추출
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }

    // 필드 값 읽기
    private String readPartValue(Part part) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
        StringBuilder value = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            value.append(line);
        }
        return value.toString();
    }
}
