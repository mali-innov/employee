package com.maliinnov.employee.security;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 86400000; // 1 day expressed in milliseconds 24*60*60*1000
    public static final String JWT_SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final String GET_COMPANY_LLC = "Mali";
    public static final String GET_ARRAYS_ADMINISTRATION = "System de gestion des gestion";
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous connecter pour accéder à cette page";
    public static final String ACCESS_DENIED_MESSAGE = "Vous n'avez pas la permission d'effectuer cette opération";

}
