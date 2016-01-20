package flair.gradle.tasks

import flair.gradle.dependencies.Sdk
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PublishAtlases extends AbstractVariantTask
{
	public PublishAtlases()
	{
		group = Groups.TEXTURE_PACKER.name
		description = ""
	}

	@TaskAction
	public void publishAtlases()
	{
		String moduleName = extensionManager.getFlairProperty( "moduleName" )
		boolean generateATFTexturesFromAtlases = extensionManager.getFlairProperty( "texturepacker" , "generateATFTexturesFromAtlases" )

		List<String> list = new ArrayList<String>( )

		list.add( "main" )
		list.add( variant.platform.name )

		variant.productFlavors.each {
			list.add( it )
		}

		list.add( variant.buildType )

		String toATF = ""

		list.each {

			FileTree tree = project.fileTree( "${ moduleName }/src/${ it }/" )

			tree.each { file ->

				if( file.getName( ).toLowerCase( ).indexOf( ".tps" ) >= 0 )
				{
					ByteArrayOutputStream output = new ByteArrayOutputStream( )

					project.exec {
						executable Os.isFamily( Os.FAMILY_WINDOWS ) ? "texturepacker" : "/usr/local/bin/texturepacker"
						args file.getPath( )
						ignoreExitValue = true

						standardOutput = output
					}

					if( output.toString( ).indexOf( "Output files are newer than the input files, nothing to do" ) < 0 ) toATF += file.getName( ).toLowerCase( ).split( "\\." )[ 0 ]
				}
			}

			if( generateATFTexturesFromAtlases )
			{
				String png2atf = "${ new Sdk( project ).path }/atftools/png2atf"

				tree = project.fileTree( "${ moduleName }/src/${ it }/resources" ) {
					include "drawable*/**/*.png"
				}

				tree.each { file ->

					if( toATF.indexOf( file.getName( ).toLowerCase( ).replaceAll( "[0-9]+\\." , "\\." ).split( "\\." )[ 0 ] ) >= 0 )
					{
						String input = file.getAbsolutePath( )
						String output = input.replaceAll( "\\.png" , "\\.atf" )

						project.exec {
							commandLine "${ png2atf }" , "-c" , "e2" , "-n" , "0,0" , "-r" , "-i" , input , "-o" , output
							ignoreExitValue = true
						}
					}
				}
			}
		}
	}
}
