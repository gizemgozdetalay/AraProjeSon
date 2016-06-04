package com.example.general;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Hakem;
import com.example.model.Kullanici;
import com.example.model.Yazar;
import com.example.model.Yonetici;
import com.example.repositories.HakemRepository;
import com.example.repositories.KullaniciRepository;
import com.example.repositories.YazarRepository;
import com.example.repositories.YoneticiRepository;

@Controller
public class KayıtController {
	
	@Autowired
	private KullaniciRepository kulRep;	
	@Autowired
	private YoneticiRepository yonRep;	
	@Autowired
	private HakemRepository hakRep;	
	@Autowired
	private YazarRepository yazRep;
	
	
	@RequestMapping(path="/signup",method = RequestMethod.POST)
	public String kayitkontrol(HttpSession session)
	{	
		Kullanici kul =(Kullanici) session.getAttribute("user");
		if(kul==null){
			return "signup";
		}else{
		return "redirect:/";
		}
		
	}
	
	@RequestMapping(path="/kayit",method =RequestMethod.POST)
	public String kayit(@RequestParam(value="isim", required=false) String isim,
			@RequestParam(value="soyisim", required=false) String soyisim,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="kurum", required=false) String kurum,
			@RequestParam(value="type", required=false) String type ,
			@RequestParam(value="sifre",required=false) String sifre,Model model,HttpSession session)
	{
		
		
		
		 try{
		        MessageDigest messageDigestNesnesi = MessageDigest.getInstance("MD5");
		        messageDigestNesnesi.update(sifre.getBytes());
		        byte messageDigestDizisi[] = messageDigestNesnesi.digest();
		        StringBuffer sb16 = new StringBuffer();
		        StringBuffer sb32 = new StringBuffer();
		        for (int i = 0; i < messageDigestDizisi.length; i++) {
		        sb16.append(Integer.toString((messageDigestDizisi[i] & 0xff) + 0x100, 16).substring(1));
		        sb32.append(Integer.toString((messageDigestDizisi[i] & 0xff) + 0x100, 32));
		        }
		// System.out.println("Parolanın Şifrelenmiş Hali:(16) " + sb16.toString());
		 System.out.println("Parolanın Şifrelenmiş Hali:(32) " + sb32.toString());
		 
		 
		 System.out.println("isim : "+isim);
			Kullanici kullanici = kulRep.findByEmailAndType(email, type);
			if(kullanici!=null)
			{
				System.out.println("Zaten böyle bir kullanıcı var");
				return "redirect:/signup";
			}
			else
			{
				Kullanici kullanicim = new Kullanici(isim,soyisim,email,kurum,type,sb32.toString());
				try
				{
					kulRep.save(kullanicim);
				}catch( Exception e)
				{
					e.toString();
				}
				session.setAttribute("user", kullanici);
			}
		 }
		    catch(NoSuchAlgorithmException ex){
		        System.err.println(ex);
		    }
		
		
		
		
		
		
	//	model.addAttribute("kullanici", kullanici);
	//	System.out.println("Kullanıcı id : "+kullanici.getId());
		System.out.println(" kullanici sayı : "+kulRep.count());
	//	System.out.println(kulRep.findOne((long)1).getKurum());
	//	System.out.println("type : "+kulRep.findOne((long)1).getType());
				
		return "redirect:/";	
	}
	
}
