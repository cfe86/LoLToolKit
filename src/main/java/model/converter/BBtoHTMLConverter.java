package model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import config.Config;
import model.converter.interfaces.IBBtoHTMLConverter;
import model.exception.ConverterException;
import model.regex.Regex;
import model.util.FileUtil;

public class BBtoHTMLConverter implements IBBtoHTMLConverter {

	/**
	 * the BB Text
	 */
	private String bbTxt;

	/**
	 * the HTML Text
	 */
	private String htmlTxt;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.converter.interfaces.IBBtoHTMLConverter#getHTMLCode()
	 */
	@Override
	public String getHTMLCode() {
		return this.htmlTxt;
	}

	/**
	 * Constructor
	 */
	public BBtoHTMLConverter() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * model.converter.interfaces.IBBtoHTMLConverter#convert(java.lang.String)
	 */
	@Override
	public void convert(String txt) throws ConverterException {
		this.bbTxt = txt;
		this.htmlTxt = "";

		// replace -> with &#8594;
		htmlTxt = bbTxt.replace("->", "&#8594;");

		// replace < and >
		htmlTxt = htmlTxt.replace("<", "§AND§lt;");
		htmlTxt = htmlTxt.replace(">", "§AND§gt;");

		// replace components
		htmlTxt = parseBoldTags(htmlTxt);
		htmlTxt = parseItalicTags(htmlTxt);
		htmlTxt = parseUnderlineTags(htmlTxt);
		htmlTxt = parseItemTags(htmlTxt);
		htmlTxt = parseListTag(htmlTxt);

		// make html breaks
		htmlTxt = formatHTML(htmlTxt);

		// complete < und >
		htmlTxt = htmlTxt.replaceAll("§AND§", "&");
	}

	/**
	 * formats the given string in paragraphs
	 * 
	 * @param str
	 *            given string
	 * 
	 * @return modified string
	 */
	private String formatHTML(String str) {
		str = str.trim();

		String result = "<p>" + str;
		result = result.replace("\n\n", "</p>");
		result = result.replace("\n", "<br/>\n");
		result = result.replace("</p>", "</p>\n\n<p>");
		result += "</p>";

		return result;
	}

	/**
	 * parses all [B][/B] tags and converts them to HTML
	 * 
	 * @param txt
	 *            given text
	 * 
	 * @return modified text
	 */
	private String parseBoldTags(String txt) {

		Regex regex = new Regex("(?i)\\[b\\](.*?)\\[/b\\]", Pattern.DOTALL);
		List<String> found = regex.find(txt);

		for (String f : found) {
			regex.matches(f);
			String bold = regex.getGroup(1);
			txt = txt.replace(f, "<b>" + bold + "</b>");
		}

		return txt;
	}

	/**
	 * parses all [I][/I] tags and converts them to HTML
	 * 
	 * @param txt
	 *            given text
	 * 
	 * @return modified text
	 */
	private String parseItalicTags(String txt) {

		Regex regex = new Regex("(?i)\\[i\\](.*?)\\[/i\\]", Pattern.DOTALL);
		List<String> found = regex.find(txt);

		for (String f : found) {
			regex.matches(f);
			String italic = regex.getGroup(1);
			txt = txt.replace(f, "<i>" + italic + "</i>");
		}

		return txt;
	}

	/**
	 * parses all [U][/U] tags and converts them to HTML
	 * 
	 * @param txt
	 *            given text
	 * 
	 * @return modified text
	 */
	private String parseUnderlineTags(String txt) {

		Regex regex = new Regex("(?i)\\[u\\](.*?)\\[/u\\]", Pattern.DOTALL);
		List<String> found = regex.find(txt);

		for (String f : found) {
			regex.matches(f);
			String underline = regex.getGroup(1);
			txt = txt.replace(f, "<u>" + underline + "</u>");
		}

		return txt;
	}

	/**
	 * parses all [ITEM][/ITEM] tags and converts them to HTML
	 * 
	 * @param txt
	 *            given text
	 * 
	 * @return modified text
	 */
	private String parseItemTags(String txt) {
		Regex regex = new Regex("(?i)\\[item\\](.*?)\\[/item\\]", Pattern.DOTALL);
		List<String> found = regex.find(txt);

		for (String f : found) {
			regex.matches(f);
			String item = regex.getGroup(1);
			String path = Config.getInstance().getItemFolder() + FileUtil.formatFilePath(item) + ".png";
			txt = txt.replace(f, "<img src=\"file:///" + path + "\" width=\"" + Config.getInstance().getEditorImageLength() + "\" height=\""
					+ Config.getInstance().getEditorImageLength() + "\">");
		}

		return txt;
	}

	/**
	 * parses all [LIST=0/1]...[/LIST] tags and converts them to HTML
	 * 
	 * @param txt
	 *            given text
	 * 
	 * @return modified text
	 */
	private String parseListTag(String txt) {

		Regex regex = new Regex("(?i)\\[list=(\\d)\\](.*?)\\[/list\\]", Pattern.DOTALL);
		List<String> found = regex.find(txt);
		for (String f : found) {
			regex.matches(f);
			int type = Integer.parseInt(regex.getGroup(1));
			String lst = regex.getGroup(2);
			List<String> lines = getLines(lst);

			String newLst = "";
			for (String line : lines)
				newLst += "<li>" + line + "</li>";

			if (type == 0)
				newLst = "<ol>" + newLst + "</ol>";
			else
				newLst = "<ul>" + newLst + "</ul>";

			txt = txt.replace(f, newLst);
		}

		return txt;
	}

	/**
	 * gets the lines of the given BB code list
	 * 
	 * @param lst
	 *            given BB Code list
	 * 
	 * @return a list with all list entries
	 */
	private List<String> getLines(String lst) {
		List<String> result = new ArrayList<>();
		String[] tmp = lst.split("\\[\\*\\]");

		for (int i = 1; i < tmp.length; i++)
			result.add(tmp[i].trim());

		return result;
	}
}