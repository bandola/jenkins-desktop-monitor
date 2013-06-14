package br.com.instaweb.jenkins.monitor.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ResourcesTest {

	private Resources resources;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	@Before
	public void setUp(){
		resources = new Resources();
	}
	
	@Test
	public void canLoadTxtResource() throws Exception {
		String text = resources.text("/fixtures/test.txt");
		String expected = String.format("lorem ipsum%1$sdolor sit%1$samet", LINE_SEPARATOR);
		assertThat("Resources is able to load and read a txt file", text, equalTo(expected));
	}

	@Test(expected=IllegalArgumentException.class)
	public void nullFileNameShouldThrowException() throws Exception {
		String text = resources.text(null);
		assertThat("Resources is able to load and read a txt file", text, equalTo("lorem ipsum"));
	}

}
