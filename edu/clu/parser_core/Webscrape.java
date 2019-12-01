package edu.clu.parser_core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author Yu-Chun,Chou
 * 
 * This program will use the input stock name to query the price and show in the screen in the real time,
 * Since the program will use some libraries,so it is packaged as a executable jar file.
 * 
 * example:
 * 1. the user click parser-core-0.0.1-SNAPSHOT.jar 
 * 2. the system will show a frame screen
 * 3. the user input the stock name (ex:AAPL)
 * 4. the user click the button 
 * 5. the system will show the price of the stock name in the screen
 * 6. In the 0.0.1-snapshot version,there is no stock name check control, it means that, if the use input a
 * name and there is no correspondent information, the system won't show anything
 */
public class Webscrape {

	public static void main(final String[] args) throws IOException, SAXException {

		Webscrape htmlParse = new Webscrape();
		MainFrame ex = htmlParse.new MainFrame();

	}

	public class MainFrame {

		public MainFrame() {
			initUI();
		}

		//initialize the frame and UI screen
		public final void initUI() {
			JFrame f = new JFrame("Button Example");
			JButton button = new JButton("Query price");
			final JTextField field1 = new JTextField();
			final JTextField field2 = new JTextField();
			field1.setBounds(50, 60, 100, 30);
			field2.setBounds(50, 90, 100, 30);
			button.setBounds(180, 60, 100, 30);
			f.getContentPane().setLayout(null);
			f.getContentPane().add(field1);
			f.getContentPane().add(field2);
			f.getContentPane().add(button);

			/**
			 * add event Listener in this button, 
			 * when click the button, it will catch 
			 * the query criteria,
			 */
			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					String input = field1.getText();
					try {
						field2.setText(getResult(input));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					}

				}
			});
			f.setVisible(true);
			f.setSize(300, 200);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		}

		/**
		 * 
		 * @param company name
		 * @return result price
		 * @throws IOException
		 * @throws SAXException
		 */
		public String getResult(String companyName) throws IOException, SAXException {
			
			//using the company name to get the dedicated HTML page
			Response resp = Jsoup.connect("https://finance.yahoo.com/quote/" + companyName + "/").execute();
			Document doc = resp.parse();
			
			//The HTML file is in a W3C document format,and the wanted information will be embedded in javascript
			Elements content = doc.getElementsByTag("script");

			Iterator iter = content.iterator();
			String result = "";
			while (iter.hasNext()) {
				Element e = (Element) iter.next();
				//The javascript will use json data structure to transmit information,the price is in root.App.main
				String index = "root.App.main = ";
				if (e.data().contains(index)) {
					int start = e.data().indexOf(index) + index.length();					
					String json = e.data().substring(start);
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(json);
					//find the node in JSON tree format with the attribute currentPrice
					JsonNode node = rootNode.findValue("currentPrice");
					System.out.println(node.get("raw"));
					//get the raw value
					result = node.get("raw").toString();
				}

			}
			return result;
		}
	}

}
