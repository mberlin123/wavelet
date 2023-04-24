import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    String savedStrings = "";

    public String handleRequest(URI url) {
        //Home page
        if (url.getPath().equals("/")) {
            return String.format("Welcome to string server! Use /add-message?s=<string> to add to the list of strings stored on this server");
        }
        else 
        {
            System.out.println("Path: " + url.getPath());
            //Add a string to the list
            if (url.getPath().contains("/add-message")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    //First message
                    if (savedStrings == "")
                    {
                        savedStrings = parameters[1];
                    }
                    //Later messages
                    else
                    {
                        savedStrings = savedStrings + "\n" + parameters[1];
                    }

                    return savedStrings;
                }
            }
            
            //Else if invalid address return error message
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