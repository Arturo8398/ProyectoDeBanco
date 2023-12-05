package procedimientos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DAOExcel implements DAO<Cliente> {

	@Override
	public int create(Cliente cliente) throws SQLException {
		String filePath = "nuevo_archivo.xlsx";

		Workbook workbook;
		File file = new File(filePath);

		if (file.exists()) {
			try (FileInputStream fis = new FileInputStream(file)) {
				workbook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			workbook = new XSSFWorkbook();
		}

		Sheet sheet = workbook.getSheet("Hoja1");
		if (sheet == null) {
			sheet = workbook.createSheet("Hoja1");
		}

		Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
		newRow.createCell(0).setCellValue(Integer.parseInt(cliente.getId()));
		newRow.createCell(1).setCellValue(cliente.getNombre());
		newRow.createCell(2).setCellValue(cliente.getApellidos());
		newRow.createCell(3).setCellValue(cliente.getCorreo());
		newRow.createCell(4).setCellValue(Integer.parseInt(cliente.getTelefono()));
		newRow.createCell(5).setCellValue(cliente.getTipoCuenta());
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			workbook.write(fos);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	@Override
	public Cliente read(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Cliente cliente) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String clienteId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Cliente> readAll() throws SQLException {
		try {
			String filePath = "nuevo_archivo.xlsx";
			FileInputStream fis = new FileInputStream(new File(filePath));
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);

			Generic<Cliente> agregarClienteExcel = new Generic<>();
			for (Row row : sheet) {
				Cliente cliente = new Cliente();
				int cellIndex = 0;
				for (Cell cell : row) {
					switch (cellIndex) {
					case 0:
						cliente.setId(String.valueOf(cell.getNumericCellValue()));
						break;
					case 1:
						cliente.setNombre(cell.getStringCellValue());
						break;
					case 2:
						cliente.setApellidos(cell.getStringCellValue());
						break;
					case 3:
						cliente.setCorreo(cell.getStringCellValue());
						break;
					case 4:
						cliente.setTelefono(String.valueOf(cell.getNumericCellValue()));
						break;
					case 5:
						cliente.setTipoCuenta(cell.getStringCellValue());
						break;
					}
					cellIndex++;
				}
				agregarClienteExcel.addElement(cliente);
			}
			ArrayList<Cliente> lista = agregarClienteExcel.obtenerArrayList();
			for (Cliente x : lista) {
				System.out.println(x.toString());
			}
			System.out.println();

			// Cerrar el FileInputStream y liberar recursos
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
