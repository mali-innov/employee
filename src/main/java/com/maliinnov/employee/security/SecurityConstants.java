package com.maliinnov.employee.security;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 86400000; // 1 day expressed in milliseconds 24*60*60*1000
    public static final String JWT_SECRET_KEY = "secretKey";
    public static final String GET_COMPANY_LLC = "Mali Innov, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "Gestion des employés";
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous connecter pour accéder à cette page";
    public static final String ACCESS_DENIED_MESSAGE = "Vous n'avez pas la permission d'effectuer cette opération";

}
