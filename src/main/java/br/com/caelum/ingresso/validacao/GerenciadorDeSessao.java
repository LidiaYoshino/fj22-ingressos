package br.com.caelum.ingresso.validacao;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoes;
	
	public GerenciadorDeSessao(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}
	
	public boolean cabe(final Sessao sessaoAtual) {
		Optional<Boolean> optionalCabe = sessoes.stream()
											.map(sessaoExistente -> horarioValido(sessaoExistente, sessaoAtual))
											.reduce(Boolean::logicalAnd);
		return optionalCabe.orElse(true);
	}
	
	public boolean horarioValido(Sessao existente, Sessao atual) {
		LocalTime horarioSessao = existente.getHorario();
		LocalTime horarioAtual = atual.getHorario();
		
		boolean antes = horarioAtual.isBefore(horarioSessao);
		
		if(antes) {
			return atual.getHorarioTermino().isBefore(horarioSessao);
		} else {
			return existente.getHorarioTermino().isBefore(horarioAtual);
		}
	}
}
