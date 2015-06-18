public class SimuladorEscalonamento {
    public static void main(String[] args) {
    	ListaProcessos listaProcessos = new ListaProcessos();
    	listaProcessos.addProcesso(new Processo("P1", 3, 1, 5));
    	listaProcessos.addProcesso(new Processo("P2", 3, 2, 1));
    	listaProcessos.addProcesso(new Processo("P3", 6, 3, 2));
    	listaProcessos.addProcesso(new Processo("P4", 3, 4, 0));
    	listaProcessos.addProcesso(new Processo("P5", 3, 16, 5));
    	//System.out.println(listaProcessos.escalonarPrioridadePremptiva());
    	System.out.println("Execucao = "+listaProcessos.escalonarHighestResponseRatioNext()); //Vetor com ordem de execução
    	listaProcessos.imprimir();//lista dos processos por ordem de finalização com tempo de espera e turnarround
    }
    
}
