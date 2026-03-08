package com.firststep.primer_paso.exception;

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
}
