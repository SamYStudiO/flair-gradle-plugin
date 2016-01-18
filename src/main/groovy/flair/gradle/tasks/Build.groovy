package flair.gradle.tasks

import flair.gradle.executable.IExecutable
import flair.gradle.executable.MXMLC
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Group
import flair.gradle.tasks.TaskManager
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Build extends AbstractVariantTask
{
	public Build()
	{
		group = Group.BUILD.name
		description = ""
	}

	@TaskAction
	public void build()
	{
		boolean hasBuildDependencies = false

		dependsOn.each { dependencyList ->

			println( dependencyList.getClass( ) )

			dependencyList.each { dependency ->

				println( dependency.getClass( ) )
				if( dependency.toString( ).startsWith( "build" ) ) hasBuildDependencies = true
			}
		}

		println( "hasBuildDependencies " + hasBuildDependencies )

		if( !hasBuildDependencies )
		{
			ByteArrayOutputStream output = new ByteArrayOutputStream( )

			/*IExecutable exe = new MXMLC( project , platform , productFlavor , buildType )

			project.exec {
				executable exe.getExecutable( )
				args exe.getArguments( )

				standardOutput = output
			}*/

			println( output.toString( ) )
		}
	}
}
