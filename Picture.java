import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
	/** Method to keep only the blue pixels */
	public void keepOnlyBlue(){
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels){
			for (Pixel pixelObj : rowArray){
				pixelObj.setGreen(0);
				pixelObj.setRed(0);
			}
		}
	}
  /** Sets the color value of each color to 255-current val */
	public void negate(){
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels){
			for (Pixel pixelObj : rowArray){
				pixelObj.setGreen(255-pixelObj.getGreen());
				pixelObj.setRed(255-pixelObj.getRed());
				pixelObj.setBlue(255-pixelObj.getBlue());
			}
		}
	}
	/**
	 *	Turns all pixels to gray by averaging the red, blue, and green values
	 */
	public void grayscale(){
		Pixel[][] pixels = this.getPixels2D();
		for(Pixel[] rowArray : pixels){
			for(Pixel pixelObj : rowArray){
				int avg = (pixelObj.getBlue()+pixelObj.getRed()+pixelObj.getGreen())/3;
				pixelObj.setGreen(avg);
				pixelObj.setRed(avg);
				pixelObj.setBlue(avg);
			}
		}
	}
	/** To pixelate by dividing area into size x size
	 * @param size	Side length of sqaure area to pixelate.
	 */
	public void pixelate(int size){
		Pixel[][] pixels = this.getPixels2D();
		int width = this.getWidth();
		int height = this.getHeight();
		Pixel[][] pixelated = new Pixel[height/size+1][width/size+1];
		int avgBlue = 0;
		int avgRed = 0;
		int avgGreen = 0;
		int locationx = 0;
		int locationy = 0;
		int counter = 0;
		int counter1 = 0;
		for(int a = size/2;a<width;a+=size){
			for(int i = size/2;i<height;i+=size){
				if(i==height-1){
					for(int b = i-size/2;b<height;b++){
						if(b<0)
							b = 0;
						if(a==width-1){
							for(int c = a-size/2;c<a;c++){
								if(c<0)
									c = 0;
								avgBlue += pixels[b][c].getBlue();
								avgRed += pixels[b][c].getRed();
								avgGreen += pixels[b][c].getGreen();
								counter++;
							}
						}
						else{
							for(int c = a-size/2;c<a+size/2;c++){
								if(c<0)
									c = 0;
								avgBlue += pixels[b][c].getBlue();
								avgRed += pixels[b][c].getRed();
								avgGreen += pixels[b][c].getGreen();
								counter++;
							}
						}
					}
					avgBlue = avgBlue/counter;
					avgRed = avgRed/counter;
					avgGreen = avgGreen/counter;
					pixelated[locationy][locationx].setBlue(avgBlue);
					pixelated[locationy][locationx].setGreen(avgGreen);
					pixelated[locationy][locationx].setRed(avgRed);
					locationy = 0;
					locationx += 1;
				}
				else{
					for(int b = i-size/2;b<=i+size/2;b++){
						for(int c = a-size/2;c<=a+size/2;c++){
							avgBlue += pixels[b][c].getBlue();
							avgRed += pixels[b][c].getRed();
							avgGreen += pixels[b][c].getGreen();
							counter++;
						}
					}
					avgBlue = avgBlue/counter;
					avgRed = avgRed/counter;
					avgGreen = avgGreen/counter;
					pixelated[locationy][locationx].setBlue(avgBlue);
					pixelated[locationy][locationx].setGreen(avgGreen);
					pixelated[locationy][locationx].setRed(avgRed);
					locationy++;
					locationx++;
				}
			}
		}
		locationx = 0;
		locationy = 0;
		counter = 0;
		for(Pixel[] rowarr : pixels){
			for(Pixel pixelObj: rowarr){
				pixelObj.setGreen(pixelated[locationy][locationx].getGreen);
				pixelObj.setRed(pixelated[locationy][locationx].getRed);
				pixelObj.setBlue(pixelated[locationy][locationx].getBlue);
				counter++;
				if(counter==size){
					locationx++;
					counter = 0;
				}
			}
			counter1++;
			if(counter1==size){
				counter1 = 0;
				locationy++;
			}
			locationx = 0; 
		}
	}
				  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
