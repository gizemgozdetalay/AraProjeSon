package com.example.general;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.repositories.KonferansRepository;
import com.example.repositories.KullaniciRepository;
import com.example.repositories.MakaleRepository;

//import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {
	
	@Autowired
	private KullaniciRepository kulRep;
	@Autowired
	private KonferansRepository konRep;
	@Autowired
	private MakaleRepository makRep;
	
	@RequestMapping(path="/")
	public String index(ModelMap model)
	{
		Long kullanicilar=kulRep.count();
		Long konferanslar =konRep.count();
		Long makaleler =makRep.count();
		model.put("kullanicilar", kullanicilar);
		model.put("konferanslar", konferanslar);
		model.put("makaleler", makaleler);
		return "index3";
	}
	
	@RequestMapping(path="/konferans")
	public String about()
	{
		return "bok";
	}
	
	@RequestMapping(path="cikis",method=RequestMethod.GET)
	public String cikis(HttpSession session)
	{
		session.removeAttribute("yazar");
		session.removeAttribute("yonetici");
		session.removeAttribute("hakem");
		return "redirect:/";
	}
	
	/*@RequestMapping(path="/about",method=RequestMethod.POST)
	public String about2()
	{
		return "redirect:/index2";
	}
	*/
	/*@RequestMapping(path="/konferans",method=RequestMethod.POST)
	public String konferans()
	{
		return "redirect:/index2";
	}*/

}




