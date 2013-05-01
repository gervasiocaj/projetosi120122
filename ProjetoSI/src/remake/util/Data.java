package remake.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import remake.excecao.AnoInvalidoException;
import remake.excecao.DiaInvalidoException;
import remake.excecao.MesInvalidoException;

public class Data implements Comparable<Data>, Serializable {

	private static final long serialVersionUID = 7406540674063929783L;
	private Calendar cal;

	/**
	 * Construtor a partir de um horario e uma data
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @param hora
	 * @param minuto
	 * @param segundo
	 * @throws DiaInvalidoException
	 * @throws MesInvalidoException
	 * @throws AnoInvalidoException
	 */
	public Data(int dia, int mes, int ano, int hora, int minuto, int segundo)
			throws DiaInvalidoException, MesInvalidoException,
			AnoInvalidoException {
		if (dia < 0 || (mes30(mes) && dia > 30) || (!mes30(mes) && dia > 31)
				|| (mes == 2 && ehBissexto(ano) && dia > 29)
				|| (mes == 2 && !ehBissexto(ano) && dia > 28))
			throw new DiaInvalidoException("Data de Criação inválida");

		if (mes < 0 || mes > 12)
			throw new MesInvalidoException("Data de Criação inválida");

		Calendar teste = new GregorianCalendar();

		if (teste.get(Calendar.YEAR) > ano)
			throw new AnoInvalidoException("Data de Criação inválida");
		else if ((teste.get(Calendar.YEAR) == ano)
				&& (teste.get(Calendar.MONTH) > mes))
			throw new MesInvalidoException("Data de Criação inválida");
		else if ((teste.get(Calendar.YEAR) == ano)
				&& (teste.get(Calendar.MONTH) == mes)
				&& (teste.get(Calendar.DAY_OF_MONTH) > dia))
			throw new DiaInvalidoException("Data de Criação inválida");
		this.cal = new GregorianCalendar(ano, mes - 1, dia, hora, minuto,
				segundo);

	}

	/**
	 * Construtor a partir da hora e data atual
	 */
	public Data() {
		this.cal = new GregorianCalendar();
	}

	/**
	 * Construtor a partir de uma data
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @throws DiaInvalidoException
	 * @throws MesInvalidoException
	 */
	public Data(int dia, int mes, int ano) throws DiaInvalidoException,
			MesInvalidoException, AnoInvalidoException {
		if (dia < 0 || (mes30(mes) && dia > 30) || (!mes30(mes) && dia > 31)
				|| (mes == 2 && ehBissexto(ano) && dia > 29)
				|| (mes == 2 && !ehBissexto(ano) && dia > 28))
			throw new DiaInvalidoException("Data de Criação inválida");

		if (mes < 0 || mes > 12)
			throw new MesInvalidoException("Data de Criação inválida");

		Calendar teste = new GregorianCalendar();

		if (teste.get(Calendar.YEAR) > ano)
			throw new AnoInvalidoException("Data de Criação inválida");
		else if ((teste.get(Calendar.YEAR) == ano)
				&& (teste.get(Calendar.MONTH) > mes))
			throw new MesInvalidoException("Data de Criação inválida");
		else if ((teste.get(Calendar.YEAR) == ano)
				&& (teste.get(Calendar.MONTH) == mes)
				&& (teste.get(Calendar.DAY_OF_MONTH) > dia))
			throw new DiaInvalidoException("Data de Criação inválida");

		this.cal = new GregorianCalendar(ano, mes - 1, dia);
	}

	/**
	 * Retorna o horario formatado a seguir: <Hora>:<Minuto>:<Segundo>
	 * 
	 * @return Horario Formatado
	 */
	public String getHorarioToString() {
		return String.format("%02d:%02d:%02d", getHora(), getMinuto(),
				getSegundo());
	}

	/**
	 * Retorna a data formatada a seguir: dd/mm/aaaa<
	 * 
	 * @return
	 */
	public String getDataToString() {

		return String.format("%02d/%02d/%04d", getDia(), getMes(), getAno());
	}

	/**
	 * Retorna o segundo OurTime
	 * 
	 * @return Segundo
	 */
	public int getSegundo() {
		return cal.get(Calendar.SECOND);
	}

	/**
	 * Retorna o minuto OurTime
	 * 
	 * @return Minuto
	 */
	public int getMinuto() {
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * Retorna a hora OurTime
	 * 
	 * @return Hora
	 */
	public int getHora() {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Retorna o ano OurTime
	 * 
	 * @return Ano
	 */
	public int getAno() {
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Retorna o mes OurTime
	 * 
	 * @return Mes
	 */
	public int getMes() {
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * Retorna o dia OurTime
	 * 
	 * @return Dia
	 */
	public int getDia() {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public int compareTo(Data o) {
		return cal.compareTo(o.cal);
	}

	protected long getDiffEmMillis(Data o) {
		return cal.getTimeInMillis() - o.cal.getTimeInMillis();
	}

	private boolean mes30(int mes) {
		return mes == 4 || mes == 6 || mes == 9 || mes == 11;
	}

	public static boolean ehBissexto(int ano) {
		return (ano % 4) == 0;
	}
}