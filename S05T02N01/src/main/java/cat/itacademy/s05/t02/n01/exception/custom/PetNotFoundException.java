package cat.itacademy.s05.t02.n01.exception.custom;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String message){
        super(message);
    }
}

