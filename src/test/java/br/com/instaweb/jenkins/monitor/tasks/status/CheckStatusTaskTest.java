package br.com.instaweb.jenkins.monitor.tasks.status;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.instaweb.jenkins.monitor.bean.BuildState;
import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;
import br.com.instaweb.jenkins.monitor.events.BuildStateChangedEvent;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;

import com.google.common.eventbus.EventBus;

public class CheckStatusTaskTest {

	private CheckStatusTask task;
	private EventBus eventBus;
	private JenkinsService service;

	@Before
	public void setUp() {
		eventBus = mock(EventBus.class);
		service = mock(JenkinsService.class);
		task = new CheckStatusTask(eventBus, service);
	}

	@Test
	public void buildStatusHasNotChanged() throws Exception {
		executeBuilds(new JenkinsJob[]{
			success(),
			success()
		});
		verify(eventBus, times(1)).post(aBuildStateEvent());
	}

	@Test
	public void buildStatusHasChangedOnce() throws Exception {
		executeBuilds(new JenkinsJob[]{
			success(),
			aborted()
		});
		verify(eventBus, times(2)).post(aBuildStateEvent());
	}
	
	@Test
	public void buildStatusHasChangedOnceAfterAWhile() throws Exception {
		executeBuilds(new JenkinsJob[]{
			success(),
			success(),
			success(),
			success(),
			aborted(),
			aborted()
		});
		verify(eventBus, times(2)).post(aBuildStateEvent());
	}

	@Test
	public void buildStatusHasChangedTwiceAfterAWhile() throws Exception {
		executeBuilds(new JenkinsJob[] {
			success(),
			success(),
			aborted(),
			success()
		});
		verify(eventBus, times(3)).post(aBuildStateEvent());
	}

	@Test
	public void buildStatusHasChangedFourTimes() throws Exception {
		executeBuilds(new JenkinsJob[]{
			success(),
			success(),
			aborted(),
			failure(),
			aborted(),
			success()		
		});
		verify(eventBus, times(5)).post(aBuildStateEvent());
	}
	
	private static JenkinsJob success(){
		return new JenkinsJob(BuildState.blue);
	}
	
	private static JenkinsJob failure(){
		return new JenkinsJob(BuildState.red);
	}
	
	private static JenkinsJob aborted(){
		return new JenkinsJob(BuildState.aborted);
	}
	
	private void executeBuilds(JenkinsJob[] builds) {
		when(service.getCurrentBuild()).thenReturn(builds[0], Arrays.copyOfRange(builds, 1, builds.length));
		for(int i = 0; i < builds.length; i++){
			task.run();
		}
		verify(service, times(builds.length)).getCurrentBuild();
	}
	
	private static Object aBuildStateEvent(){
		return Mockito.argThat(instanceOf(BuildStateChangedEvent.class));
	}
	
	
}
