package com.example.general;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Kullanici;


@Controller
public class LogInController {
	

	
	@RequestMapping(path="/yazarGiris")
	public String yazarGirisKontrol(HttpSession session,ModelMap model)
	{
		
		Kullanici kullanici = (Kullanici) session.getAttribute("yazar");
		if(kullanici==null)
		return "yazarGiris";
		else
		{
			model.put("Isim", kullanici.getIsim());
			return "redirect:/yazar";
		}
	}
	
	@RequestMapping(path="/yoneticiGiris")
	public String yoneticiGirisKontrol(HttpSession session,ModelMap model)
	{
		Kullanici kullanici = (Kullanici) session.getAttribute("yonetici");
		if(kullanici==null)
		return "yoneticiGiris";
		else
		{
			model.put("Isim", kullanici.getIsim());
			return "redirect:/yonetici";
		}
		
	}
	
	@RequestMapping(path="/hakemGiris")
	public String hakemGirisKontrol(HttpSession session,ModelMap model)
	{
		Kullanici kullanici = (Kullanici) session.getAttribute("hakem");
		if(kullanici==null)
			return "hakemGiris";
		else
		{
			model.put("Isim", kullanici.getIsim());
			return "redirect:/hakem";
		}
		
	}
	
		
	@RequestMapping(path="/signup")
	public String uyeOl(){
		return "signup";
	}

	
}
