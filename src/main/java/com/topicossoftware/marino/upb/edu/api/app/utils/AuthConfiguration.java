package com.topicossoftware.marino.upb.edu.api.app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class AuthConfiguration {

    private static final String rsaPrivateKey = "MIICXAIBAAKBgE9iILJdoRY+ZljDDi5QIpqx7Vtd3IBfSPYxUFweCY/NSAN0QcZz"
            +"NhMlwLKWCVPmZuOwHAJRM0gqcuK7KYnJznCpRmbXj9EoGuYr556HcdzkoLoGDKUg"
            +"lzTDr94tWM75zSIkC/RCH68J2D3Zqh08P23Eh8jnCoi2DXmWnx5MzYjrAgMBAAEC"
            +"gYAXIRkzvxILYB0D6bk9Bz494m+s+lkf0iN5D/9BB8NzcbV0xC/R+Ymc5JgTsKGB"
            +"H5WE67ANgMk3Up7srum4auNKHwWTeuagvJ65Wv5X3qqM5pPy2s9Tt+QLwAo0Eu4y"
            +"ekTSdjqx7zVSH3RpyABAjFuumZsBCEH5rmukYgKHCJK/sQJBAI71F+eM9EMbrZ+J"
            +"C9hAIgouY/gvs1DLN6aWR/FJ/wlPGu7y72PMB2oywq8rS+fZY9zWHpSBY1LxSQJu"
            +"KysWQykCQQCOJ7bVK2w/nqcye1vgueg0zzmbxSCVp8TN+Xn1Sz51Jae2DeA6uNSQ"
            +"0yr2PF3xoPK1K23sA9TEhLzKUKfn16HzAkEAidlGyq29n7Rh0HNbK5Rz/nNLTLfa"
            +"Zi7/I7DIAKCEv5o682zob8f+4mW+GyFZ2WBjjkAeTr8RYppt91z6XVrjOQJBAIAm"
            +"y0tHFxIPa7k1vjFMR0p8WfnUbzibAu+Q7OI2T/r51ERqI++sOmzJAZzz9NjkeIjc"
            +"uv6ewYvcAfn9VjDKkwMCQC6sLZ27F+FW/OTvKR6/XGlmff4Zva/wF6ySbkVBD3At"
            +"U7VnQELa3z+mNO1uecEBEjqwHrdWSa8vTnv+7+S4SEI=";

    private static final String rsaPublicKey = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgE9iILJdoRY+ZljDDi5QIpqx7Vtd"
            +"3IBfSPYxUFweCY/NSAN0QcZzNhMlwLKWCVPmZuOwHAJRM0gqcuK7KYnJznCpRmbX"
            +"j9EoGuYr556HcdzkoLoGDKUglzTDr94tWM75zSIkC/RCH68J2D3Zqh08P23Eh8jn"
            +"Coi2DXmWnx5MzYjrAgMBAAE=";

    @Bean
    public Algorithm algorithm(){
        return Algorithm.HMAC384(rsaPrivateKey);
    }

    @Bean
    public JWTVerifier verifier(){
        return JWT.require(algorithm())
                .build();
    }
}