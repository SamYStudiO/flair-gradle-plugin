package flair.gradle.plugins

import flair.gradle.dependencies.Sdk
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractIdePlugin extends AbstractPlugin implements IdePlugin
{
	private List<Sdk> sdks

	@Override
	public void apply( Project project )
	{
		super.apply( project )

		sdks = findSdks( )
	}

	protected abstract List<Sdk> findSdks()
}
