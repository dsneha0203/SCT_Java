
package com.simpsoft.salesCommission.app.XMLReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.simpsoft.salesCommission.app.api.RuleAssignmentAPI;
import com.simpsoft.salesCommission.app.model.Frequency;

@Component
public class ReadXMLForFrequency {
	public static void main(String argv[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		RuleAssignmentAPI rAssAPI = (RuleAssignmentAPI) context.getBean("ruleAssignmentApi");

		ReadXMLForFrequency rdx = new ReadXMLForFrequency();
		List<Frequency> freqList = rdx.parseXML();
		for (Iterator iterator = freqList.iterator(); iterator.hasNext();) {

			Frequency frequency = (Frequency) iterator.next();
			rAssAPI.createFrequency(frequency);
		}
	}

	public List<Frequency> parseXML() {
		List<Frequency> frequencies = new ArrayList<Frequency>();
		try {

			File fXmlFile = new File("WebContent/WEB-INF/resources/XMLFile/frequency.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Frequency");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;

					String frequencyName = node.getAttributes().getNamedItem("frequencyName").getNodeValue();

					Frequency frq = new Frequency();
					frq.setFrequencyName(frequencyName);
					frequencies.add(frq);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return frequencies;
	}

}