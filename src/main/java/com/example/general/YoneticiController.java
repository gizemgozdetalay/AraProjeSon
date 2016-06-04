package com.example.general;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.GizemDenemeApplication;
import com.example.model.AnahtarKelime;
import com.example.model.Esleme;
import com.example.model.Hakem;
import com.example.model.Konferans;
import com.example.model.Kullanici;
import com.example.model.Makale;
import com.example.model.Yonetici;
import com.example.repositories.AnahtarKelimeRepository;
import com.example.repositories.EslemeRepository;
import com.example.repositories.HakemRepository;
import com.example.repositories.KonferansRepository;
import com.example.repositories.KullaniciRepository;
import com.example.repositories.MakaleRepository;
import com.example.repositories.YoneticiRepository;


@Controller
public class YoneticiController {
	
	@Autowired
	private KullaniciRepository kulRep;
	
	@Autowired
	private KonferansRepository konRep;
	
	@Autowired
	private AnahtarKelimeRepository anaKelRep;
	
	@Autowired
	private YoneticiRepository yonRep;
	
	
	@Autowired
	private EslemeRepository esRep;
	
	@Autowired
	private HakemRepository hakRep;
	
	@Autowired
	private MakaleRepository makRep;
	
	@RequestMapping(path="/yonetici",method=RequestMethod.GET)
	public String yoneticiYonlendir(HttpSession session,ModelMap model)
	{
		Kullanici kullanici = (Kullanici) session.getAttribute("yonetici");
		if(kullanici==null)
			return "redirect:/yoneticiGiris";
		else
		{
			model.put("Isim", kullanici.getIsim());
			return "yonetici";
		}
		
	}
	
	@RequestMapping(path="/yoneticiGirisOnay",method = RequestMethod.GET)
	public String yoneticiGiris(
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="sifre", required=false) String sifre,HttpSession session,ModelMap model)
	{
		Kullanici kullanici = kulRep.findByEmailAndType(email,"1");
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
				session.setAttribute("yonetici",kullanici);
				model.put("Isim", kullanici.getIsim());
				//model.put("giris", "Hoşgeldiniz ");
				//her sey dogru hakeme döndür
				return "yonetici";	
				
					
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
		
				
	}
	@RequestMapping(path="/yeniKonferans",method=RequestMethod.GET)
	public String deneme()
	{
		return "yeniKonferans";
	}
	
	
	@RequestMapping(path="konferansKayit",method=RequestMethod.POST)
	public String konferansKayit(
			@RequestParam(value="konferans_isim", required=false) String konferans_isim,
			@RequestParam(value="web_adres", required=false) String web_adres,
			@RequestParam(value="konferans_mekan",required=false) String konferans_mekan,
			@RequestParam(value="konferans_baslama",required=false) String konferans_baslama,
			@RequestParam(value="konferans_bitis",required=false) String konferans_bitis,
			@RequestParam(value="area",required=false) List<String> kelimeler,HttpSession session,
			@RequestParam(value="maxMakaleSayisi",required=false) int makaleSayisi,ModelMap model,
			@RequestParam(value="konferansKisaltma",required=false) String konferansKisaltma
			)
	{
		Date baslamaTarih= new Date();
		Date bitisTarih = new Date();
		System.out.println("baslamaTarihi: "+konferans_baslama);
		System.out.println("bitisTarihi: "+konferans_bitis);
		try
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			baslamaTarih= df.parse(konferans_baslama);
			bitisTarih = df.parse(konferans_bitis);
			System.out.println("parse sonrası : "+baslamaTarih);
			System.out.println("parse sonrası btis: "+bitisTarih);
			System.out.println(df.format(baslamaTarih));
						
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		System.out.println("kayıta girdim");
		
		
		//Kullanici kul =(Kullanici) session.getAttribute("user");
		
		System.out.println("0");
		Kullanici yonetici =(Kullanici) session.getAttribute("yonetici");
		System.out.println("1");
		System.out.println(yonetici.getEmail());
		Yonetici yoneticim = new Yonetici();
		yoneticim.setYoneticiler(yonetici);
		List<Yonetici> konferans_yoneticiler = new ArrayList<>();
	//	Set<Kullanici> konferans_yoneticiler = new HashSet<Kullanici>();
		System.out.println("2");
		konferans_yoneticiler.add(yoneticim);
		//Set<Konferans> yonetici_konferanslar = new HashSet<>();
		Konferans konferans = new Konferans(konferans_isim,baslamaTarih,bitisTarih,konferans_mekan,web_adres,makaleSayisi, konferansKisaltma);
	//	yonetici_konferanslar.add(konferans);
	//	konferans_yoneticiler.get(0).setYonetici_konferanslar(yonetici_konferanslar);
		System.out.println(konferans_yoneticiler.toString());
		konferans.setYoneticiKonferanslar(konferans_yoneticiler);
		System.out.println("3");
		//konRep.save(konferans);
		yoneticim.setYoneticiler(yonetici);
		yoneticim.setYoneticiKonferanslar(konferans);
		//yonRep.save(yoneticim);
		System.out.println("4");
		
		model.put("konferans", konferans);
		session.setAttribute("konferans",konferans);
		session.setAttribute("yoneticim", yoneticim);
		//List<Konferans> deneme =konRep.findByKonferans_ismi(konferans_isim);
		//deneme.get(0).setKonferans_yoneticiler(konferans_yoneticiler);
	//	konferans.setKonferans_yoneticiler(konferans_yoneticiler);
		return "konferansKelimeSecimi";
	}
	
	@RequestMapping(path="konferansOnay",method=RequestMethod.POST)
	public String konferansOnay(@RequestParam(value="anahtarKelime",required=false) List<String> kelimeler,
			//@RequestParam(value="id", required=false) String gelenId,
			HttpSession session)
	{
		//System.out.println("konferans onaydaki gelen id :"+gelenId);
		//Long id = Long.valueOf(gelenId);
		Konferans konferans = (Konferans) session.getAttribute("konferans");
		konRep.save(konferans);
		Yonetici yonetici = (Yonetici) session.getAttribute("yoneticim");
		yonRep.save(yonetici);
		//Konferans konferans = konRep.findOne(id);
		System.out.println("konfernas onay daki kelime size : "+kelimeler.size());
		for(int i=0;i<kelimeler.size();i++)
		{
			System.out.println("for : "+i);
			AnahtarKelime kelime = new AnahtarKelime(kelimeler.get(i),konferans);
			anaKelRep.save(kelime);
		}
		System.out.println("fordan ciktim");
		return "redirect:/yonetici";
	}
	
	@RequestMapping(path="yoneticiKonferanslari",method=RequestMethod.GET)
	public String konferanslarim(HttpSession session, ModelMap model)
	{
		System.out.println("KONFERANSLARINA GİRDİM");
		Kullanici yonetici =(Kullanici) session.getAttribute("yonetici");
		System.out.println("yoneticiyi aldım bile");
		if(yonetici==null)
			return "redirect:/signup";
		System.out.println("yoneticinin konferans sayisini bulucam");
		List<Yonetici> yoneticiler = yonRep.findByYoneticiler(yonetici);
		System.out.println("yoneticinin konferans sayisi : "+yoneticiler.size());
		
		if(yoneticiler.size()==0)
		{
			System.out.println("valla bu adamın konferansı yok");
			return "yonetici";
		}
		else
		{
			List<Konferans> konferanslar = new ArrayList<>();
			List<Konferans> eslemeYapilanKonferanslar = new ArrayList<>();
			
			for(int i=0;i<yoneticiler.size();i++)
			{
				Konferans konferans = yoneticiler.get(i).getYoneticiKonferanslar();
				System.out.println("konferansin id si : "+konferans.getKonferansId());
				if(konferans.getEsleme().equals("true"))
				{
					eslemeYapilanKonferanslar.add(konferans);
				}
				else
					konferanslar.add(konferans);
			
			}
			 model.put("konferanslar", konferanslar);
			 if(eslemeYapilanKonferanslar.size()!=0)
			 model.put("eslemeYapilanKonferanslar", eslemeYapilanKonferanslar);
			 
			 return "yoneticiKonferanslari";
		}
				
		
	}
	
	@RequestMapping(path="/konferansaHakemCagir",method=RequestMethod.POST)
	public String konferansaHakemCagir(HttpSession session,ModelMap model)
	{
		Kullanici yonetici =(Kullanici) session.getAttribute("yonetici");
		List<Yonetici> konferans_yoneticiler = new ArrayList<>();
		Yonetici yoneticim = new Yonetici();
		yoneticim.setYoneticiler(yonetici);
		
			System.out.println("2");
			konferans_yoneticiler.add(yoneticim);
			
			List<Yonetici> yoneticiKonferanslar = yonRep.findByYoneticiler(yonetici);
			List<Konferans> konferanslar = new ArrayList<>();
			for(int i=0;i<yoneticiKonferanslar.size();i++)
			{
				List<Konferans> konf= konRep.findByKonferansId(yoneticiKonferanslar.get(i).getYoneticiKonferanslar().getKonferansId());
				konferanslar.add(konf.get(0));
				System.out.println("i: "+i+" devamı :  "+konRep.findByKonferansId(yoneticiKonferanslar.get(i).getYoneticiKonferanslar().getKonferansId()));
			}
		
		//List<Konferans> konferanslar=konRep.findByYoneticiKonferanslar(konferans_yoneticiler);
		
		for(int i=0;i<konferanslar.size();i++)
		{
			System.out.println("merhaba for"+i);
			System.out.println(konferanslar.get(i).getKonferansMekani());
		}
		//model.addAttribute("konferanslar",konferanslar);
		
		    model.put("konferanslar", konferanslar);
		    
		  if(model.isEmpty()) 
			  System.out.println("ee bu model boş");
		  
		 System.out.println(model.size()); 
		
		return "konferansaHakemCagir";
	}
	
	@RequestMapping(path="/hakemCagir",method=RequestMethod.POST)
	public String hakemCagir(@RequestParam(value="hakem", required=false) String gelen_id)
	{
		System.out.println("cagırılacak hakem konferansi : "+gelen_id);
		
		return "hakemCagir";
	}
	
	@RequestMapping(path="/hakemCagir",method=RequestMethod.GET)
	public String merhaba()
	{
		return "hakemCagir";
	}
	
	@RequestMapping(path="hakemKaydet",method=RequestMethod.POST)
	public String hakemKaydet(
			@RequestParam(value="isimSoyisim",required= false) List<String> hakemIsimler,
			@RequestParam(value="email",required=false) List<String> hakemMailler)
	{
		
		for(int i=0;i<hakemMailler.size();i++)
		{
			System.out.println("hakem ismi"+hakemIsimler.get(i).toString());
			System.out.println("hakem Maili : "+hakemMailler.get(i).toString());
		}
		
		
		final String username = "gizem@talaysoftware.com";
		final String password = "gozdegozde12";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtpout.secureserver.net");
		props.put("mail.smtp.port", "80");
		//587 smtp.gmail.com
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		try {
			for(int i=0;i<hakemMailler.size();i++)
			{
				String gonderilecek_email_adresi = hakemMailler.get(i).toString();
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("mail@talaysoftware.com"));
				message.setSubject("Konferans Davet");
				message.setText("Merhaba "+hakemIsimler.get(i).toString()+"\n Sizi konferansa hakem olmaya davet etti"
						+ " localhost:8080/ "
						+"adresine giderek hakem olarak üye olun lütfen :)");
				message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(gonderilecek_email_adresi));
						Transport.send(message);
			}
			
			
			// duzeltmen gereken yerler username,password,mail@gmail.com var message.setFrom
		} catch(Exception ex){
			
			ex.printStackTrace();
		}
		return "redirect:/yonetici";
	}
	
	
	@RequestMapping(path="makaleOnayla",method=RequestMethod.GET)
	public String makaleOnayla(@RequestParam(value="makale",required= false) List<String> secilenMakaleler)
	{
		for(int i=0;i<secilenMakaleler.size();i++)
		{
			Long id =Long.parseLong(secilenMakaleler.get(i));
			Makale makale = makRep.findOne(id);
			makale.setMakaleOnay("true");
			makRep.save(makale);
		}
		System.out.println("secilen makale sayisi : "+secilenMakaleler.size());
		return "redirect:/yonetici";
	}
	
	@RequestMapping(path="makaleGoster",method= RequestMethod.GET)
	public String makaleGoster(HttpServletRequest req,HttpServletResponse res,
			@RequestParam(value="isim",required=false)String isim)
	{
		 try {
			 System.out.println("açılacak dosyanın ismi "+isim);
		   //   ServletContext context = req.getServletContext();
//		      System.out.println("context : "+context.toString());
//		      System.out.println("context. "+context.getRealPath(""));
//		     
		    //  String contextpath = context.getRealPath("/")+GlobalVariables.OUTPUT_FOLDER;
		      /*HttpSession session = req.getSession();   
		      String user_order_id ="";
		      if(session.getAttribute("user_order_id") != null){
		         user_order_id = session.getAttribute("user_order_id").toString(); //when user_order_id already exists in session .
		       }*/
		   //   String fileName = user_order_id+"-PrintLabels.pdf";
		      System.out.println("1");
		      res.setContentType("application/pdf");
		      res.setHeader("Expires", "0");
		      res.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		      res.setHeader("Content-Disposition","inline;filename=" + isim);
		      res.setHeader("Accept-Ranges", "bytes");
		      System.out.println("1");
		      String denemelik = "Users/gizemgozde/Documents/ECLIPSE/gizemDeneme/";
		      String fullPath = denemelik+GizemDenemeApplication.ROOT + "/" + isim;
		 //     String fullPath = context.getRealPath("")+GizemDenemeApplication.ROOT + "/" + isim;
		      System.out.println("fullPath"+fullPath);
		      System.out.println("2");
		      File nfsPDF = new File(fullPath);
		      System.out.println("3");
		   //   FileInputStream inputStream = new FileInputStream(nfsPDF);
		      System.out.println("445");
		      
		      HttpHeaders headers = new HttpHeaders();
		      headers.setContentType(MediaType.parseMediaType("application/pdf"));
		      String filename = "hw2.pdf";
		      headers.setContentDispositionFormData(filename, filename);
		      System.out.println(headers);
		    /*  headers.setContentType(MediaType.parseMediaType("application/pdf"));
		      String filename = "output.pdf";
		      headers.setContentDispositionFormData(filename, filename);
		      headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		      ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		      return response;*/
		    //  System.out.println("inputs stream :"+inputStream.toString());
		      System.out.println(nfsPDF.toString());
		     FileInputStream fis = new FileInputStream(nfsPDF);
		      System.out.println("4");
		   //   BufferedInputStream bis = new BufferedInputStream(fis);
		      System.out.println("5");
		      byte[] buffer = null;
		    //  ServletOutputStream sos = res.getOutputStream();
		      System.out.println("6");
		      ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
		              buffer, headers, HttpStatus.OK);
		    System.out.println("response : "+response.toString());
		    /*  while (true) {
		        int bytesRead = bis.read(buffer, 0, buffer.length);
		        if (bytesRead < 0) {
		          break;
		        }
		        System.out.println("7");
		      sos.write(buffer, 0, bytesRead);
		      sos.flush();
		      }
		      sos.flush();
		      bis.close();*/
		    //  inputStream.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		 
		 
		 
		 return "";
		
	}
	
	

}
