package flair.gradle.plugins

import flair.gradle.structure.IStructure
import flair.gradle.variants.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractStructurePlugin extends AbstractPlugin implements IStructurePlugin
{
	protected Platform platform

	protected List<IStructure> structureFactories = new ArrayList<IStructure>( )

	@Override
	public void apply( Project project )
	{
		super.apply( project )

		addStructures( )

		project.afterEvaluate {
			updateStructures( )
		}
	}

	@Override
	public final void addStructure( IStructure structure )
	{
		structureFactories.add( structure )
	}

	@Override
	public final void updateStructures()
	{
		structureFactories.each {
			it.create( project )
		}
	}

	protected abstract void addStructures()
}
