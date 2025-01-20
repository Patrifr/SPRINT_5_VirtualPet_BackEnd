package cat.itacademy.s05.t02.n01.exception.custom;

public class InvalidPetInteraction extends RuntimeException {
    public InvalidPetInteraction(String message){
        super(message);
    }
}

