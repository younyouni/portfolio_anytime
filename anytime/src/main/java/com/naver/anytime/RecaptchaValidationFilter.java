package com.naver.anytime;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class RecaptchaValidationFilter extends OncePerRequestFilter {

    private AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher("/member/loginProcess", "POST");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            String recaptchaToken = request.getParameter("grecaptcha");

            // Google reCAPTCHA API 엔드포인트 URL
            String url = "https://www.google.com/recaptcha/api/siteverify";

            // Google reCAPTCHA 시크릿 키
            String secretKey = "6LfKpMkoAAAAABCXYqKqMu_pN_YmSVzVW1fVoEE2";

            // 요청 파라미터 설정
            String parameters = "secret=" + URLEncoder.encode(secretKey, "UTF-8") +
                    "&response=" + URLEncoder.encode(recaptchaToken, "UTF-8");

            // API 요청을 위한 연결 설정
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // 요청 파라미터 전송
            connection.getOutputStream().write(parameters.getBytes("UTF-8"));

            // 응답 받기
            int responseCode = connection.getResponseCode();
            StringBuilder responseBuilder = new StringBuilder(); 
         // 응답 내용 읽기
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
            } catch (IOException e) {
                // 예외 처리
                e.printStackTrace();
            }

            // 응답 내용 파싱
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBuilder.toString(), JsonObject.class);
            boolean success = jsonObject.get("success").getAsBoolean();  // success 변수 설정
            if (success) {
                filterChain.doFilter(request, response);
            } else {
                // 검증 결과가 실패라면 로그인 요청을 거부
                // ...
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
