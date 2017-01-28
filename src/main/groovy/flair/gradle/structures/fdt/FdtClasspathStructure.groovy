package flair.gradle.structures.fdt

import flair.gradle.dependencies.Config
import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.plugins.PluginManager
import flair.gradle.structures.IStructure
import flair.gradle.utils.Platform
import groovy.xml.XmlUtil
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class FdtClasspathStructure implements IStructure
{
	private Project project

	private String moduleName

	@Override
	void create( Project project , File source )
	{
		this.project = project

		IExtensionManager extensionManager = project.flair as IExtensionManager
		moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )

		File output = project.file( ".settings/com.powerflasher.fdt.classpath" )
		File file = PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? project.file( "${ source.path }/fdt/classpath_template_all.xml" ) : project.file( "${ source.path }/fdt/classpath_template_mobile.xml" )
		Node xml = new XmlParser( ).parse( file )
		List<String> list = new ArrayList<String>( )

		project.configurations.findAll { it.name.toLowerCase( ).contains( Config.SOURCE.name ) || it.name.contains( Config.AS_LIBRARY.name ) }.each {

			it.files.each {

				if( it.exists( ) )
				{
					if( !list.contains( it.path ) )
					{
						new Node( xml , "AS3Classpath" , [ generateProblems: "true" , sdkBased: "false" , type: "source" , useAsSharedCode: "false" ] , buildPathFromModule( it.path ) )
						list.add( it.path )
					}
				}
			}
		}

		project.configurations.findAll {
			it.name.toLowerCase( ).contains( Config.LIBRARY.name ) && !it.name.contains( Config.AS_LIBRARY.name )
		}.each {

			it.files.each {

				if( it.exists( ) )
				{
					String path = buildPathFromModule( it.isDirectory( ) ? it.path : it.parentFile.path )

					if( !xml.children( ).find { it.name( ) == "AS3LibraryFolder" && it.text( ) == path } )
					{
						new Node( xml , "AS3LibraryFolder" , path )
					}
				}
			}
		}

		output.withOutputStream { writer -> XmlUtil.serialize( xml , writer ) }
	}

	private String buildPathFromModule( String path )
	{
		path = path.replaceAll( "\\\\" , "/" )
		String modulePath = project.file( moduleName ).path.replaceAll( "\\\\" , "/" )

		if( path.startsWith( modulePath ) )
		{
			return path.replace( modulePath , "app" )
		}
		else
		{
			int count = 0

			while( !path.startsWith( modulePath ) && modulePath.indexOf( "/" ) > 0 )
			{
				List<String> a = modulePath.split( "/" ).toList( )

				a.pop( )

				modulePath = a.join( "/" )

				count++
			}

			if( path.startsWith( modulePath ) )
			{
				String up = ""

				for( int i = 0; i < count; i++ ) up += "../"

				return up + path.replace( modulePath + "/" , "app" )
			}
		}

		return path.replaceAll( "\\\\" , "/" )
	}
}
