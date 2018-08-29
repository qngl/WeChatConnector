package com.vwfsag.fso.service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vwfsag.fso.config.GlobalSettings;
import com.vwfsag.fso.view.vo.ImageDescriptor;
import com.vwfsag.fso.view.vo.ImageDescriptor.ImageFormat;

/**
 * @author liqiang
 *
 */
@Service
public class ImageServiceImpl implements ImageService {

	private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

	private static final String RESOURCES_IMAGES_DIRECTORY = "resources.images.directory";
	private static final String RESOURCES_IMAGES_HOST = "resources.images.host";

	@Autowired
	private GlobalSettings settings;;

	@Override
	public String saveImage(ImageDescriptor image) {
		File directory = getImageRootDirectory();
		String imageHost = settings.get(RESOURCES_IMAGES_HOST);
		if(image.getName().contains(imageHost)) {
			String fileName = image.getName().replace(imageHost + "/", "");
			FileUtils.deleteQuietly(new File(directory, fileName));
		}
		StringBuilder filePath = new StringBuilder();
		filePath.append(Calendar.getInstance().get(Calendar.YEAR)).append("/");
		filePath.append(Calendar.getInstance().get(Calendar.MONTH)).append("/");
		filePath.append(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).append("/");
		filePath.append(String.format("%s.%s", image.getName(), image.getFormat().name()));
		String fileName = filePath.toString();
		File file = new File(directory, fileName);
		
		try {
			FileUtils.writeByteArrayToFile(file, image.getData());
		} catch (IOException e) {
			fileName = null;
			log.error("Trying to write file failed.", e);
		}
		return String.format("%s/%s", imageHost, fileName);
	}

	private File getImageRootDirectory() {
		String imageRootDirectory = settings.get(RESOURCES_IMAGES_DIRECTORY);
		StringBuilder path = new StringBuilder(imageRootDirectory);
		if(!imageRootDirectory.endsWith(File.separator)) {
			path.append(File.separator);
		}
		File directory = new File(path.toString());
		return directory;
	}

	@Override
	public ImageDescriptor getImage(String fileName) {
		File directory = getImageRootDirectory();
		File file = new File(directory, fileName);
		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			ImageDescriptor image = new ImageDescriptor(fileName, ImageFormat.PNG, data);
			return image;
		} catch (IOException e) {
			log.error("Trying to read file failed.", e);
		}
		return null;
	}

}
