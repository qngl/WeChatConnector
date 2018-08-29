package com.vwfsag.fso.view.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.vwfsag.fso.domain.City;
import com.vwfsag.fso.domain.Shop;
import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.domain.StaffRole;
import com.vwfsag.fso.domain.Status;
import com.vwfsag.fso.persistence.ShopMapper;
import com.vwfsag.fso.service.CityService;
import com.vwfsag.fso.service.EncryptService;
import com.vwfsag.fso.service.ShopService;
import com.vwfsag.fso.service.StaffService;
import com.vwfsag.fso.view.vo.Page;
import com.vwfsag.fso.view.vo.ResponseStatus;

/**
 * @author liqiang
 * 
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopController {

	private static final Logger log = LoggerFactory.getLogger(ShopController.class);

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopMapper mapper;
	@Autowired
	private StaffService staffService;

	@Autowired
	private EncryptService encryptService;
	
	@Autowired
	private CityService cityService;

	@RequestMapping(method = RequestMethod.GET)
	public String shops(Model model) {
		return "shops";
	}

//	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public String importLocations(Model model) {
//		File file = new File("C:\\Users\\softs\\Work\\Develop\\workspaces\\WeChatConnector\\WeChatConnector\\docs\\locations.xlsx");
//		File file = new File("G:\\Work\\workspaces\\car\\WeChatConnector\\docs\\shop.xlsx");
		File file = new File("/home/WeChatConnector/tomcat-WeChatConnector/deploy/WeChatConnector/docs/locations.xlsx");
		Workbook wb = null;
		try {
			List<Shop> shops = new ArrayList<Shop>();
			wb = new XSSFWorkbook(file);
			Sheet sheet = wb.getSheetAt(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row r = sheet.getRow(i);
				try {
					String code = r.getCell(0).getStringCellValue();
					String lng = String.valueOf(r.getCell(3).getNumericCellValue());
					String lat = String.valueOf(r.getCell(4).getNumericCellValue());
					
					Shop sh = mapper.selectByCode(code);
					if(sh == null) {
						System.err.println("missing shop: " + code);
					} else {
						Shop s = new Shop();
						s.setId(sh.getId());
						s.setLongitude(lng);
						s.setLatitude(lat);
						shops.add(s);
						
					}

				} catch (Exception e) {
					log.error(String.format("row: %d error.", i), e);
				}
			}
			shopService.saveShop(shops.toArray(new Shop[0]));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(wb);
		}

		return "redirect:.";
	}

	public String importShops(Model model) {
//		File file = new File("C:\\Users\\softs\\Work\\Develop\\workspaces\\WeChatConnector\\WeChatConnector\\docs\\shop.xlsx");
//		File file = new File("G:\\Work\\workspaces\\car\\WeChatConnector\\docs\\shop.xlsx");
		File file = new File("/home/WeChatConnector/tomcat-WeChatConnector/deploy/WeChatConnector/docs/shop.xlsx");
		Workbook wb = null;
		try {
			Map<String, City> provincesByName = new HashMap<String, City>();
			Map<Integer, City> provincesById = new HashMap<Integer, City>();
			Map<String, City> citiesByName = new HashMap<String, City>();
			Map<Integer, City> citiesById = new HashMap<Integer, City>();
			List<City> allProvinces = cityService.getProvinces();
			for (City p : allProvinces) {
				provincesByName.put(p.getName(), p);
				provincesById.put(p.getId(), p);
			}
			List<City> allCities = cityService.getAll();
			for (City c : allCities) {
				if(!provincesById.containsKey(c.getId())) {
					citiesByName.put(c.getName(), c);
					citiesById.put(c.getId(), c);
				}
			}

			List<Shop> shops = new ArrayList<Shop>();
			wb = new XSSFWorkbook(file);
			Sheet s = wb.getSheetAt(0);
			for (int i = 1; i < s.getPhysicalNumberOfRows(); i++) {
				Row r = s.getRow(i);
				try {
					String name = r.getCell(9).getStringCellValue();
//					log.warn(name);
					String address = r.getCell(10).getStringCellValue();
					String email = r.getCell(13).getStringCellValue();
//					log.warn(address);
					String phone = r.getCell(11).getStringCellValue();
//					log.warn(phone);
					String provinceName = r.getCell(2).getStringCellValue();
//					log.warn(provinceName);
					String cityName = r.getCell(3).getStringCellValue();
//					log.warn(cityName);
					String code = r.getCell(4).getStringCellValue();
					String newName = r.getCell(6).getStringCellValue();
					
					String preSalesId = null;
					try {
						preSalesId = r.getCell(7).getStringCellValue();
					} catch (Exception e) {
						try {
							preSalesId = String.format("%d", new Double(r.getCell(7).getNumericCellValue()).longValue());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					
					Shop sh = mapper.selectByName(name);
//					if(sh == null) {
//						sh = mapper.selectByName(name);
//					} 
					if(sh == null) {
						System.err.println("missing shop: " + name);
					} else {
//						sh.setName(newName);
						sh.setCode(code);
						sh.setPreSalesId(preSalesId);
						sh.setEmail(email);
						shops.add(sh);
						

						Staff staff = new Staff();
						staff.setRole(StaffRole.Sales);
						staff.setUserName(email);
						staff.setFullName(name);
						staff.setShopId(sh.getId());
						staff.setPassword(encryptService.sha1("2oI6"));
						staff.setCreateTime(System.currentTimeMillis());
						staffService.save(staff);
					}

//					City province = provincesByName.get(provinceName);
//					if (province == null) {
//						province = provincesByName.get(provinceName + "省");
//					}
//					if (province == null) {
//						province = provincesByName.get(provinceName + "市");
//					}
//					if (province == null) {
//						log.warn(String.format("row: %d has no matched province data. %s", i, provinceName));
//					}
//					City city = citiesByName.get(cityName);
//					if (city == null) {
//						city = citiesByName.get(cityName + "市");
//					}
//					if (city == null) {
//						log.warn(String.format("row: %d has no matched city data. %s", i, cityName));
//					} else {
//						city = getBigCity(provincesById, citiesById, city);
//						Shop shop = new Shop();
//						shop.setName(name);
//						shop.setAddress(address);
//						shop.setPhone(phone);
//						shop.setCityId(city.getId());
//						shop.setCityName(city.getName());
//						shop.setProvinceId(province.getId());
//						shop.setProvinceName(province.getName());
//						shop.setStatus(1);
//						shop.setCreateTime(System.currentTimeMillis());
//						shop.setLatitude("0");
//						shop.setLongitude("0");;
//						
//						shops.add(shop);
//						System.out.println(shop);
//					}

				} catch (Exception e) {
					log.error(String.format("row: %d error.", i), e);
				}
			}
			shopService.saveShop(shops.toArray(new Shop[0]));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(wb);
		}

		return "redirect:.";
	}

	private City getBigCity(Map<Integer, City> provincesById, Map<Integer, City> citiesById, City city) {
		if(provincesById.containsKey(city.getParentId())) {
			return city;
		} else {
			city = citiesById.get(city.getParentId());
			return getBigCity(provincesById, citiesById, city);
		}
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("id", id);
		return "shop-edit";
	}

	@RequestMapping(value = "/invalidate/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus invalidate(@PathVariable("id") Integer id, Model model) {
		shopService.delete(id);
		return ResponseStatus.success();
	}

	@RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
	public @ResponseBody List<Shop> list(@PathVariable("page") int page, @RequestParam(value = "pageSize", required = false) Integer size, HttpServletRequest req) {
		page -= 1;
		int pageSize = size == null ? 10 : size;
		int provinceId = NumberUtils.toInt(req.getParameter("provinceId"), -1);
		int cityId = NumberUtils.toInt(req.getParameter("cityId"), -1);
		List<Shop> shops = null;
		if(cityId > 0) {
			shops = shopService.getByCity(cityId, page * pageSize, pageSize);
		} else {
			shops = shopService.getByProvince(provinceId, page * pageSize, pageSize);
		}
		return shops;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody Page get(HttpServletRequest req) {
		Page page = new Page();
		int pageSize = 10;
		int provinceId = NumberUtils.toInt(req.getParameter("provinceId"), 2);
		int cityId = NumberUtils.toInt(req.getParameter("cityId"), -1);
		if(cityId > 0) {
			int total = shopService.countByCity(cityId);
			page.setTotal(total);
		} else {
			int total = shopService.countByProvince(provinceId);
			page.setTotal(total);
		}
		page.setPageSize(pageSize);
		return page;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Shop get(@PathVariable("id") Integer id) {
		Shop shop = null;
		if(id > 0) {
			shop = shopService.getById(id);
		} else {
			shop = new Shop();
		}
		return shop;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String save(@PathVariable("id") Integer id, @RequestParam("provinceId") Integer provinceId,
			@RequestParam("cityId") Integer cityId, @RequestParam("name") String name, 
			@RequestParam("phone") String phone, @RequestParam("address") String address,
			@RequestParam("code") String code, @RequestParam("preSalesId") String preSalesId,
			@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude,
			@RequestParam("email") String email, Model model) {
		City province = cityService.getLocation(provinceId);
		City city = cityService.getLocation(cityId);
		
		Shop shop = new Shop();
		if(id != null && id > 0) {
			shop = shopService.getById(id);
		}
		shop.setName(name);
		shop.setAddress(address);
		shop.setPhone(phone);
		shop.setCityId(cityId);
		shop.setCityName(city.getName());
		shop.setProvinceId(provinceId);
		shop.setProvinceName(province.getName());
		shop.setStatus(Status.ACTIVE.getCode());
		shop.setCreateTime(System.currentTimeMillis());
		shop.setLatitude(latitude);
		shop.setLongitude(longitude);
		shop.setCode(code);
		shop.setEmail(email);
		shop.setPreSalesId(preSalesId);
		shopService.saveShop(shop);
		return "redirect:./edit/{id}";
	}
}
