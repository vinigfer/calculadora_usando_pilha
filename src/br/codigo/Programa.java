package br.codigo;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Programa {

	public static void main(String[] args) {
		Leitor leitor = null;
		Escritor escritor = null;
		Calculadora calc = new Calculadora();
		Pilha pilhaAux = new Pilha();
		
		//le expressoes, interpreta e guarda os resultados em um arquivo
		try {
			leitor = new Leitor("expressoes.txt");
			escritor = new Escritor("resultados.txt");
			String linhaAtual = leitor.leProximaLinha();
			while(linhaAtual != null) {
				Pilha pilha = new Pilha();
				boolean erro = false;
				escritor.escreveProximaLinha("Expressao: " + linhaAtual);
				String[] termos = linhaAtual.split(" ");
				for(String proximoTermo : termos) {
					if(proximoTermo.equals(")")) {
						try {
							while(!pilha.top().equals("("))
								pilhaAux.push(pilha.pop());
							//elimina o "abre parenteses" excedente
							pilha.pop();
						
							while(pilhaAux.size() > 2) {
								calc.setOperador1(Double.parseDouble(pilhaAux.pop()));
								calc.setOperando(pilhaAux.pop().charAt(0));
								calc.setOperador2(Double.parseDouble(pilhaAux.pop()));
								
								pilhaAux.push(String.format("%f", calc.calcula()).replace(',','.'));
							}
						
							pilha.push(pilhaAux.pop());
						}
						catch (Exception e) {
							erro = true;
							escritor.escreveProximaLinha("Erro de sintaxe");
							escritor.escreveProximaLinha("Tamanho maximo da pilha: " + pilha.getTamanhoMaximoAtingido());
							escritor.escreveProximaLinha("");
							break;
						}
						
					}
					else
						pilha.push(proximoTermo);
				}
				
				if (!erro) {
					escritor.escreveProximaLinha("Resultado: " + pilha.pop());
					escritor.escreveProximaLinha("Tamanho maximo da pilha: " + pilha.getTamanhoMaximoAtingido());
					escritor.escreveProximaLinha("");
				}
				linhaAtual = leitor.leProximaLinha();
				pilha.clear();
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println("Arquivo nao encontrado.");
		}
		catch (IOException e) {
			System.out.println("Erro na leitura do arquivo");
		}
		finally {
			try {
				leitor.fechaArquivo();
				escritor.fechaArquivo();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Imprime no console os resultados obtidos
		try {
			leitor = new Leitor("resultados.txt");
			String linhaAtual = leitor.leProximaLinha();
			while(linhaAtual != null) {
				System.out.println(linhaAtual);
				linhaAtual = leitor.leProximaLinha();	
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				leitor.fechaArquivo();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
