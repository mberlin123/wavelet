import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;

class Handler implements URLHandler {
    LinkedList<String> savedStrings = new LinkedList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("My name is Michael!");
        } 
        else 
        {
            System.out.println("Path: " + url.getPath());
            //Add a string to the list
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    savedStrings.add(parameters[1]);
                    return String.format("Added %s to saved strings!", parameters[1]);
                }
            }
            else if (url.getPath().contains("/search"))
            {
                String[] parameters = url.getQuery().split("=");
                LinkedList<String> listOfValidStrings = new LinkedList<String>();
                if (parameters[0].equals("s")) {
                    for (String string : savedStrings) {
                        if (string.contains(parameters[1]) == true)
                        {
                            listOfValidStrings.add(string);
                        }
                    }

                    String returnedString = "Valid Strings found: ";
                    for (String string : listOfValidStrings) {
                        returnedString += (string + ", ");
                    }
                    return returnedString;
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}