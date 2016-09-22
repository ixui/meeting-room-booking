package jp.co.ixui.tamura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 特定のリクエストに対してセキュリティ設定をカスタマイズする
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 静的リソースに対する認証処理を無効にする
		web.ignoring().antMatchers("/css/**", "/js/**");
	}

	// 認証設定
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 認可処理、loginFormにはすべてのユーザがアクセスできるようにする
		http.authorizeRequests()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated();

		// ログイン
		// フォーム認証を有効に
		http.formLogin()
				// ログインフォーム表示パス
				.loginPage("/login")
				// 認証処理のパス
//				.loginProcessingUrl("/login")
				// 認証失敗時の遷移先
//				.failureUrl("/login?error")
				// 認証成功時の遷移先
				.defaultSuccessUrl("/calendar", true)
				// ユーザ名、パスワードのパラメータ名
				.usernameParameter("empNo").passwordParameter("pass")
				.and();

		// ログアウト
		http.logout()
				// ログアウト完了後の遷移先
				.logoutSuccessUrl("/login");
	}

	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		UserDetailsService userDetailsService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {

			// DaoAuthenticationProviderのフィールドsaltsourceに入力されたempNoが設定されるようにする
			ReflectionSaltSource rss = new ReflectionSaltSource();
			rss.setUserPropertyToUse("getSalt");
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setSaltSource(rss);

			// 認証処理
			provider.setUserDetailsService(this.userDetailsService);
			provider.setPasswordEncoder(new LoginPasswordEncoder());
			auth.authenticationProvider(provider);

		}
	}
}
