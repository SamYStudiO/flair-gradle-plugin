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
		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).getPath( ) )
			into project.getRootDir( )

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		structureFactories.each {
			it.create( project , project.file( "scaffold" ) )
		}

		project.file( "scaffold" ).deleteDir( )
	}

	protected abstract void addStructures()
}
