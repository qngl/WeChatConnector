package com.vwfsag.fso.view.vo;

import java.io.Serializable;
import java.net.URL;
import java.util.UUID;

/**
 * @author liqiang
 * 
 */
public class ImageDescriptor implements Serializable {

	private static final long serialVersionUID = 20160412L;

	public static enum ImageFormat {
		PNG, JPG;
		
		public static ImageFormat fromFileName(String fileName) {
			if(fileName.toUpperCase().endsWith(PNG.name())) {
				return PNG;
			}
			if(fileName.toUpperCase().endsWith(JPG.name())) {
				return JPG;
			}
			return PNG;
		}
	}

	private String name;
	private ImageFormat format;
	private URL url;
	private byte[] data;

	public ImageDescriptor(ImageFormat format, byte[] data) {
		super();
		this.name = UUID.randomUUID().toString();
		this.format = format;
		this.data = data;
	}

	public ImageDescriptor(String name, ImageFormat format, byte[] data) {
		super();
		this.name = name;
		this.format = format;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public ImageFormat getFormat() {
		return format;
	}

	public void setFormat(ImageFormat format) {
		this.format = format;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
}
