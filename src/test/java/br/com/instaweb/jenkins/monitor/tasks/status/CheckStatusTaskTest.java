package br.com.instaweb.jenkins.monitor.tasks.status;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.instaweb.jenkins.monitor.bean.BuildInfo;
import br.com.instaweb.jenkins.monitor.bean.BuildState;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;

import com.google.common.eventbus.EventBus;

public class CheckStatusTaskTest {

	private static final int IRRELEVANT = -1;
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
		executeBuilds(new BuildInfo[]{
			success(),
			success(),
			success()
		});
		verify(eventBus, never()).post(Mockito.anyObject());
	}

	@Test
	public void buildStatusHasChangedOnce() throws Exception {
		executeBuilds(new BuildInfo[]{
			success(),
			aborted()
		});
		verify(eventBus, times(1)).post(Mockito.anyObject());
	}
	
	@Test
	public void buildStatusHasChangedOnceAfterAWhile() throws Exception {
		executeBuilds(new BuildInfo[]{
			success(),
			success(),
			success(),
			success(),
			aborted(),
			aborted()
		});
		verify(eventBus, times(1)).post(Mockito.anyObject());
	}

	@Test
	public void buildStatusHasChangedTwiceAfterAWhile() throws Exception {
		executeBuilds(new BuildInfo[] {
			success(),
			success(),
			aborted(),
			success()
		});
		verify(eventBus, times(2)).post(Mockito.anyObject());
	}

	@Test
	public void buildStatusHasChangedFourTimes() throws Exception {
		executeBuilds(new BuildInfo[]{
			success(),
			success(),
			aborted(),
			failure(),
			aborted(),
			success()		
		});
		verify(eventBus, times(4)).post(Mockito.anyObject());
	}
	
	private static BuildInfo success(){
		return new BuildInfo(IRRELEVANT, BuildState.blue);
	}
	
	private static BuildInfo failure(){
		return new BuildInfo(IRRELEVANT, BuildState.red);
	}
	
	private static BuildInfo aborted(){
		return new BuildInfo(IRRELEVANT, BuildState.aborted);
	}
	
	private void executeBuilds(BuildInfo[] builds) {
		when(service.getCurrentBuild()).thenReturn(builds[0], Arrays.copyOfRange(builds, 1, builds.length));
		for(int i = 0; i < builds.length; i++){
			task.run();
		}
		verify(service, times(builds.length)).getCurrentBuild();
	}
	
	
}
