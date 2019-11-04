package hu.oe.bakonyi.bkk.bkkdataapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

@Component
public class HeaderInterceptorConfiguration implements HandlerInterceptor {

    @Value("${api.key}")
    String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURL().toString().contains("bkk/dev")){
            if(request.getHeader(HttpHeaders.AUTHORIZATION) == null){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            String remoteApiKey = request.getHeader(HttpHeaders.AUTHORIZATION).toString();
            if(!apiKey.equals(remoteApiKey)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
        return true;
    }
}
