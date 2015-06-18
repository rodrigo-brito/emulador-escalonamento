public class Processo {
    private String nome;
    private int burst;
    private int chegada;
    private int prioridade;
    private int tempoEspera;

    public Processo(String nome, int burst, int chegada, int prioridade) {
        this.nome = nome;
        this.burst = burst;
        this.chegada = chegada;
        this.prioridade = prioridade;
        tempoEspera = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getBurst() {
        return burst;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    public int getChegada() {
        return chegada;
    }

    public void setChegada(int chegada) {
        this.chegada = chegada;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }
    
    public int getResponseRatio(){
    	return ( getTempoEspera() + getBurst() ) / getBurst();
    }
    
    public int getTurnaround(){
    	return getTempoEspera()+getBurst();
    }
    
    public String toString(){
    	return getNome()+" - "+getTempoEspera();
    }
}
