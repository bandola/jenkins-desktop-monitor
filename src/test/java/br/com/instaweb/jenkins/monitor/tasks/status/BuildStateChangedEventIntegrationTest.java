package br.com.instaweb.jenkins.monitor.tasks.status;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.instaweb.jenkins.monitor.bean.BuildState;
import br.com.instaweb.jenkins.monitor.events.BuildStateChangedEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class BuildStateChangedEventIntegrationTest {

	private EventBus eventBus;
	private BuildStateChangedEvent capturedEvent;
	
	@Before
	public void setUp(){
		eventBus = new EventBus();
	}
	
	@Test
	public void canCaptureEvent() throws Exception {
		eventBus.register(new BuildStateObserver());
		eventBus.post(new BuildStateChangedEvent(BuildState.aborted, BuildState.blue));
		assertThat(capturedEvent.getCurrent(), is(BuildState.aborted));
		assertThat(capturedEvent.getPrevious(), is(BuildState.blue));
	}
	
	class BuildStateObserver{
		@Subscribe 
		 public void buildStatusChanged(BuildStateChangedEvent event) {
			 BuildStateChangedEventIntegrationTest.this.capturedEvent = event;
		 }
	}
}
