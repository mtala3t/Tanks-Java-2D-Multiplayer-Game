import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
/*
 * Tank.java
 *
 * Created on 24 „«—”, 2008, 11:36 ’
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class Tank {
    
    private Image[] tankImg;
    private BufferedImage ImageBuff;
    private Bomb bomb[]=new Bomb[1000];
    private int curBomb=0;
    private int tankID;
    private int posiX=-1,posiY=-1;
    private int direction=1;
    private float velocityX=0.03125f,velocityY=0.03125f;
    private int width=559,height=473;

    public int getDirection() 
    {
        return direction;
    }
    
    /** Creates a new instance of Tank */
    public Tank() 
    {  
        while(posiX<70|posiY<50|posiY>height-43|posiX>width-43)
        {
            posiX=(int)(Math.random()*width);
            posiY=(int)(Math.random()*height);
        }
        loadImage(4);
        
    }
    public Tank(int x,int y,int dir,int id)
    {
        posiX=x;
        posiY=y;
        tankID=id;
        direction=dir;
        loadImage(0);
    }
    public void loadImage(int a)
    {
        tankImg=new Image[4];
        for(int i=a;i<tankImg.length+a;i++)
        {
            tankImg[i-a]=new ImageIcon("Images/"+i+".png").getImage();
        }
        
        ImageBuff=new BufferedImage(tankImg[direction-1].getWidth(null),tankImg[direction-1].getHeight(null),BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(tankImg[direction-1],0,0,null);
    }
    public BufferedImage getBuffImage()
    {
        return ImageBuff;
    }
    
    public int getXposition()
    {
        return posiX;
    }
    public int getYposition()
    {
        return posiY;
    }
    public void setXpoistion(int x)
    {
        posiX=x;
    }
    public void setYposition(int y)
    {
        posiY=y;
    }
    public void moveLeft()
    {
        if(direction==1|direction==3)
        {
           ImageBuff=new BufferedImage(tankImg[3].getWidth(null),tankImg[3].getHeight(null),BufferedImage.TYPE_INT_RGB);
           ImageBuff.createGraphics().drawImage(tankImg[3],0,0,null);
           direction=4;
        }
        else
        {
            
            int temp;
            
            temp=(int)(posiX-velocityX*posiX);
            if(checkCollision(temp,posiY)==false&&temp<70)
            {
                posiX=70;
            }
            else if(checkCollision(temp,posiY)==false)
            {
                posiX=temp;
            }
            
        }
        
    }
    public void moveRight()
    {
        if(direction==1|direction==3)
        {
           ImageBuff=new BufferedImage(tankImg[1].getWidth(null),tankImg[1].getHeight(null),BufferedImage.TYPE_INT_RGB);
           ImageBuff.createGraphics().drawImage(tankImg[1],0,0,null);
           direction=2;
        }
        else
        {       
            int temp;
            temp=(int)(posiX+velocityX*posiX);
            if(checkCollision(temp,posiY)==false&&temp>width-43+20)
            {
            
                posiX=width-43+20;
            }
            else if(checkCollision(temp,posiY)==false)
            {
                posiX=temp;
            }
        }
        
    }


    public void moveForward()
    {
        if(direction==2|direction==4)
        {
           ImageBuff=new BufferedImage(tankImg[0].getWidth(null),tankImg[0].getHeight(null),BufferedImage.TYPE_INT_RGB);
           ImageBuff.createGraphics().drawImage(tankImg[0],0,0,null);
           direction=1;
        }
        else
        {
            
                int temp;
                temp=(int)(posiY-velocityY*posiY);
                if(checkCollision(posiX,temp)==false&&temp<50) 
                {
                    posiY=50;
                }
                else if(checkCollision(posiX,temp)==false)
                {
                    posiY=temp;
                }
           
        }
    }
    public void moveBackward()
    {
        if(direction==2|direction==4)
        {
           ImageBuff=new BufferedImage(tankImg[2].getWidth(null),tankImg[2].getHeight(null),BufferedImage.TYPE_INT_RGB);
           ImageBuff.createGraphics().drawImage(tankImg[2],0,0,null);
           direction=3;
        }
        else
        {
            
            int temp;
            temp=(int)(posiY+velocityY*posiY);   
            if(checkCollision(posiX,temp)==false&&temp>height-43+50)
            {
              posiY=height-43+50;
            }
            else if(checkCollision(posiX,temp)==false)
            {
                posiY=temp;
            } 
        }
    }
    
    public void shot()
    {
        bomb[curBomb]=new Bomb(this.getXposition(),this.getYposition(),direction);
        
        bomb[curBomb].startBombThread(true);
        curBomb++;
    }
    public Bomb[] getBomb()
    {
        return bomb;
    }
    public void setTankID(int id)
    {
        tankID=id;
    }
    public int getTankID()
    {
        return tankID;
    }
    public void setDirection(int dir)
    {
        ImageBuff=new BufferedImage(tankImg[dir-1].getWidth(null),tankImg[dir-1].getHeight(null),BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(tankImg[dir-1],0,0,null);
        direction=dir;
    }

    public void Shot() 
    {
        bomb[curBomb]=new Bomb(this.getXposition(),this.getYposition(),direction);
        
        bomb[curBomb].startBombThread(false);
        curBomb++;
    
    }
    public boolean checkCollision(int xP,int yP)
    {
        ArrayList<Tank>clientTanks=GameBoardPanel.getClients();
        int x,y;
        for(int i=1;i<clientTanks.size();i++) {
            if(clientTanks.get(i)!=null) 
            {
                x=clientTanks.get(i).getXposition();
                y=clientTanks.get(i).getYposition();
                if(direction==1)
                {       
                    if(((yP<=y+43)&&yP>=y)&&((xP<=x+43&&xP>=x)||(xP+43>=x&&xP+43<=x+43))) 
                    { 
                        return true;
                    }
                }
                else if(direction==2)
                {
                    if(((xP+43>=x)&&xP+43<=x+43)&&((yP<=y+43&yP>=y)||(yP+43>=y&&yP+43<=y+43))) 
                    { 
                        return true;
                    }
                }
                else if(direction==3)
                {
                    if(((yP+43>=y)&&yP+43<=y+43)&&((xP<=x+43&&xP>=x)||(xP+43>=x&&xP+43<=x+43))) 
                    { 
                        return true;
                    }
                }
                else if(direction==4)
                {
                    if(((xP<=x+43)&&xP>=x)&&((yP<=y+43&&yP>=y)||(yP+43>=y&&yP+43<=y+43))) 
                    { 
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
