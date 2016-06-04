package com.example.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.support.DelegatingSmartContextLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.AnahtarKelime;
import com.example.model.Degerlendirme;
import com.example.model.Esleme;
import com.example.model.Hakem;
import com.example.model.HakemAnahtarKelime;
import com.example.model.Konferans;
import com.example.model.Kullanici;
import com.example.model.Makale;
import com.example.model.Yonetici;
import com.example.repositories.DegerlendirmeRepository;
import com.example.repositories.EslemeRepository;
import com.example.repositories.HakemKelimeRepository;
import com.example.repositories.HakemRepository;
import com.example.repositories.KonferansRepository;
import com.example.repositories.KullaniciRepository;
import com.example.repositories.MakaleRepository;

import ch.qos.logback.core.net.SyslogOutputStream;




@Controller
public class HakemController {
	
	 private static final int BUFFER_SIZE = 4096;
	
	@Autowired
	private KullaniciRepository kulRep;
	
	@Autowired
	private KonferansRepository konRep;
	
	@Autowired
	private HakemRepository hakRep;
	
	@Autowired
	private HakemKelimeRepository hakKelRep;
	
	@Autowired
	private EslemeRepository esRep;
	
	@Autowired
	private MakaleRepository makRep;
	
	@Autowired DegerlendirmeRepository degRep;
	
	@RequestMapping(path="/hakem",method=RequestMethod.GET)
	public String hakemeDon(HttpSession session,ModelMap model)
	{
		Kullanici kullanici =(Kullanici) session.getAttribute("hakem");
		if(kullanici==null)
			return "redirect:/hakemGiris";
		else
		{
			model.put("Isim", kullanici.getIsim());
			return "hakem";
		}
		
		
		
		
	}
	
	@RequestMapping(path="/hakemGirisOnay",method = RequestMethod.GET)
	public String hakemGiris(	
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="sifre", required=false) String sifre,HttpSession session,ModelMap model)
	{	
		Kullanici kullanici = kulRep.findByEmailAndType(email,"2");
		//System.out.println("merhaba kullanici null");
		if(kullanici==null)
		{
			System.out.println("kullanici null");
			return "redirect:/signup";
		
		}
		
		
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
				session.setAttribute("hakem",kullanici);
				model.put("Isim", kullanici.getIsim());
				//model.put("giris", "Hoşgeldiniz ");
				//her sey dogru hakeme döndür
				return "hakem";	
				
					
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
	//////////////////////		
		/*System.out.println("find gelen sifre : "+kullanici.getSifre());
		System.out.println("sayfadan gelen sifre: "+sifre);
		if(kullanici.getSifre().equals(sifre))
		{
			session.setAttribute("hakem",kullanici);
			model.put("Isim", kullanici.getIsim());
			//her sey dogru hakeme döndür
			return "hakem";	
				
		}
		else
		{	
			//String yanlis= "false";
			//model.put("yanlis",yanlis);
			System.out.println("sifren yanlis yeniden dene");
			//sifre yanlis hakem girise döndur tekrar
			return "redirect:/hakemGiris";
		}*/
					
		
	}
	
	
	@RequestMapping(path="hakemKonferansaKatil",method=RequestMethod.GET)
	public String hakemKonferansaKatil(HttpSession session,ModelMap model )
	{
		System.out.println("konferans sayisi : "+konRep.count());
		Kullanici hakem = (Kullanici) session.getAttribute("hakem");
		try
		{
		
			Iterable<Konferans> allKonferanslar = konRep.findAll();
			//System.out.println(tumKonferanslar);
			List<Konferans> tumKonferanslar = makeCollection(allKonferanslar);
			List<Konferans>konferanslar = new ArrayList<>();
//			for(int i=0;i<konferanslar.size();i++)
//			{
//				Date tarih = new Date();
//				if(konferanslar.get(i).getSonGonderimSuresi().before(tarih))
//				{
//					konferanslar.remove(konferanslar.get(i));
//				}
//			}
			Date date = new Date();
			for(int i=0;i<tumKonferanslar.size();i++)
			{
				Hakem hakemim= hakRep.findByHakemKonferanslarAndHakemler(tumKonferanslar.get(i), hakem);
				if(hakemim==null&&tumKonferanslar.get(i).getEsleme().equals("false")&&tumKonferanslar.get(i).getKonferansBaslamaTarihi().after(date))
				{
					
					konferanslar.add(tumKonferanslar.get(i));
				}
				
				
			}
			System.out.println("listeye gelen konferans sayisi : "+konferanslar.size());
			model.put("konferanslar", konferanslar);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
		
		return "hakemKonferansaKatil";
	}
	
	@RequestMapping(path="/hakemKonferansSecimi",method=RequestMethod.POST)
	public String sec(@RequestParam(value="konferans", required=false) String gelen_id,ModelMap model)
	{
		Long id= Long.parseLong(gelen_id);
		System.out.println("html den gelenin cevirisi : "+id);
		List<Konferans> konferanslar = konRep.findByKonferansId(id);
		List<AnahtarKelime> anahtarKelimeler = konferanslar.get(0).getKonferansAnahtarKelime();
		/*for(int i=0;i<anahtarKelimeler.size();i++)
		{
			System.out.println("Anahtar Kelime: "+anahtarKelimeler.get(i).getAnahtar_kelime_deger());
		}*/
		model.put("anahtarKelimeler", anahtarKelimeler);
		model.put("id", gelen_id);
		//System.out.println("radio dan dönen deger: "+email);
		return "hakemAnahtarKelimeSec";
	}
	
	@RequestMapping(path="hakemKonferansKayit",method=RequestMethod.POST)
	public String hakemKonferansKayit(@RequestParam(value="anahtar",required=false) List<String> kelimeler,
			@RequestParam(value="id",required=false) String gelen_id,HttpSession session)
	{
		System.out.println("gelen id: "+gelen_id);
		Long id = Long.parseLong(gelen_id);
		System.out.println("kelime size : "+kelimeler.size());
		for(int i=0;i<kelimeler.size();i++)			
		System.out.println("kelimeni indexindeki ddegeri : "+kelimeler.get(i).toString());
		
		List<Konferans> hakemKonferanslar = konRep.findByKonferansId(id);
		
		
		Hakem hakem = new Hakem();
		hakem.setHakemKonferanslar(hakemKonferanslar.get(0));
		Kullanici hakemim =(Kullanici) session.getAttribute("hakem");
		hakem.setHakemler(hakemim);
		hakRep.save(hakem);
		
		
		for(int i=0;i<kelimeler.size();i++)
		{
			HakemAnahtarKelime hakemAnahtarKelime = new HakemAnahtarKelime();
			hakemAnahtarKelime.setHakem(hakem);
			long kelimeId = Long.parseLong(kelimeler.get(i));
			hakemAnahtarKelime.setAnahtarKelimeId(kelimeId);
			hakKelRep.save(hakemAnahtarKelime);
			//hakemAnahtarKelime.s
			//AnahtarKelime kelime = new AnahtarKelime()
			//kelimeler.get(i).
		}
			//EslemeController.class.getEnclosingMethod();		
		
		return "redirect:/hakem";
	}
	
	@RequestMapping(path="hakemKonferanslari",method=RequestMethod.GET)
	public String hakemKonferanslari(HttpSession session,ModelMap model)
	{
		Kullanici hakem = (Kullanici) session.getAttribute("hakem");
		List<Hakem> hakemler = hakRep.findByHakemler(hakem);
		List<Konferans> konferanslar = new ArrayList<>();
		for(int i=0;i<hakemler.size();i++)
		{
			Konferans konferans = hakemler.get(i).getHakemKonferanslar();
			konferanslar.add(konferans);
		}
		
		model.put("konferanslar", konferanslar);
		if(konferanslar.size()==0)
			System.out.println("hakemin konferansı yok ki");
		
		  
		return "hakemKonferanslari";
	}
	
	@RequestMapping(path="hakemMakaleGetir",method={RequestMethod.POST,RequestMethod.GET})
	public String hakemMakaleGetir(@RequestParam(value="konferans", required=false) String gelenId,ModelMap model,HttpSession session)
	{
		Kullanici hakem = (Kullanici) session.getAttribute("hakem");
		Long id = Long.valueOf(gelenId);
		System.out.println("konferansin id si : "+id);
		Hakem hakemim = hakRep.findByHakemKonferanslarAndHakemler(konRep.findOne(id), hakem);
		
		if(hakemim==null)
			System.out.println("burda bi sııkıntı var");
		List<Esleme> eslemeler = esRep.findByEslemeHakem(hakemim);
		List<Makale> degerlenmisMakaleler = new ArrayList<>();
		List<Makale> degerlenmemisMakaleler = new ArrayList<>();
		for(int i=0;i<eslemeler.size();i++)
		{
			//
			Makale makale =eslemeler.get(0).getEslemeMakale();
			Esleme esleme = esRep.findByEslemeMakaleAndEslemeHakem(makale, hakemim);
			if(esleme.getDegerlendirme()!=null)
			degerlenmisMakaleler.add(makale);
			else
			degerlenmemisMakaleler.add(makale);
		}
		model.put("degerlenmisMakaleler",degerlenmisMakaleler);
		model.put("degerlenmemisMakaleler", degerlenmemisMakaleler);
		//Hakem hakem = hakRep.findOne(id)
		
		return "hakemMakaleleri";
	}
	
	
	
	
	@RequestMapping(path="makaleIndir")
	public String download(@RequestParam(value="uzanti", required=false) String makaleUzanti,
            HttpServletResponse response,HttpServletRequest request) throws IOException
	{		
			   System.out.println("makalenin uzantısı"+makaleUzanti);	             
	    /**
	     * Path of the file to be downloaded, relative to application's directory
	     */
	     String filePath = makaleUzanti;	     
	    /**
	     * Method for handling file download request from client
	     */	   
	        // get absolute path of the application
	        ServletContext context = request.getServletContext();
	        String appPath = context.getRealPath("");	 
	        System.out.println("context ten gelen apppath :"+appPath);
	        appPath= "/Users/gizemgozde/Documents/ECLIPSE/gizemDeneme/";
	        System.out.println("appPath = " + appPath);
	        System.out.println("file path : "+filePath);
	        // construct the complete absolute path of the file
	        String fullPath =appPath + filePath;     
	        System.out.println("full path : "+fullPath);
	        File downloadFile = new File(fullPath);
	        FileInputStream inputStream = new FileInputStream(downloadFile);	         
	        // get MIME type of the file
	        String mimeType = context.getMimeType(fullPath);
	        if (mimeType == null) {
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        System.out.println("MIME type: " + mimeType);
	 
	        // set content attributes for the response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	 
	        // set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	                downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	 
	        // get output stream of the response
	        OutputStream outStream = response.getOutputStream();
	 
	        byte[] buffer = new byte[BUFFER_SIZE];
	        int bytesRead = -1;
	 
	        // write bytes read from the input stream into the output stream
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	       
	        inputStream.close();
	        outStream.close(); 

	            
	        System.out.println("hakeme donucem");

	        return null;

	
	}
	
	@RequestMapping(path="makaleDegerlendir",method=RequestMethod.GET)
	
	public String degerlendir(@RequestParam("uzanti")String makaleUzanti,ModelMap model,HttpSession session)
			{
		System.out.println("degerlendirme uzanti"+makaleUzanti);
		Makale makale = makRep.findByMakaleUzanti(makaleUzanti);
		
		model.put("makale",makale);
		session.setAttribute("makale", makale);
		
		return "makaleDegerlendir";
	}
	
	@RequestMapping(path="/degerlendirmeKaydet",method=RequestMethod.POST)
	public String degerlendirmeKaydet(
			@RequestParam(value="recommendation",required =false)String degerlendirme,
			@RequestParam(value="category",required =false)String kategori,
			@RequestParam(value="value",required =false)String deger,
			@RequestParam(value="familiar",required =false)String hakemBilgisi,
			@RequestParam(value="bpcandidate",required =false)String aday,
			@RequestParam(value="lenght",required =false)String uzunluk,
			@RequestParam(value="kayit",required=false)String kayit,HttpSession session)
	{
		
		if(kayit.equals("dolu"))
		{
			int puan=0;
			System.out.println("degerlendirme: "+degerlendirme);
			puan+= Integer.parseInt(degerlendirme);
			puan+= Integer.parseInt(uzunluk);
			puan+= Integer.parseInt(aday);
			puan+= Integer.parseInt(deger);
			puan+= Integer.parseInt(kategori);
			System.out.println("adamın puanı :"+puan);
			System.out.println("kategori: "+kategori);
			System.out.println("hakemBilgisi : "+hakemBilgisi);
			System.out.println("aday : "+aday);
			System.out.println("uzunluk : "+uzunluk);
			System.out.println("degerler: "+deger);
			System.out.println("degerlendirme kaydete girdim");
			if(hakemBilgisi.equals("1"))
				puan+=3;
			if(hakemBilgisi.equals("0"))
			{
				if(puan<=40)
					puan+=5;
				else
					puan+=1;
			}
			else
			{
				if(puan<=40)
					puan+=1;
				else 
					puan+=5;
			}
			System.out.println("adamın puanı :"+puan);
			Degerlendirme degerlendirmeSonuc = new Degerlendirme(puan,degerlendirme,kategori,hakemBilgisi,aday,uzunluk,deger);
			Makale makale = (Makale) session.getAttribute("makale");
		
			Kullanici kul= (Kullanici) session.getAttribute("hakem");
			Hakem hakem = hakRep.findByHakemKonferanslarAndHakemler(makale.getKonferansMakale(), kul);
			Esleme esleme = esRep.findByEslemeMakaleAndEslemeHakem(makale, hakem);
			esleme.setDegerlendirme(degerlendirmeSonuc);
			//if(puan<40)
			/*if(kayit.equals("dolu"))
				System.out.println("puanı hesapla kaydet");
			System.out.println("kayit tan donen"+kayit);*/
			degRep.save(degerlendirmeSonuc);
		}
		
		return "redirect:/hakem";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static final /*<Konferans> */List<Konferans> makeCollection(Iterable<Konferans> iter) {
	    List<Konferans> list = new ArrayList<Konferans>();
	    for (Konferans item : iter) {
	        list.add(item);
	    }
	    return list;
	}
	
}
