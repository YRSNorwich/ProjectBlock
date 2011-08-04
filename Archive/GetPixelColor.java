import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GetPixelColor
{
    public static void main(String args[]) throws IOException {
        File file = new File("uk_1km.png");
        
        BufferedImage image = ImageIO.read(file);
        
        System.out.println("Width = " + image.getWidth());
        System.out.println("Height = " + image.getHeight());
        
        int[][] mapArray = new int[image.getWidth()][image.getHeight()];
        
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        
        for (int y = 0; y < imageHeight; y++)
        {
            for (int x = 0; x < imageWidth; x++)
            {
                int clr =  image.getRGB(x, y);
                
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                
                if (red == 255 && green == 255 && blue == 255) {
                    //white water?
                    mapArray[x][y] = 0;                    
                } else {
                    //Black earth?                    
                    mapArray[x][y] = 1;
                }                
            }
        }
        
        
        try {
            
            FileWriter fstream = new FileWriter("out.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            
            for (int y = 0; y < imageHeight; y++)
            {
                String line = "";
            
                for (int x = 0; x < imageWidth; x++)
                {
                    line += mapArray[x][y] + ",";
                }
                
                line = new StringBuffer(line).reverse().toString();
                
                //System.out.println(line);
                
                out.write(line + "\n");
            }
            
            out.close();
            
        } catch (Exception e) {
            
            System.err.println("Error: " + e.getMessage());
            
        }
        
        
        
        
    }
}