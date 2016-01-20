package flair.gradle.ide

import flair.gradle.extensions.IPlatformExtensionManager
import flair.gradle.structure.IStructure
import flair.gradle.structure.IdeaStructure
import groovy.xml.XmlUtil
import org.apache.tools.ant.taskdefs.condition.Os
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

		if( !project.rootProject.file( ".idea/modules.xml" ).exists( ) ) return
		Node node = new XmlParser( ).parse( project.rootProject.file( ".idea/modules.xml" ) )

		if( !node.'**'.module.find { it.@'fileurl'.contains( "${ moduleName }.iml" ) } )
		{
			new Node( ( Node ) node.component.modules.first( ) , "module" , [ 'fileurl': "file://\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" , 'filepath': "\$PROJECT_DIR\$/${ moduleName }/${ moduleName }.iml" ] )

			project.rootProject.file( ".idea/modules.xml" ).withWriter { out -> XmlUtil.serialize( node , out ) }
		}
	}

	public class SdkInfos implements ISdkInfos
	{

		private String name

		private String path

		public SdkInfos( String name , String path )
		{
			this.name = name
			this.path = path
		}

		public String getName()
		{
			return name
		}

		public String getPath()
		{
			return path
		}
	}

	public ISdkInfos getSdkInfos()
	{
		String path = ""

		switch( true )
		{
			case Os.isFamily( Os.FAMILY_WINDOWS ):
				path = "${ System.getProperty( "user.home" ) }/"
				break;

			case Os.isFamily( Os.FAMILY_UNIX ):
				path = "${ System.getProperty( "user.home" ) }/"
				break;

			case Os.isFamily( Os.FAMILY_MAC ):
				path = "${ System.getProperty( "user.home" ) }/"
				break;
		}

		if( !path ) return new SdkInfos( null , null )

		File sdks = project.file( path )
		List<String> all = new ArrayList<String>( )


		sdks.listFiles( new FilenameFilter( ) {
			@Override
			boolean accept( File dir , String name )
			{
				return name.contains( "IntelliJIdea" )
			}
		} ).each { file ->

			all.add( internalGetSdkPath( file ) )
		}

		int index = 0
		int currentIndex = 0
		int currentVersion = 0

		all.each {

			int version = Integer.parseInt( it.split( "|" )[ 2 ] )

			if( version > currentVersion )
			{
				index = currentIndex
				currentVersion = version
			}

			currentIndex++
		}

		if( all.size( ) )
		{

			String[] a = all[ index ].split( "|" )
			if( a.size( ) ) return new SdkInfos( a[ 0 ] , a[ 1 ] )
		}

		return new SdkInfos( null , null )
	}

	private String internalGetSdkPath( File file )
	{
		Node node = new XmlParser( ).parse( file )
		NodeList list = node.'**'.jdk.( type.@'value'.contains == "Flex SDK" )

		int index = 0
		int currentIndex = 0
		int currentVersion = 0

		list.each {

			int version = Integer.parseInt( ( String ) it.version.replace( "AIR SDK " , "" ) )

			if( version > currentVersion )
			{
				index = currentIndex
				currentVersion = version
			}

			currentIndex++
		}

		return list[ index ].children( ).find { it.name( ) == "name" }.@'value' + "|" + list[ index ].homePath.@'value' + "|" + version
	}
}
