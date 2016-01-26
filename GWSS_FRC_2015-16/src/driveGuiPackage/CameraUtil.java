/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driveGuiPackage;

import static com.sun.javafx.fxml.expression.Expression.add;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;

public class CameraUtil {

    public static void main(String[] args) {

        try {
            URL myURL = new URL("http://example.com/");
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            
            CameraUtil camera = new CameraUtil(myURL);
            
        } catch (MalformedURLException e) {
            // new URL() failed
            // ...
        } catch (IOException e) {
            // openConnection() failed
            // ...
        }

        
    }



    public CameraUtil(URL mediaURL) {
        setLayout(new BorderLayout()); // use a BorderLayout

        // Use lightweight components for Swing compatibility
        Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);

        try {
            // create a player to play the media specified in the URL
            Player mediaPlayer = Manager.createRealizedPlayer(mediaURL);

            // get the components for the video and the playback controls
            Component video = mediaPlayer.getVisualComponent();
            Component controls = mediaPlayer.getControlPanelComponent();

            if (video != null) {
                add(video, BorderLayout.CENTER); // add video component
            }
            if (controls != null) {
                add(controls, BorderLayout.SOUTH); // add controls
            }
            mediaPlayer.start(); // start playing the media clip
        } // end try
        catch (NoPlayerException noPlayerException) {
            System.err.println("No media player found");
        } // end catch
        catch (CannotRealizeException cannotRealizeException) {
            System.err.println("Could not realize media player");
        } // end catch
        catch (IOException iOException) {
            System.err.println("Error reading from the source");
        } // end catch
    } // end MediaPanel constructor

    private void setLayout(BorderLayout borderLayout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
} // end class MediaPanel
