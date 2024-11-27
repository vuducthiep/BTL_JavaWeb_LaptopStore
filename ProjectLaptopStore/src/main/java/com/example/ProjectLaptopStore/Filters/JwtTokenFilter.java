package com.example.ProjectLaptopStore.Filters;


import com.example.ProjectLaptopStore.DTO.CustomUserDetails;
import com.example.ProjectLaptopStore.Entity.UserEntity;
import com.example.ProjectLaptopStore.Util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//@Component
//@RequiredArgsConstructor
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final JwtTokenUtil jwtTokenUtil;
//
//    private CustomUserDetails customUserDetails;
//
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            if (isBypassToken(request)) {
//                filterChain.doFilter(request, response); // enable bypass
//                return;
//            }
//            final String authHeader = request.getHeader("Authorization");
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                return;
//            }
//            final String token = authHeader.substring(7);
//
//            // Trích xuất id-user từ token
//            int id_user = jwtTokenUtil.getUserID(token);
//            int id_customer = jwtTokenUtil.getCustomerID(token);
//            int id_cart = jwtTokenUtil.getCartID(token);
//            String phone = jwtTokenUtil.extractPhone(token);
//
//
//            if (id_user > 0 && SecurityContextHolder.getContext().getAuthentication() == null) {
//                // Tải thông tin người dùng dựa trên id-user
//                //CustomUserDetails userDetails = (CustomUserDetails) customUserDetails.loadUserByUsername(phone);
//
//                if ( !jwtTokenUtil.isTokenExpired(token)) {
//                    // Thiết lập thông tin người dùng trong SecurityContext
//                    CustomUserDetails userDetails = new CustomUserDetails();
//                    userDetails.setId_User(id_user);
//                    userDetails.setId_Customer(id_customer);
//                    userDetails.setId_Cart(id_cart);
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null,
//                                    (Collection<? extends GrantedAuthority>) userDetails.getAuthorities());
//                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            }
//            filterChain.doFilter(request, response); // continue chain
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//        }
//    }
//    private boolean isBypassToken(@NonNull  HttpServletRequest request) {
//        final List<Pair<String, String>> bypassTokens = Arrays.asList(
//                Pair.of(String.format("/register"), "POST"),
//                Pair.of(String.format("/login"), "POST")
//        );
//        for(Pair<String, String> bypassToken: bypassTokens) {
//            if (request.getServletPath().contains(bypassToken.getFirst()) &&
//                    request.getMethod().equals(bypassToken.getSecond())) {
//                return true;
//            }
//        }
//        return false;
//    }
//}

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Nếu là các API bypass không cần xác thực, bỏ qua filter
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response); // tiếp tục chuỗi filter
                return;
            }

            // Lấy token từ header
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            // Trích xuất token (loại bỏ "Bearer ")
            final String token = authHeader.substring(7);

            // Giải mã token và lấy các giá trị
            int idUser = jwtTokenUtil.getUserID(token);
            int idCart = jwtTokenUtil.getCustomerID(token);
            int idCustomer = jwtTokenUtil.getCartID(token);
            String phoneNumber = jwtTokenUtil.extractPhone(token);

                // Tải thông tin người dùng từ database (thường qua UserDetailsService)

                // Kiểm tra token hợp lệ và chưa hết hạn
                if (jwtTokenUtil.CheckValidateToken(token)) {
                    // Tạo CustomUserDetails chứa các thông tin người dùng
                    CustomUserDetails userDetails = new CustomUserDetails();
                    userDetails.setId_User(idUser);
                    userDetails.setId_Customer(idCustomer);
                    userDetails.setId_Cart(idCart);
                    userDetails.setPhoneNumber(phoneNumber);

                    // Thiết lập thông tin xác thực trong SecurityContext
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            // Tiếp tục chuỗi filter
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/register", "api"), "POST"),
                Pair.of(String.format("%s/login", "api"), "POST")
        );
        for (Pair<String, String> bypassToken : bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
}

