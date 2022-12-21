package com.maliinnov.employee.security;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 86400000; // 1 day expressed in milliseconds 24*60*60*1000
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token ne peut pas être vérifié";
    public static final String GET_COMPAGNY_LLC = "THL Technologies, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "Maeva-Palace";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous connecter pour accéder à cette page";
    public static final String ACCESS_DENIED_MESSAGE = "Vous n'avez pas la permission d'effectuer cette opération";
    public static final String[] POST_AUTHORIZE_URL = { "/employee/**"};
    public static final String[] GET_AUTHORIZE_URL = { "/employee/**"};
    public static final String[] PUT_AUTHORIZE_URL = { "/employee/**"};
    public static final String[] DELETE_AUTHORIZE_URL = { "/employee/**"};
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
}
