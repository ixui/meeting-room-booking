package jp.co.ixui.tamura.test.feature;

import static org.assertj.core.api.Assertions.*;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;


/**
 * ログイン周りに関する自動実行テスト
 * @author t-kawasaki
 *
 */
public class LoginTest extends FluentTest {

	@Test
    public void ログイン情報を入力してログインする() {

		//ログイン画面を表示
        goTo("https://" + System.getenv("SECURITY_USER_NAME") + ":" + System.getenv("SECURITY_USER_PASSWORD") + "@" + System.getenv("APP_URL") + "/login");

        fill("#empNo").with("1");
        fill("#pass").with("12345678");
        findFirst(".btn").click();

        assertThat(find(".header-right a").getText()).contains("ログアウト");
    }

}
