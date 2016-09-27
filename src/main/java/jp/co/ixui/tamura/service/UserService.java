package jp.co.ixui.tamura.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ixui.tamura.controller.signup.SignupForm;
import jp.co.ixui.tamura.controller.user.UserForm;
import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.mapper.EmpMstMapper;

/**
 * @author tamura
 *
 */
@Service
public class UserService {

	@Autowired
	EmpMstMapper empMstMapper;

	@Autowired
	AuthenticationManager authenticationManager;

	/**
	 * 登録ユーザ一覧を表示するための情報を取得する。
	 *
	 * @return List<EmpMst>
	 */
	public List<EmpMst> getAllUser() {
		return this.empMstMapper.selectAllUser();
	}

	/**
	 * セッションに社員番号が存在するか確認する
	 *
	 * @param request
	 */
	public boolean isValidUserSession(HttpServletRequest request) {

    	// セッションを取得し取得できなかった場合はタイムアウトとみなす
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            return false;
        }

    	// リクエストに含まれるセッションIDでセッションを再取得し
        // 取得できないもしくは保持しているセッションと異なる場合はタイムアウトとみなす
        String requestSession = request.getRequestedSessionId();
        if( currentSession == null ||
        		requestSession == null ||
        		!request.isRequestedSessionIdValid() ||
        		!requestSession.equals(currentSession.getId())){

        	return false;
        }

        // セッションに保存されている認証情報から社員番号を取得する
        String empNo = getEmpNoFromAuthentication();

        if (empNo == null ||
    		"".equals(empNo) ||
    		"anonymousUser".equals(empNo)) {

        	return false;
		}

		return true;
	}

	/**
	 * セッションに保存された認証情報から社員番号を取得する
	 *
	 * @param request
	 * @param loginDTO
	 */
	@SuppressWarnings("static-method")
	public String getEmpNoFromAuthentication() {

		String empNo = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
        	empNo = ((UserDetails)principal).getUsername();
        } else {
        	empNo = principal.toString();
        }
		return empNo;
	}

	/**
	 * 新規ユーザ登録
	 *
	 * @param request
	 * @param signupDTO
	 */
	@Transactional
	public void createUser(SignupForm signupDTO) {
		EmpMst employee = new EmpMst();
		employee.setEmpNo(signupDTO.getEmpNo());
		employee.setName(signupDTO.getName());
		employee.setAuth(signupDTO.getAuth());
		employee.setMail("xxx@xxx.co.jp");
		employee.setPass(UserService.getSafetyPassword(signupDTO.getPass(), signupDTO.getEmpNo()));
		this.empMstMapper.create(employee);
	}

	/**
	 * ユーザー情報の更新
	 *
	 * @param userDTO
	 */
	public void updateUser(UserForm userDTO) {
		String empNo = getEmpNoFromAuthentication();
		EmpMst employee = new EmpMst();
		employee.setEmpNo(empNo);
		employee.setName(userDTO.getName());
		employee.setMail(userDTO.getEmail());
		employee.setPass(UserService.getSafetyPassword(userDTO.getPass(), empNo));
		this.empMstMapper.update(employee);
	}

	/**
	 * 	認証情報の更新
	 *
	 * @param UserDTO
	 */
	public void setAuthentication(UserForm userDto) {

		Authentication request = new UsernamePasswordAuthenticationToken(getEmpNoFromAuthentication(), userDto.getPass());
		Authentication result = this.authenticationManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
	}

	 /** パスワードを安全にするためのアルゴリズム */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    /** ストレッチング回数 */
    private static final int ITERATION_COUNT = 10000;
    /** 生成される鍵の長さ */
    private static final int KEY_LENGTH = 256;

    /**
     * 平文のパスワードとソルトから安全なパスワードを生成し、返却します
     *
     * @param password 平文のパスワード
     * @param salt ソルト
     * @return 安全なパスワード
     */
    @SuppressWarnings("boxing")
	public static String getSafetyPassword(String password, String salt) {

        char[] passCharAry = password.toCharArray();
        byte[] hashedSalt = getHashedSalt(salt);

        PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);

        SecretKeyFactory skf;
        try {
            skf = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        SecretKey secretKey;
        try {
            secretKey = skf.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        byte[] passByteAry = secretKey.getEncoded();

        // 生成されたバイト配列を16進数の文字列に変換
        StringBuilder sb = new StringBuilder(64);
        for (byte b : passByteAry) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    /**
     * ソルトをハッシュ化して返却します
     * ※ハッシュアルゴリズムはSHA-256を使用
     *
     * @param salt ソルト
     * @return ハッシュ化されたバイト配列のソルト
     */
    private static byte[] getHashedSalt(String salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(salt.getBytes());
        return messageDigest.digest();
    }
}
