package remake.sistema;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import remake.excecao.DataInvalidaException;

public class Verificador {

	private static final SimpleDateFormat FOMATO_DATA = new SimpleDateFormat("dd/MM/yyy");

	public static boolean verificaStringValida(String str) {
		if (str == null)
			return false;
		return !str.equals("");
	}
	
	public static Calendar convertStringEmData(String dataAntes)
            throws DataInvalidaException {
        Calendar calendarioHoje = Calendar.getInstance();
        Calendar calendarioRetorno = Calendar.getInstance();

        try {
            calendarioRetorno.setTime(FOMATO_DATA.parse(dataAntes));
        } catch (Exception e) {
            throw new DataInvalidaException("Data de Criação inválida");
        }

        String dataDepois = "";
        dataDepois = FOMATO_DATA.format(calendarioRetorno.getTime());

        if (!dataAntes.equals(dataDepois)
                || calendarioHoje.getTimeInMillis() > calendarioRetorno
                        .getTimeInMillis()) {
            throw new DataInvalidaException("Data de Criação inválida");
        }

        return calendarioRetorno;
    }

}