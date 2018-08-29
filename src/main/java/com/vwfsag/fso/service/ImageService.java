package com.vwfsag.fso.service;

import com.vwfsag.fso.view.vo.ImageDescriptor;

/**
 * @author liqiang
 *
 */
public interface ImageService {

	public String saveImage(ImageDescriptor image);
	
	public ImageDescriptor getImage(String fileName);
	
}
