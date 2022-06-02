
package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class FundoDeTela extends javax.swing.JDesktopPane{
 
    private Image imagem;

    public FundoDeTela(String imagem) {
        
        this.imagem= new ImageIcon(imagem).getImage();
        
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        
        g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
        
        
    }
    
    
    
    
    
    
}
