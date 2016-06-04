package com.example.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.AnahtarKelime;
import com.example.model.Degerlendirme;
import com.example.model.Hakem;
import com.example.model.HakemAnahtarKelime;
import com.example.model.Konferans;
import com.example.model.Kullanici;
import com.example.model.Makale;
import com.example.model.MakaleAnahtarKelime;
import com.example.model.Yazar;
import com.example.model.Esleme;
import com.example.repositories.AnahtarKelimeRepository;
import com.example.repositories.EslemeRepository;
import com.example.repositories.HakemKelimeRepository;
import com.example.repositories.HakemRepository;
import com.example.repositories.KonferansRepository;
import com.example.repositories.KullaniciRepository;
import com.example.repositories.MakaleRepository;
import com.example.repositories.MakaleKelimeRepository;
import com.example.repositories.YazarRepository;
import com.example.EslemeBilgisi;


@Controller
public class EslemeController {
	
	
	@Autowired
	private HakemKelimeRepository hakKelRep;	
	@Autowired
	private MakaleKelimeRepository makKelRep;	
	@Autowired 
	private HakemRepository hakRep;	
	@Autowired 
	private KonferansRepository konRep;	
	@Autowired 
	private AnahtarKelimeRepository anKelRep;	
	@Autowired 
	private MakaleRepository makRep;	
	@Autowired
	private YazarRepository yazRep;
	@Autowired
	private EslemeRepository esRep;
	@Autowired
	private KullaniciRepository kulRep;
	
	
	
	@RequestMapping(path="/eslemeYap",method=RequestMethod.GET)
	public String Esleme(@RequestParam(value="id",required=false)String gelenId)
	{
		Long konferansId= Long.valueOf(gelenId);
		System.out.println("eslemeye girdim");
		System.out.println("konferans idsi : "+konferansId);
		//Long konferansId = (long) 1;
		Konferans konferans = konRep.findOne(konferansId);
		
		//List<AnahtarKelime> konferansKelimeler = anKelRep.findByKonferansAnahtarKelime(konferans);
		List<Makale> makaleler = makRep.findByKonferansMakale(konferans);
		System.out.println("Konferansın makale sayisi : "+makaleler.size());
		int [][] makaleMatris = new int[makaleler.size()][2];
		int [] makaleSize = new int[makaleler.size()];
		int [] makaleId = new int[makaleler.size()];
		List<EslemeBilgisi> esitSayidaKelimeler = new ArrayList<EslemeBilgisi>();
		List<EslemeBilgisi> hakemdeFazlaKelimeler = new ArrayList<EslemeBilgisi>();
		List<EslemeBilgisi> makaledeFazlaKelimeler = new ArrayList<EslemeBilgisi>();

		
		for(int i=0;i<makaleler.size();i++)
		{
			
			List<MakaleAnahtarKelime> makaleAnahtarKelimeleri = makKelRep.findByMakale(makaleler.get(i));
			makaleSize[i]= makaleAnahtarKelimeleri.size();
			makaleId[i]= (int)makaleler.get(i).getMakaleId();
		}
		makaleMatris = selectionSort(makaleSize,makaleId);
	
		
		for(int i=0;i<makaleler.size();i++)
		{
			makaleId[i]= makaleMatris[i][0];
			System.out.println("sirali makaleId :"+makaleId[i]);
		}
		
		List<Hakem> hakemler = hakRep.findByHakemKonferanslar(konferans);
		System.out.println("konferansın hakem sayısı :"+hakemler.size());
		//System.out.println("konferansın hakem maili : "+hakemler.get(0).getHakemler().getEmail());
		
	/*	int [][] makaleninAtandigiHakemSayisi = new int[makaleler.size()][2];
		for(int i=0;i<makaleler.size();i++)
		{
			makaleninAtandigiHakemSayisi[i][0]=makaleId[i];
			makaleninAtandigiHakemSayisi[i][1]=0;			
		}
		
		for(int i=0;i<makaleler.size();i++)
			for(int j=0;j<2;j++)
				System.out.println("makalenin id leri sıralı mı :"+makaleninAtandigiHakemSayisi[i][j]);
		
		*/
		
		for(int i=0;i<makaleler.size();i++)
		{
					
			//Long kullanilacak_id = makaleler.get(i).getMakaleId();
			Long kullanilacak_id = Long.valueOf(makaleId[i]);
			System.out.println("makalenin id si : "+kullanilacak_id);
			List<MakaleAnahtarKelime> makaleKelimeleri =makKelRep.findByMakale(makRep.findOne(kullanilacak_id));
			System.out.println("makalenin kelime sayisi : "+makaleKelimeleri.size());
			for(int j=0;j<hakemler.size();j++)
			{								
				List<HakemAnahtarKelime> hakemKelimeleri = hakKelRep.findByHakem(hakemler.get(j));
				System.out.println("hakem id si : "+(j+1));
				
				int sayac = kelimeKarsilastir(makaleKelimeleri, hakemKelimeleri);
				if(sayac==makaleKelimeleri.size()&&makaleKelimeleri.size()==hakemKelimeleri.size())
				{
					System.out.println("MAKALEYLE ESLESEN BULDUM");	
					EslemeBilgisi esleme = new EslemeBilgisi(makaleId[i], hakemler.get(j).getHakemId(),0);
					esitSayidaKelimeler.add(esleme);
				}
				else if(sayac==makaleKelimeleri.size()&&hakemKelimeleri.size()>makaleKelimeleri.size())
				{
					System.out.println("HAKEMDE MAKALEDEN FAZLA BULDUM");	
					EslemeBilgisi esleme = new EslemeBilgisi(makaleId[i], hakemler.get(j).getHakemId(),hakemKelimeleri.size()-sayac);
					hakemdeFazlaKelimeler.add(esleme);
				}
				else if(sayac>0)
				{
					System.out.println("MAKALEDE HAKEMDEN FAZLA BULDUM");	
					EslemeBilgisi esleme = new EslemeBilgisi(makaleId[i], hakemler.get(j).getHakemId(),sayac);
					makaledeFazlaKelimeler.add(esleme);
				}
				
								
								
			}
		}
		System.out.println("esit sayidakiler");
		for(int i=0;i<esitSayidaKelimeler.size();i++)
		{
			System.out.println(i+1);
			System.out.println("makalenin id :"+esitSayidaKelimeler.get(i).getMakaleninId());
			System.out.println("hakemin id :"+esitSayidaKelimeler.get(i).getHakeminId());	
			System.out.println("esit oldugundan degerlik 0");
		}
		System.out.println("hakemde fazla olanlar");
		for(int i=0;i<hakemdeFazlaKelimeler.size();i++)
		{
			System.out.println(i+1);
			System.out.println("makalenin id :"+hakemdeFazlaKelimeler.get(i).getMakaleninId());
			System.out.println("hakemin id : "+hakemdeFazlaKelimeler.get(i).getHakeminId());
			System.out.println("hakemde fazla olan kelime sayisi : "+hakemdeFazlaKelimeler.get(i).getYakinlik());
		}
		System.out.println("makalede fazla olanlar");
		for(int i=0;i<makaledeFazlaKelimeler.size();i++)
		{
			System.out.println(i+1);
			System.out.println("makalenin id :"+makaledeFazlaKelimeler.get(i).getMakaleninId());
			System.out.println("hakemin id : "+makaledeFazlaKelimeler.get(i).getHakeminId());	
			System.out.println("makaleyle ortak olan kelime sayisi : "+makaledeFazlaKelimeler.get(i).getYakinlik());
		}
		eslemeYap(esitSayidaKelimeler,hakemdeFazlaKelimeler,makaledeFazlaKelimeler,makaleId,makaleler.size(),hakemler.size(),hakemler);
		//konferans.setEsleme("true");
		//System.out.println("konferans İD : "+konferans.getKonferansId());
		//System.out.println("konferans esleme deger: "+konferans.getEsleme());
		//System.out.println("konferans isim"+konferans.getKonferansIsmi());
		//konRep.save(konferans);
		return "redirect:/";
		
		
	}
	
	public int[][] selectionSort(int[] dizi, int[] dizi2 )
	{
		 for (int i = 0; i < dizi.length - 1; i++)
	        {
	            int index = i;
	            for (int j = i + 1; j < dizi.length; j++)
	                if (dizi[j] < dizi[index])
	                    index = j;
	      
	            int smallerNumber = dizi[index]; 
	            dizi[index] = dizi[i];
	            dizi[i] = smallerNumber;
	            int a = dizi2[index];
	            dizi2[index]=dizi2[i];
	            dizi2[i]= a;
	        }
		 int [][]array = new int[10][2];
		 for(int i=0;i<dizi.length;i++)			 
			 {
				 array[i][0]=dizi2[i];
				 array[i][1]=dizi[i];
			 }
	        return array;			
	}
	
	public int kelimeKarsilastir (List<MakaleAnahtarKelime> makaleKelimeleri,List<HakemAnahtarKelime> hakemKelimeleri)
	{
		int sayac=0;
		for(int k=0;k<makaleKelimeleri.size();k++)
		{
			int deger=0;
			System.out.println("k degeri :"+k);
			int l=0;
			AnahtarKelime makaleninKelimesi = anKelRep.findOne(makaleKelimeleri.get(k).getAnahtarKelimeId());
			while((l<hakemKelimeleri.size())&&(deger==0))
			{					
				System.out.println("while icindeyim l degeri : "+l);
				AnahtarKelime hakeminKelimesi = anKelRep.findOne(hakemKelimeleri.get(l).getAnahtarKelimeId());
				System.out.println("hakemin Kelimesi :"+hakeminKelimesi.getAnahtarKelimeDeger().toString());
				System.out.println("makalenin kelimesi : "+makaleninKelimesi.getAnahtarKelimeDeger().toString());
				if(hakeminKelimesi.getAnahtarKelimeDeger().equals(makaleninKelimesi.getAnahtarKelimeDeger()))
				{
					
					deger=1;
					sayac++;
					System.out.println("kelimeler ayni ");
				}
				else 
					l++;
			}
			
		}
		return sayac;
	 
	}

	
	public void eslemeYap(List<EslemeBilgisi> esitSayidaKelimeler,List<EslemeBilgisi> hakemdeFazlaKelimeler,List<EslemeBilgisi> makaledeFazlaKelimeler, int[] makaleId, int makaleSayisi, int hakemSayisi,List<Hakem> hakemler)
	{
		
		int [][] hakeminAldigiMakaleSayisi = new int[hakemSayisi][2];
		int [][] makaleninAtandigiHakemSayisi = new int[makaleSayisi][2];
		System.out.println("hakemler icin counter lar acildi"+hakeminAldigiMakaleSayisi.length);
		for(int i=0;i<hakemSayisi;i++)
		{
			hakeminAldigiMakaleSayisi[i][0]=0;
			int id =(int) hakemler.get(i).getHakemId();
			hakeminAldigiMakaleSayisi[i][1]=id;
		}
		for(int i=0;i<makaleSayisi;i++)
		{
			makaleninAtandigiHakemSayisi[i][0]=makaleId[i];
			makaleninAtandigiHakemSayisi[i][1]=0;
			
		}
		for(int i=0;i<hakemSayisi;i++)
			for(int j=0;j<2;j++)
			System.out.println("hakemin aldıgı sayılar"+hakeminAldigiMakaleSayisi[i][j]);
		for(int i=0;i<makaleSayisi;i++)
			for(int j=0;j<2;j++)
				System.out.println("makalenin id leri sıralı mı :"+makaleninAtandigiHakemSayisi[i][j]);
		
		/*for(int i=0;i<makaleId.length;i++)
		{
			System.out.println("makale Id : "+makaleId[i]);
		}*/
		
		for(int i=0;i<makaleSayisi;i++)
		{
			Long kullanilacakId = Long.valueOf(makaleId[i]);
			System.out.println("kullanilacak makalenin id si :"+kullanilacakId);
			
			Makale makale = makRep.findOne(kullanilacakId);
			Yazar yazar = yazRep.findOne(makale.getYazarMakale().getYazarId());
			Kullanici kullanici = yazar.getYazarlar();
			
			System.out.println("kullanici 1 in id si:"+kullanici.getKullanici_id());
			int count =0;
			System.out.println("öncelikle esit sayidakilere bakiyorum");
			 count = siralama3(esitSayidaKelimeler, hakeminAldigiMakaleSayisi, makale,count);
			 System.out.println("count : "+count);
			 if(count!=3)
			 {
				 System.out.println("hakemde fazlalara bakıyorum");
				 count= siralama3(hakemdeFazlaKelimeler,hakeminAldigiMakaleSayisi,makale,count);
				 System.out.println("hakemden cıkınca count : "+count);
				 if(count!=3)
				 {
					 System.out.println("makalede fazla olanlara bakıyorum");
					 count=siralama3(makaledeFazlaKelimeler, hakeminAldigiMakaleSayisi, makale, count);
					 System.out.println("makaleden cıkınca count : "+count);
				 }
				 
			 }
			 int m=0;
			 while(makaleninAtandigiHakemSayisi[m][0]!=makale.getMakaleId())
				 m++;
			 makaleninAtandigiHakemSayisi[m][1]=count;
			 
			 
			 
							
		}
		System.out.println("makalelerin eslenme sayisini yazıyorum");
		for(int k=0;k<makaleSayisi;k++)
		{
			System.out.println((k+1)+" .ci mkalenin count u : "+makaleninAtandigiHakemSayisi[k][1]);
		}
		System.out.println("hakemlerin makale sayısını yazıyorum");
		for(int m=0;m<hakemSayisi;m++)
		{
			System.out.println("makale sayisi "+hakeminAldigiMakaleSayisi[m][0]);
			System.out.println("hakem Id si "+hakeminAldigiMakaleSayisi[m][1]);
		}
		for(int i=0;i<makaleSayisi;i++)
		{
			
			List<Integer> geriyeKalanHakemler = new ArrayList<>();
			if(makaleninAtandigiHakemSayisi[i][1]!=3)
			{
				Makale makale= makRep.findOne(Long.valueOf(makaleninAtandigiHakemSayisi[i][0]));	
				System.out.println("makale id : "+makale.getMakaleId());
				List<Esleme> makaleninHakemleri = esRep.findByEslemeMakale(makale);
				for(int k=0;i<makaleninHakemleri.size();k++)
				{
					int l=0;
					while(Long.valueOf(hakeminAldigiMakaleSayisi[l][0])==makaleninHakemleri.get(k).getEslemeHakem().getHakemId())
						l++;
					if(!geriyeKalanHakemler.contains(l))
					geriyeKalanHakemler.add(l);
				}
				for(int m=0;m<geriyeKalanHakemler.size();m++)
				{
					System.out.println("geriye kalan hakem index: "+geriyeKalanHakemler.get(m));
				}
			}
		}
		
		/*for(int i=0;i<makaleSayisi;i++)
		{
			while(makaleninAtandigiHakemSayisi[i][1]!=3)
			{
				int hakemId = enAzHakem(hakeminAldigiMakaleSayisi);
				System.out.println("en az makalesi olan hakem : "+hakemId);
				Long id = Long.valueOf(hakemId);
				Hakem hakem = hakRep.findOne(id);
				Long makaleninId = Long.valueOf(makaleninAtandigiHakemSayisi[i][0]);
				Esleme esleme =esRep.findByEslemeMakaleAndEslemeHakem(makRep.findOne(makaleninId), hakem);
				System.out.println("esleme : "+esleme);
				if(esleme==null)
				{
					Esleme asilEsleme = new Esleme();
					asilEsleme.setEslemeMakale(makRep.findOne(makaleninId));
					System.out.println("makaleyi setledim");
					asilEsleme.setEslemeHakem(hakem);
					esRep.save(asilEsleme);
					System.out.println("makaleye hakem atadım");
					int k=0;
					while(hakeminAldigiMakaleSayisi[k][1]!=id)
						k++;
					hakeminAldigiMakaleSayisi[k][0]++;	
					
					makaleninAtandigiHakemSayisi[i][1]++;
				}
				
				//3 hakeme atanamamış gereğini yap
			}
		}	*/	
		int hakemIdsi = enAzHakem(hakeminAldigiMakaleSayisi);
		System.out.println(hakemIdsi);
	}
	
	
	public int enAzHakem(int[][] hakeminAldigiMakaleSayisi)
	{
			int min = hakeminAldigiMakaleSayisi[0][0];
			System.out.println("min : "+min);
			 int index =0;
			// System.out.println("hakemin id si: "+hakemId);
			for(int i=1;i<hakeminAldigiMakaleSayisi.length;i++)
			{
				if(min>hakeminAldigiMakaleSayisi[i][0])
				{
					min = hakeminAldigiMakaleSayisi[i][0];
					index =i;
					System.out.println("min> id si :"+index);
				}
			}
			
			return hakeminAldigiMakaleSayisi[index][1];
		
	}
	
	public int siralama3(List<EslemeBilgisi> eslemeListesi,int [][]hakeminAldigiMakaleSayisi,Makale makale,int count)
	{
		Yazar yazar = yazRep.findOne(makale.getYazarMakale().getYazarId());
		Kullanici kullanici = yazar.getYazarlar();
		System.out.println();
		
		 int l=0;
		 int index =-1;
		 while(l<eslemeListesi.size())
		 {
			 while(l<eslemeListesi.size()&&count<3)
			 {
				 while((l<eslemeListesi.size())&&(eslemeListesi.get(l).getMakaleninId()!=makale.getMakaleId()))
				 {
					 l++;
				 }
				 if(l!=eslemeListesi.size())
				 {				
					 	//System.out.println("esleme Listesi size: "+eslemeListesi.size());
					 	Hakem hakem = hakRep.findOne(eslemeListesi.get(l).getHakeminId());
						Kullanici kullanici2 = hakem.getHakemler();
						System.out.println("hakemin hakemdeki id si : "+hakem.getHakemId());
						System.out.println("hakemin kullanici iki nin id si"+kullanici2.getKullanici_id());											
						if(index==-1)
						{
							index =l;
						}
						else if(!kullanici.getKurum().equals(kullanici2.getKurum()))
						{
													
							int id = (int)(eslemeListesi.get(l).getHakeminId());
							 int id2= (int)eslemeListesi.get(index).getHakeminId();
							 int k=0,m=0;
							 while(hakeminAldigiMakaleSayisi[k][1]!=id) 
								 k++; 
							 while(hakeminAldigiMakaleSayisi[m][1]!=id2)
								 m++;
							 System.out.println("");
							 if(hakeminAldigiMakaleSayisi[k][0]<hakeminAldigiMakaleSayisi[m][0])
							 {
								 index =l;
							 }
							/* if(hakeminAldigiMakaleSayisi[k][0]<hakeminAldigiMakaleSayisi[m][0])
							 {
								 min =eslemeListesi.get(l).getYakinlik();
								 index =l;
							 }
							//System.out.println("makalenin kurumu"+kullanici.getKurum());
							//System.out.println("hakemin kurumu"+kullanici2.getKurum());
							//System.out.println("kurumlar farkli girdim");
							 if(eslemeListesi.get(l).getYakinlik()<min)
							 {
								System.out.println("min : "+min);
								System.out.println("kontrol edilen :"+eslemeListesi.get(l).getYakinlik());
								 		System.out.println("esitlik yok");				 								
										min = eslemeListesi.get(l).getYakinlik();
										 index =l;		
										 System.out.println("kücük oldugu icin index :"+index);
										
							 }
							 else if(eslemeListesi.get(l).getYakinlik()==min)
							 {
								 System.out.println("esitlik var");
								 int id = (int)(eslemeListesi.get(l).getHakeminId());
								 int id2= (int)eslemeListesi.get(index).getHakeminId();
								 int k=0,m=0;
								 while(hakeminAldigiMakaleSayisi[k][1]!=id) 
									 k++; 
								 while(hakeminAldigiMakaleSayisi[m][1]!=id2)
									 m++;
								 System.out.println("");
								 if(hakeminAldigiMakaleSayisi[k][0]<hakeminAldigiMakaleSayisi[m][0])
								 {
									 min =eslemeListesi.get(l).getYakinlik();
									 index =l;
								 }
								
							 }*/
						} 
						l++;
				 }		 
				 
			 }
			 if(count ==3)
				 return count;
			 else if(index!=-1)
			 {			 
				 	Esleme esleme = new Esleme();
					esleme.setEslemeMakale(makale);
					System.out.println("makaleyi setledim");
					Hakem hakem = hakRep.findOne(eslemeListesi.get(index).getHakeminId());
					esleme.setEslemeHakem(hakem);
					esRep.save(esleme);
					System.out.println("makaleye hakem atadım");
					int id = (int)hakem.getHakemId();
					int k=0;
					while(hakeminAldigiMakaleSayisi[k][1]!=id)
						k++;
					hakeminAldigiMakaleSayisi[k][0]++;
					eslemeListesi.remove(eslemeListesi.get(index));
					count++;
					l=0; index=-1;
					//System.out.println("id :"+id);
					//hakeminAldigiMakaleSayisi[deger]++;
					//hakeminAldigiMakaleSayisi[id-1]++;
				 //esleme olustur 
				 //eslemeListesi[index]i kaldır
				 // count u arttır  l=0; min =100000; index =-1;
			 }
		 }
		
		
		return count;
	}
	
	
	
	@RequestMapping("/eslemeGoster")
	public String eslemeGoster(@RequestParam(value="id",required=false)String id,ModelMap model)
	{
		Long konferansId= Long.valueOf(id);
		Konferans konferans = konRep.findOne(konferansId);
		List<Hakem> hakemler = hakRep.findByHakemKonferanslar(konferans);
		List<Esleme> eslesenOnaylilar = new ArrayList<>();
		List<Esleme> eslesenOnaysizlar = new ArrayList<>();
		List<Esleme> trueEslemeler = new ArrayList<>();
		for(int i=0;i<hakemler.size();i++)
		{
			List<Esleme> eslemeler = esRep.findByEslemeHakem(hakemler.get(i));
			if(eslemeler.size()!=0)
			{
				for(int j=0;j<eslemeler.size();j++)
				{
					Degerlendirme degerlendirme = eslemeler.get(j).getDegerlendirme();
					if(degerlendirme!=null)
					{
						if(eslemeler.get(j).getEslemeMakale().getMakaleOnay().equals("true"))
						{
							trueEslemeler.add(eslemeler.get(j));
						}
						else
						eslesenOnaylilar.add(eslemeler.get(j));
						
					}
						
					else
						eslesenOnaysizlar.add(eslemeler.get(j));
						
				}
			}
		
		}
		model.put("trueEslemeler", trueEslemeler);
		model.put("eslesenOnaylilar", eslesenOnaylilar);
		model.put("eslesenOnaysizlar",eslesenOnaysizlar);
		
		return "konferansEslemeEkrani";
	}
	
	
}
