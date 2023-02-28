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
		Color avgColor;
		for(int loc = 0;loc<pixels.length;loc+=size){
			for(int colloc = 0;colloc<pixels[0].length;colloc+=size){
				for(int row = loc;row<loc+size;row++){
					for(int col = colloc;col<colloc+size;col++){
						if(row<height&&col<width){
							avgBlue += pixels[row][col].getBlue();
							avgRed += pixels[row][col].getRed();
							avgGreen += pixels[row][col].getGreen();
							counter++;
						}
					}
				}
				avgBlue = avgBlue/counter;
				avgRed = avgRed/counter;
				avgGreen = avgGreen/counter;
				avgColor = new Color(avgRed, avgGreen, avgBlue);
				for(int row = loc;row<loc+size;row++){
					for(int col = colloc;col<colloc+size;col++){
						if(row<height&&col<width)
							pixels[row][col].setColor(avgColor);
					}
				}
				avgBlue = 0;
				avgGreen = 0;
				avgRed = 0;
				counter = 0;
			}
		}
	}
	
	/**
	 * Determines the average color of a group of pixels from a given 2D array
	 * @param	pixels	The input array of pixels
	 * @param	row		The center row of the group
	 * @param	col		The center column of the group
	 * @param	factor	Determines how large the group is
	 * @return	The average Color of the group
	 */
	public Color averageColor(Pixel[][] pixels, int row, int col, int factor){
		int sumRed = 0;
		int sumBlue = 0;
		int sumGreen = 0;
		int count = 0;
		for(int r = row-factor;r<row+factor;r++){
			for(int c = col-factor;c<col+factor;c++){
				if(r<pixels.length&&c<pixels[0].length&&r>=0&&c>=0){
					sumGreen += pixels[r][c].getGreen();
					sumBlue += pixels[r][c].getBlue();
					sumRed += pixels[r][c].getRed();
					count++;
				}
			}
		}
		return new Color(sumRed/count, sumGreen/count, sumBlue/count);
	}
	
	/**
	 * Blurs the picture to a magnitude specified by the user
	 * @param	size	Determines the magnitude of the blurring
	 * @return			The blurred picture
	 */
	public Picture blur(int size){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] respix = result.getPixels2D();
		int locationx = 0;
		int locationy = 0;
		for(Pixel[] rowArray : pixels){
			for(Pixel pixelObj : rowArray){
				pixelObj.setColor(averageColor(pixels, locationx, locationy, size));
				locationy++;
			}
			locationx++;
			locationy = 0;
		}
		for(int i = 0;i<pixels.length;i++){
			for(int a = 0;a<pixels[0].length;a++){
				respix[i][a].setColor(pixels[i][a].getColor());
			}
		}
		return result;
	}
	/** Method that enhances a picture by getting average Color around
	 *  a pixel then applies the following formula:
	 *
	 * pixelColor <- 2 * currentValue - averageValue
	 *
	 * size is the area to sample for blur.
	 *
	 * @param size Larger means more area to average around pixel
	 * and longer compute time.
	 * @return enhanced picture
	*/
	public Picture enhance(int size){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		Color cl;
		int locx = 0;
		int locy = 0;
		for(Pixel[] rowArray : pixels){
			for(Pixel pixelObj : rowArray){
				cl = averageColor(pixels, locx, locy, size);
				pixelObj.setBlue(2*pixelObj.getBlue()-cl.getBlue());
				pixelObj.setRed(2*pixelObj.getRed()-cl.getRed());
				pixelObj.setGreen(2*pixelObj.getGreen()-cl.getGreen());
				locy++;
			}
			locx++;
			locy = 0;
		}
		for(int i = 0;i<pixels.length;i++){
			for(int a = 0;a<pixels[i].length;a++){
				resultPixels[i][a].setColor(pixels[i][a].getColor());
			}
		}
		pixels = this.getPixels2D();
		return result;
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
  
  /**
   * Swaps the left and right sides of the picture
   * @return	The swapped picture
   */
  public Picture swapLeftRight(){
	  Pixel[][] pixels = this.getPixels2D();
	  Picture result = new Picture(pixels.length, pixels[0].length);
	  Pixel[][] respix = result.getPixels2D();
	  int width = pixels[0].length;
	  for(int r = 0;r<pixels.length;r++){
		  for(int c = 0;c<pixels[0].length;c++){
			  int newcol = (c+width/2)%width;
			  respix[r][newcol].setColor(pixels[r][c].getColor());
		  }
	  }
	  return result;
  }
  
   /** Splits the picture into groups of a value specified by the input, and increments them differently based on their location, creating a stair effect
	* @param shiftCount The number of pixels to shift to the right
	* @param steps The number of steps
	* @return The picture with pixels shifted in stair steps
    */
	public Picture stairStep(int shiftCount, int steps){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] respix = result.getPixels2D();
		int width = pixels[0].length;
		int newcol;
		int rowpos = 0;
		double length = (double)pixels.length/(double)steps;
		int runRows = 0;
		for(int a = 0;a<steps;a++){
			for(int r = rowpos;r<pixels.length;r++){
				for(int c = 0;c<width;c++){
					
						newcol = c+(int)(a*shiftCount);
						while(newcol>=width)
							newcol = newcol-width;
						respix[r][newcol].setColor(pixels[r][c].getColor());
					
				}
			}
			rowpos += pixels.length/steps;//program does not work for step values that are not divisble by pixels.length as 
			//rowpos +='s a double that is not an integer, and rounds down causing breakage
		}
		return result;
	}
	
	/** Distorts the horizontal center of the picture by shifting pixels horizontally
	 * @param maxFactor Max height (shift) of curve in pixels
	 * @return Liquified picture
	 */
	public Picture liquify(int maxHeight){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] respix = result.getPixels2D();
		int bellWidth = 100;
		int width = pixels[0].length;
		int rightShift;
		int newCol;
		double exponent;
		 for(int r = 0;r<pixels.length;r++){
		  for(int c = 0;c<pixels[0].length;c++){
			 exponent = Math.pow(r - pixels.length / 2.0, 2) / (2.0 * Math.pow(bellWidth, 2));
			 rightShift = (int)(maxHeight * Math.exp(- exponent));
			 newCol = c+rightShift;
			 if(newCol>=width)
				newCol = newCol-width;
			//System.out.println(newCol);
			//System.out.println(width+"W");
			 respix[r][newCol].setColor(pixels[r][c].getColor());
		  }
	  }
	  return result;
	}
	
	/**
	 * Creates oscillating distortions in a picture
	 * @param amplitude		The maximum shift of the pixels
	 * @return				Wavy picture
	 */
	public Picture wavy(int amplitude){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] respix = result.getPixels2D();
		int width = pixels[0].length;
		double frequency = 0.002;
		int phaseShift = 10;
		for(int r = 0;r<pixels.length;r++){
			for(int c = 0;c<pixels[0].length;c++){
				int pixelShift = (int)(amplitude * Math.sin((2*Math.PI*frequency*r+phaseShift)));
				//System.out.println(pixelShift);
				//System.out.println("ZZ"+Math.sin(60)+" "+r);
				int newCol = c+pixelShift;
				if(newCol>=width)
					newCol = newCol-width;
				if(newCol<0)
					newCol = width+newCol;
				respix[r][newCol].setColor(pixels[r][c].getColor());
			}
		}
		return result;
	}
	
	/**
	 * Uses the pixel below to calculate the color distance and thus determine where edges are
	 * @param threshhold	Determines what color distance is considered an edge
	 * @return	A black and white picture showing edges
	 */
	public Picture edgeDetectionBelow(int threshhold){
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] respix = result.getPixels2D(); 
		Pixel topPix = null;
		Pixel bottomPix = null;
		Color bottomColor = null;
		for(int r = 0;r<pixels.length-1;r++){
			for(int c = 0;c<pixels[0].length;c++){
				topPix = pixels[r][c];
				bottomPix = pixels[r+1][c];
				bottomColor = bottomPix.getColor();
				if(topPix.colorDistance(bottomColor)>threshhold)
				  respix[r][c].setColor(Color.BLACK);
				else
				  respix[r][c].setColor(Color.WHITE);
			}
		}
		return result;
	}
	
	/**
	 * Returns a picture of a cat and dog superimposed on a background
	 * @return	A picture of a cat and dog superimposed on a background
	 */
	public Picture greenScreen(){
		SimplePicture cat0 = new SimplePicture("kitten1GreenScreen.jpg");
		SimplePicture dog0 = new SimplePicture("puppy1GreenScreen.jpg");
		Picture cat = cat0.scale(0.4, 0.4);
		Picture dog = dog0.scale(0.2, 0.2);
		Picture background = new Picture("IndoorHouseLibraryBackground.jpg");
		Pixel[][] backgroundpixels = background.getPixels2D();
		Picture result = new Picture(backgroundpixels.length, backgroundpixels[0].length);
		Pixel[][] catPixels = cat.getPixels2D();
		Pixel[][] dogPixels = dog.getPixels2D();
		Pixel[][] respix = result.getPixels2D();
		int dogrow = 0;
		int dogcol = 0;
		int catrow = 0;
		int catcol = 0;
		for(Pixel[] rowArray: catPixels){
			for(Pixel pixelObj : rowArray){
				if(pixelObj.getRed()==51&&pixelObj.getGreen()==204&&pixelObj.getBlue()==51){
					pixelObj.setAlpha(255);
				}
			}
		}
		for(Pixel[] rowArray: dogPixels){
			for(Pixel pixelObj : rowArray){
				if(pixelObj.getRed()==51&&pixelObj.getGreen()==204&&pixelObj.getBlue()==51){
					pixelObj.setAlpha(255);
				}
			}
		}
		for(int row = 0;row<backgroundpixels.length;row++){
			for(int col = 0;col<backgroundpixels[0].length;col++){
				if(row>=332&&row<=391&&col>=320&&col<=399){
					if(isGreen(dogPixels[dogrow][dogcol])){
						respix[row][col].setColor(backgroundpixels[row][col].getColor());
					}
					else{
						respix[row][col].setColor(dogPixels[dogrow][dogcol].getColor());
					}
					if(dogcol<dog.getWidth()-1)
						dogcol++;
					else{
						dogrow++;
						dogcol = 0;
					}
				}
				else if(row>=428&&row<=547&&col>=550&&col<=669){
					if(isGreen(catPixels[catrow][catcol])){
						respix[row][col].setColor(backgroundpixels[row][col].getColor());
					}
					else{
						respix[row][col].setColor(catPixels[catrow][catcol].getColor());
					}
					if(catcol<cat.getWidth()-1){
						catcol++;
					}
					else{
						catrow++;
						catcol = 0;
					}
				}
				else{
					respix[row][col].setColor(backgroundpixels[row][col].getColor());
				}
			}
		}
		return result;
	}
	
	/**
	 * Checks to see if a selected pixel is green
	 * @param in	The input pixel
	 * @return	If the pixel is green
	 */
	public boolean isGreen(Pixel in){
		if(in.getGreen()-in.getBlue()>50&&in.getGreen()-in.getRed()>50)
			return true;
		else
			return false;
	}
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("redMotorcycle.jpg");
    beach.explore();
	beach = beach.stairStep(1, 400);
	beach.explore();
    beach = beach.greenScreen();
    beach.explore();
  }
}
