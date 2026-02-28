import java.util.Arraylist;

class DataHandler{
    ArrayList<String> lectures = new ArrayList<>();

    private handleRequest(String requestRaw) throws IncorrectActionException{
        String request = requestRaw.split("|"); // or in any other way

        switch(request[0]){
            case "ADD":
                //some shit
                break;
            case "REMOVE":
                //some remove shit
                break;
            case "DISPLAY":
                //some fancy shit
                break;
            case "OTHER":
                //no clue what shit
                break;
            default:
                throw new IncorrectActionException("Go and Fuck Yourself");
                break;
        }
    }
}