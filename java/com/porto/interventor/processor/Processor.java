package com.porto.interventor.processor;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.porto.interventor.data.Conexao;
import com.porto.interventor.data.Statment;

public class Processor {

	private final Conexao con;
	private final static Logger Logger =  LoggerFactory.getLogger(Processor.class);

	public Processor() throws Exception {
		try {
			this.con = new Conexao("PRD");
//			this.con = new Conexao("HML");
		} 
		catch (Exception e) {
			Logger.error("Error connecting to bank:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	public void executionreprocessing() throws Exception, SQLException {

		try {
			Logger.info("===== START reprocessing ======");

			String MAINVARIABLE = JOptionPane.showInputDialog(null, "Enter the complete MAINVARIABLE number for reprocessing", con.getAmbiente() + " - Reprocessing de PDF", JOptionPane.PLAIN_MESSAGE);

			insertMAINVARIABLEReprocessing(MAINVARIABLE); 
			updateRequest   (MAINVARIABLE);

			Logger.info("======= END reprocessing =======");
		}
		catch (Exception e) {
			Logger.error("Error: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			con.closeConnection();
		}

	}

	private boolean isMAINVARIABLEValida(String str) {
		return str.matches("[x-x]*");
	}

	private void insertMAINVARIABLEReprocessing(String MAINVARIABLE) throws Exception, SQLException {

		Logger.info("starting to insert MAINVARIABLE for reprocessing...");
		if (!thereIsRecord(MAINVARIABLE) && existsMAINVARIABLE(MAINVARIABLE)) {
			Logger.info("entering MAIN VARIABLE ["+MAINVARIABLE+"] for reprocessing...");
			String insertIntegracao = "insert into genericname " +
					 " select a.genericname, a.genericname,'genericname', a.genericname, a.genericname, a.genericname, 'genericname', 'genericname', genericname " + 
					 " from genericname a, genericname b, genericname c " + 
					 " where a.genericname = b.genericname and a.genericname = b.genericname and a.genericname  = " + MAINVARIABLE + 
					 " and b.genericname is not null and a.genericname = c.genericname and a.genericname = c.genericname";
			Statment.run(insertIntegracao);

			JOptionPane.showMessageDialog(null, "MAINVARIABLE " + MAINVARIABLE + " manually entered for reprocessing", null, JOptionPane.INFORMATION_MESSAGE);
			Logger.info("MAINVARIABLE inserted!!!");
		}
		Logger.info("End of MAINVARIABLE insertion for reprocessing!\n");
	}

	private void updateRequest(String MAINVARIABLE) throws Exception, SQLException {

		Logger.info("starting update.");
		// checks if the record exists in the table
		if (findRequest(MAINVARIABLE)) {
			Statment.runUpdate("update genericname.genericname set genericname = genericname, genericname = genericname where genericname like '%" + MAINVARIABLE + "%'");
			JOptionPane.showMessageDialog(null, "MAINVARIABLE " + MAINVARIABLE + " atualizada na tabela");
		}
		else
			Logger.info("Record ["+MAINVARIABLE+"] not found for reprocessing, so no update!");

		Logger.info("END update !\n");

	}

	private boolean thereIsRecord(String MAINVARIABLE) throws Exception {

		Logger.info("Seeking out MAINVARIABLE " + MAINVARIABLE + "...");
		ResultSet rs = null;
		if (MAINVARIABLE == null || MAINVARIABLE.isEmpty() || isMAINVARIABLEValida(MAINVARIABLE) != true) {
			JOptionPane.showMessageDialog(null, "MAINVARIABLE not informed. Finishing process.", "MAINVARIABLE", JOptionPane.INFORMATION_MESSAGE);
			closeProgram();
		}

		String verificarMAINVARIABLE = 
				"select 1 \r\n" + 
				"from \r\n" + 
				"    genericname a, \r\n" + 
				"    genericname b, \r\n" + 
				"    genericname c,\r\n" + 
				"    genericname d\r\n" + 
				"where \r\n" + 
				"    a.genericname = b.genericname and \r\n" + 
				"    a.genericname = b.genericname and\r\n" + 
				"    a.genericname = c.genericname and \r\n" +
				"    a.genericname = c.genericname and \r\n" + 
				"    a.genericname = d.genericname and\r\n" + 
				"    a.genericname  = '" + MAINVARIABLE + "' and genericname = 'genericname' and \r\n" + 
				"    b.genericname is not null";
		
		
		rs = Statment.returnResultSet(verificarMAINVARIABLE);

		if (rs.next()) {
			Logger.info("MAINVARIABLE found!");
			JOptionPane.showMessageDialog(null, "MAINVARIABLE is already registered in the table de genericname", "Tabela genericname", JOptionPane.INFORMATION_MESSAGE);
			rs.close();
			return true;
		}
		rs.close();
		Logger.info("MAINVARIABLE not found in table!");
		return false;
	}
	
	private boolean existsMAINVARIABLE(String MAINVARIABLE) throws Exception, SQLException {
		ResultSet rs;
		Logger.info("Checking if MAINVARIABLE exists in MAINVARIABLE table.");

		String buscaMAINVARIABLE = 
		"select \n" + 
		"    a.genericname \n" +
		"from \n" +
		"    genericname a, genericname b, genericname c \n" +  
		"where \n" +
		"    a.genericname = b.genericname and \n" + 
		"    a.genericname = b.genericname and \n" + 
		"    a.genericname  = " + MAINVARIABLE + " and \n" + 
		"    b.genericname is not null and \n" +
		"    a.genericname = c.genericname and \n" +
		"    a.genericname = c.genericname";

		rs = Statment.returnResultSet(buscaMAINVARIABLE);

		if (!rs.next()) {
			Logger.info("MAINVARIABLE not found in MAINVARIABLE table!");
			JOptionPane.showMessageDialog(null, 
					"MAINVARIABLE is not registered\n"+
					"Check the MAINVARIABLE ["+MAINVARIABLE+"] to include in the table.\n"+
					"Ending the program.", "MAINVARIABLE", JOptionPane.ERROR_MESSAGE);
			rs.close();
			closeProgram();
		}
		Logger.info("MAINVARIABLE found in the MAINVARIABLE table!");

		rs.close();
		return true;
	}

	private boolean findRequest(String MAINVARIABLE) throws Exception {
		ResultSet rs = null;
		if (MAINVARIABLE == null || MAINVARIABLE.isEmpty() || isMAINVARIABLEValida(MAINVARIABLE) != true) {
			JOptionPane.showMessageDialog(null, "MAINVARIABLE not informed. Finalizing process.", "genericname", JOptionPane.INFORMATION_MESSAGE);
			closeProgram();
		}

		rs = Statment.returnResultSet("select 1 from genericname.genericname where genericname like '%" + MAINVARIABLE + "%'");

		if (!rs.next()) {
			JOptionPane.showMessageDialog(null, "The file ["+MAINVARIABLE+"] is not in the print request table.\nThe record will not be updated", "genericname", JOptionPane.WARNING_MESSAGE);
			rs.close();
			closeProgram();	
		}
		rs.close();

		return true;
	}
	
	
	private void closeProgram() throws Exception {
		Logger.info("Finishing reprocessing");
		con.closeConnection();
		System.exit(-1);
	}


}
