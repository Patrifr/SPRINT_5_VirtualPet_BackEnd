package cat.itacademy.s05.t02.n01.exception.custom;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message){
        super(message);
    }
}

