package jp.co.ixui.tamura.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ixui.tamura.controller.login.LoginForm;
import jp.co.ixui.tamura.controller.signup.SignupForm;
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

	/**
	 * セッションに社員番号を格納する
	 *
	 * @param request
	 * @param loginDTO
	 */
	public static void setEmpNoSession(HttpServletRequest request,LoginForm loginDTO) {
		HttpSession session = request.getSession();
		session.setAttribute("empNo", loginDTO.getEmpNo());
	}

	/**
	 * @param request
	 * @param signupDTO
	 */
	@Transactional
	public void createUser(SignupForm signupDTO) {
		EmpMst employee = new EmpMst();
		employee.setEmpNo(signupDTO.getEmpNo());
		employee.setName(signupDTO.getName());
		employee.setMail(signupDTO.getEmail());
		employee.setPass(UserService.getSafetyPassword(signupDTO.getPass(), signupDTO.getEmpNo()));
		this.empMstMapper.create(employee);
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
