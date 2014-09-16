package func.pseudo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PsedoRedFile {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PsedoRedFile.class);

	public static List<String[]> readFiles() {
		final List<String[]> result = new ArrayList<String[]>();

		try {
			final List<String> lines = IOUtils.readLines(PsedoRedFile.class
					.getResourceAsStream("person"));

			for (String line : lines) {
				result.add(StringUtils.splitPreserveAllTokens(line, ","));
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return result;
	}
}
