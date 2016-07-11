package jp.co.ixui.tamura.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import jp.co.ixui.tamura.service.UserService;

@Component
public class SessionExpiredFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 前処理
    	HttpServletRequest req = (HttpServletRequest) request;
		if (isTarget(req) && !UserService.isValidUserSession(req)) {
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/timeout");
            return;
		}


        // 次のFilterへ
        chain.doFilter(request, response);


        // 後処理

    }

    @Override
    public void destroy() {
    	// 終了処理
    }


    /**
     * 対象のリクエストか判定
     * ログイン画面、タイムアウト表示ログイン画面、新規ユーザ登録画面については対象外
     * css, js等のリソースアクセスについては対象外
     */
    private boolean isTarget(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return (uri.indexOf("/login") < 0 &&
        		 uri.indexOf("/signup") < 0 &&
       		     uri.indexOf("/timeout") < 0 &&
        		 uri.indexOf("/css") < 0 &&
        		 uri.indexOf("/js") < 0);
    }


}