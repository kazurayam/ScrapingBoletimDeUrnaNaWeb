package my.common

import java.nio.charset.StandardCharsets;

public class FileAppender {

	private File output
	private PrintWriter pw

	FileAppender(File f) {
		this.output = f
		this.pw = new PrintWriter(
				new BufferedWriter(
				new OutputStreamWriter(
				new FileOutputStream(output),
				StandardCharsets.UTF_8.name()
				)
				)
				)
	}

	Appendable append(CharSequence csq) throws IOException {
		pw.println(csq)
		pw.flush()
	}

	void close() {
		pw.flush()
		pw.close()
	}
}
