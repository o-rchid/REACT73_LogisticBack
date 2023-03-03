package kr.co.seoulit.logistics.prodcsvc.production.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.co.seoulit.logistics.prodcsvc.production.service.ProductionService;
import kr.co.seoulit.logistics.prodcsvc.production.to.MrpGatheringTO;
import kr.co.seoulit.logistics.prodcsvc.production.to.MrpTO;

@RestController
@RequestMapping("/production/*")
public class MrpController {

	@Autowired
	private ProductionService productionService;
	
	ModelMap map = null;

	private static Gson gson = new GsonBuilder().serializeNulls().create();	
	
	@RequestMapping(value="/mrp/list", method=RequestMethod.GET)
	public ModelMap getMrpList(HttpServletRequest request, HttpServletResponse response) {
		String mrpGatheringStatusCondition = request.getParameter("mrpGatheringStatusCondition");	
		String dateSearchCondition = request.getParameter("dateSearchCondition");
		String mrpStartDate = request.getParameter("mrpStartDate");
		String mrpEndDate = request.getParameter("mrpEndDate");
		String mrpGatheringNo = request.getParameter("mrpGatheringNo");
		map = new ModelMap();
		try {
			ArrayList<MrpTO> mrpList = null;
			
			if(mrpGatheringStatusCondition != null ) {
				mrpList = productionService.searchMrpList(mrpGatheringStatusCondition);
			} else if (dateSearchCondition != null) {
				mrpList = productionService.selectMrpListAsDate(dateSearchCondition, mrpStartDate, mrpEndDate);
			} else if (mrpGatheringNo != null) {
				mrpList = productionService.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
			}
			map.put("gridRowJson", mrpList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	
	@GetMapping("/mrp/open")
	public ModelMap openMrp(@RequestParam(value="mpsNoList[]") ArrayList<String> mpsNoList, HttpServletRequest request) {
		System.out.println("request.getQueryString() = " + request.getQueryString());
		System.out.println("mpsNoList = " + mpsNoList);
		map = new ModelMap();
		try {
			HashMap<String, Object> mrpMap = productionService.openMrp(mpsNoList);
			
			map.put("gridRowJson", mrpMap.get("result"));
			map.put("errorCode", mrpMap.get("errorCode"));
			map.put("errorMsg", mrpMap.get("errorMsg"));
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	
	@GetMapping("/mrp")
	public ModelMap registerMrp(@RequestParam String mrpRegisterDate) {
		map = new ModelMap();
		try {
			HashMap<String, Object> resultMap = productionService.registerMrp(mrpRegisterDate);
			
			map.put("result", resultMap.get("result"));
			map.put("errorCode", resultMap.get("errorCode"));
			map.put("errorMsg", resultMap.get("errorMsg"));
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	
	
	@RequestMapping(value="/mrp/gathering-list", method=RequestMethod.GET)
	public ModelMap getMrpGatheringList(HttpServletRequest request, HttpServletResponse response) {
		String mrpNoList = request.getParameter("mrpNoList");
		map = new ModelMap();
		try {
			ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
					new TypeToken<ArrayList<String>>() { }.getType());
			ArrayList<MrpGatheringTO> mrpGatheringList = productionService.getMrpGathering(mrpNoArr);
			
			map.put("gridRowJson", mrpGatheringList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
	

	@RequestMapping(value="/mrp/gathering", method=RequestMethod.POST)
	public ModelMap registerMrpGathering(HttpServletRequest request, HttpServletResponse response) {
		String mrpGatheringRegisterDate = request.getParameter("mrpGatheringRegisterDate"); 
		String mrpNoList = request.getParameter("mrpNoList");
		String mrpNoAndItemCodeList = request.getParameter("mrpNoAndItemCodeList");
		map = new ModelMap();
		try {
			ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
					new TypeToken<ArrayList<String>>() { }.getType());
			HashMap<String, String> mrpNoAndItemCodeMap =  gson.fromJson(mrpNoAndItemCodeList, //mprNO : ItemCode 
	              new TypeToken<HashMap<String, String>>() { }.getType());
			HashMap<String, Object> resultMap  = productionService.registerMrpGathering(mrpGatheringRegisterDate, mrpNoArr,mrpNoAndItemCodeMap);	 
			
			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	

	@RequestMapping(value="/mrp/mrpgathering/list", method=RequestMethod.GET)
	public ModelMap searchMrpGathering(HttpServletRequest request, HttpServletResponse response) {
		String searchDateCondition = request.getParameter("searchDateCondition");
		String startDate = request.getParameter("mrpGatheringStartDate");
		String endDate = request.getParameter("mrpGatheringEndDate");
		map = new ModelMap();
		try {
			ArrayList<MrpGatheringTO> mrpGatheringList = 
					productionService.searchMrpGatheringList(searchDateCondition, startDate, endDate);
				
			map.put("gridRowJson", mrpGatheringList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	
}
