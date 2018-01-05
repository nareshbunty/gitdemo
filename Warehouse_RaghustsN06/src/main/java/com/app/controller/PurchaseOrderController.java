package com.app.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.controller.view.VendorInvoicePdfView;
import com.app.model.PurchaseOrder;
import com.app.model.PurchaseOrderDetails;
import com.app.service.IPurchaseOrderService;
import com.app.util.PurchaseOrderUtil;
import com.app.validator.PurchaseOrderValidator;

@Controller
public class PurchaseOrderController {
	@Autowired
	private IPurchaseOrderService service;
	@Autowired
	private PurchaseOrderValidator validator;
	@Autowired
	private PurchaseOrderUtil util;

	/**
	 * 1. Show Register Page
	 * @param map
	 * @return
	 */
	@GetMapping("/regPurchaseOrder")
	public String showPage(ModelMap map){
		map.addAttribute("po", new PurchaseOrder("OPEN"));
		util.getDynamics(map);
		return "PurchaseOrderRegister";
	}

	/**
	 * 2. Save PurchaseOrder
	 * @param po
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/insertPurchaseOrder")
	public String save(@ModelAttribute PurchaseOrder po,BindingResult errors,ModelMap map){
		validator.validate(po, errors);
		if(errors.hasErrors()){
			map.addAttribute("po", po);
		}else{
			long id=service.save(po);
			map.addAttribute("message", "PurchaseOrder '"+id+"' Saved");
			map.addAttribute("po", new PurchaseOrder("OPEN"));
		}
		util.getDynamics(map);
		return "PurchaseOrderRegister";
	}

	/**
	 * 3. Get All PurchaseOrders
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllPurchaseOrders")
	public String getAll(ModelMap map){
		List<PurchaseOrder> list=service.getAll();
		map.addAttribute("posList", list);
		return "PurchaseOrderData";
	}

	/**
	 * 4. Delete PurchaseOrder by Id
	 * @param poId
	 * @return
	 */
	@GetMapping("/deletePurchaseOrder")
	public String delete(@RequestParam long poId){
		service.deleteById(poId);
		return "redirect:getAllPurchaseOrders";
	}

	/**
	 * 5. Show Edit Page
	 * @param poId
	 * @param map
	 * @return
	 */
	@GetMapping("/editPurchaseOrder")
	public String edit(@RequestParam long poId,ModelMap map){
		map.addAttribute("po", service.getOneById(poId));
		util.getDynamics(map);
		return "PurchaseOrderDataEdit";
	}

	/**
	 * 6. Update PurchaseOrder
	 * @param po
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/updatePurchaseOrder")
	public String update(@ModelAttribute PurchaseOrder po,BindingResult errors,ModelMap map){
		validator.validate(po, errors);
		String page=null;
		if(errors.hasErrors()){
			map.addAttribute("po", po);
			page="PurchaseOrderDataEdit";
			util.getDynamics(map);
		}else{
			service.update(po);
			map.addAttribute("po", new PurchaseOrder("OPEN"));
			page="redirect:getAllPurchaseOrders";
		}
		return page;
	}
	/**
	 * 5. Show Add Items Page
	 * @param poId
	 * @param map
	 * @return
	 */

	@GetMapping("/addPoItems")
	public String showAddItems(@RequestParam long poId,ModelMap map){
		PurchaseOrder po=service.getOneById(poId);

		PurchaseOrderDetails poDtls=new PurchaseOrderDetails();
		poDtls.setPoHdrId(po.getOrderId());
		poDtls.setSlno(po.getDetails().size()+1); //set serial num
		map.addAttribute("poDtl",poDtls); //new obj having serial number
		map.addAttribute("po", po);

		map.addAttribute("venItems",util.getVendorItems(po.getVendor().getWhUserTypeId()));
		return "AddPoItems";
	}

	/**
	 * 6. Add item to PO 
	 * @param poDtl
	 * @param error
	 * @param itemOpr
	 * @param map
	 * @return
	 */
	@PostMapping("/poItemAdd")
	public String itemOperations(@ModelAttribute PurchaseOrderDetails poDtl,BindingResult error,@RequestParam String itemOpr,ModelMap map){
		PurchaseOrder po=service.getOneById(poDtl.getPoHdrId());
		String page=null;
		if("Add Item".equals(itemOpr)){
			//if we click on add item..
			validator.validate(poDtl, error);
			if(error.hasErrors()){
				map.addAttribute("poDtl", poDtl);

			}else{
				po.getDetails().add(poDtl);
				po.setStatus("PICKING"); //no vendor change
				service.save(po);
				poDtl=new PurchaseOrderDetails();
				poDtl.setPoHdrId(po.getOrderId());
				poDtl.setSlno(po.getDetails().size()+1);
				map.addAttribute("poDtl",poDtl);
			}

			map.addAttribute("venItems",util.getVendorItems(po.getVendor().getWhUserTypeId()));
			map.addAttribute("po", po);
			page="AddPoItems";
			
		}else if("Save and Continue".equals(itemOpr)){
			if(!po.getDetails().isEmpty()){
				po.setStatus("ORDERED"); // no more edit..
				service.update(po);
			    page="redirect:getAllPurchaseOrders";
			}
		}
		return page;
	}

	/**
	 * 7. Remove Item from PO
	 * @param slno
	 * @param poId
	 * @param map
	 * @return
	 */
	@GetMapping("/removeItem")
	public String removeItem(@RequestParam int slno,@RequestParam long poId,ModelMap map){
		PurchaseOrder po=service.getOneById(poId);
		List<PurchaseOrderDetails> details=po.getDetails();
		if(details!=null){
			Iterator<PurchaseOrderDetails> poDtlItr=details.iterator();
			while (poDtlItr.hasNext()) {
				PurchaseOrderDetails poDtl =  poDtlItr.next();
				if(poDtl.getSlno()==slno) //get num and check with given input
					poDtlItr.remove();
			}
		}
		//set new serial numbers
		if(details!=null && details.size()>0){
			int slNo=1;
			Iterator<PurchaseOrderDetails> poDtlItr=details.iterator();
			while (poDtlItr.hasNext()) {
				PurchaseOrderDetails poDtl =  poDtlItr.next();
				poDtl.setSlno(slNo++);
			}
		}
		if(details.size()==0) po.setStatus("OPEN"); //you can change Vendor
		service.update(po);

		PurchaseOrderDetails poDtls=new PurchaseOrderDetails();
		poDtls.setPoHdrId(po.getOrderId());
		poDtls.setSlno(po.getDetails().size()+1);

		map.addAttribute("venItems",util.getVendorItems(po.getVendor().getWhUserTypeId()));
		map.addAttribute("poDtl",poDtls);
		map.addAttribute("po", po);

		return "AddPoItems";
	}
	/**
	 * 8. Confirm PO 
	 * Change Status to INVOICED
	 * @param poId
	 * @return
	 */
	@GetMapping("/poConfirm")
	public String confirmOrder(@RequestParam long poId){
		PurchaseOrder po=service.getOneById(poId);
		po.setStatus("INVOICED");
		service.update(po);
		return "redirect:getAllPurchaseOrders";
	}
	/**
	 * 9. PO Cancel
	 * Change Status to CANCELLED
	 * @param poId
	 * @return
	 */
	@GetMapping("/cancelOrder")
	public String cancelOrder(@RequestParam long poId){
		PurchaseOrder po=service.getOneById(poId);
		po.setStatus("CANCELLED");
		service.update(po);
		return "redirect:getAllPurchaseOrders";
	}
	
	@GetMapping("/poInvoceGen")
	public ModelAndView generateInvoice(@RequestParam long poId){
		PurchaseOrder po=service.getOneById(poId);
		ModelAndView m=null;
		if(po.getStatus().equals("INVOICED")){
			m=new ModelAndView(new VendorInvoicePdfView(),"po",po);
		}else{
			m=new ModelAndView("PurchaseOrderData","posList",service.getAll());
		}
		return m;
	}



}
