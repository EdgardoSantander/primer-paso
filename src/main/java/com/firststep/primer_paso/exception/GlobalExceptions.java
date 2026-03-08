package com.firststep.primer_paso.exception;

import io.jsonwebtoken.ExpiredJwtException;

public class GlobalExceptions {

    public static  class emailYaExistenteException extends RuntimeException{
        public emailYaExistenteException(String mensaje){
            super(mensaje);
        }
    }

    public static class usuarioNoEncontradoException extends RuntimeException{
        public usuarioNoEncontradoException(String mensaje){
            super(mensaje);
        }
    }

    public static class tokenExpiradoException extends RuntimeException{
        public tokenExpiradoException(String mensaje){
            super(mensaje);
        }
    }

    public static class tokenInvalidoException extends  RuntimeException{
        public tokenInvalidoException(String mensaje){
            super(mensaje);
        }
    }
}
