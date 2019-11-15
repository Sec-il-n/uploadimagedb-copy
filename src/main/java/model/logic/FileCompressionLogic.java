package model.logic;

import java.awt.AlphaComposite;
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
//	public void compress(String tmpPath,String filename) throws IOException {
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

//	    String suffix=new GetSuffixLogic().getSuffix(filename);
	    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(suffix);
	    ImageWriter writer = (ImageWriter) writers.next();

	    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	    writer.setOutput(ios);

	    ImageWriteParam param = writer.getDefaultWriteParam();

	    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    param.setCompressionQuality(0.5f);  // Change the quality value you prefer
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
//	    File compressedImageFile = new File(createdFile.toString());

		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
//		BufferedImage resizeImageJpg = resizeImage(originalImage, type);
//		ImageIO.write(resizeImageJpg, suffix, new File(createdFile));
		BufferedImage resizeImageHintJpg = resizeImageWithHint(image, type);
        ImageIO.write(resizeImageHintJpg, suffix, new File(createdFile.toString()));
	}
//	public static BufferedImage resizeImage(BufferedImage originalImage,int type) {
//		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
//	    Graphics2D g = resizedImage.createGraphics();
//	    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
//	    g.dispose();
//
//	    return resizedImage;
//	}
	public static BufferedImage resizeImageWithHint(BufferedImage image, int type){
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(image, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);//画像の左上が引数に指定した(x, y)(0, 0)の位置になるように表示
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
}
