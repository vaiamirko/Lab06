package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	List<SimpleCity> sequenzacitta=new ArrayList<SimpleCity>();
	List<SimpleCity> sequenzacitta2=new ArrayList<SimpleCity>();
	double minimocosto=10000000000000.0;
	

	public Model() {

	}

	public String getUmiditaMedia(int mese) {
		MeteoDAO dao=new MeteoDAO();
		List<String> listadati=dao.getUmiditàavg(mese);
		String s=listadati.toString();

		return s;
	}

	public String trovaSequenza(int mese) {
		List<SimpleCity> sequenzacitta=new ArrayList<SimpleCity>();
		List<Citta> listacitta=new ArrayList<Citta>();
		Map<Citta,Integer> mappacitta=new HashMap<Citta,Integer>();
		double minimocosto=10000000000000.0;
		MeteoDAO dao=new MeteoDAO();
		Citta genova=new Citta("Genova",dao.getAllRilevamentiLocalitaMese(mese, "Genova"));
		Citta torino=new Citta("Torino",dao.getAllRilevamentiLocalitaMese(mese, "Torino"));
		Citta milano=new Citta("Milano",dao.getAllRilevamentiLocalitaMese(mese, "Milano"));
		listacitta.add(genova);
		listacitta.add(torino);
		listacitta.add(milano);
		this.ricorsionesequenza(sequenzacitta, 0, listacitta);
		
		

		return sequenzacitta2.toString();
	}
	public void ricorsionesequenza(List<SimpleCity> parziale,int L,List<Citta> listacitta) {
		int j;
		//condizione di terminazione finale... se sono arrivato al quindicesimo giorno
		if(L==15) {
			if(this.controllaParziale(parziale, listacitta)) //controllo che alla fine della sequenza siano presenti tute e 3 le citta
			{ 
				//System.out.println(parziale+"punteggio tutti: "+this.punteggioSoluzione(parziale));
				if(this.punteggioSoluzione(parziale)<minimocosto)
					{minimocosto=this.punteggioSoluzione(parziale);
				System.out.println(parziale.toString()+" "+minimocosto);
					sequenzacitta2=parziale;}
			}
				
			
			return;
		}
		
		//ciclo sulle tre possibilita che ho....devo scegliere una delle tre citta
		for(Citta c : listacitta) {
			//se la citta pi§ un eventuale scelta diveterebbe piu di 6 allora non la scelgo;
			if(c.getCounter()+3>6) {
				continue;
			}
			//faccio un ciclo perchè una volta scelta la citta la metto 3 volte visto che il numero di gironi consecutivi minimi è 3
			int k=L;
			for(j=k;j<3+L;j++) {
				//se l'ultima citta della tripletta è uguale a 
				if(k==0 || !parziale.get(parziale.size()-1).getNome().equals(c.getNome())) {
					//la prima volta devo aggiungere al costo il costo di spostameto
				SimpleCity cittascelta=new SimpleCity(c.getNome(),COST+c.getRilevamenti().get(k).getUmidita());
				parziale.add(cittascelta);
				k++;
				}else {
				SimpleCity cittascelta=new SimpleCity(c.getNome(),c.getRilevamenti().get(j).getUmidita());
				parziale.add(cittascelta);
				k++;
											}
				//aggiunta la nuova citta cambio il valore counter della citta per controllare che non vengano fatti più di 6 giorni in una citta
				c.increaseCounter();
			}
			List<SimpleCity> parziale2=new ArrayList();
			parziale2.addAll(parziale);		
			//scendo nei livelli
			this.ricorsionesequenza(parziale2, k, listacitta);
			
			
			
			//backtracking della lista
			this.removeLast(parziale);
			this.removeLast(parziale);
			this.removeLast(parziale);
			//backtracking del counter dei giorni analizzati
			c.setCounter(c.getCounter()-3);
			
			
			
		}
		
		
		
		
		
	}
	
	
	

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		for(SimpleCity s:soluzioneCandidata)
			score+=s.getCosto();
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale,List<Citta> citta) {
		List<String> cittacontenute=new ArrayList();
		for(SimpleCity c :parziale)
			cittacontenute.add(c.getNome());
		if(cittacontenute.contains(citta.get(0).getNome()) && cittacontenute.contains(citta.get(1).getNome()) && cittacontenute.contains(citta.get(2).getNome()))
			return true;
	
		
		

		return false;
	}
	private void removeLast(List<SimpleCity> parziale) {
		int i;
		int n=parziale.size();
		
		//System.out.print(n);
		parziale.remove(n-1);
	}

}
