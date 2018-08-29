package com.vwfsag.fso.view.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vwfsag.fso.config.GlobalSettings;
import com.vwfsag.fso.domain.ActivityPage;
import com.vwfsag.fso.domain.ActivityPageType;
import com.vwfsag.fso.service.ActivityPageService;
import com.vwfsag.fso.service.ImageService;
import com.vwfsag.fso.view.vo.ImageDescriptor;
import com.vwfsag.fso.view.vo.ImageDescriptor.ImageFormat;

/**
 * @author liqiang
 * 
 */
@Controller
@RequestMapping(value = "/page")
public class ActivityController {

	private static final Logger log = LoggerFactory.getLogger(ActivityController.class);

	@Autowired
	private ActivityPageService activityPageService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private GlobalSettings settings;;

	@RequestMapping(value = "/activity", method = RequestMethod.GET)
	public String activities(Model model) {
		return "activities";
	}

	@RequestMapping(value = "/activity/edit/{id}", method = RequestMethod.GET)
	public String activityEdit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("id", id);
		return "activity-edit";
	}

	@RequestMapping(value = "/activity/list", method = RequestMethod.GET)
	public @ResponseBody List<ActivityPage> activityList() {
		List<ActivityPage> pages = activityPageService.getPages(null);
		return pages;
	}

	@RequestMapping(value = "/activity/{id}", method = RequestMethod.GET)
	public @ResponseBody ActivityPage activity(@PathVariable("id") Integer id) {
		ActivityPage page = activityPageService.getPage(id);
		if (page == null) {
			page = new ActivityPage();
			page.setLayoutType(ActivityPageType.IMAGE.getCode());
		}
		return page;
	}

	@RequestMapping(value = "/activity/{id}", method = RequestMethod.POST)
	public String activity(@PathVariable("id") Integer id, @RequestParam("type") int type,
			@RequestParam("productId") Integer productId, @RequestParam("title") String title,
			@RequestParam("sourceURL") String[] sourceURLs, @RequestParam("image") MultipartFile[] images,
			Model model) {
		try {
			List<String> imageURLs = new ArrayList<String>();
			for (int i = 0; i < images.length; i++) {
				MultipartFile image = images[i];
				String sourceURL = sourceURLs[i];
				if (!image.isEmpty()) {
					ImageFormat fmt = ImageFormat.fromFileName(image.getName());
					ImageDescriptor imageDesc = new ImageDescriptor(fmt, image.getBytes());
					sourceURL = imageService.saveImage(imageDesc);
				}
				imageURLs.add(sourceURL);
			}
			ActivityPage page = null;
			if (id > 0) {
				page = activityPageService.getPage(id);
			} else {
				page = new ActivityPage();
			}
			page.setLayoutType(ActivityPageType.fromCode(type).getCode());
			if (productId != null && productId > 0) {
				String targetURL = settings.get("target.product") + productId;
				page.setProductId(productId);
				page.setTargetURL(targetURL);
			}
			page.setDisplayTitle(title);
			page.setDisplayImgs(StringUtils.join(imageURLs, ","));
			page.setCreateTime(System.currentTimeMillis());
			activityPageService.save(page);
		} catch (IOException e) {
			log.error("Trying to read the uploaded image data failed.", e);
		}
		return "redirect:.";
	}
}
