package flair.gradle.tasks.build

import flair.gradle.executable.IExecutable
import flair.gradle.executable.MXMLC
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Group
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

		//this.platform = platform
		//this.productFlavor = productFlavor
		//this.buildType = buildType
		//dependsOn Task.PROCESS_ANDROID_RESOURCES
	}

	@TaskAction
	public void build()
	{
		println( "test> " + platform + "--" + productFlavor + "--" + buildType )

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

			IExecutable exe = new MXMLC( project , platform , productFlavor , buildType )

			project.exec {
				executable exe.getExecutable( )
				args exe.getArguments( )

				standardOutput = output
			}

			println( output.toString( ) )
		}
	}
}
