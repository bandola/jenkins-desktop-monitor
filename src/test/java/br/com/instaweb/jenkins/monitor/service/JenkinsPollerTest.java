package br.com.instaweb.jenkins.monitor.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.instaweb.jenkins.monitor.tasks.Task;

public class JenkinsPollerTest {

	private JenkinsPoller poller;
	private StubTask pollTask;
	private boolean executed;
	
	@Before
	public void setUp(){
		pollTask = new StubTask();
		poller = new JenkinsPoller(pollTask, 100);
	}
	
	@Test
	public void canPollJenkins() throws Exception {
		poller.run();
		Thread.sleep(300);
		assertThat("Task should have been executed", executed, is(true));
	}
	
	private class StubTask implements Task{
		@Override
		public void run() {
			executed = true;
		}
	}
	

}
