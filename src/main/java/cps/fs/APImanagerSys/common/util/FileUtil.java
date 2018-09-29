package cps.fs.APImanagerSys.common.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 文件校验类
 * @author fs
 * @date 2018年9月11日
 * @description
 */
public class FileUtil {
	
	/**
	 * 图片文件转换成字节
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] convert(MultipartFile file) throws IOException {
        validateFile(file);
        return file.getBytes();
    }
	/**
	 * 验证是否是图片类型
	 * @param file
	 * @throws IOException
	 */
	private static void validateFile(MultipartFile file) throws IOException {
		String contentType = file.getContentType();
        if (!contentType.equals(MediaType.IMAGE_JPEG.toString()) && !contentType.equals(MediaType.IMAGE_PNG.toString())) {
            throw new IOException("Invalid media type");
        }
	}
	
	/**
	 * 转换成图片
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage convertToImage(MultipartFile file) throws IOException {
        InputStream in = new ByteArrayInputStream(file.getBytes());
        return ImageIO.read(in);
    }
	
	
	/**
	 * 图片转换成字节序列
	 * @param image
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	private static byte[] convertToByteArr(BufferedImage image, String contentType) throws IOException {
        byte[] imageInByte;

        String typeName = "jpg";
        if (contentType.equals(MediaType.IMAGE_PNG.toString())) {
            typeName = "png";
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, typeName, baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }
	
	private static byte[] convertToByteArr(MultipartFile file, String ext) throws IOException {
        byte[] imageInByte;
        InputStream in = new ByteArrayInputStream(file.getBytes());
        BufferedImage image = ImageIO.read(in);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, ext, baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

	

    public static byte[] compressPic(MultipartFile file, int width, int height, float per, String ext) throws IOException {
        InputStream in = new ByteArrayInputStream(file.getBytes());
        BufferedImage img = ImageIO.read(in);
        //得到源图宽
        int oldWidth = img.getWidth(null);
        int oldHeight = img.getHeight(null);
        int newWidht = 0;
        //得到源图长
        int newHeight = 0;

        double w2 = oldWidth / width;
        double h2 = oldHeight / height;

        //图片跟据长宽留白，成一个正方形图。
        BufferedImage oldpic;
        if (oldWidth > oldHeight) {
            oldpic = new BufferedImage(oldWidth, oldWidth, BufferedImage.TYPE_INT_RGB);
        } else {
            if (oldWidth < oldHeight) {
                oldpic = new BufferedImage(oldHeight, oldHeight, BufferedImage.TYPE_INT_RGB);
            } else {
                oldpic = new BufferedImage(oldWidth, oldHeight, BufferedImage.TYPE_INT_RGB);
            }
        }
        Graphics2D g = oldpic.createGraphics();
        g.setColor(Color.white);
        if (oldWidth > oldHeight) {
            g.fillRect(0, 0, oldWidth, oldWidth);
            g.drawImage(img, 0, (oldWidth - oldHeight) / 2, oldWidth, oldHeight, Color.white, null);
        } else {
            if (oldWidth < oldHeight) {
                g.fillRect(0, 0, oldHeight, oldHeight);
                g.drawImage(img, (oldHeight - oldWidth) / 2, 0, oldWidth, oldHeight, Color.white, null);
            } else {
                g.drawImage(img.getScaledInstance(oldWidth, oldHeight, Image.SCALE_SMOOTH), 0, 0, null);
            }
        }
        g.dispose();
        img = oldpic;
        //图片调整为方形结束
        if (oldWidth > width) {
            newWidht = (int) Math.round(oldWidth / w2);
        } else {
            newWidht = oldWidth;
        }
        if (oldHeight > height) {
            //计算新图长宽
            newHeight = (int) Math.round(oldHeight / h2);
        } else {
            newHeight = oldHeight;
        }
        BufferedImage tag = new BufferedImage(newWidht, newHeight, BufferedImage.TYPE_INT_RGB);
        //tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图
        tag.getGraphics().drawImage(img.getScaledInstance(newWidht, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(tag, ext, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }

    public static String saveFile(MultipartFile filedata, String saveFilePath, String newFileName) {
        // 保存相对路径到数据库 文件写入服务器
        if (filedata != null && !filedata.isEmpty()) {
            try {
                saveFile(newFileName, filedata, saveFilePath);
                return newFileName;
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        throw new RuntimeException("文件上传异常");
    }

    /**
     * 存储图片到本地
     *
     * @param newFileName 新文件名
     * @param filedata    需要存储的文件
     */
    private static void saveFile(String newFileName, MultipartFile filedata, String saveFilePath) {
        try {
            // 根据配置文件获取服务器图片存放路径
        /* 构建文件目录 */
            File fileDir = new File(saveFilePath);
            if (!fileDir.exists()) {
                if (!fileDir.mkdirs()) {
                    throw new RuntimeException("创建文件夹失败");
                }
            }
            FileOutputStream out = new FileOutputStream(saveFilePath + "/" + newFileName);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }

    }

    public static boolean delFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            Files.delete(path);
            return true;
        } catch (NoSuchFileException x) {
           // LOGGER.error(path + "no such file or directory", x);
        } catch (DirectoryNotEmptyException x) {
          //  LOGGER.error(path+"not empty",x);
            x.printStackTrace();
        } catch (IOException x) {
            // File permission problems are caught here.
           // LOGGER.error(path+"has less permission or is using",x);
        }
        return false;
    }

    public static MultipartBody.Part fileToMultipartBodyPart(String fileParams, MultipartFile file) throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(file.getContentType()), file.getBytes());
        builder.addFormDataPart(fileParams, file.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody.Part part = MultipartBody.Part.createFormData(file.getName(), file.getOriginalFilename(), requestBody);
        return part;
    }
}
