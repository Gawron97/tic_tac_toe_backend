package com.example.tic_tac_toe_backend.utils.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtDecoder jwtDecoder;

    @Override
    public Principal determineUser(ServerHttpRequest request, WebSocketHandler handler, Map<String, Object> attributes) {
        log.info("CustomHandshakeHandler is determining the user.");
        String token = "eyJraWQiOiI1VVdUdXIyZ01CaW8wbTM4MHBEVUozb1JcLzBLS3ZWcUg5V3JtWG1RaEJabz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NDk4MzQ2OC1hMGIxLTcwZDYtY2ZkYS05NDgxZGQwNmJlY2MiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV92MW8xNFVKSmQiLCJjbGllbnRfaWQiOiIyNTBjajVxZmdjamRnZ3NrZzEwbjdsOWlwNCIsIm9yaWdpbl9qdGkiOiIyZTZmZTlhOC01MTEwLTRjNzYtODg4MC1mY2MwMDRhMTU2ZjciLCJldmVudF9pZCI6Ijk5N2IyM2Y5LWFjMzAtNGMwOC1iYWYxLWI3MDM5OWZlNTE4ZiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MTM4NzIxNDUsImV4cCI6MTcxMzg5NDgxOSwiaWF0IjoxNzEzODkxMjE5LCJqdGkiOiI2NWRkNDMxNi1iM2Y3LTQ2ZDYtOTJkNC0wOTg1OTU5NzEyMzkiLCJ1c2VybmFtZSI6Imdhd3JvbiJ9.t22olulekFZ6CH8guNzEuE5CxmdrBhHX880ojeVuRTv6bxLS0wPqcSmRng4rUOWMzyFOW622810I2_ZC2vPQOxP1iMVIGhcXiaSrOjl_T_-lgoD68eobq-IZ63VB9psDJc7fPhkx2s_qaoAD-XElve57IcwhEPLDKzYzLrhXiEYZ8ZvLxqWvbd3x6Zw4ctB8TLzg5uaIQP_1GmL7px0341MdZTnuh_TI8JJur5hyvQ7mYQf19Ufr9EmavIY_Gf6C1hgHn13QouvneZYcmUKXt2MHSE_P1mPn8wX0nbCwfZB36SD46B0BhxUBeOf1wS6IwNHQBk5GK6RdSnw9PluKJw";
        Jwt jwt = jwtDecoder.decode(token);
        JwtAuthenticationToken auth = new JwtAuthenticationToken(jwt);
        return auth;
    }

}
