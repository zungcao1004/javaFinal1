package Interface;

import java.rmi.Remote;

import javax.swing.table.DefaultTableModel;

public interface IProductDAO extends Remote{
	public String insert(String name, double price) throws Exception;
	public String delete(int id) throws Exception; 
	public String update(int id, String name, double price) throws Exception;
	public DefaultTableModel showAll() throws Exception;
	public String readXML() throws Exception;
	public String writeXML() throws Exception;
}
