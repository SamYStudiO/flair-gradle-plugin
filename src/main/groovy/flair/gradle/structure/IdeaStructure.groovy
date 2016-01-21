package flair.gradle.structure

import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.Properties
import groovy.xml.XmlUtil
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IExtensionManager ).getFlairProperty( Properties.MODULE_NAME.name )

		if( !project.file( "${ moduleName }/${ moduleName }.iml" ).exists( ) )
		{
			project.copy {
				from "${ source.path }/scaffold.iml"
				into moduleName

				rename "scaffold.iml" , "${ moduleName }.iml"
			}
		}

		if( !project.rootProject.file( ".idea/libraries/libs_as" ).exists( ) )
		{
			project.copy {
				from "${ source.path }/idea/libraries"
				into "${ project.rootProject.rootDir }/.idea/libraries"
			}
		}

		project.rootProject.fileTree( ".idea/libraries" ).each { file ->

			if( file.name.indexOf( "libs_" ) == 0 ) file.write( file.getText( ).replace( '${moduleName}' , moduleName ) )
		}

		if( !project.rootProject.file( ".idea/modules.xml" ).exists( ) ) return

		Node node = new XmlParser( ).parse( project.rootProject.file( ".idea/modules.xml" ) )

		if( !node.'**'.module.find { it.@'fileurl'.contains( "${ moduleName }.iml" ) } )
		{
			new Node( ( Node ) node.component.modules.first( ) , "module" , [ 'fileurl': "file://\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" , 'filepath': "\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" ] )

			project.rootProject.file( ".idea/modules.xml" ).withWriter { out -> XmlUtil.serialize( node , out ) }
		}
	}
}