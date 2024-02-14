import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket = null;
    private DataInputStream inputStd = null;
    private DataInputStream inputServer = null;
    private DataOutputStream out = null;

    public Client(String address, int port)
    {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            inputStd = new DataInputStream(System.in);
            inputServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException i) {
            System.out.println(i.getMessage());
            return;
        }

        String line = "";

        while (!line.equals("exit")) {
            try {
                line = inputStd.readLine();
                out.writeUTF(line);

                System.out.println(inputServer.readUTF());
            }
            catch (IOException i) {
                System.out.println("Server terminated");
                return;
            }
        }

        try {
            inputStd.close();
            out.close();
            socket.close();
        }
        catch (IOException i) {
            System.out.println(i.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8000);
    }
}
