package model.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.regex.Regex;

public class FileUtil {

	/**
	 * gets the path of a given file path. e.g. the/path/to/file will be
	 * the/path/to
	 * 
	 * @param file
	 *            given file path
	 * 
	 * @return the path of the file
	 */
	public static String getFilePath(String file) {
		String sep = file.contains("/") ? "/" : Regex.replaceRegexChars("\\");
		String[] tmp = file.split(sep);

		if (tmp.length == 1)
			return null;

		String result = "";
		for (int i = 0; i < tmp.length - 1; i++)
			result += tmp[i] + Regex.removeRegexChars(sep);

		return result.substring(0, result.length() - 1);
	}
	
	/**
	 * gets the filename of the given path, e.g. path/filename.txt ->
	 * filename.txt
	 * 
	 * @param file
	 *            given file path
	 * 
	 * @return the filename with extension
	 */
	public static String getFileName(String file) {
		if (file.contains("\\"))
			file = file.replace("/", "\\");
		String sep = file.contains("/") ? "/" : Regex.replaceRegexChars("\\");

		String[] tmp = file.split(sep);

		return tmp[tmp.length - 1].trim();
	}

	/**
	 * gets the file extension of the given path. If no extension is given null
	 * will be returned. E.g. path/test.txt will return txt
	 * 
	 * @param file
	 *            given file path
	 * 
	 * @return the extension or null if no extension is there
	 */
	public static String getFileExtension(String file) {
		String filename = getFileName(file);
		if (!file.contains("."))
			return null;

		String[] tmp = filename.split("\\.");
		return tmp[tmp.length - 1].trim();

	}

	/**
	 * writes a file in bytes[] to the given path
	 * 
	 * @param bytes
	 *            given bytes
	 * @param path
	 *            given path of the file
	 * 
	 * @return true if the file could be written, else false
	 * 
	 * @throws IOException
	 *             thrown if the file could not be created
	 */
	public static boolean writeByteToFile(byte[] bytes, String path) throws IOException {
		// create an OutputStream
		FileOutputStream outStream;
		if (bytes.length <= 0)
			return false;

		// write Img into Path
		outStream = new FileOutputStream(new File(path));
		outStream.write(bytes, 0, bytes.length);
		outStream.close();

		return true;
	}

	/**
	 * copies the given file to the given destination
	 * 
	 * @param src
	 *            given source path
	 * @param dst
	 *            given destination path
	 * 
	 * @return true of copied correctly, else false
	 * 
	 * @throws IOException
	 */
	public static boolean copyFile(String src, String dst) throws IOException {
		return writeByteToFile(getFileInByte(src), dst);
	}

	/**
	 * reads a file in bytes[]
	 * 
	 * @param path
	 *            path to the file
	 * 
	 * @return the given file in bytes[]
	 * 
	 * @throws IOException
	 *             thrown if the file couldn't be read
	 */
	public static byte[] getFileInByte(String path) throws IOException {
		// create File
		File file = new File(path);

		// create FileInputStream
		FileInputStream fileInputStream = new FileInputStream(file);
		// create byte Array for Input Data
		byte[] data = new byte[(int) file.length()];
		// read File and close Stream
		fileInputStream.read(data);
		fileInputStream.close();

		return data;
	}

	/**
	 * deletes all ', : and . and replaces a - with a space
	 * 
	 * @param path
	 *            given path to modify
	 * 
	 * @return modified path
	 */
	public static String formatFilePath(String path) {

		path = path.replace("'", "");
		path = path.replace(":", "");
		path = path.replace(".", "");
		path = path.replace(" ", "-");

		return path;
	}

	/**
	 * reads a file from the jar
	 * 
	 * @param path
	 *            path to the file inside the jar of the form path/to/file
	 * 
	 * @return the file content as a string
	 * 
	 * @throws IOException
	 *             thrown if file couldn't be found
	 */
	public static String readFileFromJar(String path) throws IOException {
		InputStream stream;
		stream = FileUtil.class.getResourceAsStream("/" + path);
		// if this fails try again using relativ paths and ClassLoader
		if (stream == null) {
			stream = ClassLoader.getSystemResourceAsStream(path);
		}

		if (stream == null)
			throw new IOException("File not found.");

		BufferedReader br = new BufferedReader(new InputStreamReader(stream));

		StringBuffer sb = new StringBuffer();
		String line;

		while ((line = br.readLine()) != null)
			sb.append(line).append("\n");
		br.close();

		return sb.toString();
	}
}