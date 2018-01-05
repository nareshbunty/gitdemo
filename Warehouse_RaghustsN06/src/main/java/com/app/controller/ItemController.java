package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.model.Item;
import com.app.service.IItemService;
import com.app.util.ItemUtil;
import com.app.validator.ItemValidator;

@Controller
public class ItemController {

	@Autowired
	private IItemService service;
	@Autowired
	private ItemValidator validator;
	@Autowired
	private ItemUtil util;

	/**
	 * 1. Show Register Page
	 * @param map
	 * @return
	 */
	@GetMapping("/regItem")
	public String showPage(ModelMap map){
		map.addAttribute("item", new Item());
		util.getDynamics(map);
		return "ItemRegister";
	}
	
	/**
	 * 2. Save Item
	 * @param item
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/insertItem")
	public String save(@ModelAttribute Item item,BindingResult errors,ModelMap map){
		validator.validate(item, errors);
		if(errors.hasErrors()){
			map.addAttribute("item", item);
		}else{
			long id=service.save(item);
			map.addAttribute("message", "Item '"+id+"' Saved");
			map.addAttribute("item", new Item());
		}
		util.getDynamics(map);
		return "ItemRegister";
	}
	
	/**
	 * 3. Get All Items
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllItems")
	public String getAll(ModelMap map){
		List<Item> list=service.getAll();
		map.addAttribute("itemsList", list);
		return "ItemData";
	}
	
	/**
	 * 4. Delete Item by Id
	 * @param itemId
	 * @return
	 */
	@GetMapping("/deleteItem")
	public String delete(@RequestParam long itemId){
		service.deleteById(itemId);
		return "redirect:getAllItems";
	}
	
	/**
	 * 5. Show Edit Page
	 * @param itemId
	 * @param map
	 * @return
	 */
	@GetMapping("/editItem")
	public String edit(@RequestParam long itemId,ModelMap map){
		map.addAttribute("item", service.getOneById(itemId));
		util.getDynamics(map);
		return "ItemDataEdit";
	}
	
	/**
	 * 6. Update Item
	 * @param item
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/updateItem")
	public String update(@ModelAttribute Item item,BindingResult errors,ModelMap map){
		validator.validate(item, errors);
		String page=null;
		if(errors.hasErrors()){
			map.addAttribute("item", item);
			page="ItemDataEdit";
			util.getDynamics(map);
		}else{
			service.update(item);
			map.addAttribute("item", new Item());
			page="redirect:getAllItems";
		}
		return page;
	}
}
