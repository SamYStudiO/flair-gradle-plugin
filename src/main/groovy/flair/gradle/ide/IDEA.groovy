package flair.gradle.ide

import flair.gradle.extensions.IPlatformExtensionManager
import flair.gradle.structure.IStructure
import flair.gradle.structure.IdeaStructure
import groovy.xml.XmlUtil
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Idea implements Ide
{
	private Project project

	private IStructure structure = new IdeaStructure( )

	public IStructure getStructure()
	{
		return structure
	}

	public Idea( Project project )
	{
		this.project = project
	}

	public boolean getIsActive()
	{
		return project.rootProject.rootDir.listFiles( new FilenameFilter( ) {
			@Override
			boolean accept( File dir , String name )
			{
				return name.endsWith( ".iml" )
			}
		} ).size( ) > 0
	}

	public void refresh()
	{
		String moduleName = ( project.flair as IPlatformExtensionManager ).getFlairProperty( "moduleName" )

		if( !project.rootProject.file( ".idea/modules.xml" ).exists(  ) ) return
		Node node = new XmlParser( ).parse( project.rootProject.file( ".idea/modules.xml" ) )

		if( !node.'**'.module.find { it.@'fileurl'.contains( "${ moduleName }.iml" ) } )
		{
			new Node( ( Node ) node.component.modules.first( ) , "module" , [ 'fileurl': "file://\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" , 'filepath': "\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" ] )

			project.rootProject.file( ".idea/modules.xml" ).withWriter { out -> XmlUtil.serialize( node , out ) }
		}
	}
}
