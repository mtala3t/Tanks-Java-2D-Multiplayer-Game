import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/*
 * SoundManger.java
 *
 * Created on 12 ÃÈÑíá, 2008, 04:34 ã
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class SoundManger {
    
    AudioFormat audioFormat;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    String X="start.wav";
        
    public SoundManger()
    {
        new PlayThread().start();
    }
    class PlayThread extends Thread{
        byte tempBuffer[] = new byte[10000];
        
        public void run(){
            File soundFile =new File(X);
            while(true) {
                try {
                    audioInputStream = AudioSystem.
                            getAudioInputStream(soundFile);
                    audioFormat = audioInputStream.getFormat();
                    
                    
                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
                    
                    sourceDataLine =(SourceDataLine)AudioSystem.getLine(dataLineInfo);
                    sourceDataLine.open(audioFormat);
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                sourceDataLine.start();
                try{
                    int cnt;
                    while((cnt = audioInputStream.read(
                            tempBuffer,0,tempBuffer.length)) != -1) {
                        if(cnt > 0){
                            sourceDataLine.write(tempBuffer, 0, cnt);
                        }
                    }
                    sourceDataLine.flush();
                    sourceDataLine.close();
                    Thread.sleep(2000);
                }catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }
        
    }
}
