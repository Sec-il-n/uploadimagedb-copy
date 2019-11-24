package model.logic;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServlet;

public class FileCompressionLogic extends HttpServlet {

	public Path compress(String tmpPath,String suffix) throws IOException {
		//tmp読み込み
		GetPathLogic gplogic=new GetPathLogic();
		Path path=gplogic.getTmpDir();
		File input = new File(tmpPath);
		BufferedImage image = ImageIO.read(input);
		
		//圧縮されたtmpの出力先作成
		CreateFileLogic clogic=new CreateFileLogic();
		Path createdFile=clogic.createFile(path, null);
		File compressedImageFile = new File(createdFile.toString());
		OutputStream os = new FileOutputStream(compressedImageFile);

		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(suffix);
		ImageWriter writer = (ImageWriter) writers.next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);
		
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(0.5f);  
		writer.write(null, new IIOImage(image, null, null), param);
		
		os.close();
		ios.close();
		writer.dispose();

		return createdFile;
	}

	static int IMG_WIDTH=200;
	static int IMG_HEIGHT=300;
	
	public void getBufferedImage(Path tmpPath, String suffix) throws IOException {
	
		GetPathLogic gplogic=new GetPathLogic();
		Path path=gplogic.getTmpDir();
		File input = new File(tmpPath.toString());
		BufferedImage image = ImageIO.read(input);

		CreateFileLogic clogic=new CreateFileLogic();
		Path createdFile=clogic.createFile(path, null);

		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();

		BufferedImage resizeImageHintJpg = resizeImageWithHint(image, type);
		ImageIO.write(resizeImageHintJpg, suffix, new File(createdFile.toString()));
	}

	public static BufferedImage resizeImageWithHint(BufferedImage image, int type){
		
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		int[] i=getraito(image);
		g.drawImage(image, i[0], i[1], i[2], i[3],null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
	public static int[] getraito(BufferedImage image) {
		
		int[] i=new int[4];
		int w= image.getWidth();
		int h = image.getHeight();
		int x1 = (IMG_WIDTH-w)/2;
	    	int x2 = (IMG_HEIGHT-h)/2;
		
		if(w > h) {
			h = IMG_WIDTH;
			w = w * (IMG_HEIGHT/h);
			
			if(IMG_WIDTH > w) {
				h= h * (IMG_WIDTH / w);
				w = IMG_WIDTH;
			}
		
		} else {
			w = IMG_WIDTH;
			h = IMG_HEIGHT * (IMG_WIDTH/w);
			
			if(IMG_HEIGHT > h) {
				w = w * (IMG_HEIGHT/h);
				h = IMG_HEIGHT;
			}
		}

		i[0]=x1;
		i[1]=x2;
		i[2]=w;
		i[3]=h;
		
		return i;

	}
}
