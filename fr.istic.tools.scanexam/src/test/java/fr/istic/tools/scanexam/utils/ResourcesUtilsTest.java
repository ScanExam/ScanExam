package fr.istic.tools.scanexam.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResourcesUtilsTest {

	
	private String readInputStream(InputStream stream) {
		return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
	}
	
	@Test
	@DisplayName("getTextResource sur fichiers existants")
	void getTextResourceTest1() {
		final String result = ResourcesUtils.getTextResource("resources_utils/Test1.foo");
		assertEquals("This is\na test", result);
		
		final String result2 = ResourcesUtils.getTextResource("resources_utils/Test2.properties");
		assertEquals("foo\nbar", result2);
	}
	
	@Test
	@DisplayName("getTextResource avec paramètre null")
	void getTextResourceTest2() {
		assertThrows(NullPointerException.class, () -> ResourcesUtils.getTextResource(null));
	}
	
	@Test
	@DisplayName("getTextResource sur fichiers non existants")
	void getTextResourceTest3() {
		assertNull(ResourcesUtils.getTextResource("resources_utils/Test3"));
		assertNull(ResourcesUtils.getTextResource("Test3"));
	}

	
	@Test
	@DisplayName("getInputStreamResource sur fichiers existants")
	void getInputStreamResourceTest1() {
		final InputStream result = ResourcesUtils.getInputStreamResource("resources_utils/Test1.foo");
		assertNotNull(result);
		assertEquals("This is\na test", readInputStream(result));
		
		final InputStream result2 = ResourcesUtils.getInputStreamResource("resources_utils/Test2.properties");
		assertNotNull(result2);
		assertEquals("foo\nbar", readInputStream(result2));
	}
	
	@Test
	@DisplayName("getInputStreamResource avec paramètre null")
	void getInputStreamResourceTest2() {
		assertThrows(NullPointerException.class, () -> ResourcesUtils.getInputStreamResource(null));
	}
	
	@Test
	@DisplayName("getInputStreamResource sur fichiers non existants")
	void getInputStreamResourceTest3() {
		assertNull(ResourcesUtils.getInputStreamResource("resources_utils/Test3"));
		assertNull(ResourcesUtils.getInputStreamResource("Test3"));
	}
	
	
	@Test
	@DisplayName("getFolderContentNames sur dossier existant")
	void getFolderContentNamesTest1() {
		final Collection<String> result = ResourcesUtils.getFolderContentNames("resources_utils/");
		final List<String> expected = Arrays.asList("Test1.foo", "Test2.properties");
		assertNotNull(result);
		assertTrue(expected.containsAll(result) && result.containsAll(expected));
	}
	
	@Test
	@DisplayName("getFolderContentNames avec paramètre null")
	void getFolderContentNamesTest2() {
		assertThrows(NullPointerException.class, () -> ResourcesUtils.getFolderContentNames(null));
	}
	
	@Test
	@DisplayName("getFolderContentNames sur fichiers non existants")
	void getFolderContentNamesTest3() {
		assertNull(ResourcesUtils.getTextResource("resources_utils/foo"));
	}
}
