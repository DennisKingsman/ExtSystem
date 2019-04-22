package studentpj.net;

import studentpj.Greetable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Server
{
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225, 200);

        Map<String, Greetable> handlers = loadHandlers(); //load classes

        System.out.println("Server is started ");
        while (true)
        {
            Socket client = socket.accept(); //stream of bytes
            new SimpleServer(client, handlers).start(); //create new thread
        }
    }

    private static Map<String, Greetable> loadHandlers() {
        Map<String, Greetable> result = new HashMap<>();

        try (InputStream is = Server.class.getClassLoader() //get instructions from de "server.properties"
            .getResourceAsStream("server.properties")) {

            Properties properties = new Properties();
            properties.load(is);

            for(Object command : properties.keySet()){ //for each instruction
                String className = properties.getProperty(command.toString()); //find className

                Class<Greetable> cl = (Class<Greetable>) Class.forName(className); //load class by className
                Greetable handler = cl.getConstructor().newInstance(); //create object of dis class
                result.put(command.toString(), handler);  // creating table
            }

        }catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return result;
    }
}

class SimpleServer extends Thread
{
    private Socket client;
    private Map<String, Greetable> handlers;

    public SimpleServer(Socket client,  Map<String, Greetable> handlers)
    {
        this.client = client;
        this.handlers = handlers;
    }

    @Override
    public void run()
    {
        handleRequest();
    }

    private void handleRequest()
    {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream())); //string <- chars <- bytes from Internet
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String request = bufferedReader.readLine();
            String[] lines = request.split("\\s+"); // split with "    "

            String command = lines[0];
            String userName = lines[1];
            System.out.println("server got string: " + command);
            System.out.println("server got string: " + userName);

//            Thread.sleep(2000);

            String response = buildResponse(command, userName);
            bufferedWriter.write(response);
            bufferedWriter.newLine();
            bufferedWriter.flush(); // send a bite of data

            bufferedReader.close();
            bufferedWriter.close();

            client.close();
        }catch (Exception ex){
            ex.printStackTrace(System.out);

        }
    }

    private String buildResponse(String command, String userName) {
        Greetable handler = handlers.get(command);
        if(handler != null)
        {
            return handler.buildResponse(userName);
        }

        return "Hello " + userName;
    }
}
