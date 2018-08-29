package com.vwfsag.fso.view.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vwfsag.fso.service.ImageService;
import com.vwfsag.fso.view.vo.ImageDescriptor;

/**
 * @author liqiang
 *
 */
@Controller
public class ImageController {

	@Autowired
	private ImageService imageService;

	@RequestMapping(value = "/images/{year}/{month}/{day}/{file}.{format}", method = RequestMethod.GET)
	public void index(@PathVariable String year, @PathVariable String month, @PathVariable String day,
			@PathVariable String file, @PathVariable String format, HttpServletResponse res) {
		StringBuilder filePath = new StringBuilder();
		filePath.append(year.replace("../", "")).append("/");
		filePath.append(month.replace("../", "")).append("/");
		filePath.append(day.replace("../", "")).append("/");
		filePath.append(file.replace("../", "")).append(".").append(format);
		ImageDescriptor image = imageService.getImage(filePath.toString());
		res.setContentType("image/" + format.toLowerCase());
		try {
			IOUtils.write(image.getData(), res.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
