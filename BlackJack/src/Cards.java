public class Cards {

    String value;
    String type;

    public Cards(String value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return  value + type;
    }

    public int getValue(){
        if ("AJQK".contains(value)){  //return A J Q K
            if (value == "A"){
                return 11;
            }return 10;
        }
        return  Integer.parseInt(value); /// return 2-10
    }

    public boolean isAce(){
        return value == "A";
    }
}
