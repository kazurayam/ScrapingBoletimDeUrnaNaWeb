package my.common

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils

public class CustomFileUtils {

	private CustomFileUtils() {}

	/**
	 * create a new empty directory,
	 * or create a direcgtory after deleting it recursively if already exists
	 *
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	static void initializeDir(File dir) throws IOException {
		if (dir.exists()) {
			FileUtils.deleteDirectory(dir)
		}
		dir.mkdirs()
	}

	static void writeTextIntoFile(String text, File outfile) throws IOException {
		OutputStream os = new FileOutputStream(outfile)
		Writer wr = new OutputStreamWriter(os, StandardCharsets.UTF_8.name())
		wr.write(text)
		wr.flush()
		os.close()
	}
}
