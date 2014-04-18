package de.ahaus.dennis.javautils.impl.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Dennis Ahaus
 * 
 */
public class XmlUtil {

	TransformerFactory factory = TransformerFactory.newInstance();

	/**
	 * @param document
	 * @param omitXmlDesclaration
	 * @return
	 * @throws TransformerException
	 */
	public String transform(Document document, boolean omitXmlDesclaration)
			throws TransformerException {

		Transformer transformer = createNewTRansformerInstance();
		if (!omitXmlDesclaration) {
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
		}

		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(document);
		transformer.transform(source, result);
		String xmlString = sw.toString();

		return xmlString;
	}

	/**
	 * @return
	 */
	public TransformerFactory getFactory() {
		return factory;
	}

	/**
	 * @return
	 * @throws TransformerConfigurationException
	 */
	protected Transformer createNewTRansformerInstance()
			throws TransformerConfigurationException {
		Transformer transformer = getFactory().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		return transformer;

	}

	/**
	 * @param document
	 * @return
	 * @throws TransformerException
	 */
	public String transform(Document document) throws TransformerException {
		return transform(document, false);
	}

	/**
	 * @param source
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document transform(InputSource source) throws TransformerException,
			SAXException, IOException, ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(source);
	}

	/**
	 * @param documentStr
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document transform(String documentStr) throws TransformerException,
			SAXException, IOException, ParserConfigurationException {
		InputSource input = new InputSource(new StringReader(documentStr));
		return transform(input);
	}

	/**
	 * @param stream
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document transform(InputStream stream) throws TransformerException,
			SAXException, IOException, ParserConfigurationException {
		InputSource input = new InputSource(stream);
		return transform(input);
	}

	/**
	 * @param file
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document transform(File file) throws TransformerException,
			SAXException, IOException, ParserConfigurationException {
		InputSource input = new InputSource(new FileInputStream(file));
		return transform(input);
	}
}
