import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class ListaProcessos {
    
    private ArrayList<Processo> listaProcessos = new ArrayList<Processo>();
    
    public void addProcesso(Processo p) {
        listaProcessos.add(p);
    }
    
    public Processo get(int indice) {
    	return listaProcessos.get(indice);
    }
    
    public void set(int indice, Processo p) {
    	listaProcessos.set(indice, p);
    }
    
    public void remove(int indice) {
    	listaProcessos.remove(indice);
    }
    
    public void setListaProcesso(ArrayList<Processo> lista) {
		this.listaProcessos = lista;
	}
    
    public ArrayList<Processo> getListaProcesso() {
		return this.listaProcessos;
	}
    
    public void atualizaEspera() {
    	for (int i = 1; i < listaProcessos.size(); i++) {
			Processo p1 = listaProcessos.get(i);
			p1.setTempoEspera(p1.getTempoEspera()+1);
			listaProcessos.set(i, p1);
		}
    }
    
    public void sortByPrioridade() {
        Collections.sort(listaProcessos, new PrioridadeComparator());
    }
    
    public void sortByChegada() {
        Collections.sort(listaProcessos, new ChegadaComparator());
    }
    
    public void sortByHRRN() {
        Collections.sort(listaProcessos, new HRRNComparator());
    }
    
    
    public void imprimir(){
    	System.out.print("Espera = ");
    	for(int i=0; i<listaProcessos.size(); i++){
    		System.out.print(listaProcessos.get(i)+" |");
    	}
    	System.out.println();
    }
    
    public ArrayList<String> escalonarPrioridadePremptiva(){
    	ListaProcessos filaEspera = new ListaProcessos();
    	ArrayList<Processo> resultado = new ArrayList<Processo>();
    	ArrayList<String> execucao = new ArrayList<String>();   		
		   	
    	if(listaProcessos.size() > 0){    		
    		sortByChegada();//Ordena a chegada de processos
    		for (int tempoAtual = 0; listaProcessos.size()>0 || filaEspera.getListaProcesso().size() > 0; tempoAtual++) {//Percorre um a 1 até a última chegada
    			while(listaProcessos.size() > 0 && listaProcessos.get(0).getChegada() == tempoAtual){
    				filaEspera.addProcesso(listaProcessos.get(0));
    				listaProcessos.remove(0);
    			}
    			if(filaEspera.getListaProcesso().size() > 0){
        			filaEspera.sortByPrioridade();
    				Processo processoExecucao = filaEspera.get(0);
        			processoExecucao.setBurst(processoExecucao.getBurst()-1);
        			filaEspera.atualizaEspera();
        			execucao.add(processoExecucao.getNome());
        			if(processoExecucao.getBurst() <= 0){
        				resultado.add(processoExecucao);
        				filaEspera.remove(0);
        			}
    			}else{
    				execucao.add("Ocioso");
    			}
    			
    		}	
    	}
    	setListaProcesso(resultado);
    	return execucao; 
    }
    
    public ArrayList<String> escalonarHighestResponseRatioNext(){
    	ListaProcessos filaEspera = new ListaProcessos();//fila de processos em espera a cada rodada
    	ArrayList<Processo> resultado = new ArrayList<Processo>();// resultado de tempo de espera
    	ArrayList<String> execucao = new ArrayList<String>();//ordem de execução (Por nome)
		boolean finalizado = true;
    	if(listaProcessos.size() > 0){    		
    		sortByChegada();//Ordena a chegada de processos
    		for (int tempoAtual = 0; listaProcessos.size()>0 || filaEspera.getListaProcesso().size() > 0; tempoAtual++) {//Percorre um a 1 até a última chegada
    			while(listaProcessos.size() > 0 && listaProcessos.get(0).getChegada() == tempoAtual){
    				filaEspera.addProcesso(listaProcessos.get(0));
    				listaProcessos.remove(0);
    			}
    			if(filaEspera.getListaProcesso().size() > 0){
    				if(finalizado){//verifica se processo foi finalizado, evita preempção
        				filaEspera.sortByHRRN();
        				finalizado = false;
    				}
    				Processo processoExecucao = filaEspera.get(0);
        			processoExecucao.setBurst(processoExecucao.getBurst()-1);
        			filaEspera.atualizaEspera();
        			execucao.add(processoExecucao.getNome());
        			if(processoExecucao.getBurst() <= 0){//quando tempo chega a zero, remove da fila e sinaliza fim de processo
        				resultado.add(processoExecucao);
        				filaEspera.remove(0);
        				finalizado = true;
        			}
    			}else{
    				execucao.add("Ocioso");
    			}
    			
    		}	
    	}
    	setListaProcesso(resultado);
    	return execucao; 
    }
}

// Menor numero é maior prioridade 0 = Alta / 5 = Baixa
class PrioridadeComparator implements Comparator<Processo> {
    public int compare(Processo p1, Processo p2) {
        return p1.getPrioridade()- p2.getPrioridade();
    }
}

//Menor numero é maior prioridade
class ChegadaComparator implements Comparator<Processo> {
    public int compare(Processo p1, Processo p2) {
        return p1.getChegada()-p2.getChegada();
    }
}

//Maio número é maior prioridade
class HRRNComparator implements Comparator<Processo> {
    public int compare(Processo p1, Processo p2) {
        return p2.getResponseRatio()-p1.getResponseRatio();
    }
}