package com.example.general;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.GizemDenemeApplication;
import com.example.model.AnahtarKelime;
import com.example.model.Hakem;
import com.example.model.HakemAnahtarKelime;
import com.example.model.Konferans;
import com.example.model.Kullanici;
import com.example.model.Makale;
import com.example.model.Yazar;
import com.example.model.MakaleAnahtarKelime;
import com.example.repositories.AnahtarKelimeRepository;
import com.example.repositories.KonferansRepository;
import com.example.repositories.KullaniciRepository;
import com.example.repositories.MakaleRepository;
import com.example.repositories.MakaleKelimeRepository;
import com.example.repositories.YazarRepository;

@Controller
public class YazarController {
	
	@Autowired
	private KullaniciRepository kulRep;
	
	@Autowired
	private KonferansRepository konRep;
	
	@Autowired
	private AnahtarKelimeRepository anKelRep;
	
	@Autowired
	private MakaleRepository makRep;
	
	@Autowired 
	private YazarRepository yazRep;
	
	@Autowired
	private MakaleKelimeRepository makKelRep;
	
	@RequestMapping(path="yazar",method=RequestMethod.GET)
	public String yazaraGit(HttpSession session,ModelMap model)
	{
		Kullanici kullanici = (Kullanici) session.getAttribute("yazar");
		if(kullanici==null)
			return "redirect:/yazarGiris";
		else
		{
			model.put("Isim", kullanici.getIsim());
			return "yazar";
		}
	}
	
	@RequestMapping(path="/yazarGirisOnay",method = RequestMethod.GET)
	public String yazarGiris(	
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="sifre", required=false) String sifre,HttpSession session,ModelMap model)
	{	
		Kullanici kullanici = kulRep.findByEmailAndType(email,"3");
		
	
		if(kullanici==null)
			return "redirect:/signup";
		
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
		
		 System.out.println("find gelen sifre : "+kullanici.getSifre());
			System.out.println("sayfadan gelen sifre: "+sb32.toString());
		 if(kullanici.getSifre().equals(sb32.toString()))
			{
				session.setAttribute("yazar",kullanici);
				model.put("Isim", kullanici.getIsim());
				//model.put("giris", "Hoşgeldiniz ");
				//her sey dogru hakeme döndür
				return "yazar";	
				
					
			}
			else
			{
				return "redirect:/yoneticiGiris";
			}
				//return "redirect:/signup";
			
		 }catch(NoSuchAlgorithmException ex){
		        System.err.println(ex);
		        return "redirect:/signup";
		    }
		
		/*System.out.println("find gelen sifre : "+kullanici.getSifre());
		System.out.println("sayfadan gelen sifre: "+sifre);
		if(kullanici.getSifre().equals(sifre))
		{
			session.setAttribute("yazar",kullanici);
			model.put("Isim", kullanici.getIsim());
			//her sey dogru hakeme döndür
			return "yazar";	
				
		}
		else
		{	
			System.out.println("sifren yanlis yeniden dene");
			//sifre yanlis hakem girise döndur tekrar
			return "redirect:/signup";
		}*/
			

}

	@RequestMapping(path="yazarKonferansaKatil",method=RequestMethod.POST)
	public String yazarKonferansaKatil(ModelMap model,HttpSession session)
	{
		System.out.println("konferans sayisi : "+konRep.count());
		Kullanici yazar = (Kullanici) session.getAttribute("yazar");
		try
		{
			Iterable<Konferans> allKonferanslar = konRep.findAll();
			//System.out.println(allKonferanslar);
			List<Konferans> tumKonferanslar = makeCollection(allKonferanslar);
			List<Konferans> konferanslar = new ArrayList<>();
			System.out.println("başlangıcta konferans size : "+konferanslar.size());
			System.out.println("listeye gelen konferans sayisi : "+tumKonferanslar.size());
			Date date = new Date();	
			for(int i =0;i<tumKonferanslar.size();i++)
			{
				System.out.println("konferans id :"+tumKonferanslar.get(i).getKonferansId());
				System.out.println("esleme degeri"+tumKonferanslar.get(i).getEsleme());
				System.out.println("baslama tarihi"+tumKonferanslar.get(i).getKonferansBaslamaTarihi());
				System.out.println("suanki zaman"+date);
				if((tumKonferanslar.get(i).getEsleme().equals("false"))&&(tumKonferanslar.get(i).getKonferansBaslamaTarihi().after(date)))
				{
					
					System.out.println("eslemesi false çıktı ekliyorum "+tumKonferanslar.get(i).getKonferansId());
					konferanslar.add(tumKonferanslar.get(i));
				}										
					
			}
//			
			System.out.println("yazara gosterilecek konferans sayisi: "+konferanslar.size());
			model.put("konferanslar", konferanslar);
			model.put("anahtarKelime", anKelRep.findAll());
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "yazarKonferansaKatil";
	}
	
	@RequestMapping(path="/yazarKonferansSecimi",method=RequestMethod.POST)
	public String yazarDosyaYukle(@RequestParam(value="secim", required=false) String gelen_id,ModelMap model)
	{
		
		
		System.out.println("yazarin katilmak icin sectiği konferans id :"+gelen_id);
		model.put("kId", gelen_id);
		
		return "yazarMakaleYukle";
	}
	
	@RequestMapping(path="makaleKaydet",method=RequestMethod.POST)
	public String dosyayiKaydet(@RequestParam("id") String id,@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes,HttpSession session,ModelMap model,HttpServletRequest request)
	{
		
		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(GizemDenemeApplication.ROOT + "/" + file.getOriginalFilename())));
                FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();				
				
			}
			catch (Exception e) {
				e.printStackTrace();
		}
		
	}
		 ServletContext context = request.getServletContext();
		 String fullPath = context.getRealPath("")+GizemDenemeApplication.ROOT + "/" + file.getOriginalFilename();
		 System.out.println("full path :"+fullPath);
		 String mimeType = context.getMimeType(fullPath);
	        if (mimeType == null) {
	            // set to binary type if MIME mapping not found
	            System.out.println("mimeType : "+mimeType);	  
	            return "yazar";
	        }
	        else
	        	System.out.println("mime Type: "+mimeType);
		System.out.println("makale orijinal isim: "+file.getOriginalFilename());
		Makale makale = new Makale();
		
		System.out.println("yazarin sectiği konferansın id : "+id);
		Long kId = Long.parseLong(id);
		List<Konferans> konferanslar = konRep.findByKonferansId(kId);
		makale.setKonferansMakale(konferanslar.get(0));
		makale.setMakaleUzanti(GizemDenemeApplication.ROOT + "/" + file.getOriginalFilename());
		//makale.setKonferans_makale(konferans_makale);
		makale.setMakaleIsmi(file.getOriginalFilename());
		
		Yazar yazar = new Yazar();
		yazar.setYazarKonferanslar(konferanslar.get(0));
		
		Kullanici yazarim =(Kullanici) session.getAttribute("yazar");
		yazar.setYazarlar(yazarim);
		yazRep.save(yazar);
		makale.setYazarMakale(yazar);
		
		//yazar.setYazarlar(yazarlar);
		//makRep.save(makale);
		session.setAttribute("makale",makale);
		//session.setAttribute("makaleYazar", yazar);
		
	
		List<AnahtarKelime> anahtarKelimeler = konferanslar.get(0).getKonferansAnahtarKelime();
		
		model.put("anahtarKelimeler", anahtarKelimeler);
		model.put("id",id);
		

		return "yazarAnahtarKelimeSec";
	}
	
	@RequestMapping(path="yazarKelimeKayit",method=RequestMethod.POST)
	public String yazarKelimeKayit(@RequestParam(value="anahtar",required=false) List<String> kelimeler,
			@RequestParam(value="id",required=false) String gelen_id,HttpSession session)
	{
		System.out.println("yzarin kelime saysi : "+kelimeler.size());
		Kullanici yazarim =(Kullanici) session.getAttribute("yazar");
		Makale makalem = (Makale) session.getAttribute("makale");
		makRep.save(makalem);
		
		//Long id = Long.parseLong(gelen_id);
		for(int i=0;i<kelimeler.size();i++)
		{
			MakaleAnahtarKelime makaleAnahtarKelime = new MakaleAnahtarKelime();
			
			
			System.out.println("sessiondan yazarı cektim");
			//Makale makalem = (Makale) session.getAttribute("makale");
			System.out.println("makaleyisessiondan aldım");
			//List<Yazar> yazar = yazRep.findByYazarlar(yazarim);
			Long kId = Long.parseLong(gelen_id);
			System.out.println("4");
			List<Konferans> konferanslar = konRep.findByKonferansId(kId);
			System.out.println("5");
			//Yazar yazarlar = yazRep.findByYazarKonferanslarAndYazarlar(konferanslar.get(0), yazarim);
			makaleAnahtarKelime.setMakale(makalem);
			System.out.println("6");
			//makaleAnahtarKelime.setYazar(yazarlar.get(0));
			long kelimeId = Long.parseLong(kelimeler.get(i));
			makaleAnahtarKelime.setAnahtarKelimeId(kelimeId);
			
			
			makKelRep.save(makaleAnahtarKelime);
			//hakemAnahtarKelime.s
			//AnahtarKelime kelime = new AnahtarKelime()
			//kelimeler.get(i).
		}	
		

		
		return "redirect:/yazar";
	}
	
	@RequestMapping(path="yazarKonferanslari", method = RequestMethod.POST)
    public String yazarKonferanslari(HttpSession session, ModelMap model)
	{
		Kullanici yazar = (Kullanici) session.getAttribute("yazar");
		List<Yazar> yazarlar = yazRep.findByYazarlar(yazar);
		List<Konferans> konferanslar = new ArrayList<>();
		for(int i=0;i<yazarlar.size();i++)
		{
			Konferans konferans = yazarlar.get(i).getYazarKonferanslar();
			konferanslar.add(konferans);
		}
		
		model.put("konferanslar", konferanslar);
		if(konferanslar.size()==0)
			System.out.println("hakemin konferansı yok ki");
		
		  
		return "yazarKonferanslari";
	}
	
	public static final /*<Konferans> */List<Konferans> makeCollection(Iterable<Konferans> iter) {
	    List<Konferans> list = new ArrayList<Konferans>();
	    for (Konferans item : iter) {
	        list.add(item);
	    }
	    return list;
	}
	
	
}
