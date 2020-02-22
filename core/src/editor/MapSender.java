package editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.imageio.IIOException;

import engine.Style;

public class MapSender {
    public boolean sendMap(String map){
        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = Style.MAPSENDER_TIMEOUT;
        try {
            Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, Style.MAPSENDER_IP, Style.MAPSENDER_PORT, socketHints);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.print(map);
            writer.close();
            reader.close();
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
