package beans.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import db.JDBCUtil;



public class UserDAO {
	// 로그인시 사용하는 DAO
	// 이거 사용
    public boolean loginCheck(String userId, String userPw) {// 로그인, 회원가입 아이디 중복 확인
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean loginCon = false;
        try {
			conn = JDBCUtil.getConnection();
            String strQuery = "select userId, userPw from user where userId = ? and userPw = ?";

            pstmt = conn.prepareStatement(strQuery);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);
            
            rs = pstmt.executeQuery();
            loginCon = rs.next();
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
        	JDBCUtil.close(rs, pstmt, conn);
        }
        return loginCon;
    }	
    
    public String getUserName(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userName = null;

        try {
            conn = JDBCUtil.getConnection(); // 데이터베이스 연결
            System.out.println("DEBUG: getUserName called with userId = " + userId); // 전달된 userId 확인
            String query = "SELECT userName FROM user WHERE userId = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userName = rs.getString("userName"); // userName 컬럼 값 가져오기
                System.out.println("");

                System.out.println("DEBUG: Retrieved userName = " + userName); // 디버깅 출력
            } else {
                System.out.println("DEBUG: No user found with userId = " + userId); // 디버깅 출력
            }
        } catch (Exception ex) {
            System.out.println("Exception in getUserName: " + ex.getMessage());
        } finally {
            JDBCUtil.close(rs, pstmt, conn); // 리소스 정리
        }

        return userName; // 닉네임 반환
    }
    
    public String getUserEmail(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userEmail = null;

        try {
            conn = JDBCUtil.getConnection(); // 데이터베이스 연결
            System.out.println("DEBUG: getUserEmail called with userId = " + userId); // 전달된 userId 확인
            String query = "SELECT userEmail FROM user WHERE userId = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	userEmail = rs.getString("userEmail"); // userName 컬럼 값 가져오기
                System.out.println("");

                System.out.println("DEBUG: Retrieved userEmail = " + userEmail); // 디버깅 출력
            } else {
                System.out.println("DEBUG: No user found with userId = " + userId); // 디버깅 출력
            }
        } catch (Exception ex) {
            System.out.println("Exception in getUserName: " + ex.getMessage());
        } finally {
            JDBCUtil.close(rs, pstmt, conn); // 리소스 정리
        }

        return userEmail; // 닉네임 반환
    }
    
    
    

    // 회원가입시 사용
    // 아이디 중복 체크
    public boolean idCheck(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isDuplicate = false;

        try {
            conn = JDBCUtil.getConnection();
            String query = "SELECT userId FROM user WHERE userId = ?"; // 테이블 이름 확인
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            isDuplicate = rs.next(); // 결과가 있으면 중복
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }

        return isDuplicate;
    }

	
	
    public boolean userInsert(UserDTO uDTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean flag = false;

        try {
        	conn = JDBCUtil.getConnection();
            String strQuery = "INSERT INTO user (userId, userEmail, userPw, userName) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(strQuery);
            pstmt.setString(1, uDTO.getUserId());
            pstmt.setString(2, uDTO.getUserEmail());
            pstmt.setString(3, uDTO.getUserPw());
            pstmt.setString(4, uDTO.getUserName());

            int count = pstmt.executeUpdate();

            if (count == 1) {
                flag = true;
            }
          
    	} catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            JDBCUtil.close(pstmt, conn);
        }

        return flag;
    }
    
    // 회원정보 수
    public boolean updateUser(UserDTO uDTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = JDBCUtil.getConnection();
            String query = "UPDATE user " +
                           "SET userPw = CASE WHEN ? <> '' THEN ? ELSE userPw END, " +
                           "    userName = ?, " +
                           "    userEmail = ? " +
                           "WHERE userId = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, uDTO.getUserPw()); // 비밀번호 조건 검사
            pstmt.setString(2, uDTO.getUserPw());
            pstmt.setString(3, uDTO.getUserName()); // 닉네임
            pstmt.setString(4, uDTO.getUserEmail()); // 이메일
            pstmt.setString(5, uDTO.getUserId()); // 아이디

            int rowsAffected = pstmt.executeUpdate();
            isUpdated = rowsAffected > 0; // 업데이트 성공 여부 확인
        } catch (Exception ex) {
            System.out.println("Exception in updateUser: " + ex.getMessage());
        } finally {
            JDBCUtil.close(pstmt, conn);
        }

        return isUpdated;
    }

    
    public UserDTO getUserInfo(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            conn = JDBCUtil.getConnection();
            String strQuery = "SELECT userId, userName, userEmail FROM user WHERE userId = ?";
            pstmt = conn.prepareStatement(strQuery);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setUserEmail(rs.getString("userEmail"));
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }

        return user;
    }
    
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();
            String query = "SELECT userId, userName, userEmail FROM user";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setUserEmail(rs.getString("userEmail"));
                userList.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }
        return userList;
    }

    public boolean deleteUser(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isDeleted = false;

        try {
            conn = JDBCUtil.getConnection();
            String query = "DELETE FROM user WHERE userId = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            isDeleted = pstmt.executeUpdate() > 0;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt, conn);
        }
        return isDeleted;
    }
    // 아이디 찾기 이
    public String findUserIdByEmail(String userEmail) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userId = null;

        try {
            conn = JDBCUtil.getConnection();
            String query = "SELECT userId FROM user WHERE userEmail = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userId = rs.getString("userId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }

        return userId;
    }
    
    // 비밀번호 찾기 이용
    public String findUserPassword(String userId, String userEmail) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userPw = null;

        try {
            conn = JDBCUtil.getConnection();
            String query = "SELECT userPw FROM user WHERE userId = ? AND userEmail = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userEmail);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userPw = rs.getString("userPw");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }

        return userPw;
    }
    
    






	

}
