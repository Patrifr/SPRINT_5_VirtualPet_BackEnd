package cat.itacademy.s05.t02.n01.exception.custom;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){
        super(message);
    }
}

