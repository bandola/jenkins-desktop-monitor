package br.com.instaweb.jenkins.monitor.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimerTask;

import org.junit.Before;
import org.junit.Test;

public class JenkinsPollerTest {

	private JenkinsPoller poller;
	private StubTask pollTask;
	private boolean executed;
	
	@Before
	public void setUp(){
		pollTask = new StubTask();
		poller = new JenkinsPoller(pollTask);
	}
	
	@Test
	public void canPollJenkins() throws Exception {
		poller.run();
		Thread.sleep(300);
		assertThat("Task should have been executed", executed, is(true));
	}
	
	private class StubTask extends TimerTask{
		@Override
		public void run() {
			executed = true;
		}
	}
}
