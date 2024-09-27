package desafio.urban_potato.domain.voto;

public enum Escolha {
	
	SIM("Sim"), NAO("Não");
	
	private final String descricao;
	
	private Escolha(String descricao) {
		this.descricao = descricao;
	}
	
	public static Escolha from(String s) {
		if ("S".equalsIgnoreCase(s)
				|| "sim".equalsIgnoreCase(s)) {
			return SIM;
		}
		
		return NAO;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}

}
