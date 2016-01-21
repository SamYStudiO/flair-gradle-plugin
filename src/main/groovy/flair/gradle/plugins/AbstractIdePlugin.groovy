package flair.gradle.plugins

import flair.gradle.dependencies.Sdk
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractIdePlugin extends AbstractPlugin implements IdePlugin
{
	private Sdk sdk

	@Override
	public void apply( Project project )
	{
		super.apply( project )

		sdk = findSdk( )
	}

	@Override
	public final Sdk getSdk()
	{
		return sdk
	}

	protected abstract Sdk findSdk()
}
