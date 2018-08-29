package com.vwfsag.fso.view.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import com.vwfsag.fso.domain.IndexLayout;
import com.vwfsag.fso.domain.IndexLayoutType;
import com.vwfsag.fso.domain.Product;
import com.vwfsag.fso.domain.Status;
import com.vwfsag.fso.service.ActivityPageService;
import com.vwfsag.fso.service.ImageService;
import com.vwfsag.fso.service.IndexLayoutService;
import com.vwfsag.fso.service.ProductService;
import com.vwfsag.fso.view.vo.ImageDescriptor;
import com.vwfsag.fso.view.vo.ResponseStatus;
import com.vwfsag.fso.view.vo.ImageDescriptor.ImageFormat;

/**
 * @author liqiang
 * 
 */
@Controller
@RequestMapping(value = "/page")
public class IndexLayoutController {

	private static final Logger log = LoggerFactory.getLogger(IndexLayoutController.class);

	@Autowired
	private IndexLayoutService indexLayoutService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ActivityPageService activityPageService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private GlobalSettings settings;;

	@RequestMapping(value = "/logo", method = RequestMethod.GET)
	public String logo(Model model) {
		IndexLayout logo = null;
		List<IndexLayout> logos = indexLayoutService.getLayouts(IndexLayoutType.LOGO);
		if (logos.isEmpty()) {
			logo = new IndexLayout();
		} else {
			logo = logos.get(0);
		}
		model.addAttribute("logo", logo);
		return "logo";
	}

	@RequestMapping(value = "/logo", method = RequestMethod.POST)
	public String logoSave(@RequestParam("image") MultipartFile image, Model model) {
		if (!image.isEmpty()) {
			try {
				ImageDescriptor imageDesc = new ImageDescriptor(ImageFormat.PNG, image.getBytes());
				String imageURL = imageService.saveImage(imageDesc);
				IndexLayout logo = null;
				List<IndexLayout> logos = indexLayoutService.getLayouts(IndexLayoutType.LOGO);
				if (logos.isEmpty()) {
					logo = new IndexLayout();
					logo.setLayoutType(IndexLayoutType.LOGO.getCode());
					logo.setTargetURL("#");
					logo.setLayoutSort(0);
					logo.setDisplayTitle("LOGO");
					logo.setDisplaySubtitle("LOGO");
					logo.setDisplayDetails("LOGO");
					logo.setStatus(0);
				} else {
					logo = logos.get(0);
				}
				logo.setSourceURL(imageURL);
				logo.setOnlineTime(System.currentTimeMillis());
				indexLayoutService.save(logo);
			} catch (IOException e) {
				log.error("Trying to read the uploaded image data failed.", e);
			}
		}
		return "redirect:logo";
	}

	@RequestMapping(value = "/banners", method = RequestMethod.GET)
	public String banners(Model model) {
		IndexLayoutType type = IndexLayoutType.BANNER;
		model.addAttribute("layoutType", type);
		return "banners";
	}

	@RequestMapping(value = "/entrances", method = RequestMethod.GET)
	public String activities(Model model) {
		IndexLayoutType type = IndexLayoutType.ENTRANCES;
		model.addAttribute("layoutType", type);
		return "entrances";
	}

	@RequestMapping(value = "/layout/{layoutType}", method = RequestMethod.GET)
	public String layouts(@PathVariable("layoutType") int layoutType, Model model) {
		IndexLayoutType type = IndexLayoutType.fromCode(layoutType);
		model.addAttribute("layoutType", type);
		return "layouts";
	}

	@RequestMapping(value = "/layout/edit/{layoutType}/{id}", method = RequestMethod.GET)
	public String layoutEdit(@PathVariable("layoutType") int layoutType, @PathVariable("id") long id, Model model) {
		IndexLayout layout = null;
		IndexLayoutType type = null;
		String linkType = "P";
		if (id > 0) {
			layout = indexLayoutService.getLayout(id);
			type = IndexLayoutType.fromCode(layout.getLayoutType());
			if (layout.getActivityLayoutId() != null && layout.getActivityLayoutId() > 0) {
				linkType = "A";
			}
		} else {
			layout = new IndexLayout();
			layout.setId(id);
			type = IndexLayoutType.fromCode(layoutType);
			layout.setLayoutType(type.getCode());
		}
		model.addAttribute("layoutType", type);
		model.addAttribute("layout", layout);
		model.addAttribute("linkType", linkType);
		if(type == IndexLayoutType.BANNER) {
			return "banner-edit";
		}
		if(type == IndexLayoutType.ENTRANCES) {
			return "entrance-edit";
		}
		return "layout-edit";
	}

	@RequestMapping(value = "/layout/{layoutType}/{id}", method = RequestMethod.GET)
	public @ResponseBody IndexLayout layoutLoad(@PathVariable("layoutType") int layoutType, @PathVariable("id") long id, Model model) {
		IndexLayout layout = null;
		if (id > 0) {
			layout = indexLayoutService.getLayout(id);
			if(layout.getProductId() != null && layout.getProductId() > 0) {
				Product product = productService.getProduct(layout.getProductId());
				layout.setProduct(product);
			}
			if(layout.getActivityLayoutId() != null && layout.getActivityLayoutId() > 0) {
				ActivityPage activityPage = activityPageService.getPage(layout.getActivityLayoutId());
				layout.setActivityPage(activityPage);
			}
		} else {
			layout = new IndexLayout();
			layout.setId(id);
			IndexLayoutType type = IndexLayoutType.fromCode(layoutType);
			layout.setLayoutType(type.getCode());
		}
		return layout;
	}

	@RequestMapping(value = "/layout/list/{layoutType}", method = RequestMethod.GET)
	public @ResponseBody List<IndexLayout> layoutList(@PathVariable("layoutType") int layoutType) {
		List<IndexLayout> layouts = indexLayoutService.getLayouts(IndexLayoutType.fromCode(layoutType));
		for(IndexLayout layout : layouts) {
			if(layout.getProductId() != null && layout.getProductId() > 0) {
				Product product = productService.getProduct(layout.getProductId());
				layout.setProduct(product);
			}
			if(layout.getActivityLayoutId() != null && layout.getActivityLayoutId() > 0) {
				ActivityPage activityPage = activityPageService.getPage(layout.getActivityLayoutId());
				layout.setActivityPage(activityPage);
			}
		}
		return layouts;
	}

	@RequestMapping(value = "/layout/{layoutType}/{id}", method = RequestMethod.POST)
	public String layoutSave(@PathVariable("layoutType") int layoutType, @PathVariable("id") long id,
			@RequestParam(value = "displayTitle", required = false) String title,
			@RequestParam(value = "layoutSort", required = false) String sort,
			@RequestParam(value = "targetURL", required = false) String targetURL,
			@RequestParam(value = "guidePrice", required = false) String guidePrice,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "activityId", required = false) String activityId,
			@RequestParam(value = "earnest", required = false) String earnest,
			@RequestParam(value = "sourceURL", required = false) String[] sourceURLs,
			@RequestParam("image") MultipartFile[] images, Model model) {
		IndexLayoutType indexLayoutType = IndexLayoutType.fromCode(layoutType);
		try {
			List<String> imageURLs = new ArrayList<String>();
			int i = 0;
			for(MultipartFile image : images) {
				if(!image.isEmpty()) {
					ImageFormat fmt = ImageFormat.fromFileName(image.getName());
					ImageDescriptor imageDesc = new ImageDescriptor(fmt,
							image.getBytes());
					String imageURL = imageService.saveImage(imageDesc);
					imageURLs.add(imageURL);
				} else if(StringUtils.isNotBlank(sourceURLs[i])) {
					imageURLs.add(sourceURLs[i]);
				}
				i++;
			}

			IndexLayout layout = null;
			if (id == 0) {
				layout = new IndexLayout();
			} else {
				layout = indexLayoutService.getLayout(id);
			}

			if (StringUtils.isNotBlank(productId) || StringUtils.isNotBlank(activityId)) {
				Integer prodId = NumberUtils.toInt(productId, 0);
				Integer actId = NumberUtils.toInt(activityId, 0);
				if (StringUtils.isNotBlank(productId) && prodId > 0) {
					targetURL = settings.get("target.product") + prodId;
					layout.setProductId(prodId);
					layout.setActivityLayoutId(0);
				} else if(StringUtils.isNotBlank(activityId) && actId > 0){
					targetURL = settings.get("target.activity") +  actId;
					layout.setActivityLayoutId(actId);
					layout.setProductId(0);
				}
				layout.setTargetURL(targetURL);
			} else {
				layout.setTargetURL(targetURL);
			}
			layout.setLayoutType(indexLayoutType.getCode());
			layout.setLayoutSort(NumberUtils.toInt(sort, 0));
			layout.setDisplayTitle(title);
			layout.setDisplaySubtitle("");
			layout.setDisplayDetails(String.format("[{\"key\":\"订金\",\"value\":\"%s\"},{\"key\":\"厂商指导价\",\"value\":\"%s\"}]", earnest, guidePrice));
			layout.setStatus(Status.ACTIVE.getCode());
			layout.setSourceURL(StringUtils.join(imageURLs, ","));
			layout.setOnlineTime(System.currentTimeMillis());
			model.addAttribute("layout", layout);
			indexLayoutService.save(layout);
		} catch (IOException e) {
			log.error("Trying to read the uploaded image data failed.", e);
			if(indexLayoutType == IndexLayoutType.BANNER) {
				return "banner-edit";
			}
			if(indexLayoutType == IndexLayoutType.ENTRANCES) {
				return "entrance-edit";
			}
			return "layout-edit";
		}
		switch (indexLayoutType) {
		case BANNER:
			return "redirect:../../banners";
		case ENTRANCES:
			return "redirect:../../entrances";
		default:
			return "redirect:.";
		}
	}

	@RequestMapping(value = "/layout/availability", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus layoutAvailability(@RequestParam("id") long id,
			@RequestParam(value = "available") String available, Model model) {
		IndexLayout layout = new IndexLayout();
		layout.setId(id);
		boolean avai = Boolean.valueOf(available);
		layout.setStatus(avai ? Status.ACTIVE.getCode() : Status.INACTIVE.getCode());
		indexLayoutService.save(layout);
		ResponseStatus status = ResponseStatus.success();
		return status;
	}

	@RequestMapping(value = "/layout/delete", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus layoutDelete(@RequestParam("id") long id, Model model) {
		indexLayoutService.delete(id);
		ResponseStatus status = ResponseStatus.success();
		return status;
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Model model) {
		IndexLayout layout = null;
		List<IndexLayout> contacts = indexLayoutService.getLayouts(IndexLayoutType.CONTACT);
		if (contacts.isEmpty()) {
			layout = new IndexLayout();
		} else {
			layout = contacts.get(0);
		}
		model.addAttribute("layout", layout);
		return "contact";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String contact(@RequestParam("phone") String phone, Model model) {
		IndexLayout layout = null;
		List<IndexLayout> contacts = indexLayoutService.getLayouts(IndexLayoutType.CONTACT);
		if (contacts.isEmpty()) {
			layout = new IndexLayout();
			layout.setLayoutType(IndexLayoutType.CONTACT.getCode());
			layout.setTargetURL("#");
			layout.setLayoutSort(0);
			layout.setDisplayTitle("CONTACT");
			layout.setDisplaySubtitle("CONTACT");
			layout.setStatus(0);
		} else {
			layout = contacts.get(0);
		}
		layout.setDisplayDetails(phone);
		layout.setSourceURL(phone);
		layout.setOnlineTime(System.currentTimeMillis());
		indexLayoutService.save(layout);
		return "redirect:contact";
	}

}
