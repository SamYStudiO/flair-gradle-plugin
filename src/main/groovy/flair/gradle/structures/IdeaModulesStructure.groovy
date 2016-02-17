package flair.gradle.structures

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import groovy.xml.XmlUtil
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaModulesStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IExtensionManager ).getFlairProperty( FlairProperty.MODULE_NAME )

		if( !project.rootProject.file( ".idea/modules.xml" ).exists( ) )
		{
			project.copy {
				from "${ source.path }/idea/modules_template.xml"
				into "${ project.rootDir.path }/.idea"

				rename "modules_template.xml" , "modules.xml"
			}

			File file = project.file( "${ project.rootDir.path }/.idea/modules.xml" )

			file.write( file.text.replaceAll( "\\{projectName\\}" , project.name ).replaceAll( "\\{moduleName\\}" , moduleName ) )
		}

		Node node = new XmlParser( ).parse( project.rootProject.file( ".idea/modules.xml" ) )

		if( !node.'**'.module.find { it.@'fileurl'.contains( "${ moduleName }.iml" ) } )
		{
			new Node( ( Node ) node.component.modules.first( ) , "module" , [ 'fileurl': "file://\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" , 'filepath': "\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" ] )

			project.rootProject.file( ".idea/modules.xml" ).withOutputStream { out -> XmlUtil.serialize( node , out ) }
		}
	}
}