package io.jirsis.drzewo.drzewo.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminController {
	
	@RequestMapping("/admin")
	public ModelAndView index(@RequestParam(required=false, defaultValue=".") String path){
		Map<String, Object> model = new HashMap<>();
		model.put("isAlbum", Boolean.TRUE);
		model.put("pwd", "");
		model.put("totalImages", 42);
		model.put("directories", new ArrayList<String>());
		ModelAndView view = new ModelAndView("create-album", model);
	    return view;
	}
}
